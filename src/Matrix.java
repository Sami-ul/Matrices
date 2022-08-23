public class Matrix {
    /*
     A class for mathematical matrices and their calculations.
     */
    private double[][] matrix;
    public Matrix(double[][] matrix) {
        // Provide a double 2d argument to construct a matrix
        this.matrix = matrix;
    }
    public double determinant() throws IllegalArgumentException {
        /*
         The matrix must be square
         Calculates the determinant using laplace expansion
         Determinant is used in inverses and system solving
        */
        return det(this);
    }
    private double det(Matrix mat) {
        // Recursive method to calculate determinant
        double result = 0;
        double temp;
        if (mat.getMatrix().length != mat.getMatrix()[0].length) {
            throw new IllegalArgumentException("Matrix is not square");
        } else if (mat.getMatrix().length <= 2 && mat.getMatrix()[0].length <= 2) {
            result = (mat.getMatrix()[0][0] * mat.getMatrix()[1][1]) - (mat.getMatrix()[1][0] * mat.getMatrix()[0][1]);
            return result;
        } else {
            for (int j = 0; j < mat.getMatrix()[0].length; j++) {
                temp = mat.getMatrix()[0][j] * Math.pow(-1, 1 + j + 1);
                result += temp * det(dropRC(mat.getMatrix(), 0, j));
            }
            return result;
        }
    }
    public Matrix multiply(double scalar) {
        // Multiply a matrix by a scalar
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = matrix[i][j] * scalar;
            }
        }
        return new Matrix(result);
    }

    public Matrix multiply(Matrix matrix2) throws IllegalArgumentException {
        // Multiply matrices by each other
        if (this.getDimensions()[1] == matrix2.getDimensions()[0]) {
            double[][] result = new double[this.getDimensions()[0]][matrix2.getDimensions()[1]];
            int mat1R = 0, mat1C = 0;
            int mat2R = 0, mat2C = 0;
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    while (mat1C < matrix[0].length) {
                        result[i][j] += matrix[mat1R][mat1C] * matrix2.getMatrix()[mat2R][mat2C];
                        mat1C++;
                        mat2R++;
                    }
                    mat1C = 0;
                    mat2R = 0;

                    mat2C++;
                }
                mat1R++;
                mat2C = 0;
            }
            return new Matrix(result);
        } else {
            throw new IllegalArgumentException("Matrices dimensions are not eligible to multiply");
        }
    }

    public Matrix add(Matrix matrix2) {
        // Add two matrices
        double[][] result = new double[matrix.length][matrix[0].length];
        if (matrix.length != matrix2.getMatrix().length && matrix[0].length != matrix2.getMatrix()[0].length) {
            throw new IllegalArgumentException("Matrices are not the same dimensions");
        } else {
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    result[i][j] = matrix[i][j] + matrix2.getMatrix()[i][j];
                }
            }
        }
        return new Matrix(result);
    }
    public Matrix subtract(Matrix matrix2) {
        // Subtract two matrices
        double[][] result = new double[matrix.length][matrix[0].length];
        if (matrix.length != matrix2.getMatrix().length && matrix[0].length != matrix2.getMatrix()[0].length) {
            throw new IllegalArgumentException("Matrices are not the same dimensions");
        } else {
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[0].length; j++) {
                    result[i][j] = matrix[i][j] - matrix2.getMatrix()[i][j];
                }
            }
        }
        return new Matrix(result);
    }

    public Matrix inverse() {
        /*
         Inverse the matrix
         Helps in solving systems
         Multiplying original matrix by its inverse gives Identity matrix
         Has some floating point errors which can be fixed by reducing precision and turning numbers very close to 0 to 0
         */
        double[][] nMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < nMatrix.length; i++) {
            for (int j = 0; j < nMatrix[0].length; j++) {
                nMatrix[i][j] = det(dropRC(matrix, i, j));
            }
        }
        int n = 1;
        for (int i = 0; i < nMatrix.length; i++) {
            for (int j = 0; j < nMatrix[0].length; j++) {
                nMatrix[i][j] = nMatrix[i][j] * n;
                n = -n;
            }
        }
        double[][] flippedN = new double[nMatrix.length][nMatrix[0].length];
        for (int i = 0; i < flippedN.length; i++) {
            for (int j = 0; j < flippedN[0].length; j++) {
                flippedN[j][i] = nMatrix[i][j];
            }
        }
        return new Matrix(flippedN).multiply(1/determinant());
    }

    private static Matrix dropRC(double[][] mat, int r, int c) {
        // Drop matrix rows and columns
        double[][] result = new double[mat.length-1][mat.length-1];
        int rI = 0, rJ = 0;
        boolean placed = false;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (i != r && j != c) {
                    result[rI][rJ] = mat[i][j];
                    rJ++;
                    placed = true;
                }
            }
            if (placed) {
                rI++;
                rJ = 0;
                placed = false;
            }

        }
        return new Matrix(result);
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public int[] getDimensions() {
        // Get matrix height and width in [height, width] format
        int[] result = new int[2];
        result[0] = matrix.length;
        result[1] = matrix[0].length;
        return result;
    }
    public void printMatrix() {
        // Print matrix in a neat manner
        for (double[] doubles : matrix) {
            System.out.print("[");
            for (int j = 0; j < matrix[0].length; j++) {
                if (j != matrix[0].length - 1) {
                    System.out.print(doubles[j] + ",\t");
                } else {
                    System.out.print(doubles[j]);
                }
            }
            System.out.print("]");
            System.out.println();
        }
    }
}
