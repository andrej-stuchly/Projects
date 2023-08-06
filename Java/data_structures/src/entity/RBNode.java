package entity;

public class RBNode {

    private Integer value;
    private String text;
    private RBNode parent;
    private RBNode left;
    private RBNode right;
    private String color; //Red, Black

    public RBNode(Integer value, String text) {
        this.value = value;
        this.text = text;
        this.color = "Red";
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RBNode getParent() {
        return parent;
    }

    public void setParent(RBNode parent) {
        this.parent = parent;
    }

    public RBNode getLeft() {
        return left;
    }

    public void setLeft(RBNode left) {
        this.left = left;
    }

    public RBNode getRight() {
        return right;
    }

    public void setRight(RBNode right) {
        this.right = right;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public void rotateLeft(RBTree rbTree) {
        RBNode rightChild = this.right;
        this.right = rightChild.left;
        if (rightChild.left != null)
            rightChild.left.parent = this;
        rightChild.parent = this.parent;
        if (this.parent == null)
           rbTree.setRoot(rightChild);
        else if (this == this.parent.left)
            this.parent.left = rightChild;
        else
            this.parent.right = rightChild;
        rightChild.left = this;
        this.parent = rightChild;
    }

    public void rotateRight(RBTree rbTree) {
        RBNode leftChild = this.left;
        this.left = leftChild.right;
        if (leftChild.right != null)
            leftChild.right.parent = this;
        leftChild.parent = this.parent;
        if (this.parent == null)
            rbTree.setRoot(leftChild);
        else if (this == this.parent.right)
            this.parent.right = leftChild;
        else
            this.parent.left = leftChild;
        leftChild.right = this;
        this.parent = leftChild;
    }
}