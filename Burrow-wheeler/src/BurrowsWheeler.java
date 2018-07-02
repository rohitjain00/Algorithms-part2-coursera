import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {
  // apply Burrows-Wheeler transform, reading from standard input and writing to standard output*

    /**
     *  The goal of the Burrows–Wheeler transform is not to compress a message,
     *  but rather to transform it into a form that is more amenable to compression.
     *  The transform rearranges the characters in the input so that there are lots of clusters with repeated characters,
     *  but in such a way that it is still possible to recover the original input.
     *  It relies on the following intuition: if you see the letters hen in English text,
     *  then most of the time the letter preceding it is t or w.
     *  If you could somehow group all such preceding letters together (mostly t’s and some w’s),
     *  then you would have an easy opportunity for data compression.
     */

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

    /**
     * This cnverts the burrows wheeler transformed text into orignal text
     *
     * First we make an next array that contains at ith index the index of the i+1 of the sorted array
     * basically next[i] = sortedString.indexof(orignalString.get(i + 1))
     * we make a loop from i = 0 to string.len and we make the next array and then all we need to do is goto
     * next[fisrt //where sorted string's index equals to orignal string] = first letter of the orignal string
     * next[next[first]] = 2nd character of the orignal string
     */
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
