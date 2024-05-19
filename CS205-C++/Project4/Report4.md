# Project 4: A Class to Describe a Matrix
**Sreyny THA-12113053**
## Part I: Analysis


**Memory Management:**
The Matrix class uses dynamic memory allocation through the "new" operator to allocate memory for the matrix elements. To avoid memory leaks, the class implement memory deallocation using the "delete" operator in the destructor.
```cpp
Matrix::~Matrix() {
    delete[] data;
}
```
**Operation Overloading:**
The Matrix class overloads operators such as assignment (=), equality (==), addition (+), subtraction (-), and multiplication (). The provided code already overloads the assignment operator (=) and implements the addition operator (+) and multiplication operator () for matrix operations.
```cpp
Matrix& Matrix::operator=(const Matrix& other) {
    if (this != &other) {
        delete[] data;
        rows = other.rows;
        cols = other.cols;
        isROI = other.isROI;
        rowOffset = other.rowOffset;
        colOffset = other.colOffset;
        roiRows = other.roiRows;
        roiCols = other.roiCols;
        copyDataFrom(other);
    }
    return *this;
}
```
