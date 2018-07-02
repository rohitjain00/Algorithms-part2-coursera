import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

public class MoveToFront {
    private static int R = 256;// all ASCII characters

    // apply move-to-front encoding, reading from standard input and writing to standard output
    /**
     * Given a text file in which sequences of the same character occur near each other many times,
     * convert it into a text file in which certain characters appear more frequently than others.
     *
     * The main idea of move-to-front encoding is to maintain an ordered sequence of the characters in the alphabet,
     * and repeatedly read a character from the input message,
     * print out the position in which that character appears, and move that character to the front of the sequence.
     */
    public static void encode() {

        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        // Store the list of chars.
        LinkedList<Integer> strList = new LinkedList<>();
        for (int i = 0; i < R; i++)
            strList.add(i);
        // Check whether the char is in the list.
        for (int i = 0; i < input.length; i++) {
            int idx = strList.indexOf((int) input[i]);
            BinaryStdOut.write((char) idx, 8);
            int obj = strList.remove(idx);
            strList.add(0, obj);
        }

        BinaryStdOut.close();
    }
    /**
     * Given a text file in which certain characters appear more frequently than others and convert it into,
     * a text file in which sequences of the same character occur near each other many times.
     *
     * The main idea of move-to-front decoding is to maintain an ordered sequence of the characters in the alphabet,
     * and repeatedly read a character from the input message,
     * print out the character appears, and move that character to the front of the sequence.
     */
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        LinkedList<Integer> strList = new LinkedList<>();
        for (int i = 0; i < R; i++)
            strList.add(i);

        for (int i = 0; i < input.length; i++) {
            int obj = strList.remove((int) input[i]);
            strList.add(0, obj);
            BinaryStdOut.write((char) obj, 8);
        }

        // Total, worst, R*N, Best, N
        BinaryStdOut.close();

    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
       if (args.length > 1) {
           throw new IllegalArgumentException("Usage java MoveToFront -/+");
       }
       if (args[0].equals("-")) {
           encode();
       } if (args[0].equals("+")) {
           decode();
       } else {
           throw new IllegalArgumentException("Only +/- accepted as arguments");
        }
    }
}