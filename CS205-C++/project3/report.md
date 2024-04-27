# Project 3: Improved Matrix Multiplication in C
12113053 THA Sreyny

### 1. Introduction
Matrix multiplication, a very basic operation in linear algebra, plays an important role in deep learning.
Please implement matrix multiplication in C and try your best to improve its speed. 
### 2. Methods
In this project, I have implemented follwing methods:
- matmul_plain(): a brute force matrix multiplication
- matmul_swapJK(): this method just change the position of j and k
- matmul_block(): this method divides matrix into blocks for friendly cache use
- matmul_improved(): using SIMD+OpenMp
- matmul_blas(): using blas library
### 3. Time Execution Comparation





### FYI
I use following command line to compile the code:
```c
gcc -o main *.c -DWITH_AVX2 -mavx2 -lblas
gcc -o main *.c -DWITH_AVX2 -mavx2 -lblas -O3
```
