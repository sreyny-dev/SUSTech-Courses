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
The Matrix class overloads operators such as assignment (=), equality (==), addition (+), subtraction (-), and multiplication (). 
```cpp
Matrix Matrix::operator+(const Matrix& other) const {
    if (rows != other.rows
    || cols != other.cols) {
        throw std::invalid_argument("Matrices must have the same dimensions for addition.");
    }
    Matrix result(rows, cols);
    for (size_t i = 0; i < rows * cols; ++i) {
        result.data[i] = data[i] + other.data[i];
    }
    return result;
}

Matrix Matrix::operator-(const Matrix& other) const {
    if (rows != other.rows || cols != other.cols) {
        throw std::invalid_argument("Matrices must have the same dimensions for subtraction.");
    }
    Matrix result(rows, cols);
    for (size_t i = 0; i < rows * cols; ++i) {
        result.data[i] = data[i] - other.data[i];
    }
    return result;
}
```
**Region of Interest (ROI):**
The ROI allows sharing the same memory between two Matrix objects, where one object represents the entire matrix, and the other object represents a subregion of the matrix.
The ROI is implemented using the following data members in the Matrix class: isROI, rowOffset, colOffset, roiRows, and roiCols. The setROI() function sets the ROI parameters.

To avoid memory hard copy, the Matrix class maintain a single data array for all instances, regardless of whether they represent the entire matrix or a subregion. The element access operators should take into account the ROI parameters to access the correct elements.
```cpp
void Matrix::setROI(size_t rowOffset, size_t colOffset, size_t roiRows, size_t roiCols) {
    if (rowOffset + roiRows > rows || colOffset + roiCols > cols) {
        throw std::out_of_range("ROI exceeds matrix dimensions.");
    }
    this->rowOffset = rowOffset;
    this->colOffset = colOffset;
    this->roiRows = roiRows;
    this->roiCols = roiCols;
    isROI = true;
}
```
