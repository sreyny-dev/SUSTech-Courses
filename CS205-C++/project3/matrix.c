#include "matrix.h"
#include <string.h>
#include <cblas.h>
#include <math.h>
// Allocate memory for a matrix
Matrix *create_matrix(size_t rows, size_t cols) {
    Matrix *mat = (Matrix *)malloc(sizeof(Matrix));
    if (mat == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        exit(EXIT_FAILURE);
    }
    mat->rows = rows;
    mat->cols = cols;
    mat->data = (float *)malloc(rows * cols * sizeof(float));
    if (mat->data == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        free(mat);
        exit(EXIT_FAILURE);
    }
    return mat;
}

// Free memory allocated for a matrix
void free_matrix(Matrix *mat) {
    if (mat != NULL) {
        free(mat->data);
        free(mat);
    }
}

// Initialize matrix with random values
void init_matrix(Matrix *mat) {
    for (size_t i = 0; i < mat->rows * mat->cols; i++) {
        mat->data[i] = (float)rand() / RAND_MAX; // Random value between 0 and 1
    }
}

// brute force
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

// change the order of j,k
Matrix *matmul_swapJK(const Matrix *mat1, const Matrix *mat2) {
    if (mat1->cols != mat2->rows) {
        fprintf(stderr, "Invalid matrix dimensions for multiplication\n");
        return NULL;
    }

    Matrix *result = create_matrix(mat1->rows, mat2->cols);
    size_t j;
    for (size_t i = 0; i < mat1->rows; i++) {
        for (size_t k = 0; k < mat2->cols; k++) {
            float sum = 0.0f;
            for (j = 0; j < mat1->cols; j++) {
                sum += mat1->data[i * mat1->cols + k] * mat2->data[k * mat2->cols + j];
            }
            result->data[i * result->cols + j] = sum;
        }
    }

    return result;
}

//using block for friendly cache
Matrix *matmul_block(const Matrix *mat1, const Matrix *mat2) {
    if (mat1->cols != mat2->rows) {
        fprintf(stderr, "Invalid matrix dimensions for multiplication\n");
        return NULL;
    }
    size_t block_size=32;
    size_t rows = mat1->rows;
    size_t cols = mat2->cols;
    size_t common_dim = mat1->cols;

    Matrix *result = create_matrix(rows, cols);

    for (size_t ii = 0; ii < rows; ii += block_size) {
        for (size_t jj = 0; jj < cols; jj += block_size) {
            for (size_t kk = 0; kk < common_dim; kk += block_size) {
                for (size_t i = ii; i < ii + block_size && i < rows; i++) {
                    for (size_t j = jj; j < jj + block_size && j < cols; j++) {
                        for (size_t k = kk; k < kk + block_size && k < common_dim; k++) {
                            result->data[i * cols + j] += mat1->data[i * common_dim + k] * mat2->data[k * cols + j];
                        }
                    }
                }
            }
        }
    }

    return result;
}

//using SIMD and OpenMP
Matrix *matmul_improved(const Matrix *mat1, const Matrix *mat2) {
    if (mat1->cols != mat2->rows) {
        fprintf(stderr, "Invalid matrix dimensions for multiplication\n");
        return NULL;
    }

    Matrix *result = create_matrix(mat1->rows, mat2->cols);

#pragma omp parallel for
    for (size_t i = 0; i < mat1->rows; i++) {
        for (size_t j = 0; j < mat2->cols; j++) {
            __m256 sum = _mm256_setzero_ps();
            for (size_t k = 0; k < mat1->cols; k += 8) {
                __m256 a = _mm256_loadu_ps(&mat1->data[i * mat1->cols + k]);
                __m256 b = _mm256_loadu_ps(&mat2->data[k * mat2->cols + j]);
                sum = _mm256_add_ps(sum, _mm256_mul_ps(a, b));
            }
            float tmp[8];
            _mm256_storeu_ps(tmp, sum);
            float total = 0.0f;
            for (size_t k = 0; k < 8; k++) {
                total += tmp[k];
            }
            for (size_t k = mat1->cols - mat1->cols % 8; k < mat1->cols; k++) {
                total += mat1->data[i * mat1->cols + k] * mat2->data[k * mat2->cols + j];
            }
            result->data[i * result->cols + j] = total;
        }
    }
    return result;
}

//using BLAS library
Matrix *matmul_blas(const Matrix *mat1, const Matrix *mat2) {
    if (mat1->cols != mat2->rows) {
        fprintf(stderr, "Invalid matrix dimensions for multiplication\n");
        return NULL;
    }
    Matrix *result = create_matrix(mat1->rows, mat2->cols);

    cblas_sgemm(CblasRowMajor, CblasNoTrans, CblasNoTrans, mat1->rows, mat2->cols, mat1->cols,
                1.0f, mat1->data, mat1->cols, mat2->data, mat2->cols, 0.0f, result->data, result->cols);

    return result;
}



