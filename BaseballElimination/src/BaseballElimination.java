import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class BaseballElimination {

    private int noOfTeams;
    private ArrayList<String> Team;
    //0 for wins 1 for looses
    private ArrayList<Integer> w;
    private ArrayList<Integer> l;
    private ArrayList<Integer> r;
    private int[][] g;
    private int source;
    private int sink;
    private FordFulkerson FF;

    public BaseballElimination(String filename) {
        Scanner sc = new Scanner(System.in);
        noOfTeams = sc.nextInt();
        g = new int[noOfTeams][noOfTeams];
        System.out.println("Taking INT done");
        for (int i = 0 ;i < noOfTeams; i++) {
            Team.add(sc.next());
            w.add(sc.nextInt());
            l.add(sc.nextInt());
            r.add(sc.nextInt());
            int j = 0;
            while (sc.hasNextBigInteger()) {
                g[i][j] = sc.nextInt();
            }
            sc.nextLine();
            System.out.println("One for loop done!!");
        }
        System.out.println("For loop exited");
    }                    // create a baseball division from given filename in format specified below

    public              int numberOfTeams(){
        return noOfTeams;
    }                        // number of teams

    public Iterable<String> teams(){
        return () -> this.Team.iterator();
    }                                // all teams

    public              int wins(String team) {
        return this.w.get(Team.indexOf(team));
    }                     // number of wins for given team

    public              int losses(String team) {
        return this.l.get(Team.indexOf(team));
    }                   // number of losses for given team

    public              int remaining(String team) {
        return this.r.get(Team.indexOf(team));
    }                 // number of remaining games for given team

    public              int against(String team1, String team2){
        int team1ID = Team.indexOf(team1);
        int team2ID = Team.indexOf(team2);
        return this.g[team1ID][team2ID];
    }    // number of remaining games between team1 and team2

    private int intToX(int a) {
        return a % noOfTeams;
    }
    private int intToY(int a) {
        return a / noOfTeams;
    }

    private int xyTo1D(int x, int y) {
        return (x * noOfTeams) + y + numberOfTeams();
    }

    private int noOfgamesToBePlayed() {
        int total = 0;
        for (int i = 0; i < noOfTeams; i++) {
            for (int j = i; j < noOfTeams; j++) {
                if (g[i][j] != 0) {
                    total += 1;
                }
            }
        }
        return total;
    }

    public          boolean isEliminated(String team) {
        int TeamID = Team.indexOf(team);

        if ((this.wins(team) + this.remaining(team)) < Collections.max(w)) {
            return true;
        }

        source = noOfgamesToBePlayed() + noOfTeams + 1;
        sink = noOfgamesToBePlayed() + noOfTeams + 2;

        FlowNetwork flowNetwork = new FlowNetwork(noOfgamesToBePlayed() + noOfTeams + 2);
        boolean[] teamVerticesConnected = new boolean[noOfTeams];
        ArrayList<Integer> verticesToCheck = new ArrayList<>();
        for (int i = 0; i < noOfTeams; i++) {
            for (int j = i+1; j < noOfTeams; j++) {
                if (g[i][j] != 0 && i != TeamID && j != TeamID) {
                    flowNetwork.addEdge(new FlowEdge(source,xyTo1D(i,j),g[i][j]));
                    verticesToCheck.add(xyTo1D(i,j));
                    flowNetwork.addEdge(new FlowEdge(xyTo1D(i,j),i,Double.POSITIVE_INFINITY));
                    flowNetwork.addEdge(new FlowEdge(xyTo1D(i,j),j,Double.POSITIVE_INFINITY));
                    if (!teamVerticesConnected[i]){
                        flowNetwork.addEdge(new FlowEdge(i,sink, r.get(i) + w.get(i) - w.get(TeamID)));
                        flowNetwork.addEdge(new FlowEdge(j,sink, r.get(j) + w.get(j) - w.get(TeamID)));
                        teamVerticesConnected[i] = true;
                        teamVerticesConnected[j] = true;
                    }
                }
            }
        }

        FF = new FordFulkerson(flowNetwork,source,sink);
        if (FF.edgeSourceToAll(verticesToCheck.toArray())) {
            return false;
        }
        return true;

    }             // is given team eliminated?
    public Iterable<String> certificateOfElimination(String team) {
        if (!isEliminated(team)) {
            return null;
        }
        ArrayList<String> teamsInCut = new ArrayList<>();

        for (int teamID = 0; teamID < noOfTeams; teamID ++) {
            if (FF.inCut(teamID)) {
                teamsInCut.add(Team.get(teamID));
            }
        }
        return () -> teamsInCut.iterator();

    } // subset R of teams that eliminates given team; null if not eliminated

}
