// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)
// Modified by Matchy

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class BST extends AbstractBST { // Binary Search Tree implementation
    protected boolean NOBSTified = false;
    protected boolean OBSTified = false;

    public BST() {
        super();
    }

    public int size() {
        return 0;
    }

    public void insert(String key) {
        Node node = new Node(key);
        if (root == null) {
            root = node;
        } else {
            _insert(root, node);
        }
    }

    private void _insert(Node root, Node node) {
        if (node.compareTo(root) < 0) { //
            if (root.left == null) {
                root.left = node;
                node.parent = root;
            } else {
                _insert(root.left, node);
            }
        } else if (node.compareTo(root) > 0) {
            if (root.right == null) {
                root.right = node;
                node.parent = root;
            } else {
                _insert(root.right, node);
            }
        } else {
            root.freq = root.freq + 1;
        }
    }

    public boolean find(String key) {
        return true;
    }

    public int sumFreq() {
        return 0;
    }

    public int sumProbes() {
        return 0;
    }

    public int sumWeightedPath() {
        return 0;
    }

    public void resetCounters() {
    }

    public void nobst() {
        NOBSTified = true;
    }    // Set NOBSTified to true.

    public void obst() {
        OBSTified = true;
    }    // Set OBSTified to true.

    public void print() {
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

    private void keys(Node x, ArrayList<String> arrayList) {
        if (x == null) return;
        keys(x.left, arrayList);
        arrayList.add(x.key);
        keys(x.right, arrayList);
    }

}

