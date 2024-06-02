# Project 3: Improved Matrix Multiplication in C
**12113053 THA Sreyny**
## **Introduction**
Why do we need CUDA?
GPUs are designed to perform high-speed parallel computations to display graphics such as games. Use available CUDA resources. It provides 30-100x speed-up over other microprocessors for some applications.
GPUs have very small Arithmetic Logic Units (ALUs) compared to the somewhat larger CPUs. This allows for many parallel calculations, such as calculating the color for each pixel on the screen, etc.

**Architecture of CUDA**

![cuda](https://github.com/sreyny1902/SUSTech-Courses/blob/main/CS205-C%2B%2B/project5/img/Cuda.jpg)
<p align="center">
credit: geeksforgeeks.org
</p>

## Task1

There two methods to calculate A=aB+b. One uses CPU and another one uses GPU.

```cu
bool addCPU(const Matrix * pMat1, Matrix * pMat2, float a, float b)
{
    if( pMat1 == NULL
        || pMat2 == NULL
            )
    {
        fprintf(stderr, "Null pointer.\n");
        return false;
    }
    if (pMat1->rows != pMat2->rows || pMat1->cols != pMat2->cols)
    {
        fprintf(stderr, "The 2 matrics are not in the same size.\n");
        return false;
    }
    size_t len = pMat1->rows * pMat1->cols;
    for (int i = 0; i < len; i++)
        pMat2->data[i] = pMat1->data[i] * a + b;
    return true;
}
```
The CUDA kernel for performing the same operation on the GPU. Each thread processes one element of the array.
```cu
__global__ void addKernel(const float * input1, const float * input2, float * output, size_t len, float a, float b)
{
    int i = blockDim.x * blockIdx.x + threadIdx.x;
    if(i < len)
        output[i] = input1[i] * a + b;
}
```
```cu
bool addGPU(const Matrix * pMat1, Matrix * pMat2, float a, float b)
{
    if( pMat1 == NULL
        || pMat2 == NULL)
    {
        fprintf(stderr, "Null pointer.\n");
        return false;
    }
    if (pMat1->rows != pMat2->rows || pMat1->cols != pMat2->cols)
    {
        fprintf(stderr, "The 2 matrics are not in the same size.\n");
        return false;
    }

    cudaError_t ecode = cudaSuccess;
    size_t len = pMat1->rows * pMat1->cols;

    cudaMemcpy(pMat1->data_device, pMat1->data, sizeof(float)*len, cudaMemcpyHostToDevice);
    addKernel<<<(len+255)/256, 256>>>(pMat1->data_device, pMat2->data_device, pMat2->data_device, len, a, b);    if ((ecode = cudaGetLastError()) != cudaSuccess)
    {
        fprintf(stderr, "CUDA Error: %s\n", cudaGetErrorString(ecode));
        return false;
    }
    cudaMemcpy(pMat2->data, pMat2->data_device, sizeof(float)*len, cudaMemcpyDeviceToHost);

    return true;
}
```
### Result
![cuda](https://github.com/sreyny1902/SUSTech-Courses/blob/main/CS205-C%2B%2B/project5/img/matrixAdd.png)
## Task 2
Compare the matrix multiplication using openBlas and cuBlas.
```cu
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
```
```cu
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
```
**Result**

![cuda](https://github.com/sreyny1902/SUSTech-Courses/blob/main/CS205-C%2B%2B/project5/img/cublas.png)
### Advantages and disadvantages of openBlas
**Advantages:**
- The code is simple, without the need for device memory management.
- Runs on any system without the need for a compatible GPU.
- 
**Disadvantages:**
- Constrained by the computational power and memory bandwidth of the CPU.
- Not suitable for very large matrices or high-performance computing tasks requiring the power of a GPU.
### Advantages and disadvantages of cuBlas
**Advantages:**
- Utilizes the parallel processing power of the GPU, significantly speeding up matrix multiplication for large matrices.
- Suitable for large-scale computations and high-performance computing tasks.
- 
**Disadvantages:**
- Requires careful management of device memory, including allocation, data transfer, and deallocation.
- Requires a compatible GPU and appropriate drivers, limiting portability to systems with the necessary hardware.
### Performence Comparison
- OpenBLAS will generally be slower, especially for large matrices, due to the limited computational power and memory bandwidth of the CPU.
- cuBLAS leverages the massive parallelism and high memory bandwidth of the GPU, offering much faster execution for large matrices.
  **Scalability:**
- OpenBLAS performance scales poorly with matrix size compared to cuBLAS.
- cuBLAS scales much better, efficiently handling larger matrices due to the GPU's parallel architecture.
