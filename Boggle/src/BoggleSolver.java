import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
public class BoggleSolver
{

    private HashSet<String> dict;
    private BoggleBoard board;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        dict = new HashSet<>();

        for (String word : dictionary) {
            dict.add(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        System.out.println(board.toString());
        this.board = board;

        //using DFS for finding all the words in the boggleboard
        Boolean[] visited;
        ArrayList<String> wordsFound = new ArrayList<>();
        String iteratedString = "";

        for (int i = 0; i < board.rows()*board.cols(); i++) {
            visited = new Boolean[board.rows()*board.cols()];
            for (int k = 0, j = visited.length;k < j ;k++) {
                visited[k] = false;
            }
            DFS(i,visited,wordsFound,iteratedString);
        }
        System.out.print(wordsFound.toString());
        return (Iterable<String>) wordsFound.iterator();
    }

    private int xyto1d(int i,int j) {
        return i * board.cols() + j;
    }

    private void DFS(int v, Boolean[] visited, ArrayList<String> wordsFound,String iteratedString){
        visited[v] = true;
        int p = v / board.cols();
        int q = v % board.cols();
        iteratedString += board.getLetter(p,q);
        if (dict.contains(iteratedString)) {
            wordsFound.add(iteratedString);
        }
        Iterator<Integer> i = getAdjacent(v).iterator();
        while (i.hasNext()){
            int n = i.next();
            if (!visited[n]) {
                DFS(n, visited, wordsFound, iteratedString);
            }
        }
    }

    private boolean isValidCoordinates(int i, int j) {
        return (i >= 0 && j >= 0) && (i < board.rows() && j < board.cols());
    }
    private ArrayList getAdjacent(int v) {
        int i = v / board.cols();
        int j = v % board.cols();

        ArrayList<Integer> adj = new ArrayList<>();
        //iterate over all the neighbour of any dice on Boggleboard

        for (int x = i-1; x <= i+1; x ++) {
            for (int y = j-1; y <= j + 1; y++ ) {
                if (x == i && y == j) {
                    continue;
                }
                if (isValidCoordinates(x,y)) {
                    adj.add(xyto1d(x,y));
                }
            }
        }

        return adj;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dict.contains(word)) {
            throw new IllegalArgumentException("Word not found in the dictionary provided");
        }
        int len = word.length();

        if (len <= 2) {return 0;}
        else if (len >=3 && len <=4) {return 1;}
        else if (len ==5 ) {return 2;}
        else if (len ==6 ) {return 3;}
        else if (len ==7 ) {return 5;}
        else {
            return 11;
        }
    }
}
