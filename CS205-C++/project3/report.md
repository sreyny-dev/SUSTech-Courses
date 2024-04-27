# Project 3: Improved Matrix Multiplication in C
12113053 THA Sreyny

## 1. Introduction
Matrix multiplication, a very basic operation in linear algebra, plays an important role in deep learning.
Please implement matrix multiplication in C and try your best to improve its speed. 
### 2. Methods
In this project, I have implemented follwing methods:
- matmul_plain(): a brute force matrix multiplication
- matmul_swapJK(): this method just change the position of j and k
- matmul_block(): this method divides matrix into blocks for friendly cache use
- matmul_improved(): using SIMD+OpenMp
- matmul_blas(): using blas library

  Below the brief ideas of each methods:
- matmul():
  ```c
     Matrix *matmul_plain(const Matrix *mat1, const Matrix *mat2) {
        if (mat1->cols != mat2->rows) {
            fprintf(stderr, "Invalid matrix dimensions for multiplication\n");
            return NULL;
        }
    
        Matrix *result = create_matrix(mat1->rows, mat2->cols);
    
        for (size_t i = 0; i < mat1->rows; i++) {
            for (size_t j = 0; j < mat2->cols; j++) {
                float sum = 0.0f;
                for (size_t k = 0; k < mat1->cols; k++) {
                    sum += mat1->data[i * mat1->cols + k] * mat2->data[k * mat2->cols + j];
                }
                result->data[i * result->cols + j] = sum;
            }
        }
    
        return result;
    }
```
## 3. Time Execution Without option O3



## 4. ime Execution Without option O3

## 5. Analysis

## FYI
I use following command line to compile the code:

This command line include AVX2 and Blas library
```c
gcc -o main *.c -DWITH_AVX2 -mavx2 -lblas
```
This command line include AVX2, Blas library and Optimization flag -O3
```c
gcc -o main *.c -DWITH_AVX2 -mavx2 -lblas -O3
```
## Reference
1. Improving the performance of Matrix Multiplication [stackoverflow](https://stackoverflow.com/questions/44375076/improving-the-performance-of-matrix-multiplication).
2. Matrix Multiplication: Optimizing the code from 6 hours to 1 sec (2021). [Meduim](https://vaibhaw-vipul.medium.com/matrix-multiplication-optimizing-the-code-from-6-hours-to-1-sec-70889d33dcfa).
