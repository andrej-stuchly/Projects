package entity;

public class AVLNode{
    private Integer value;
    private String text;
    private AVLNode left;
    private AVLNode right;
    private int depth;
    public AVLNode(Integer value, String text) {
        this.value = value;
        this.text = text;
        this.depth = 1;
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

    public AVLNode getLeft() {
        return left;
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public AVLNode getRight() {
        return right;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public AVLNode singlerotateL() {
        AVLNode help = this.getRight();
        this.setRight(help.getLeft());
        help.setLeft(this);
        AVLTree.updateDepth(help.getRight());
        AVLTree.updateDepth(help.getLeft());
        AVLTree.updateDepth(help);
        return help;
    }

    public AVLNode singlerotateR() {
        AVLNode help = this.getLeft();
        this.setLeft(help.getRight());
        help.setRight(this);
        AVLTree.updateDepth(help.getRight());
        AVLTree.updateDepth(help.getLeft());
        AVLTree.updateDepth(help);
        return help;
    }

    public AVLNode doublerotateL() {
        AVLNode help = this.getRight(), help2 = this.getRight().getLeft();
        this.setRight(help2.getLeft());
        help.setLeft(help2.getRight());
        help2.setLeft(this);
        help2.setRight(help);
        AVLTree.updateDepth(help);
        AVLTree.updateDepth(this);
        AVLTree.updateDepth(help2);
        return help2;
    }

    public AVLNode doublerotateR() {
        AVLNode help = this.getLeft(), help2 = this.getLeft().getRight();
        this.setLeft(help2.getRight());
        help.setRight(help2.getLeft());
        help2.setRight(this);
        help2.setLeft(help);
        AVLTree.updateDepth(help);
        AVLTree.updateDepth(this);
        AVLTree.updateDepth(help2);
        return help2;
    }
}
