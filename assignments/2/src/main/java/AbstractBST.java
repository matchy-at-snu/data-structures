
public abstract class AbstractBST {
    public static class Node implements Comparable<Node> {
        private Node left;
        private Node right;
        private final String key;
        private int freq;
        private int accessCount;

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public String getKey() {
            return key;
        }

        public int getFreq() {
            return freq;
        }

        public void setFreq(int freq) {
            this.freq = freq;
        }

        public int getAccessCount() {
            return accessCount;
        }

        public void setAccessCount(int accessCount) {
            this.accessCount = accessCount;
        }

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
}
