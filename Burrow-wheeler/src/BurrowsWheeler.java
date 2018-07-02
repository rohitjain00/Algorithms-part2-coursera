import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output*

    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);
        BinaryStdOut.write(circularSuffixArray.rowWithZero());
        BinaryStdOut.write(String.valueOf(circularSuffixArray.t()));
        for (int i = 0; i < circularSuffixArray.t().length; i++) {
          BinaryStdOut.write(circularSuffixArray.t()[i]);
        }

        BinaryStdOut.write(circularSuffixArray.rowWithZero());
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform(){
        Integer first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int len = s.length();
        int[] next = new int[len];
        char[] sSorted = s.toCharArray();
        Arrays.sort(sSorted);
        boolean[] isUsed = new boolean[len];
        for (int i = 0 ; i < len ; i ++) {
            for (int j = 0 ; j < len ; j++) {
                if (sSorted[i] == s.toCharArray()[j] && !isUsed[j]) {
                    isUsed[j] = true;
                    next[i] = j;
                    break;
                }
            }
        }
        sSorted = new char[len]; // the final answer

        int tempNext = first;
        for (int i = 0; i < len; i++) {
            sSorted[i] = s.charAt(next[tempNext]);
            tempNext = next[tempNext];
        }
        BinaryStdOut.write(String.valueOf(sSorted));
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        transform();
        inverseTransform();
    }
}
