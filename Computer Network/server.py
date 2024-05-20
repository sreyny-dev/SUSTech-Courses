import argparse
import socket
import base64
import threading
import time
import uuid
import mimetypes
import os
from status import HTTPStatus

# Authorization credentials
valid_credential = {'client1': '123', 'client2': '123', 'client3': '123'}

session = {}

root = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'data')


def sendError(client_socket, code: HTTPStatus):
    response = f"HTTP/1.1 {code} {code.phrase}\r\n\r\n{code.description}"
    client_socket.sendall(response.encode('utf-8'))


def sendAuthResponse(client_socket):
    response = f"HTTP/1.1 {HTTPStatus.UNAUTHORIZED} {HTTPStatus.UNAUTHORIZED.phrase}\r\n"
    headers = {"WWW-Authenticate": 'Basic realm="Authorization Required"'}
    for header, value in headers.items():
        response += f"{header}: {value}\r\n"
    response += "\r\n"
    print("401 Unauthorized")
    client_socket.sendall(response.encode('utf-8'))


def handleGet(client_socket, request_data, session_id):
    request_line, _ = request_data.split("\r\n", 1)
    method, url, version = request_line.split(" ")
    sustech_http = "0"
    ifChunk = False
    if "?" in url:
        url, query = url.split("?")
        if "=" not in query:
            raise ValueError(f"'=' not in {query}")
        if url == "/upload" or url == "/delete":
            sendError(client_socket, HTTPStatus.METHOD_NOT_ALLOWED)
            return
        key, value = query.split("=")
        if not os.path.exists(root + url):
            sendError(client_socket, HTTPStatus.NOT_FOUND)
            return
        if os.path.isdir(root + url):
            if key != "SUSTech-HTTP" or (value != "0" and value != "1"):
                sendError(client_socket, HTTPStatus.BAD_REQUEST)
                return
            sustech_http = value
        elif os.path.isfile(root + url):
            if key != "chunked" or value != "1":
                pass
            ifChunk = True
    path = root + url
    try:
        if os.path.exists(path):
            if not ifChunk:
                response = f"HTTP/1.1 {HTTPStatus.OK} {HTTPStatus.OK.phrase}\r\n"
                if os.path.isfile(path):
                    sustech_http = "1"
                listing, content_type = generate_directory_listing(path, url, sustech_http)
                # print(content_type, len(listing), os.path.getsize(path))
                headers = {
                    "Content-Type": str(content_type),
                    "Content-Length": str(len(listing)),
                    "Connection": "keep-alive",
                    "Set-Cookie": f"session-id={session_id}",
                }
                for header, value in headers.items():
                    response += f"{header}: {value}\r\n"
                if os.path.isdir(path):
                    response += f"\r\n{listing}"
                    client_socket.sendall(response.encode('utf-8'))
                else:
                    response += f"\r\n"
                    client_socket.sendall(response.encode('utf-8'))
                    client_socket.sendall(listing)
            else:
                with open(path, 'rb') as file:
                    response = f"HTTP/1.1 {HTTPStatus.OK} {HTTPStatus.OK.phrase}\r\n"
                    content_type = get_content_type(path)
                    headers = {
                        "Content-Type": str(content_type),
                        "Transfer-Encoding": "chunked",
                        "Connection": "keep-alive",
                        "Set-Cookie": f"session-id={session_id}",
                    }
                    for header, value in headers.items():
                        response += f"{header}: {value}\r\n"
                    response += "\r\n"
                    client_socket.sendall(response.encode('utf-8'))

                    chunk_size = 1024
                    while True:
                        chunk = file.read(chunk_size)
                        if not chunk:
                            break
                        chunk_size_hex = hex(len(chunk))[2:]
                        client_socket.sendall(f"{chunk_size_hex}\r\n".encode('utf-8'))
                        client_socket.sendall(chunk)
                        client_socket.sendall(b"\r\n")

                    client_socket.sendall(b'0\r\n\r\n')
        else:
            sendError(client_socket, HTTPStatus.NOT_FOUND)
    except Exception as e:
        sendError(client_socket, HTTPStatus.BAD_REQUEST)
        print(e)


def handleHead(client_socket, url, session_id):
    path = root + url
    if os.path.exists(path):
        response = f"HTTP/1.1 {HTTPStatus.OK} {HTTPStatus.OK.phrase}\r\n"
        headers = {
            "Content-Type": str(get_content_type(path)),
            "Content-Length": str(os.path.getsize(path)),
            "Connection": "keep-alive",
            "Set-Cookie": f"session-id={session_id}",
        }
        for header, value in headers.items():
            response += f"{header}: {value}\r\n"
        response += "\r\n"
        client_socket.sendall(response.encode('utf-8'))

    else:
        sendError(client_socket, HTTPStatus.NOT_FOUND)


