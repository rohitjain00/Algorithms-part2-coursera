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

    public List<String> sortedSuffix() {
        return sortedlSuffixes;
    }

    public int rowWithZero() {
        return row;
    }
    public char[] t() {
        return t;
    }

    public int length() {
        return orignalString.length();
    }                    // length of s
    public int index(int i){
        return index[i];
    }                 // returns index of ith sorted suffix
    public static void main(String[] args) {
        //Unit tested the components
    }  // unit testing (required
}
