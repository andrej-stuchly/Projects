package entity;

public class AVLTree {
    private AVLNode root;

    public AVLTree() {
        this.root = null;
    }

    public void setRoot(AVLNode root) {
        this.root = root;
    }

    public AVLNode getRoot() {
        return (AVLNode) root;
    }

    public static AVLTree createTree() {
        return new AVLTree();
    }

    public static AVLTree createTree(Integer value, String text) {
        AVLTree tree = new AVLTree();
        tree.insert(value, text);
        return tree;
    }

    public AVLNode search(Integer value) {
        AVLNode currentNode = this.getRoot();
        while (currentNode != null) {
            if (currentNode.getValue().equals(value)) {
                return currentNode;
            } else if (currentNode.getValue() < value) {
                currentNode = currentNode.getRight();
            } else {
                currentNode = currentNode.getLeft();
            }
        }
        return null;
    }
    public void insert(int value, String text) {
        this.root = insertRec(this.root, value, text);
    }
    public AVLNode insertRec(AVLNode currentNode, int value, String text) {
        if (currentNode == null) {
            return new AVLNode(value, text);
        }
        if (currentNode.getValue() > value) {
            currentNode.setLeft(insertRec(currentNode.getLeft(), value, text));
            updateDepth(currentNode);
        } else if (currentNode.getValue() < value) {
            currentNode.setRight(insertRec(currentNode.getRight(), value, text));
            updateDepth(currentNode);
        } else {
            return currentNode;
        }
        if (currentNode.getDepth() > 2) {
            currentNode = rearrange(currentNode);
        }
        return currentNode;
    }

    public void remove(int value) {
        if (search(value) == null) {
            return;
        }

        this.root = removeRec(this.root, value);
    }

    public AVLNode removeRec(AVLNode currentNode, int value) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.getValue() < value) {
            currentNode.setRight(removeRec(currentNode.getRight(), value));
        } else if (currentNode.getValue() > value) {
            currentNode.setLeft(removeRec(currentNode.getLeft(), value));
        } else {
            if (currentNode.getLeft() == null && currentNode.getRight() == null){
                currentNode = null;
            } else if (currentNode.getLeft() == null) {
                currentNode.setValue(currentNode.getRight().getValue());
                currentNode.setText(currentNode.getRight().getText());
                currentNode.setLeft(currentNode.getRight().getLeft());
                currentNode.setRight(currentNode.getRight().getRight());
            } else if (currentNode.getRight() == null){
                currentNode.setValue(currentNode.getLeft().getValue());
                currentNode.setText(currentNode.getLeft().getText());
                currentNode.setRight(currentNode.getLeft().getRight());
                currentNode.setLeft(currentNode.getLeft().getLeft());
            } else {
                AVLNode closestHigher = closestHigher(currentNode.getRight());
                currentNode.setValue(closestHigher.getValue());
                currentNode.setText(closestHigher.getText());
                currentNode.setRight(removeRec(currentNode.getRight(), closestHigher.getValue()));
            }
        }
        if (currentNode != null) {
            updateDepth(currentNode);
            currentNode = rearrange(currentNode);
        }
        return currentNode;
    }
    public static void updateDepth(AVLNode currentNode) {
        if (currentNode == null) {
            return;
        }
        if (currentNode.getLeft() != null && currentNode.getRight() != null) {
            currentNode.setDepth(1 + Math.max(currentNode.getLeft().getDepth(), currentNode.getRight().getDepth()));
        } else if (currentNode.getRight() != null) {
            currentNode.setDepth(1 + currentNode.getRight().getDepth());
        } else if (currentNode.getLeft() != null) {
            currentNode.setDepth(1 + currentNode.getLeft().getDepth());
        } else {
            currentNode.setDepth(1);
        }
    }

    public AVLNode rearrange(AVLNode currentNode) {
        int balance = balanceValue(currentNode);
        if (balance > 1) {
            if (balanceValue(currentNode.getLeft()) > 0) {
                currentNode = currentNode.singlerotateR();
            } else {
                currentNode = currentNode.doublerotateR();
            }
        } else if (balance < -1) {
            if (balanceValue(currentNode.getRight()) < 0) {
                currentNode = currentNode.singlerotateL();
            } else {
                currentNode = currentNode.doublerotateL();
            }
        }
        return currentNode;
    }

    public int balanceValue(AVLNode currentNode){
        if (currentNode == null)
            return 0;
        int left = 0; int right = 0;
        if (currentNode.getLeft() != null) {
            left = currentNode.getLeft().getDepth();
        }
        if (currentNode.getRight() != null) {
            right = currentNode.getRight().getDepth();
        }
        return left - right;
    }

    public AVLNode closestHigher(AVLNode currentNode){
        while (currentNode.getLeft() != null){
            currentNode = currentNode.getLeft();
        }
        return currentNode;
    }

    public void printTree(AVLNode node, String prefix) {
        if(node == null) return;

        System.out.println(prefix + " + " + node.getValue());
        printTree(node.getLeft() , prefix + " ");
        printTree(node.getRight() , prefix + " ");
    }
}
