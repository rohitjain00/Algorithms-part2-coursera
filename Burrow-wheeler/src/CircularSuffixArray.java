import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CircularSuffixArray {
    private String orignalString;
    private ArrayList<String> orignalSuffixes;
    private ArrayList<String> sortedlSuffixes;
    private int[] index;
    private int row;
    private char[] t;

    /**
     * This data type makes all the rotation of the string and helps to make an relation between String's rotation to Sorted string's order
     * which is index array
     * @param s Requires a string to convert and make index array
     */

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String you provided is null");
        }
        int len = s.length();
        this.orignalString = s;
        char[] tempString = s.toCharArray();
        char temp;
        t = new char[len];
        index = new int[len];
        orignalSuffixes = new ArrayList<>();
        orignalSuffixes.add(s);
        System.out.println(tempString);
        for (int k = 0,j = len-1; k <j;k++ ){

            temp = tempString[0];

            for (int i = 0; i < j; i++) {
                tempString[i] = tempString[i+1];
            }

            tempString[len-1] = temp;
            orignalSuffixes.add(String.valueOf(tempString));
        }
        sortedlSuffixes = (ArrayList<String>) orignalSuffixes.clone();
        Collections.sort(sortedlSuffixes);

        for (int i = 0; i < len; i++) {
            index[i] = orignalSuffixes.indexOf(sortedlSuffixes.get(i));

            t[i] = sortedlSuffixes.get(i).charAt(sortedlSuffixes.get(i).length()-1);

            if (index[i] == 0) {
                this.row = i;
            }
        }


    }   // circular suffix array of s

    /**
     * This method return the list of the sorted arraays
     * @return sortedSuffix is the array that is sorted lexicographically
     */
    public List<String> sortedSuffix() {
        return sortedlSuffixes;
    }

    /**
     * @return row with zero is the row number in sorted string array that contains the string equal to the orignal string
     */
    public int rowWithZero() {
        return row;
    }

    /**
     * @return t array is the array of characters made from the last character of each of the string present in sortedStrings
     */
    public char[] t() {
        return t;
    }

    /**
     * @return the length of the string it has been provided with
     */
    public int length() {
        return orignalString.length();
    }                    // length of s

    /**
     * @param i takes an int as input and return the i th index of the index array that states at what position does the orignal string's ith rotation appears
     *          in the sorted string
     * @return the position of the index ith value
     */
    public int index(int i){
        return index[i];
    }                 // returns index of ith sorted suffix
    public static void main(String[] args) {
        //Unit tested the components
    }  // unit testing (required
}
