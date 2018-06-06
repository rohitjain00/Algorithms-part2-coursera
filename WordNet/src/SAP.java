import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class SAP {
    private Digraph Digrapgh;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.Digrapgh = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!isValid(v,w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths BFSV = new BreadthFirstDirectedPaths(Digrapgh,v);
        BreadthFirstDirectedPaths BFSW = new BreadthFirstDirectedPaths(Digrapgh,w);

        int ancestor = ancestor(v,w);
        int pathLength;
        if (ancestor == -1) {
            pathLength = -1;
        } else {
            pathLength = BFSV.distTo(ancestor) + BFSW.distTo(ancestor);
        }
        return pathLength;

    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!isValid(v,w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths BFSV = new BreadthFirstDirectedPaths(Digrapgh,v);
        BreadthFirstDirectedPaths BFSW = new BreadthFirstDirectedPaths(Digrapgh,w);
        int shortestAncestor = -1;
        int shortestPath = Digrapgh.E()+1;
        ArrayList<Integer> ancestors = new ArrayList<>();

        for (int i = 0; i < Digrapgh.V(); i++) {
            if (BFSV.hasPathTo(i) && BFSW.hasPathTo(i)){
                ancestors.add(i);
            }
        }
        for (Integer integer: ancestors) {
            if ((BFSV.distTo(integer) + BFSW.distTo(integer)) < shortestPath) {
                shortestPath = (BFSV.distTo(integer) + BFSW.distTo(integer));
                shortestAncestor = integer;
            }
        }
        return shortestAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isValid(v,w)) {
            throw new IllegalArgumentException();
        }
        BreadthFirstDirectedPaths BFSV = new BreadthFirstDirectedPaths(Digrapgh,v);
        BreadthFirstDirectedPaths BFSW = new BreadthFirstDirectedPaths(Digrapgh,w);


        int ancestor = ancestor(v,w);
        int pathLength;
        if (ancestor == -1) {
            pathLength = -1;
        } else {
            pathLength = BFSV.distTo(ancestor) + BFSW.distTo(ancestor);
        }
        return pathLength;

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isValid(v,w)) {
            throw new IllegalArgumentException();
        }

        BreadthFirstDirectedPaths BFSV = new BreadthFirstDirectedPaths(Digrapgh,v);
        BreadthFirstDirectedPaths BFSW = new BreadthFirstDirectedPaths(Digrapgh,w);
        int shortestAncestor = -1;
        int shortestPath = Digrapgh.E()+1;
        ArrayList<Integer> ancestors = new ArrayList<>();

        for (int i = 0; i < Digrapgh.V(); i++) {
            if (BFSV.hasPathTo(i) && BFSW.hasPathTo(i)){
                ancestors.add(i);
            }
        }
        for (Integer integer: ancestors) {
            if ((BFSV.distTo(integer) + BFSW.distTo(integer)) < shortestPath) {
                shortestPath = (BFSV.distTo(integer) + BFSW.distTo(integer));
                shortestAncestor = integer;
            }
        }
        return shortestAncestor;
    }

    private boolean isValid(int v, int w) {
        if (v > Digrapgh.V() || w > Digrapgh.V() ) {
            return false;
        }
        return true;
    }
    private boolean isValid(Iterable<Integer> v, Iterable<Integer> w) {
        if (!v.iterator().hasNext() || !w.iterator().hasNext() || v == null || w == null) {
            return false;
        }
        return true;
    }

    // do unit testing of this class
    public static void main(String[] args){

    }
}


