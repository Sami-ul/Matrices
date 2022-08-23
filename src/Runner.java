public class Runner {
    public static void main(String[] args) {
        Matrix x = new Matrix(new double[][]{{1, 0, 1}, {2, -2, -1}, {3, 0, 0}});
        x.printMatrix();
        System.out.println();
        x.inverse().printMatrix();
        System.out.println();
        x.multiply(x.inverse()).printMatrix();
    }
}
