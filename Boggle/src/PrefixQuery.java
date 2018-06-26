import java.util.HashSet;

public class PrefixQuery {

      static final int ALPHABET_SIZE = 26;

      public PrefixQuery (HashSet<String> dict) {
          for (String word : dict) {
              insert(word);
          }
      }

      private class TrieNode {
            TrieNode[] children = new TrieNode[ALPHABET_SIZE];

            boolean isEndOfWord;

            TrieNode() {
              isEndOfWord = false;
              for (int i = 0; i < ALPHABET_SIZE; i++) children[i] = null;
            }
      }

      static TrieNode root;

      private void insert(String key) {
            int level;
            int length = key.length();
            int index;

            TrieNode pCrawl = root;

            for (level = 0; level < length; level++) {
              index = key.charAt(level) - 'a';
              if (pCrawl.children[index] == null) pCrawl.children[index] = new TrieNode();

              pCrawl = pCrawl.children[index];
            }

            // mark last node as leaf
            pCrawl.isEndOfWord = true;
      }
    public boolean search(String key) {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;

        for (level = 0; level < length; level++)
        {
            index = key.charAt(level) - 'a';

            if (pCrawl.children[index] == null)
                return false;

            pCrawl = pCrawl.children[index];
        }

        return (pCrawl != null);
    }
}
      