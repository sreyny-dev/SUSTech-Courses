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
## **Task1**

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
