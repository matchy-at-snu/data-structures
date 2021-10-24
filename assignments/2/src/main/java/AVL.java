// AVL Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)
// Modified by Matchy

public class AVL extends BST {

    public AVL() {
        super();
    }

    @Override
    public void insert(String key) {
        Node node = new Node(key);
        if (this.root == null) {
            this.root = node;
            this.size++;
        } else {
            this.root = _insert(this.root, node);
        }
    }

    private Node _insert(Node root, Node node) {
        if (node.compareTo(root) < 0) { // should insert to right subtree
            if (root.left == null) {
                root.left = node;
                node.parent = root;
                this.size++;
            } else {
                root.left = _insert(root.left, node);
            }
            // Balance
            if (height(root.left) - height(root.right) == 2) {
                Node parent = root.parent;
                if (node.compareTo(root.left) < 0) {
                    root = rotateRight(root);
                } else {
                    root = rotateLeftRight(root);
                }
                root.parent = parent;
            }
        } else if (node.compareTo(root) > 0) { // should insert to right subtree
            if (root.right == null) {
                root.right = node;
                node.parent = root;
                this.size++;
            } else {
                root.right = _insert(root.right, node);
            }
            // Balance
            if (height(root.right) - height(root.left) == 2) {
                Node parent = root.parent;
                if (node.compareTo(root.right) >= 0) {
                    root = rotateLeft(root);
                } else {
                    root = rotateRightLeft(root);
                }
                root.parent = parent;
            }
        } else { // already in the tree
            root.freq = root.freq + 1;
        }
        root.height = 1 + Math.max(height(root.right), height(root.right));
        return root;
    }

    private Node rotateLeftRight(Node root) {
        root.left = rotateLeft(root.left);
        return rotateRight(root);
    }

    private Node rotateRightLeft(Node root) {
        root.right = rotateRight(root.right);
        return rotateLeft(root);
    }

    private Node rotateRight(Node root) {
        Node newRoot = root.left;

        root.left = newRoot.right;

        newRoot.right = root;
        root.parent = newRoot;

        return newRoot;
    }

    private Node rotateLeft(Node root) {
        Node newRoot = root.right;

        root.right = newRoot.left;

        newRoot.left = root;
        root.parent = newRoot;
        return newRoot;
    }

    private int height(Node root) {
        if (root == null) {
            return 0;
        } else {
            return 1 + Math.max(height(root.left), height(root.right));
        }
    }
}

