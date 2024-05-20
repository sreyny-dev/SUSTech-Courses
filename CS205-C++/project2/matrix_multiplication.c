//
// Created by Admin on 3/26/2024.
//
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void matrix_multiplication(float * A, float * B, float *C,int m, int n, int p){
    for(int i=0;i<m;i++){
        for(int j=0;j<p;j++){
            C[i*p+j]=0;
            for(int k=0;k<n;k++){
                C[i*p+j]+=A[i*n+k]*B[k*p+j];
            }
        }
    }
}
void print_result(float * result, int rows, int columns){
    for(int i=0;i<rows;i++){
        for(int j=0;j<columns;j++){
            printf("%.2f ", result[i*columns+j]);
        }
        printf("\n");
    }
}

void print_matrix(float * vector, int size){
    for(int i=0;i<size;i++){
        printf("%.2f  ", vector[i]);
    }
    printf("\n");
}
int main(){
    int m,n,p;
    printf("Enter m rows of matrix A: ");
    scanf("%d", &m);
    printf("Enter n columns of matrix A and rows of matrix B: ");
    scanf("%d", &n);
    printf("Enter p columns of matrix B: ");
    scanf("%d", &p);

    float * A=(float *) malloc(m * n * sizeof(float));
    float * B=(float *) malloc(n * p * sizeof (float));
    float * C=(float *) malloc(m * p * sizeof (float));

    clock_t start_time, end_time;
    double execution_time;

    //random matrix A and B
    printf("\nMatrix A: \n");
    for(int i=0;i<m;i++){
        for(int j=0;j<n;j++){
            A[i*n+j]=(float)(rand()%101)/10;
        }
//        print_matrix(&A[i*n],n);
    }
    printf("\nMatrix B: \n");
    for(int i=0;i<n;i++){
        for(int j=0;j<p;j++){
            B[i*p+j]=(float)(rand()%101)/10;
        }
//        print_matrix(&B[i*p],p);
    }
    printf("\nResult Matrix A*B=C: \n");
    start_time=clock();
    matrix_multiplication(A,B,C,m,n,p);
    end_time= clock();
    execution_time=((double )(end_time-start_time))/CLOCKS_PER_SEC;
//    print_result(C,m,p);
    printf("Total execution time: %f seconds. \n", execution_time);

    free(A); //1D array of memo, no need loop
    free(B);
    free(C);
    return 0;
}

