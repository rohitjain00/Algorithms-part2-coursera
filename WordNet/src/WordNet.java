import edu.princeton.cs.algs4.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

public class WordNet {

    private Digraph Digraph;
    private ArrayList<ArrayList<String>> nounsArrayList;
    private ArrayList<String> allNouns;

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
                allNouns.add(parseInt(SeperateCSV.get(0)),SeperateNouns.get(i));
            }
            nounsArrayList.add(parseInt(SeperateCSV.get(0)),tempArrayList);
        }
    }

    private Integer IDOfNoun(String noun) {
        return allNouns.indexOf(noun);
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

        int V = Digraph.V();
        boolean visited[] = new boolean[V];
        Integer distance[] = new Integer[V];
        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        visited[s]=true;
        distance[s] = 0;
        queue.add(s);

        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            s = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = Digraph.adj(s).iterator();

            while (i.hasNext())
            {
                int n = i.next();

                if (n == d) {
                    return distance[n];
                }

                if (!visited[n])
                {
                    visited[n] = true;
                    queue.add(n);
                    distance[n] = distance[s] + 1;
                }
            }
        }
        return 0;
    }

    private Boolean checkIfTwoNodeIsConnected(int s, int d) {
        int V = Digraph.V();
        boolean visited[] = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<>();

        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            s= queue.poll();

            Iterator<Integer> i = Digraph.adj(s).iterator();
            while (i.hasNext()) {
                int n = i.next();

                if (n == d) {
                    return true;
                }
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
        return false;

    }
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        int v = IDOfNoun(nounA);
        int w = IDOfNoun(nounB);

        BreadthFirstDirectedPaths BFSV = new BreadthFirstDirectedPaths(Digraph,v);
        BreadthFirstDirectedPaths BFSW = new BreadthFirstDirectedPaths(Digraph,w);
        SAP sap = new SAP(Digraph);
        int ancestors = sap.ancestor(v,w);

        return BFSV.pathTo(ancestors).toString()+BFSW.pathTo(ancestors).toString();
     }


    // do unit testing of this class
    public static void main(String[] args) {
        System.out.print("Everything is fine");
    }
}
