#include <iostream>
#include <chrono>

#include <openblas/cblas.h>

#include <cuda_runtime.h>
#include <cublas_v2.h>

void matrixMultiplyOpenBLAS(int N) {
    float *A = new float[N * N];
    float *B = new float[N * N];
    float *C = new float[N * N];

    for (int i = 0; i < N * N; ++i) {
        A[i] = static_cast<float>(rand()) / RAND_MAX;
        B[i] = static_cast<float>(rand()) / RAND_MAX;
        C[i] = 0.0f;
    }

    auto start = std::chrono::high_resolution_clock::now();

    cblas_sgemm(CblasRowMajor, CblasNoTrans, CblasNoTrans, N, N, N, 1.0f, A, N, B, N, 0.0f, C, N);

    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<float> duration = end - start;
    std::cout << "OpenBLAS: " << duration.count() << " seconds" << std::endl;

    delete[] A;
    delete[] B;
    delete[] C;
}

void matrixMultiplyCuBLAS(int N) {
    float *h_A = new float[N * N];
    float *h_B = new float[N * N];
    float *h_C = new float[N * N];

    for (int i = 0; i < N * N; ++i) {
        h_A[i] = static_cast<float>(rand()) / RAND_MAX;
        h_B[i] = static_cast<float>(rand()) / RAND_MAX;
        h_C[i] = 0.0f;
    }

    float *d_A, *d_B, *d_C;
    cudaMalloc((void**)&d_A, N * N * sizeof(float));
    cudaMalloc((void**)&d_B, N * N * sizeof(float));
    cudaMalloc((void**)&d_C, N * N * sizeof(float));

    cudaMemcpy(d_A, h_A, N * N * sizeof(float), cudaMemcpyHostToDevice);
    cudaMemcpy(d_B, h_B, N * N * sizeof(float), cudaMemcpyHostToDevice);

    cublasHandle_t handle;
    cublasCreate(&handle);

    auto start = std::chrono::high_resolution_clock::now();

    const float alpha = 1.0f;
    const float beta = 0.0f;
    cublasSgemm(handle, CUBLAS_OP_N, CUBLAS_OP_N, N, N, N, &alpha, d_A, N, d_B, N, &beta, d_C, N);

    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<float> duration = end - start;
    std::cout << "cuBLAS: " << duration.count() << " seconds" << std::endl;

    cublasDestroy(handle);
    cudaFree(d_A);
    cudaFree(d_B);
    cudaFree(d_C);
    delete[] h_A;
    delete[] h_B;
    delete[] h_C;
}

int main() {
    int N = 4096;
    std::cout << "Matrix multiplication with OpenBLAS and cuBLAS for " << N << "x" << N << " matrices\n";

    matrixMultiplyOpenBLAS(N);
    matrixMultiplyCuBLAS(N);

    return 0;
}
