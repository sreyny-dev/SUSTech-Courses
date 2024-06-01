#include <iostream>
#include <cuda_runtime.h>
#include <cublas_v2.h>

void checkCudaErrors(cudaError_t result) {
    if (result != cudaSuccess) {
        std::cerr << "CUDA Runtime Error: " << cudaGetErrorString(result) << std::endl;
        exit(-1);
    }
}

void checkCublasErrors(cublasStatus_t result) {
    if (result != CUBLAS_STATUS_SUCCESS) {
        std::cerr << "cuBLAS Error: " << result << std::endl;
        exit(-1);
    }
}

int main() {
    const int N = 3;
    float h_A[N][N] = { {1.0f, 2.0f, 3.0f}, {4.0f, 5.0f, 6.0f}, {7.0f, 8.0f, 9.0f} };
    float h_B[N][N] = { {1.0f, 0.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f, 1.0f} };
    float h_C[N][N];

    float *d_A, *d_B, *d_C;
    cublasHandle_t handle;

    checkCudaErrors(cudaMalloc(&d_A, N * N * sizeof(float)));
    checkCudaErrors(cudaMalloc(&d_B, N * N * sizeof(float)));
    checkCudaErrors(cudaMalloc(&d_C, N * N * sizeof(float)));

    checkCudaErrors(cudaMemcpy(d_A, h_A, N * N * sizeof(float), cudaMemcpyHostToDevice));
    checkCudaErrors(cudaMemcpy(d_B, h_B, N * N * sizeof(float), cudaMemcpyHostToDevice));

    checkCublasErrors(cublasCreate(&handle));
    const float alpha = 1.0f;
    const float beta = 0.0f;
    checkCublasErrors(cublasSgemm(handle, CUBLAS_OP_N, CUBLAS_OP_N, N, N, N, &alpha, d_A, N, d_B, N, &beta, d_C, N));

    checkCudaErrors(cudaMemcpy(h_C, d_C, N * N * sizeof(float), cudaMemcpyDeviceToHost));

    std::cout << "Result matrix C = A * B:" << std::endl;
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N; ++j) {
            std::cout << h_C[i][j] << " ";
        }
        std::cout << std::endl;
    }

    cublasDestroy(handle);
    cudaFree(d_A);
    cudaFree(d_B);
    cudaFree(d_C);

    return 0;
}
