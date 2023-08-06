package entity;

public class HashNode {
    private int value;
    private String text;
    private HashNode next;

    public HashNode(int value, String text) {
        this.value = value;
        this.text = text;
        this.next = null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashNode getNext() {
        return next;
    }

    public void setNext(HashNode next) {
        this.next = next;
    }
}
