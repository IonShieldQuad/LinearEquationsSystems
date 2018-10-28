import math.Matrix;

public class Test {
    public static void main(String[] args) {
        double[][] data1 = {{
            1, 2, 3, 4
        }, {
            9, 5, 6, 2
        }};
        double[][] data2 = {{
                10, 3
        }, {
                5, 9
        }, {
                1, 2
        }};
        
        Matrix a = Matrix.makeEmptyMatrix(2, 4).fill(data1);
        Matrix b = Matrix.makeEmptyMatrix(3, 2).fill(data2);
    
        System.out.println(a + "\n");
        System.out.println(b + "\n");
    
        System.out.println(a.multiply(b));
//        Matrix m = Matrix.makeIdentityMatrix(3);
//        System.out.println(m);/*
//        System.out.println(m.hashCode());
//        System.out.println(Matrix.makeIdentityMatrix(3).hashCode());
//        System.out.println(Matrix.makeIdentityMatrix(15).hashCode());
//        System.out.println(m.equals(Matrix.makeIdentityMatrix(3)));
//        System.out.println(m.equals(Matrix.makeEmptyMatrix(3)));*/
//        //System.out.println(m.minor(1, 0).determinant());
//        System.out.println("Determinant:");
//        System.out.println(m.determinant());
//
//        m = m.fill((x, y) -> (double)(x * x + 2 * y + 1)).add(m.multiplyEach(2.0));
//        System.out.println(m);
//
//        //System.out.println("Minor:");
//        //System.out.println(m.minor(1, 0));
//
//        System.out.println(m.determinant());
    }
}
