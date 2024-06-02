#include <stdio.h>
#include <sys/time.h>
#include <cuda_runtime.h>

#define TIME_START gettimeofday(&t_start, NULL);
#define TIME_END(name)    gettimeofday(&t_end, NULL); \
                    elapsedTime = (t_end.tv_sec - t_start.tv_sec) * 1000.0;   \
                    elapsedTime += (t_end.tv_usec - t_start.tv_usec) / 1000.0;  \
                    printf(#name " Time = %f ms.\n", elapsedTime);

typedef struct
{
    size_t rows;
    size_t cols;
    float * data; // CPU memory
    float * data_device; //GPU memory
} Matrix;

Matrix * createMatrix(size_t r, size_t c)
{
    size_t len = r * c;
    if(len == 0)
    {
        fprintf(stderr, "Invalid size. The input should be > 0.\n");
        return NULL;
    }
    Matrix * p  = (Matrix *) malloc(sizeof(Matrix));
    if (p == NULL)
    {
        fprintf(stderr, "Allocate host memory failed.\n");
        goto ERR_TAG;
    }
    p->rows = r;
    p->cols = c;
    p->data = (float*)malloc(sizeof(float)*len);
    if(p->data == NULL)
    {
        fprintf(stderr, "Allocate host memory failed.\n");
        goto ERR_TAG;
    }
    if (cudaMalloc(&p->data_device, sizeof(float) * len) != cudaSuccess)
    {
        fprintf(stderr, "Allocate device memory failed.\n");
        goto ERR_TAG;
    }
    return p;
    ERR_TAG:
    if(p && p->data) free(p->data);
    if(p) free(p);
    return NULL;
}

void freeMatrix(Matrix ** pp)
{
    if(pp == NULL) return;
    Matrix * p = *pp;
    if(p != NULL)
    {
        if(p->data) free(p->data);
        if(p->data_device) cudaFree(p->data_device);
    }
    *pp = NULL;
}

// a simple function to set all elements to the same value
bool setMatrix(Matrix * pMat, float val)
{
    if(pMat == NULL)
    {
        fprintf(stderr, "NULL pointer.\n");
        return false;
    }
    size_t len = pMat->rows * pMat->cols;
    for(size_t i = 0; i < len; i++)
        pMat->data[i] = val;

    return true;
}

bool addCPU(const Matrix * pMat1, Matrix * pMat2, float a, float b)
{
    if( pMat1 == NULL || pMat2 == NULL)
    {
        fprintf(stderr, "Null pointer.\n");
        return false;
    }
    if (pMat1->rows != pMat2->rows || pMat1->cols != pMat2->cols)
    {
        fprintf(stderr, "The 2 matrices are not in the same size.\n");
        return false;
    }
    size_t len = pMat1->rows * pMat1->cols;
    for (int i = 0; i < len; i++)
        pMat2->data[i] = pMat1->data[i] * a + b;
    return true;
}

__global__ void addKernel(const float * input1, const float * input2, float * output, size_t len, float a, float b)
{
    int i = blockDim.x * blockIdx.x + threadIdx.x;
    if(i < len)
        output[i] = input1[i] * a + b;
}

bool addGPU(const Matrix * pMat1, Matrix * pMat2, float a, float b)
{
    if( pMat1 == NULL || pMat2 == NULL)
    {
        fprintf(stderr, "Null pointer.\n");
        return false;
    }
    if (pMat1->rows != pMat2->rows || pMat1->cols != pMat2->cols)
    {
        fprintf(stderr, "The 2 matrices are not in the same size.\n");
        return false;
    }

    cudaError_t ecode = cudaSuccess;
    size_t len = pMat1->rows * pMat1->cols;

    cudaMemcpy(pMat1->data_device, pMat1->data, sizeof(float)*len, cudaMemcpyHostToDevice);
    addKernel<<<(len+255)/256, 256>>>(pMat1->data_device, pMat2->data_device, pMat2->data_device, len, a, b);
    if ((ecode = cudaGetLastError()) != cudaSuccess)
    {
        fprintf(stderr, "CUDA Error: %s\n", cudaGetErrorString(ecode));
        return false;
    }
    cudaMemcpy(pMat2->data, pMat2->data_device, sizeof(float)*len, cudaMemcpyDeviceToHost);

    return true;
}

int main()
{
    struct timeval t_start, t_end;
    double elapsedTime = 0;

    int dev_count = 0;
    cudaGetDeviceCount(&dev_count);
    if (dev_count == 0) {
        fprintf(stderr, "No CUDA devices found.\n");
        return -1;
    }

    int dev_id = 0; // Set to a valid device ID
    if (dev_id >= dev_count) {
        fprintf(stderr, "Invalid device ID %d. Only %d devices available.\n", dev_id, dev_count);
        return -1;
    }

    cudaSetDevice(dev_id);
    cudaGetDevice(&dev_id);
    printf("You have %d CUDA devices.\n", dev_count);
    printf("You are using device %d.\n", dev_id);

    Matrix * pMat1 = createMatrix(4096, 4096);
    Matrix * pMat2 = createMatrix(4096, 4096);
    Matrix * pMat3 = createMatrix(4096, 4096);

    if (pMat1 == NULL || pMat2 == NULL || pMat3 == NULL) {
        fprintf(stderr, "Matrix creation failed.\n");
        return -1;
    }

    setMatrix(pMat1, 1.0f);

    float a = 2.0f;
    float b = 3.0f;

    TIME_START
    addCPU(pMat1, pMat2, a, b);
    TIME_END(addCPU)
    printf("  Result = [%.1f, ..., %.1f]\n", pMat2->data[0], pMat2->data[pMat2->rows * pMat2->cols - 1]);

    TIME_START
    addGPU(pMat1, pMat2, a, b);
    TIME_END(addGPU)
    printf("  Result = [%.1f, ..., %.1f]\n", pMat2->data[0], pMat2->data[pMat2->rows * pMat2->cols - 1]);

    freeMatrix(&pMat1);
    freeMatrix(&pMat2);
    freeMatrix(&pMat3);

    return 0;
}
