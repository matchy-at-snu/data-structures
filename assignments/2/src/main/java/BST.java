// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)
// Modified by Matchy

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BST extends AbstractBST { // Binary Search Tree implementation

    private class OBSTPair {
        int cost;
        int index;

        OBSTPair(int cost, int index) {
            this.cost = cost;
            this.index = index;
        }
    }

    protected boolean NOBSTified = false;
    protected boolean OBSTified = false;

    public BST() {
        super();
    }

    public int size() {
        if (root == null) {
            return 0;
        } else {
            return size;
        }
    }

    public void insert(String key) {
        Node node = new Node(key);
        if (root == null) {
            root = node;
            this.size++;
        } else {
            _insert(root, node);
        }
    }

    private void _insert(Node root, Node node) {
        if (node.compareTo(root) < 0) { //
            if (root.left == null) {
                root.left = node;
                node.parent = root;
                this.size++;
            } else {
                _insert(root.left, node);
            }
        } else if (node.compareTo(root) > 0) {
            if (root.right == null) {
                root.right = node;
                node.parent = root;
                this.size++;
            } else {
                _insert(root.right, node);
            }
        } else {
            root.freq = root.freq + 1;
        }
    }

    public boolean find(String key) {
        return _find(root, key);
    }

    private boolean _find(Node root, String key) {
        if (root == null) {
            return false;
        }
        if (root.key.compareTo(key) == 0) {
            root.accessCount = root.accessCount + 1;
            return true;
        } else {
            root.accessCount = root.accessCount + 1;
            if (key.compareTo(root.key) < 0) {
                return _find(root.left, key);
            } else {
                return _find(root.right, key);
            }
        }
    }

    public int sumFreq() {
        return _sumFreq(this.root);
    }

    private int _sumFreq(Node root) {
        if (root == null) {
            return 0;
        } else {
            return root.freq + _sumFreq(root.left) + _sumFreq(root.right);
        }
    }

    public int sumProbes() {
        return _sumProbes(this.root);
    }

    private int _sumProbes(Node root) {
        if (root == null) {
            return 0;
        } else {
            return root.accessCount + _sumProbes(root.left) + _sumProbes(root.right);
        }
    }

    public int sumWeightedPath() {
        return _sumWeightedPath(this.root);
    }

    private int weightedPath(Node node) {
        return node.freq * (1 + node.level());
    }

    private int _sumWeightedPath(Node root) {
        if (root == null) {
            return 0;
        } else {
            return weightedPath(root) + _sumWeightedPath(root.left) + _sumWeightedPath(root.right);
        }
    }

    public void resetCounters() {
        _resetCounters(this.root);
    }

    private void _resetCounters(Node root) {
        if (root == null) {
            return;
        }
        root.freq = 0;
        root.accessCount = 0;
        _resetCounters(root.left);
        _resetCounters(root.right);
    }

    public void nobst() {
        NOBSTified = true; // Set NOBSTified to true.
        ArrayList<Node> nodeList = getAllNodes();

        NOBSTSubTree[][] nbt = nobstify(nodeList);
        buildNOBST(nodeList, nbt);
    }

    private void buildNOBST(ArrayList<Node> nodeList, NOBSTSubTree[][] m) {
        Node node = m[0][m.length - 1].root;
        int index = nodeList.indexOf(node);
        this.root = node;
        node.parent = null;
        this.root.left = _buildNOBST(this.root, 0, index - 1, nodeList, m);
        this.root.right = _buildNOBST(this.root, index + 1, m.length - 1, nodeList, m);
    }

    private Node _buildNOBST(Node parent, int low, int high, ArrayList<Node> nodeList, NOBSTSubTree[][] m) {
        if (low > high) {
            return null;
        }
        Node node = m[low][high].root;
        int index = nodeList.indexOf(node);
        node.parent = parent;
        node.left = _buildNOBST(node, low, index - 1, nodeList, m);
        node.right = _buildNOBST(node, index + 1, high, nodeList, m);
        return node;
    }

    private class NOBSTSubTree {
        int i;
        int j;
        Node root;
        int cost;

        NOBSTSubTree(int i, int j, Node root, int cost) {
            this.i = i;
            this.j = j;
            this.root = root;
            this.cost = cost;
        }
    }

    private NOBSTSubTree[][] nobstify(ArrayList<Node> nodeList) {
        NOBSTSubTree[][] nobstMatrix = new NOBSTSubTree[nodeList.size()][nodeList.size()];
        for (int i = 0; i < nobstMatrix.length; i++) { // Initialize a diagonal matrix
            for (int j = i; j < nobstMatrix.length; j++) {
                if (i == j) {
                    nobstMatrix[i][j] = new NOBSTSubTree(i, j, nodeList.get(i), nodeList.get(i).freq);
                } else {
                    nobstMatrix[i][j] = new NOBSTSubTree(i, j, null, Integer.MAX_VALUE);
                }
            }
        }
        for (int len = 2; len <= nobstMatrix.length; len++) {
            for (int low = 0; low <= nobstMatrix.length - len; low++) {

                int minCS = Integer.MAX_VALUE;
                int high = low + len - 1;
                int p = arraySum(low, high, nodeList);
                for (int r = low; r <= high; r++) {
                    // apply monotonicity
                    int leftMin = r > low ? nobstMatrix[low][r - 1].cost : 0;
                    int rightMin = r < high ? nobstMatrix[r + 1][high].cost : 0;
                    int cs = Math.abs(rightMin - leftMin);
                    if (cs < minCS) {
                        minCS = cs;
                        nobstMatrix[low][high].cost = p;
                        nobstMatrix[low][high].root = nodeList.get(r);
                    }
                }
            }
        }

        return nobstMatrix;
    }


//    private algorithmK(List<Node> nodeList) {
//        for
//    }

    public void obst() {
        OBSTified = true; // Set OBSTified to true.
        ArrayList<Node> nodeList = getAllNodes();
        OBSTPair[][] obstMatrix = new OBSTPair[nodeList.size()][nodeList.size()];
        constructMatrix(nodeList, obstMatrix);
        buildOBST(nodeList, obstMatrix);
    }

    private void buildOBST(ArrayList<Node> nodeList, OBSTPair[][] obstMatrix) {
        int index = obstMatrix[0][obstMatrix.length - 1].index;
        Node node = nodeList.get(index);
        this.root = node;
        node.parent = null;
        this.root.left = _buildOBST(this.root, 0, index - 1, nodeList, obstMatrix);
        this.root.right = _buildOBST(this.root, index + 1, obstMatrix.length - 1, nodeList, obstMatrix);
    }

    private Node _buildOBST(Node parent, int low, int high, ArrayList<Node> nodeList, OBSTPair[][] obstMatrix) {
        if (low > high) {
            return null;
        }
        int index = obstMatrix[low][high].index;
        Node node = nodeList.get(index);
        node.parent = parent;
        node.left = _buildOBST(node, low, index - 1, nodeList, obstMatrix);
        node.right = _buildOBST(node, index + 1, high, nodeList, obstMatrix);
        return node;
    }

    private void constructMatrix(ArrayList<Node> nodeList, OBSTPair[][] obstMatrix) {
        for (int i = 0; i < obstMatrix.length; i++) { // Initialize a diagonal matrix
            for (int j = i; j < obstMatrix.length; j++) {
                if (i == j) {
                    obstMatrix[i][j] = new OBSTPair(nodeList.get(i).freq, i);
                } else {
                    obstMatrix[i][j] = new OBSTPair(Integer.MAX_VALUE, -1);
                }
            }
        }

        // DP, len is the distance between high and low. double pair -> triple pair...
        for (int len = 2; len <= obstMatrix.length; len++) {
            for (int low = 0; low <= obstMatrix.length - len; low++) {
                int minCS = Integer.MAX_VALUE;
                int high = low + len - 1;
                int p = arraySum(low, high, nodeList);
                for (int r = low; r <= high; r++) {
                    // apply monotonicity
                    int leftMin = r > low ? obstMatrix[low][r - 1].cost : 0;
                    int rightMin = r < high ? obstMatrix[r + 1][high].cost : 0;
                    int cs = leftMin + rightMin;
                    if (cs < minCS) {
                        minCS = cs;
                        obstMatrix[low][high].cost = p + minCS;
                        obstMatrix[low][high].index = r;
                    }
                }
            }
        }
    }


    private int arraySum(int low, int high, ArrayList<Node> nodeList) {
        int result = 0;
        for (int i = low; i <= high; i++) {
            if (i >= nodeList.size())
                continue;
            result += nodeList.get(i).freq;
        }
        return result;
    }

    public void print() {
        _print(this.root);
    }

    private void _print(Node root) {
        if (root == null) {
            return;
        }
        _print(root.left);
        System.out.println(root);
        _print(root.right);
    }

    /**
     * Returns all keys in the tree as an {@code Iterable} in increasing order
     * To iterate over all the keys in the tree named {@code bst},
     * use the foreach notation: {@code for (String key : bst.keys())}.
     *
     * @return all keys in the binary search tree in increasing order (preorder)
     */
    public Iterable<String> keys() {
        ArrayList<String> arrayList = new ArrayList<>();
        keys(root, arrayList);
        return arrayList;
    }

    private void keys(Node root, ArrayList<String> arrayList) {
        if (root == null) return;
        keys(root.left, arrayList);
        arrayList.add(root.key);
        keys(root.right, arrayList);
    }

    private ArrayList<Node> getAllNodes() {
        ArrayList<Node> arrayList = new ArrayList<>();
        _getAllNodes(root, arrayList);
        return arrayList;
    }

    private void _getAllNodes(Node root, ArrayList<Node> arrayList) {
        if (root == null) return;
        _getAllNodes(root.left, arrayList);
        arrayList.add(root);
        _getAllNodes(root.right, arrayList);
    }

    public Iterable<String> keysLevelOrder() {
        LinkedList<String> keys = new LinkedList<>();
        if (root != null) {
            LinkedList<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node node = queue.remove();
                keys.add(node.key);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return keys;
    }

}

