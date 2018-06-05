import edu.princeton.cs.algs4.Digraph;

import java.util.Iterator;
import java.util.LinkedList;

public class SAP {


    private Digraph Digraph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        Digraph = G;
    }

    private int distance(int s, int d){
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
            s = queue.poll();
            Iterator<Integer> i = Digraph.adj(s).iterator();
            while (i.hasNext()) {
                int n = i.next();

                if (n == d) {
                    return distance[n];
                }
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                    distance[n] = distance[s] + 1;
                }
            }
        }
        return 0;
    }
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int s, int d) {
        Digraph = Digraph.reverse();
        Integer shortestNode = null;
        int shortestDistance = Digraph.E();

        for (int i = 0; i< Digraph.V(); i++) {
            Integer nounToSearch = i;
            if (distance(nounToSearch,s) == 0 || distance(nounToSearch,d) == 0) {
                continue;
            }
            int totalDistance = distance(nounToSearch,s) + distance(nounToSearch,d);
            if (shortestDistance > totalDistance) {
                shortestDistance = totalDistance;
                shortestNode = nounToSearch;
            }
        if (shortestDistance == Digraph.E()) {
            return -1;
        }
        return shortestDistance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int s, int d) {
        Digraph = Digraph.reverse();
        Integer shortestNode = null;
        int shortestDistance = Digraph.E();

        for (int i = 0; i< Digraph.V(); i++) {
            Integer nounToSearch = i;
            if (distance(nounToSearch,s) == 0 || distance(nounToSearch,d) == 0) {
                continue;
            }
            int totalDistance = distance(nounToSearch,s) + distance(nounToSearch,d);
            if (shortestDistance > totalDistance) {
                shortestDistance = totalDistance;
                shortestNode = nounToSearch;
            }
        }
        if (shortestNode == null) {
            return -1;
        }
        return shortestNode;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)

    // do unit testing of this class
    public static void main(String[] args)
}
