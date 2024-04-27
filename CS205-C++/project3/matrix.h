#ifndef MATRIX_H
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <omp.h>
#include <immintrin.h>
#include <stddef.h>

typedef struct {
    size_t rows;
    size_t cols;
    float *data;
} Matrix;

Matrix *create_matrix(size_t rows, size_t cols);
void free_matrix(Matrix *mat);
void init_matrix(Matrix *mat);
Matrix *matmul_blas(const Matrix *mat1, const Matrix *mat2);
Matrix *matmul_swapJK(const Matrix *mat1, const Matrix *mat2);
Matrix *matmul_improved(const Matrix *mat1, const Matrix *mat2);
Matrix *matmul_block(const Matrix *mat1, const Matrix *mat2);
Matrix *matmul_plain(const Matrix *mat1, const Matrix *mat2);


#endif /* MATRIX_H */

