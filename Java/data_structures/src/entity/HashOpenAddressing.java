package entity;

public class HashOpenAddressing {
    private HashNode[] nodes;
    private int capacity;
    private int nodesAmount;

    public HashOpenAddressing(int initialCapacity) {
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

    public Integer get(String text) {
        int index = hashCodeIndex(text);
        if(this.getNodes()[index] != null && this.getNodes()[index] .getText().equals(text)){
            return this.getNodes()[index].getValue();
        }
        int newIndex = changeIndex(index, this.getCapacity());
        while(newIndex != index){
            if(this.getNodes()[newIndex] != null && this.getNodes()[newIndex].getText().equals(text)){
                return this.getNodes()[newIndex].getValue();
            }
            else{newIndex = changeIndex(newIndex, this.getCapacity());}
        }
        return null;
    }

    public void put(String text, int value) {
        int index = hashCodeIndex(text);
        if(this.getNodes()[index] == null){
            this.getNodes()[index] = new HashNode(value, text);
        }
        else{
            boolean found = false;
            int newIndex = changeIndex(index, this.getCapacity());
            while(!found) {
                if (this.getNodes()[newIndex] == null) {
                    this.getNodes()[newIndex] = new HashNode(value, text);
                    found = true;
                }
                else{
                    newIndex = changeIndex(newIndex, this.getCapacity());
                }
            }
        }
        this.setNodesAmount(this.getNodesAmount() + 1);
        if ((double) this.getNodesAmount() / this.getCapacity() > 0.75) {
            resize();
        }
    }

    public void remove(String text) {
        int index = hashCodeIndex(text);
        if(this.getNodes()[index] != null && this.getNodes()[index].getText().equals(text)){
            this.getNodes()[index] = null;
            this.setNodesAmount(this.getNodesAmount() - 1);
            if ((double) this.getNodesAmount() / this.getCapacity() < 0.5) {
                resize();
            }
            return;
        }
        int newIndex = changeIndex(index, this.getCapacity());
        boolean found = false;
        while(!found && newIndex != index){
            if(this.getNodes()[newIndex] != null && this.getNodes()[newIndex].getText().equals(text)){
                this.getNodes()[newIndex] = null;
                this.setNodesAmount(this.getNodesAmount() - 1);
                if ((double) this.getNodesAmount() / this.getCapacity() < 0.5) {
                    resize();
                }
                found = true;
            }
            else{newIndex = changeIndex(newIndex, this.getCapacity());}
        }
        if(!found) {
            System.out.println("Node was not found, nothing was removed");
        }
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
            if (node != null) {
                int newIndex = node.getText().hashCode() % newCapacity;
                if (newIndex < 0){
                    newIndex *= -1;
                }
                HashNode newNode = newNodes[newIndex];
                if (newNode == null) {
                    newNodes[newIndex] = new HashNode(node.getValue(), node.getText());
                } else {
                    boolean found = false;
                    newIndex = changeIndex(newIndex, newCapacity);
                    while(!found) {
                        if (newNodes[newIndex] == null) {
                            newNodes[newIndex] = new HashNode(node.getValue(), node.getText());
                            found = true;
                        }
                        else{
                            newIndex = changeIndex(newIndex, newCapacity);
                        }
                    }
                }
            }
        }
        this.setNodes(newNodes);
        this.setCapacity(newCapacity);
    }

    public int hashCodeIndex(String text){
        int index = text.hashCode()%this.getCapacity();
        if(index < 0){
            index *= -1;
        }
        return index;
    }

    public int changeIndex(int index, int capacity){
        if(index + 1 >= capacity){
            return 0;
        }
        else {return index+ 1;}
    }
}