def handlePost(client_socket, request_data, auth_header, session_id):
    headers = request_data.split('\n')
    method, url, version = headers[0].split()
    user_path = ""
    if "?" in url:
        url, query = url.split("?")
        if str(url).endswith("/"):
            sendError(client_socket, HTTPStatus.METHOD_NOT_ALLOWED)
            return
        elif not (url == "/upload" or url == "/delete"):
            sendError(client_socket, HTTPStatus.BAD_REQUEST)
            return
        if "path=" not in query:
            raise ValueError(f"'=' not in {query}")
        _, user_path = query.split("path=")
    isAuth = False
    auth_type, encoded_info = auth_header.split(' ')[1:]
    decoded_info = base64.b64decode(encoded_info).decode('utf-8')
    username = decoded_info.split(":")[0]
    password = decoded_info.split(":")[1]
    # print(username, password)
    if username in valid_credential:
        isAuth = password == valid_credential[username]
    if user_path != "":
        if username != user_path.split('/')[0]:
            isAuth = False
        if not isAuth:
            sendError(client_socket, HTTPStatus.FORBIDDEN)
            return
        path = root
        if not user_path.startswith("/"):
            path = root + "/"
        path += user_path
    else:
        sendError(client_socket, HTTPStatus.OK)
        return
    try:
        if url == "/upload":
            if os.path.exists(os.path.join(path, os.pardir)):
                response = f"HTTP/1.1 {HTTPStatus.OK} {HTTPStatus.OK.phrase}\r\n"
                content_type_line = [h for h in headers if h.startswith('Content-Type:')]
                content_type, content_boundary = content_type_line[0].split("; boundary=")
                file_line = [h for h in headers if h.startswith('Content-Disposition:')]
                content_title_line = [h for h in file_line[0].split(" ") if h.startswith('filename=')]
                content_title = content_title_line[0].split('"')[1]
                print(os.path.join(path, content_title))
                start = False
                content = []
                print(content_boundary)
                for h in headers:
                    print(start, h)
                    if h.startswith(f"Content-Disposition:"):
                        start = True
                        continue
                    if h.startswith('--') and start:
                        cur = h.split("--")[1]
                        if cur == content_boundary[:-1]:
                            break
                    if start:
                        content += h
                print(content)
                content = content[1:]
                with open(os.path.join(path, content_title), 'w') as file:
                    for d in content:
                        file.write(d)
                response_body = f"File {path} uploaded successfully."
                response += f"Content-Length: {len(response_body)}\r\nSet-Cookie: session-id={session_id}\r\n\r\n{response_body}"
                client_socket.sendall(response.encode('utf-8'))
            else:
                sendError(client_socket, HTTPStatus.NOT_FOUND)
        if url == "/delete":
            if os.path.exists(path):
                try:
                    os.remove(path)
                    response = f"HTTP/1.1 {HTTPStatus.OK} {HTTPStatus.OK.phrase}\r\n"
                    response_body = f"File {path} deleted successfully."
                    response += f"Content-Length: {len(response_body)}\r\nSet-Cookie: session-id={session_id}\r\n\r\n{response_body}"
                    client_socket.sendall(response.encode('utf-8'))
                except Exception as e:
                    sendError(client_socket, HTTPStatus.INTERNAL_SERVER_ERROR)
            else:
                sendError(client_socket, HTTPStatus.NOT_FOUND)


    except Exception as e:
        sendError(client_socket, HTTPStatus.BAD_REQUEST)
        print(e)


def get_content_type(file_path):
    # Get the content type of the file
    if file_path.endswith('/') or file_path.endswith('"'):
        file_path = file_path[:-1]
    content_type, _ = mimetypes.guess_type(file_path)
    print(file_path)
    print(content_type)
    return content_type or 'application/octet-stream'


