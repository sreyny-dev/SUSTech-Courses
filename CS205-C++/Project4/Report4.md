![image](https://github.com/sreyny1902/SUSTech-Courses/assets/131957560/8c27ff0c-a10c-4f14-b013-ae9f134e2313)# Project 4: A Class to Describe a Matrix
**Sreyny THA-12113053**
## Part I: Analysis
**Memory Management:**
The Matrix class uses dynamic memory allocation through the "new" operator to allocate memory for the matrix elements. To avoid memory leaks, the class should implement proper memory deallocation using the "delete" operator in the destructor.
The provided code already includes a destructor that correctly deletes the allocated memory:
```c
Matrix::~Matrix() {
    delete[] data;
}
```

