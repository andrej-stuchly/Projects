package entity;

public class HashChaining {
    private HashNode[] nodes;
    private int capacity;
    private int nodesAmount;

    public HashChaining(int initialCapacity) {
        this.capacity = initialCapacity;
        this.nodes = new HashNode[initialCapacity];
        this.nodesAmount = 0;
    }

    public HashNode[] getNodes() {
        return nodes;
    }

    public void setNodes(HashNode[] nodes) {
        this.nodes = nodes;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNodesAmount() {
        return nodesAmount;
    }

    public void setNodesAmount(int nodesAmount) {
        this.nodesAmount = nodesAmount;
    }

    public int hashCodeIndex(String text){
        int index = text.hashCode()%this.getCapacity();
        if(index < 0){
            index *= -1;
        }
        return index;
    }

    public Integer get(String text) {
        int index = hashCodeIndex(text);
        HashNode currentNode = this.getNodes()[index];
        while(currentNode != null){
            if(currentNode.getText().equals(text)){
                return currentNode.getValue();
            }
            currentNode = currentNode.getNext();
        }
        return null;
    }
    public void put(String text, int value) {
        int index = hashCodeIndex(text);
        if(this.getNodes()[index] == null){
            this.getNodes()[index] = new HashNode(value, text);
        }
        else{
            HashNode currentNode = this.getNodes()[index];
            while(currentNode.getNext() != null){
                currentNode = currentNode.getNext();
            }
            currentNode.setNext(new HashNode(value, text));
        }
        this.setNodesAmount(this.getNodesAmount() + 1);
        if ((double) this.getNodesAmount() / this.getCapacity() > 0.75) {
            resize();
        }
    }

    public void remove(String text) {
        int index = hashCodeIndex(text);
        HashNode currentNode = this.getNodes()[index];
        HashNode prevNode = null;
        while (currentNode != null) {
            if (currentNode.getText().equals(text)) {
                if (prevNode == null) {
                    this.getNodes()[index] = currentNode.getNext();
                } else {
                    prevNode.setNext(currentNode.getNext());
                }
                this.setNodesAmount(this.getNodesAmount() - 1);
                if ((double) this.getNodesAmount() / this.getCapacity() < 0.5) {
                    resize();
                }
                return;
            }
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }
        System.out.println("Node was not found, nothing was removed");
    }

    public void resize() {
        int newCapacity;
        if ((double) this.getNodesAmount() / getCapacity() > 0.75) {
            newCapacity = 2 * this.getNodesAmount();
        }
        else{
            newCapacity = this.getCapacity()/2;
            if(newCapacity < 1){
                newCapacity = 1;
            }
        }
        HashNode[] newNodes = new HashNode[newCapacity];
        for (HashNode node : this.getNodes()) {
            while (node != null) {
                int newIndex = node.getText().hashCode() % newCapacity;
                if (newIndex < 0){
                    newIndex *= -1;
                }
                HashNode newNode = newNodes[newIndex];
                if (newNode == null) {
                    newNodes[newIndex] = new HashNode(node.getValue(), node.getText());
                } else {
                    while (newNode.getNext() != null) {
                        newNode = newNode.getNext();
                    }
                    newNode.setNext(new HashNode(node.getValue(), node.getText()));
                }
                node = node.getNext();
            }
        }
        this.setNodes(newNodes);
        this.setCapacity(newCapacity);
    }
}