def generate_directory_listing(directory_path, url, sustech_http):
    listing = ""
    content_type = "text/html"
    if sustech_http == "0":
        # Generate a simple HTML directory listing
        listing = (f"<!DOCTYPE html>\n<html lang='en'>\n<meta charset='UTF-8'>\n"
                   f"<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n"
                   f"<title>File List</title>\n</head>\n<body>\n"
                   f"<h1>Directory Listing for ./data{url}</h1><ul>\n"
                   f'<li><a href="/?SUSTech-HTTP=0">/</a></li>\n'
                   )
        if url != "/":
            listing += f'<li><a href="{os.path.join(url, os.pardir)}/?SUSTech-HTTP=0">../</a></li>\n'
        for item in os.listdir(directory_path):
            item_path = os.path.join(directory_path, item)
            if os.path.exists(item_path):
                if os.path.isdir(item_path):
                    listing += f'<li><a href="{os.path.join(url, item)}/?SUSTech-HTTP=0">{item}/</a></li>\n'
                else:
                    listing += f'<li><a href="{os.path.join(url, item)}/?SUSTech-HTTP=1">{item}</a></li>\n'
        listing += '</ul>\n</body>\n</html>\n'
    elif sustech_http == "1":
        if os.path.isdir(directory_path):
            for item in os.listdir(directory_path):
                item_path = os.path.join(directory_path, item)
                if os.path.exists(item_path):
                    listing += f'"{item}",'
            listing = listing[:-1]
            listing = '[' + listing + ']'
        else:
            content_type = get_content_type(url)
            with open(directory_path, "rb") as file:
                listing = file.read()
    return listing, content_type


# Function to handle client requests
def handleClientConnection(client_socket):
    try:
        while True:
            request_data = client_socket.recv(1024).decode('utf-8')
            print(request_data)
            if not request_data:
                continue
            headers = request_data.split('\n')
            method, path, version = headers[0].split()

            # Check session cookie
            isCookie = False
            print("Check Cookie...")
            cookie = [h for h in headers if h.startswith('Cookie:')]
            session_id = 0
            if cookie:
                # print(cookie)
                try:
                    session_id = cookie[0].split("session-id=")[1]
                    if session_id[:-1] in session:
                        print("Check Time...")
                        s_id, expired = session[session_id[:-1]]
                        isCookie = expired > time.time()
                except Exception as e:
                    print(f"ERROR: {e}")
                    isCookie = False

            if not cookie or not isCookie:
                print("No Cookie, Checking Auth...")
                # Check for authorization header
                auth_header = [h for h in headers if h.startswith('Authorization:')]
                isAuth = False
                username = ""
                if auth_header:
                    auth_type, encoded_info = auth_header[0].split(' ')[1:]
                    decoded_info = base64.b64decode(encoded_info).decode('utf-8')
                    username = decoded_info.split(":")[0]
                    password = decoded_info.split(":")[1]
                    print(username, password)
                    if username in valid_credential:
                        isAuth = password == valid_credential[username]

                if not auth_header or not isAuth:
                    print("No Auth!")
                    sendAuthResponse(client_socket)
                    return

                new_session_id = str(uuid.uuid4())
                new_expired = time.time() + 360000
                session[new_session_id] = (username, new_expired)
                print(f"New Cookie {session[new_session_id]}")
                session_id = new_session_id

            print("Cookie Stay Alive")
            # Check the connection header
            connection_header = [h for h in headers if h.startswith('Connection:')]
            keep_live = ""
            if connection_header:
                keep_live = connection_header[0].split(" ")[1]

            # Construct response based on the method
            if method == 'GET':
                print("handle GET method")
                handleGet(client_socket, request_data, session_id)
            elif method == 'HEAD':
                print("handle HEAD method")
                handleHead(client_socket, path, session_id)
            elif method == 'POST':
                print("handle POST method")
                auth = [h for h in headers if h.startswith('Authorization:')]
                handlePost(client_socket, request_data, auth[0], session_id)

            if keep_live != "Keep-Alive":
                break
    except Exception as e:
        print(f"ERROR: {e}")
    finally:
        client_socket.close()


# Function to start the server
def startServer(ip, port):
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind((ip, port))
    server_socket.listen(5)
    server_socket.settimeout(1)
    print(f'Serving HTTP on {ip} port {port}...')

    # List to keep track of active client threads
    active_threads = []
    print(active_threads)

    while True:
        try:
            client_socket, client_address = server_socket.accept()
            print(f'Accepted connection from {client_address}')

            # Create a new thread for each client
            client_thread = threading.Thread(
                target=handleClientConnection,
                args=(client_socket,)
            )
            client_thread.start()

            # Keep track of active threads
            active_threads.append(client_thread)

            # Clean up inactive threads
            active_threads = [t for t in active_threads if t.is_alive()]
        except socket.timeout:
            pass


# Run the server
if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-i', '--ip', default='localhost', help='Server host address')
    parser.add_argument('-p', '--port', type=int, default=8080, help='Server port number')
    args = parser.parse_args()
    startServer(args.ip, args.port)
