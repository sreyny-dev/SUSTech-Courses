import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
public class DatabaseManipulation implements DataManipulation {
    private Connection con = null;
    private ResultSet resultSet;
    private HashMap<String, Integer> getname = new HashMap<>();
    private HashMap<String, Integer> getrepID = new HashMap<>();

    private String host = "localhost";
    private String dbname = "project1";
    private String user = "postgres";


    private String pwd = "m80761234";
    private String port = "5432";


    @Override
    public void openDatasource() {
        try {
            Class.forName("org.postgresql.Driver");

        } catch (Exception e) {
            System.err.println("Cannot find the PostgreSQL driver. Check CLASSPATH.");
            System.exit(1);
        }

        try {
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
            con = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void closeDatasource() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addUsers() {
        String sql = "insert into users(user_id, user_name) " + "values (?,?)";
        int z = 0;
        HashSet<String> name = new HashSet<>();

        try (FileReader reader = new FileReader("posts.json")) {

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                //String yes="A";String no ="N";
                String authorname = (String) jsonObject.get("Author");
                JSONArray followers = (JSONArray) jsonObject.get("Authors Followed By");
                JSONArray favorites = (JSONArray) jsonObject.get("Authors Who Favorited the Post");
                JSONArray shares = (JSONArray) jsonObject.get("Authors Who Shared the Post");
                JSONArray likes = (JSONArray) jsonObject.get("Authors Who Liked the Post");
                PreparedStatement preparedStatement = con.prepareStatement(sql);

                if (!name.contains(authorname)) {
                    z++;
                    name.add(authorname);
                    getname.put(authorname, z);
                    preparedStatement.setLong(1, z);
                    preparedStatement.setString(2, authorname);
                    //preparedStatement.setString(3, yes);
                    preparedStatement.executeUpdate();
                }
                /*else { //delete previous
                    name.add(authorname);
                    preparedStatement.setLong(1, z);
                    preparedStatement.setString(2, authorname);
                    result = preparedStatement.executeUpdate();
                }*/

                //System.out.println(id + " "+ category);
                for (int i = 0; i < followers.size(); i++) {
                    String text = followers.get(i).toString();
                    if (!name.contains(text)) {
                        z++;
                        name.add(text);
                        getname.put(text, z);
                        System.out.println(z + " " + text);
                        preparedStatement.setLong(1, z);
                        preparedStatement.setString(2, text);
                        //preparedStatement.setString(3, no);
                        preparedStatement.executeUpdate();
                    }
                }
                System.out.println("favorites");
                for (int i = 0; i < favorites.size(); i++) {
                    String text = favorites.get(i).toString();
                    if (!name.contains(text)) {
                        z++;
                        name.add(text);
                        getname.put(text, z);
                        System.out.println(z + " " + text);
                        preparedStatement.setLong(1, z);
                        preparedStatement.setString(2, text);
                        //preparedStatement.setString(3, no);
                        preparedStatement.executeUpdate();
                    }
                }
                System.out.println("share");
                for (int i = 0; i < shares.size(); i++) {
                    String text = shares.get(i).toString();
                    if (!name.contains(text)) {
                        z++;
                        name.add(text);
                        getname.put(text, z);
                        System.out.println(z + " " + text);
                        preparedStatement.setLong(1, z);
                        preparedStatement.setString(2, text);
                        //preparedStatement.setString(3, no);
                        preparedStatement.executeUpdate();
                    }
                }
                for (int i = 0; i < likes.size(); i++) {
                    String text = likes.get(i).toString();
                    if (!name.contains(text)) {
                        z++;
                        name.add(text);
                        getname.put(text, z);
                        System.out.println(z + " " + text);
                        preparedStatement.setLong(1, z);
                        preparedStatement.setString(2, text);
                        //preparedStatement.setString(3, no);
                        preparedStatement.executeUpdate();
                    }
                }
            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
        try (FileReader reader = new FileReader("replies.json")) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                //String yes="A";String no ="N";
                String authorname = (String) jsonObject.get("Reply Author");
                String secrep_authorname = (String) jsonObject.get("Secondary Reply Author");
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                if (!name.contains(authorname)) {
                    z++;
                    name.add(authorname);
                    getname.put(authorname, z);
                    preparedStatement.setLong(1, z);
                    preparedStatement.setString(2, authorname);
                    //preparedStatement.setString(3, yes);
                    preparedStatement.executeUpdate();
                }
                if (!name.contains(secrep_authorname)) {
                    z++;
                    name.add(secrep_authorname);
                    getname.put(secrep_authorname, z);
                    preparedStatement.setLong(1, z);
                    preparedStatement.setString(2, secrep_authorname);
                    //preparedStatement.setString(3, yes);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
        //return result;
    }

    @Override
    public void addAuthors() {
        String sql = "insert into authors(author_id, name, regis_time, phone_num, user_id) " + "values (?,?,?,?,?)";
        try (FileReader reader = new FileReader("posts.json")) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String authorIDString = (String) jsonObject.get("Author's ID");
                String name = (String) jsonObject.get("Author");
                int num = getname.get(name);
                String regisTime = (String) jsonObject.get("Author Registration Time");
                //Timestamp regis_time = Timestamp.valueOf(regisTime);
                //regis_time = Timestamp.valueOf(regisTime);
                //String dateString = regisTime.substring(0,regisTime.length()-2); // replace with your date string

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(regisTime, formatter);
                Timestamp timestamp = Timestamp.valueOf(dateTime);

                //Date regis_date = (Date) jsonObject.get("Author Registration Time");
                String phoneNum = (String) jsonObject.get("Author's Phone");
                Long phone_num = (long) Long.parseLong(phoneNum);
                //System.out.println(id + " "+ category);
                PreparedStatement preparedStatement = con.prepareStatement(sql);

                System.out.println(authorIDString + " " + name + " " + timestamp + " " + phone_num);

                preparedStatement.setString(1, authorIDString);
                preparedStatement.setString(2, name);
                preparedStatement.setTimestamp(3, timestamp);
                //preparedStatement.setDate(4, regis_date);
                preparedStatement.setLong(4, phone_num);
                preparedStatement.setInt(5, num);
                preparedStatement.executeUpdate();
            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPosts() {
        String sql = "insert into posts(post_id , title, content, posting_time, " +
                "posting_city, posting_country, author_id, user_id) " + "values (?,?,?,?,?,?,?,?)";
        try (FileReader reader = new FileReader("posts.json")) {

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                long postid = (long) jsonObject.get("Post ID");
                String title = (String) jsonObject.get("Title");
                String content = (String) jsonObject.get("Content");
                String postingTime = (String) jsonObject.get("Posting Time");
                Timestamp posting_time = Timestamp.valueOf(postingTime);
                //Date posting_date = (Date) jsonObject.get("Posting Time");
                String location = (String) jsonObject.get("Posting City");
                String[] arrOfStr = location.split(", ");
                String city = arrOfStr[0];
                String country = arrOfStr[1];

                String authorIDString = (String) jsonObject.get("Author's ID");
                String name = (String) jsonObject.get("Author");
                int num = getname.get(name);
                PreparedStatement preparedStatement = con.prepareStatement(sql);

                System.out.println(postid + " " + title + " " + content + " " + posting_time + " " + city + " " + country + " " + authorIDString);
                preparedStatement.setLong(1, postid);
                preparedStatement.setString(2, title);
                preparedStatement.setString(3, content);
                preparedStatement.setTimestamp(4, posting_time);
                //preparedStatement.setDate(5, posting_date);
                preparedStatement.setString(5, city);
                preparedStatement.setString(6, country);
                preparedStatement.setString(7, authorIDString);
                preparedStatement.setInt(8, num);

                preparedStatement.executeUpdate();

            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCategories() {
        String sql = "insert into categories(post_id, category) " + "values (?,?)";
        try (FileReader reader = new FileReader("posts.json")) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                long postid = (long) jsonObject.get("Post ID");
                JSONArray category = (JSONArray) jsonObject.get("Category");
                //System.out.println(id + " "+ category);
                PreparedStatement preparedStatement = con.prepareStatement(sql);

                for (int i = 0; i < category.size(); i++) {
                    String text = category.get(i).toString();
                    System.out.println(postid + " " + text);
                    preparedStatement.setLong(1, postid);
                    preparedStatement.setString(2, text);

                    preparedStatement.executeUpdate();
                }
            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFollowers() {
        String sql = "insert into followers(author_id, follower_id) " +
                "values (?,?)";
        try (FileReader reader = new FileReader("posts.json")) {

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String authorIDString = (String) jsonObject.get("Author's ID");
                JSONArray followers = (JSONArray) jsonObject.get("Authors Followed By");

                PreparedStatement preparedStatement = con.prepareStatement(sql);

                for (int i = 0; i < followers.size(); i++) {
                    String text = followers.get(i).toString();
                    int num = getname.get(text);
                    System.out.println(authorIDString + " " + text);
                    preparedStatement.setString(1, authorIDString);
                    preparedStatement.setInt(2, num);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFavourites() {
        String sql = "insert into favorites(post_id, user_id) " +
                "values (?,?)";
        //String[] movieInfo = str.split(";");
        try (FileReader reader = new FileReader("posts.json")) {

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                long postid = (long) jsonObject.get("Post ID");
                //long authorID = Long.parseLong(authorIDString);
                JSONArray favouriters = (JSONArray) jsonObject.get("Authors Who Favorited the Post");

                PreparedStatement preparedStatement = con.prepareStatement(sql);

                for (int i = 0; i < favouriters.size(); i++) {
                    String text = favouriters.get(i).toString();
                    int num = getname.get(text);
                    System.out.println(postid + " " + text);
                    preparedStatement.setLong(1, postid);
                    preparedStatement.setInt(2, num);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addShares() {
        String sql = "insert into shares(post_id, user_id) " +
                "values (?,?)";
        try (FileReader reader = new FileReader("posts.json")) {

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                long postid = (long) jsonObject.get("Post ID");
                JSONArray shares = (JSONArray) jsonObject.get("Authors Who Shared the Post");

                PreparedStatement preparedStatement = con.prepareStatement(sql);

                for (int i = 0; i < shares.size(); i++) {
                    String text = shares.get(i).toString();
                    int num = getname.get(text);
                    System.out.println(postid + " " + text);
                    preparedStatement.setLong(1, postid);
                    preparedStatement.setInt(2, num);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addLikes() {
        String sql = "insert into likes(post_id,user_id) " +
                "values (?,?)";
        try (FileReader reader = new FileReader("posts.json")) {

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                long postid = (long) jsonObject.get("Post ID");
                JSONArray likes = (JSONArray) jsonObject.get("Authors Who Liked the Post");

                PreparedStatement preparedStatement = con.prepareStatement(sql);

                for (int i = 0; i < likes.size(); i++) {
                    String text = likes.get(i).toString();
                    int num = getname.get(text);
                    System.out.println(postid + " " + text);
                    preparedStatement.setLong(1, postid);
                    preparedStatement.setInt(2, num);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addReplies() {
        String sql = "insert into replies(reply_id, post_id, reply_content, reply_starts, reply_userID) " + "values (?,?,?,?,?)";
        try (FileReader reader = new FileReader("replies.json")) {

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            int z = 0;
            HashSet<String> rep = new HashSet<>();

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                long postid = (long) jsonObject.get("Post ID");
                String content = (String) jsonObject.get("Reply Content");
                long star = (long) jsonObject.get("Reply Stars");
                String user = (String) jsonObject.get("Reply Author");
                int num = getname.get(user);
                PreparedStatement preparedStatement = con.prepareStatement(sql);

                System.out.println(postid + " " + content + " " + star + " " + user);
                if (!rep.contains(content)) {
                    z++;
                    rep.add(content);
                    getrepID.put(content, z);
                    preparedStatement.setInt(1, z);
                    preparedStatement.setLong(2, postid);
                    preparedStatement.setString(3, content);
                    preparedStatement.setLong(4, star);
                    //preparedStatement.setDate(4, regis_date);
                    preparedStatement.setInt(5, num);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSecReplies() {
        String sql = "insert into secondary_replies(sec_reply_id, reply_id, sec_reply_content, sec_reply_starts, sec_reply_userID) " + "values (?,?,?,?,?)";
        try (FileReader reader = new FileReader("replies.json")) {

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            int z = 0;

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String repcontent = (String) jsonObject.get("Reply Content");
                int replyid = getrepID.get(repcontent);
                String content = (String) jsonObject.get("Secondary Reply Content");
                long star = (long) jsonObject.get("Secondary Reply Stars");
                String user = (String) jsonObject.get("Secondary Reply Author");
                int num = getname.get(user);
                PreparedStatement preparedStatement = con.prepareStatement(sql);

                System.out.println(replyid + " " + content + " " + star + " " + user);
                z++;
                preparedStatement.setInt(1, z);
                preparedStatement.setInt(2, replyid);
                preparedStatement.setString(3, content);
                preparedStatement.setLong(4, star);
                //preparedStatement.setDate(4, regis_date);
                preparedStatement.setInt(5, num);
                preparedStatement.executeUpdate();
            }
        } catch (IOException | ParseException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void clearDataInTable() {
        Statement stmt0;
        if (con != null) {
            try {
                stmt0 = con.createStatement();
                con.commit();
                stmt0.executeUpdate(
                        "drop table followers;\n" +
                        "drop table favorites;\n" +
                        "drop table shares;\n" +
                        "drop table likes;\n" +
                        "drop table categories;\n" +
                        "drop table secondary_replies;\n" +
                        "drop table replies;\n" +
                        "drop table posts;\n" +
                        "drop table authors;\n" +
                        "drop table users;\n" );
                con.commit();
                stmt0.executeUpdate(
                        "create table if not exists users(\n" +
                        " user_id serial not null constraint user_pkey primary key,\n" +
                        " user_name varchar not null unique\n" +
                        " --,isauthor varchar(1) not null\n" +
                        ");\n" +
                        "\n" +
                        "create table if not exists authors(\n" +
                        " author_id varchar(18) not null constraint author_pkey primary key,\n" +
                        " name varchar not null unique,\n" +
                        " regis_time timestamp,\n" +
                        " phone_num bigint unique, --unique(?)\n" +
                        " user_id integer unique,\n" +
                        " constraint fk_user foreign key (user_id) references users(user_id)\n" +
                        ");\n" +
                        "create table if not exists posts(\n" +
                        " post_id integer not null constraint post_pkey primary key,\n" +
                        " title varchar not null,\n" +
                        " --category varchar(20) not null,\n" +
                        " content varchar unique,\n" +
                        " posting_time timestamp,\n" +
                        " --posting_date date,\n" +
                        " posting_city varchar,\n" +
                        " posting_country varchar,\n" +
                        " author_id varchar(18) not null,\n" +
                        " user_id integer unique,\n" +
                        " constraint fk_author foreign key (author_id) references authors(author_id),\n" +
                        " constraint fk_user foreign key (user_id) references users(user_id)\n" +
                        ");\n" +
                        "create table if not exists categories(\n" +
                        " post_id integer not null,\n" +
                        " category varchar not null, --unique constraint category_pkey primary key\n" +
                        " constraint fk_post foreign key (post_id) references posts(post_id)\n" +
                        ");\n" +
                        "\n" +
                        "create table if not exists followers(\n" +
                        " --follow_id serial not null constraint follow_pkey primary key,\n" +
                        " author_id varchar(18) not null,\n" +
                        " follower_id integer not null,\n" +
                        " constraint fk_author foreign key (author_id) references authors(author_id),\n" +
                        " constraint fk_authorfoll foreign key (follower_id) references users(user_id)\n" +
                        ");\n" +
                        "create table if not exists favorites (\n" +
                        " --favorite_id serial not null constraint favorite_pkey primary key,\n" +
                        " post_id integer not null,\n" +
                        " user_id integer not null,\n" +
                        " constraint fk_post foreign key (post_id) references posts(post_id),\n" +
                        " constraint fk_author foreign key (user_id) references users(user_id)\n" +
                        ");\n" +
                        "create table if not exists shares (\n" +
                        " --share_id serial not null constraint share_pkey primary key,\n" +
                        " post_id integer not null,\n" +
                        " user_id integer not null,\n" +
                        " constraint fk_post foreign key (post_id) references posts(post_id),\n" +
                        " constraint fk_author foreign key (user_id) references users(user_id)\n" +
                        ");\n" +
                        "create table if not exists likes (\n" +
                        " --like_id serial not null constraint like_pkey primary key,\n" +
                        " post_id integer not null,\n" +
                        " user_id integer not null,\n" +
                        " constraint fk_post foreign key (post_id) references posts(post_id),\n" +
                        " constraint fk_author foreign key (user_id) references users(user_id)\n" +
                        ");\n" +
                        "\n" +
                        "\n" +
                        "create table if not exists replies (\n" +
                        " reply_id serial not null constraint reply_pkey primary key,\n" +
                        " post_id integer not null,\n" +
                        " reply_content varchar not null,\n" +
                        " reply_starts integer not null,\n" +
                        " reply_userID integer not null,\n" +
                        " constraint fk_post foreign key (post_id) references posts(post_id),\n" +
                        " constraint fk_author foreign key (reply_userID) references users(user_id)\n" +
                        ");\n" +
                        "create table if not exists secondary_replies (\n" +
                        " sec_reply_id serial not null constraint sec_reply_pkey primary key,\n" +
                        " reply_id integer not null,\n" +
                        " sec_reply_content varchar not null,\n" +
                        " sec_reply_starts integer not null,\n" +
                        " sec_reply_userID integer not null,\n" +
                        " constraint fk_author foreign key (sec_reply_userID) references users(user_id),\n" +
                        " constraint fk_reply foreign key (reply_id) references replies(reply_id)\n" +
                        ");");
                con.commit();
                stmt0.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
