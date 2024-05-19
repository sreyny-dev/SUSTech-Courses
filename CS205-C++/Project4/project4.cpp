#include <iostream>
#include <stdexcept>
#include <cstring>

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

    void allocateMemory() {
        if (rows == 0 || cols == 0) {
            data = nullptr;
        } else {
            data = new T[rows * cols];
        }
    }

public:
    Matrix(size_t rows, size_t cols)
            : rows(rows), cols(cols), is_submatrix(false), row_offset(0), col_offset(0), parent_matrix(nullptr) {
        allocateMemory();
    }

    ~Matrix() {
        if (!is_submatrix) {
            delete[] data;
        }
    }

    Matrix(const Matrix& other)
            : rows(other.rows), cols(other.cols), is_submatrix(other.is_submatrix),
              row_offset(other.row_offset), col_offset(other.col_offset), parent_matrix(other.parent_matrix) {
        if (is_submatrix) {
            data = other.data;
        } else {
            allocateMemory();
            std::memcpy(data, other.data, rows * cols * sizeof(T));
        }
    }

    Matrix& operator=(const Matrix& other) {
        if (this == &other) return *this;
        if (!is_submatrix) {
            delete[] data;
        }

        rows = other.rows;
        cols = other.cols;
        is_submatrix = other.is_submatrix;
        row_offset = other.row_offset;
        col_offset = other.col_offset;
        parent_matrix = other.parent_matrix;

        if (is_submatrix) {
            data = other.data;
        } else {
            allocateMemory();
            std::memcpy(data, other.data, rows * cols * sizeof(T));
        }
        return *this;
    }

    Matrix(Matrix&& other) noexcept
            : rows(other.rows), cols(other.cols), data(other.data),
              is_submatrix(other.is_submatrix), row_offset(other.row_offset),
              col_offset(other.col_offset), parent_matrix(other.parent_matrix) {
        other.data = nullptr;
    }

    Matrix& operator=(Matrix&& other) noexcept {
        if (this == &other) return *this;
        if (!is_submatrix) {
            delete[] data;
        }

        rows = other.rows;
        cols = other.cols;
        data = other.data;
        is_submatrix = other.is_submatrix;
        row_offset = other.row_offset;
        col_offset = other.col_offset;
        parent_matrix = other.parent_matrix;

        other.data = nullptr;
        return *this;
    }

    // Operator Overloading
    T& operator()(size_t row, size_t col) {
        if (is_submatrix) {
            return parent_matrix->data[(row_offset + row) * parent_matrix->cols + (col_offset + col)];
        }
        return data[row * cols + col];
    }

    const T& operator()(size_t row, size_t col) const {
        if (is_submatrix) {
            return parent_matrix->data[(row_offset + row) * parent_matrix->cols + (col_offset + col)];
        }
        return data[row * cols + col];
    }

    Matrix operator+(const Matrix& other) const {
        if (rows != other.rows || cols != other.cols) {
            throw std::invalid_argument("Matrices must have the same dimensions for addition");
        }
        Matrix result(rows, cols);
        for (size_t i = 0; i < rows; ++i) {
            for (size_t j = 0; j < cols; ++j) {
                result(i, j) = (*this)(i, j) + other(i, j);
            }
        }
        return result;
    }
    Matrix operator*(const Matrix& other) const {
        if (cols != other.rows) {
            throw std::invalid_argument("Number of columns in the first matrix must match the number of rows in the second matrix for multiplication");
        }
        Matrix result(rows, other.cols);
        for (size_t i = 0; i < rows; ++i) {
            for (size_t j = 0; j < other.cols; ++j) {
                result(i, j) = 0;
                for (size_t k = 0; k < cols; ++k) {
                    result(i, j) += (*this)(i, k) * other(k, j);
                }
            }
        }
        return result;
    }
    Matrix operator-(const Matrix& other) const {
        if (rows != other.rows || cols != other.cols) {
            throw std::invalid_argument("Matrices must have the same dimensions for subtraction");
        }
        Matrix result(rows, cols);
        for (size_t i = 0; i < rows; ++i) {
            for (size_t j = 0; j < cols; ++j) {
                result(i, j) = (*this)(i, j) - other(i, j);
            }
        }
        return result;
    }
    bool operator==(const Matrix& other) const {
        if (rows != other.rows || cols != other.cols) {
            return false;
        }

        for (size_t i = 0; i < rows; ++i) {
            for (size_t j = 0; j < cols; ++j) {
                if ((*this)(i, j) != other(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
    // Region of Interest (ROI)
    Matrix getSubmatrix(size_t start_row, size_t start_col, size_t sub_rows, size_t sub_cols) {
        if (start_row + sub_rows > rows || start_col + sub_cols > cols) {
            throw std::out_of_range("Submatrix exceeds matrix dimensions");
        }
        Matrix submatrix(sub_rows, sub_cols);
        submatrix.is_submatrix = true;
        submatrix.row_offset = start_row;
        submatrix.col_offset = start_col;
        submatrix.parent_matrix = this;
        submatrix.data = this->data;
        return submatrix;
    }


    size_t getRows() const { return rows; }
    size_t getCols() const { return cols; }

    void print() const {
        for (size_t i = 0; i < rows; ++i) {
            for (size_t j = 0; j < cols; ++j) {
                std::cout << (*this)(i, j) << " ";
            }
            std::cout << std::endl;
        }
    }
};

int main() {

    Matrix<int> mat1(3, 3);
    for (size_t i = 0; i < 3; ++i) {
        for (size_t j = 0; j < 3; ++j) {
            mat1(i, j) = i * 3.0 + j;
        }
    }
    mat1.print();
    Matrix<float> mat2(3, 3);
    for (size_t i = 0; i < 3; ++i) {
        for (size_t j = 0; j < 3; ++j) {
            mat2(i, j) = 3.5f;
        }
    }
    mat2.print();
    Matrix<char> mat3(3, 3);
    for (size_t i = 0; i < 3; ++i) {
        for (size_t j = 0; j < 3; ++j) {
            mat3(i, j) = 'A';
        }
    }
    mat3.print();
    std::cout<<"SubMat: "<<std::endl;
    Matrix<int> submat = mat1.getSubmatrix(1, 1, 2, 2);
    submat.print();
    Matrix<int> result1 = mat1 + mat1;
    std::cout<<"Result1: "<<std::endl;
    result1.print();
    Matrix<float> result2 = mat2 * mat2;
    std::cout<<"Result2: "<<std::endl;
    result2.print();
    Matrix<char> result3 = mat3 + mat3;
    std::cout<<"Result3: "<<std::endl;
    result3.print();
    bool isEqual = (mat1 == result1);
    std::cout<<"isEqual: "<<isEqual<<std::endl;

    return 0;
}
