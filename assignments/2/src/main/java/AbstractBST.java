// Created by Matchy

public abstract class AbstractBST {
    protected static class Node implements Comparable<Node> {
        protected Node parent;
        protected Node left;
        protected Node right;
        protected final String key;
        protected int freq;
        protected int accessCount;

        public Node(String key) {
            this.left = null;
            this.right = null;
            this.key = key;
            this.freq = 1;
            this.accessCount = 0;
        }

        @Override
        public int compareTo(Node anotherNode) {
            return (this.key).compareTo(anotherNode.key);
        }

        @Override
        public String toString() {
            return "[" + this.key + ":" +
                         this.freq + ":" +
                         this.accessCount + "]";
        }
    }

    protected Node root;

    public AbstractBST() {
        this.root = null;
    }

    public abstract int size();

    /**
     * Inserts a key into the tree using the standard BST insertion algorithm.
     * If the same key exists already in the tree, increment its frequency
     * @param key the key to be inserted into the tree
     */
    public abstract void insert(String key);

    public abstract boolean find(String key);

    /**
     * @return the sum of frequency of all the keys in the tree
     */
    public abstract int sumFreq();

    /**
     * @return the sum of access count of all the keys in the tree
     */
    public abstract int sumProbes();

    /**
     * Returns the sum of weighted path lengths of the tree. The weighted path
     * length of a node is calculated by weight * (1 + level), where weight refers
     * to the frequency of its key
     * @return the sum of weighted path lengths of the tree
     */
    public abstract int sumWeightedPath();

    /**
     * Resets both the frequencies and access counts for all the keys in the tree
     */
    public abstract void resetCounters();

    /**
     * Converts the tree from a plain BST into a Nearly Optimal BST. The
     * frequencies of the trees are used as weights
     */
    public abstract void nobst();

    /**
     * Converts the tree from a plain BST into a Nearly Optimal BST. The
     * frequencies of the trees are used as weights
     */
    public abstract void obst();

    /**
     * Prints the keys in the tree in the increasing order of their values.
     * Each key should appear on a separate line and in the format of
     * [key:frequency:access_count]
     */
    public abstract void print();

}
