#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Function to perform matrix multiplication
void matrix_multiply(float **A, float **B, float **C, int m, int n, int p) {
    for (int i = 0; i < m; ++i) {
        for (int j = 0; j < p; ++j) {
            C[i][j] = 0;
            for (int k = 0; k < n; ++k) {
                C[i][j] += A[i][k] * B[k][j];
            }
        }
    }
}

// Function to print matrix
void print_result(float **matrix, int rows, int cols) {
    for (int i = 0; i < rows; ++i) {
        for (int j = 0; j < cols; ++j) {
            printf("%.2f\t", matrix[i][j]);
        }
        printf("\n");
    }
}

int main() {
    int m, n, p;
    printf("Enter the number of rows for matrix A: ");
    scanf("%d", &m);
    printf("Enter the number of columns for matrix A and rows for matrix B: ");
    printf("Matrix A and B must have the same columns n.");
    scanf("%d", &n);
    printf("Enter the number of columns for matrix B: ");
    scanf("%d", &p);


    float **A, **B, **C;
    clock_t startTime, endTime;
    double execution_time;

    // Allocate memory for matrices
    A = (float **)malloc(m * sizeof(float *));
    B = (float **)malloc(n * sizeof(float *));
    C = (float **)malloc(m * sizeof(float *));

    for (int i = 0; i < m; ++i) {
        A[i] = (float *)malloc(n * sizeof(float));
        B[i] = (float *)malloc(p * sizeof(float));
        C[i] = (float *)malloc(p * sizeof(float));
    }

    // Initialize matrices with random values
    srand(time(NULL));
    for (int i = 0; i < m; ++i) {
        for (int j = 0; j < n; ++j) {
            A[i][j] = (float)(rand() % 101) / 10.0;
        }
    }
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < p; ++j) {
            B[i][j] = (float)(rand() % 101) / 10.0;
        }
    }

    // Perform matrix multiplication
    startTime = clock();
    matrix_multiply(A, B, C, m, n, p);
    endTime = clock();

    // Print matrices and result
//    printf("\nMatrix A:\n");
//    print_result(A, m, n);
//    printf("\nMatrix B:\n");
//    print_result(B, n, p);
//    printf("\nResult Matrix C:\n");
//    print_result(C, m, p);

    // Calculate and print execution time
    execution_time = ((double) (endTime - startTime)) / CLOCKS_PER_SEC;
    printf("\nExecution Time: %.6f seconds\n", execution_time);

    // Free allocated memory
    for (int i = 0; i < m; ++i) {
        free(A[i]);
        free(B[i]);
        free(C[i]);
    }
    free(A);
    free(B);
    free(C);




    return 0;
}


