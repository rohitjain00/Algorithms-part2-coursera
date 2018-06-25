import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import java.util.LinkedList;
import java.util.Queue;

public class FordFulkerson {
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    private double value;
    private int source;

    public FordFulkerson(FlowNetwork G, int s, int t){
        value = 0;
        this.source = s;
        while(hasAugmentingPath(G, s, t)){
            double bottle = Double.POSITIVE_INFINITY;

            for(int v = t;v != s;v=edgeTo[v].other(v)){
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }
            for(int v = t;v!=s;v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);

            value += bottle;
        }
    }

    public boolean edgeSourceToAll(Object[] verticesToCheck) {
        for (int i = 0; i < verticesToCheck.length; i++) {
            if (edgeTo[source].residualCapacityTo((int)verticesToCheck[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    public final boolean hasAugmentingPath(FlowNetwork G, int s, int t){
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        marked[s] = true;
        while(!q.isEmpty()){

            int v = q.poll();
            for(FlowEdge e:G.adj(v)){
                int w = e.other(v);
                if(e.residualCapacityTo(w) > 0 && !marked[w]){
                    edgeTo[w] = e;

                    marked[w] = true;
                    q.add(w);
                }
            }
        }
        return marked[t];
    }

    public boolean inCut(int v){
        return marked[v];
    }
}