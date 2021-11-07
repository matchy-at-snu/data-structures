// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)
// Modified by Matchy

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BST extends AbstractBST { // Binary Search Tree implementation

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
        ArrayList<Node> nobstRootList = nobstify(nodeList);
        buildNOBST(nodeList, nobstRootList); // O(n) recursive building
    }

    private void buildNOBST(ArrayList<Node> nodeList, ArrayList<Node> nbstRootList) {
        this.root = nbstRootList.get(0);
        this.root.parent = null;
        this.root.left = null;
        this.root.right = null;
        this.size = 0;
        for (int i = 1; i < nbstRootList.size(); i++) {
            Node node = nbstRootList.get(i);
            node.parent = null;
            node.left = null;
            node.right = null;
            _insert(root, node);
        }
    }

    private void _nobstCompute(int low, int high, ArrayList<Node> nbstRootList, ArrayList<Node> nodeList) {
        if (low > high) {
            return;
        }
        int minDifference = arraySum(low + 1, high, nodeList);
        int rootIndex = low;
        for (int i = low + 1; i <= high; i++) {
            int leftFreqSum = arraySum(low, i - 1, nodeList);
            int rightFreqSum = i == high ? 0 : arraySum(i + 1, high, nodeList);
            int difference = Math.abs(leftFreqSum - rightFreqSum);
            if (difference < minDifference) {
                minDifference = difference;
                rootIndex = i;
            }
        }
        nbstRootList.add(nodeList.get(rootIndex));
        _nobstCompute(low, rootIndex - 1, nbstRootList, nodeList);
        _nobstCompute(rootIndex + 1, high, nbstRootList, nodeList);
    }

    private ArrayList<Node> nobstify(ArrayList<Node> nodeList) {
        ArrayList<Node> nbstRootList = new ArrayList<Node>(this.size);
        _nobstCompute(0, this.size - 1, nbstRootList, nodeList);
        return nbstRootList;
    }

    private class OBSTPair {
        int cost; // total cost of the subtree
        int index;

        OBSTPair(int cost, int index) {
            this.cost = cost;
            this.index = index;
        }
    }

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

        // K algorithm DP, len is the distance between high and low. double pair -> triple pair...
        for (int len = 2; len <= obstMatrix.length; len++) {
            for (int low = 0; low <= obstMatrix.length - len; low++) {
                int minCostSum = Integer.MAX_VALUE;
                int rootIndex = -1;
                int high = low + len - 1;
                // apply monotonicity
                int rootLowerBound = obstMatrix[low][high - 1].index;
                int rootUpperBound = obstMatrix[low + 1][high].index;
                for (int r = rootLowerBound; r <= rootUpperBound; r++) {
                    int leftMin = r > low ? obstMatrix[low][r - 1].cost : 0;
                    int rightMin = r < high ? obstMatrix[r + 1][high].cost : 0;
                    int costSum = leftMin + rightMin;
                    if (costSum < minCostSum) {
                        minCostSum = costSum;
                        rootIndex = r;
                    }
                }
                obstMatrix[low][high].cost = minCostSum + arraySum(low, high, nodeList);
                obstMatrix[low][high].index = rootIndex;
            }
        }
    }

    private int arraySum(int low, int high, ArrayList<Node> nodeList) {
        int result = 0;
        if (low >= nodeList.size()) {
            return result;
        }
        for (int i = low; i <= high; i++) {
            if (i >= nodeList.size())
                break;
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

