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
        if (root == null) {
            root = node;
            root.height = 1;
            this.size++;
        } else if (node.compareTo(root) > 0) {
            root.right = _insert(root.right, node);
            if (height(root.right) - height(root.left) == 2) {
                if (node.compareTo(root.right) >= 0) {
                    root = rotateLeft(root);
                } else {
                    root = rotateRightLeft(root);
                }
            }
        } else if (node.compareTo(root) < 0) {
            root.left = _insert(root.left, node);
            if (height(root.left) - height(root.right) == 2) {
                if (node.compareTo(root.left) < 0) {
                    root = rotateRight(root);
                } else {
                    root = rotateLeftRight(root);
                }
            }
        } else {
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

