package entity;

public class RBTree{
    private RBNode root;
    public RBTree() { this.root = null;
    }
    public RBTree(Integer value, String text) {
        this.root = new RBNode(value, text);
        this.getRoot().setColor("Black");
    }

    public RBNode getRoot() {
        return root;
    }

    public void setRoot(RBNode root) {
        this.root = root;
        this.getRoot().setColor("Black");
    }

    public static RBTree createTree(){return new RBTree();}

    public RBNode search(Integer value) {
        RBNode currentNode = this.getRoot();
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
    public void insert(Integer value, String text) {
        if (this.getRoot() == null) {
            this.setRoot(new RBNode(value, text));
        } else {
            RBNode currentNode = this.getRoot();
            boolean found = false;
            while (!found) {
                if (currentNode.getValue().equals(value)) {
                    return;
                } else if (currentNode.getValue() < value) {
                    if (currentNode.getRight() == null) {
                        RBNode newNode = new RBNode(value, text);
                        newNode.setParent(currentNode);
                        currentNode.setRight(newNode);
                        insertRecolor(newNode);
                        found = true;
                    } else {
                        currentNode = currentNode.getRight();
                    }
                } else {
                    if (currentNode.getLeft() == null) {
                        RBNode newNode = new RBNode(value, text);
                        newNode.setParent(currentNode);
                        currentNode.setLeft(newNode);
                        insertRecolor(newNode);
                        found = true;
                    } else {
                        currentNode = currentNode.getLeft();
                    }
                }
            }
        }
    }

    public void insertRecolor(RBNode currentNode) {
        while (currentNode != null && currentNode != this.getRoot() && currentNode.getParent().getParent() != null &&
                currentNode.getParent().getColor().equals("Red")) {
            if (currentNode.getParent() == currentNode.getParent().getParent().getLeft()) {
                RBNode uncle = currentNode.getParent().getParent().getRight();
                if (uncle != null && uncle.getColor().equals("Red")){
                    currentNode.getParent().setColor("Black");
                    uncle.setColor("Black");
                    currentNode.getParent().getParent().setColor("Red");
                    currentNode = currentNode.getParent().getParent();
                } else {
                    if (currentNode == currentNode.getParent().getRight()) {
                        currentNode = currentNode.getParent();
                        currentNode.rotateLeft(this);
                    }
                    currentNode.getParent().setColor("Black");
                    currentNode.getParent().getParent().setColor("Red");
                    currentNode.getParent().getParent().rotateRight(this);
                }
            } else {
                RBNode uncle = currentNode.getParent().getParent().getLeft();
                if (uncle != null && uncle.getColor().equals("Red")){
                    currentNode.getParent().setColor("Black");
                    uncle.setColor("Black");
                    currentNode.getParent().getParent().setColor("Red");
                    currentNode = currentNode.getParent().getParent();
                } else {
                    if (currentNode == currentNode.getParent().getLeft()) {
                        currentNode = currentNode.getParent();
                        currentNode.rotateRight(this);
                    }
                    currentNode.getParent().setColor("Black");
                    currentNode.getParent().getParent().setColor("Red");
                    currentNode.getParent().getParent().rotateLeft(this);
                }
            }
        }
        this.getRoot().setColor("Black");
    }

    public void printTree(RBNode node, String prefix) {
        if(node == null) return;

        System.out.println(prefix + " + " + node.getValue() + "  C: "+node.getColor());
        printTree(node.getLeft() , prefix + " ");
        printTree(node.getRight() , prefix + " ");
    }
}
