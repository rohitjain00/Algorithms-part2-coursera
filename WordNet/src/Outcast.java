import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Outcast {
    private WordNet WN;
    public Outcast(WordNet wordnet) {
        this.WN = wordnet;
    }         // constructor takes a WordNet object
    public String outcast(String[] nouns) {
        String outCastedNoun = null;
        int maxDistance = 0;
        for (String noun: nouns) {
            int distance = 0;
            Iterable<String> allNouns = WN.nouns();
            while (allNouns.iterator().hasNext()) {
                String currNoun = allNouns.iterator().next();
                if (currNoun == noun) {
                    continue;
                }
                distance += WN.distance(noun,currNoun);
            }
            if (distance > maxDistance) {
                maxDistance = distance;
                outCastedNoun = noun;
            }
        }
        return outCastedNoun;
    }   // given an array of WordNet nouns, return an outcast
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);`
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }  // see test client below
}
