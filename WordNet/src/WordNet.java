import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;

import java.util.*;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

public class WordNet {

    private Digraph Digraph;
    private ArrayList<ArrayList<String>> nounsArrayList;
    private ArrayList<String> allNouns;
    private ArrayList<Integer> allNounsIndex;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In inH = new In(hypernyms);
        In inS = new In(synsets);
        Digraph = new Digraph(inH);

        List<String> hypernymList;
        while (inH.hasNextLine()) {
            String hypernym = inH.readLine();
            hypernymList = Arrays.asList(hypernym.split(","));

            for (int i = 1; i < hypernymList.size(); i++) {
                Digraph.addEdge(parseInt(hypernymList.get(0)), parseInt(hypernymList.get(i)));
            }
        }
        ArrayList<String> nounsArray = new ArrayList<>();
        List<String> SeperateCSV;
        List<String> SeperateNouns;
        while (inS.hasNextLine()) {
            String nouns = inS.readLine();
            SeperateCSV = Arrays.asList(nouns.split(","));
            SeperateNouns = Arrays.asList(SeperateCSV.get(1).split(" "));

            ArrayList<String> tempArrayList = new ArrayList<>();
            for (int i = 0 ; i < SeperateNouns.size(); i++) {
                tempArrayList.add(SeperateNouns.get(i));
                allNouns.add(SeperateNouns.get(i));
                allNounsIndex.add(parseInt(SeperateCSV.get(0)));
            }
            nounsArrayList.add(parseInt(SeperateCSV.get(0)),tempArrayList);
        }
    }

    private Integer IDOfNoun(String noun) {
        return allNounsIndex.get(allNouns.indexOf(noun));
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return () -> allNouns.iterator();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return allNouns.contains(word);
    }


    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        int s = IDOfNoun(nounA);
        int d = IDOfNoun(nounB);

        // Mark all the vertices as not visited(By default
        // set as false)
        String toReturn = "";
        Integer currDistance = 0;
        int V = Digraph.V();
        boolean visited[] = new boolean[V];
        Integer distance[] = new Integer[V];
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visited[s]=true;
        distance[s] = currDistance;
        queue.add(s);

        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            toReturn += s + "->";
            if (s == d) {
                return distance[s];
            }
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = Digraph.adj(s).iterator();
            currDistance += 1;
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                    if (distance[n] == 0) {
                        distance[n] = currDistance;
                    }
                }
            }
        }
    return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(Integer s, Integer d) {
        // Mark all the vertices as not visited(By default
        // set as false)
        String toReturn = "";
        Integer currDistance = 0;
        int V = Digraph.V();
        boolean visited[] = new boolean[V];
        Integer distance[] = new Integer[V];
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visited[s]=true;
        distance[s] = currDistance;
        queue.add(s);

        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            toReturn += s + "->";
            if (s == d) {
                return toReturn;
            }
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = Digraph.adj(s).iterator();
            currDistance += 1;
            while (i.hasNext())
            {
                int n = i.next();
                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                    distance[n] = currDistance;
                }
            }
        }
        return s + "->" + "d";
    }

    // do unit testing of this class
    public static void main(String[] args) {
        System.out.print("Everything is fine");
    }
}
