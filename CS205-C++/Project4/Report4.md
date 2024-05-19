# Project 4: A Class to Describe a Matrix
**Sreyny THA-12113053**
## Part I: Analysis
**Support different data types:**
Using Templates in C++ can support different types without explicitly specifying the type. In the case of the Matrix class, it is defined as a template class using the following syntax:
```cpp
template <typename T>
class Matrix {
private:
    size_t rows;
    size_t cols;
    T* data;
    bool is_submatrix;
    size_t row_offset;
    size_t col_offset;
    Matrix* parent_matrix;
}
```
The typename T in the angle brackets (<>) is a placeholder for the actual type that will be provided when creating an instance of the Matrix class. This means that T can be any type, such as int, float, double, or even a user-defined type. When we create an instance of the Matrix class, we specify the type by providing the desired type in the angle brackets. For example:
```cpp
Matrix<int> mat1(3, 3);       // Matrix of integers
Matrix<float> mat2(3, 3);     // Matrix of floats
Matrix<double> mat3(3, 3);    // Matrix of doubles
Matrix<char> mat4(3, 3);    // Matrix of char
```

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
```cpp
int& Matrix::operator()(size_t row, size_t col) {
    return data[(row + rowOffset) * cols + (col + colOffset)];
}

const int& Matrix::operator()(size_t row, size_t col) const {
    return data[(row + rowOffset) * cols + (col + colOffset)];
}
```
