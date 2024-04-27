#include <stdio.h>
#include <stdlib.h>
#include "matrix.h"
#include <time.h>
#include <math.h>

// methods print out the result
void print_matrix_result(const Matrix *result) {
    printf("Result:\n");
    for (size_t i = 0; i < result->rows; i++) {
        for (size_t j = 0; j < result->cols; j++) {
            printf("%.2f\t", result->data[i * result->cols + j]);
        }
        printf("\n");
    }
    printf("\n");
}
void print_random_matrix(const Matrix *mat) {
    printf("Randomly generated matrix:\n");
    for (size_t i = 0; i < mat->rows; i++) {
        for (size_t j = 0; j < mat->cols; j++) {
            printf("%.2f\t", mat->data[i * mat->cols + j]);
        }
        printf("\n");
    }
    printf("\n");
}

int main() {
    srand(42);
    size_t dimensions[] = {16, 128, 1024, 8192, 65536};
    //size_t dimensions[] = {2};
    clock_t start, end;
    double cpu_time_used;

    for (int i = 0; i < sizeof(dimensions) / sizeof(dimensions[0]); i++) {
        size_t dim = dimensions[i];
        printf("Testing for dimension %zux%zu\n", dim, dim);

        Matrix *mat1 = create_matrix(dim, dim);
        Matrix *mat2 = create_matrix(dim, dim);
        init_matrix(mat1);
        init_matrix(mat2);

        //print_random_matrix(mat1);
        //print_random_matrix(mat2);

        start = clock();
        Matrix *result_blas=matmul_blas(mat1, mat2);
        end = clock();
        cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
        printf("Time BLAS: %f seconds\n", cpu_time_used);
        // print_matrix_result(result_blas);

        start = clock();
        Matrix *result_swapJK = matmul_swapJK(mat1, mat2);
        end = clock();
        cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
        printf("Time SwapJK: %f seconds\n", cpu_time_used);
        //print_matrix_result(result_swapJK);

        start = clock();
        Matrix *result_improved = matmul_improved(mat1, mat2);
        end = clock();
        cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
        printf("Time simd&openMP: %f seconds\n", cpu_time_used);
        //print_matrix_result(result_improved);

        start = clock();
        Matrix *result_block=matmul_block(mat1, mat2);
        end = clock();
        cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
        printf("Time block: %f seconds\n", cpu_time_used);
        //print_matrix_result(result_block);

        start = clock();
        Matrix *result_plain = matmul_plain(mat1, mat2);
        end = clock();
        cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
        printf("Time plain: %f seconds\n", cpu_time_used);
        //print_matrix_result(result_plain);



        puts("");

        free_matrix(mat1);
        free_matrix(mat2);
        free_matrix(result_plain);
        free_matrix(result_swapJK);
        free_matrix(result_block);
        free_matrix(result_improved);
        free_matrix(result_blas);
    }

    return 0;
}
