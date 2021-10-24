// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)
// Modified by Matchy

import java.util.ArrayList;

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
        if (this.root == null) {
            return false;
        }
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
            if (root.left != null) {
                root.left.accessCount++;
            }
            if (root.right != null) {
                root.right.accessCount++;
            }
            return _find(root.left, key) || _find(root.right, key);
        }
    }

    public int sumFreq() {
        return _sumFreq(this.root);
    }

    private int _sumFreq(Node root) {
        if (root == null) {
            return 0;
        } else {
            return root.freq + _sumFreq(root.left)  + _sumFreq(root.right);
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

    private int _sumWeightedPath(Node root) {
        if (root == null) {
            return 0;
        } else {
            int weightedPath = root.freq * (1 + root.level());
            return weightedPath + _sumWeightedPath(root.left) + _sumWeightedPath(root.right);
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
        NOBSTified = true;
    }    // Set NOBSTified to true.

    public void obst() {
        OBSTified = true;
    }    // Set OBSTified to true.

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

}

