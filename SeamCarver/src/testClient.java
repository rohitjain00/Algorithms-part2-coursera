
import edu.princeton.cs.algs4.Picture;

public class testClient {
    public static void main (String args[]) {
        System.out.print("Taking file input");
        Picture pic = new Picture("http://coursera.cs.princeton.edu/algs4/assignments/HJoceanSmall.png");

        SeamCarver carved = new SeamCarver(pic);
        System.out.println("Calculating");
        System.out.println(carved.height());
        System.out.println(carved.width());
        System.out.println(carved.energy(4,5));
        System.out.println(carved.findHorizontalSeam());
        System.out.println(carved.findVerticalSeam());
    }
}
