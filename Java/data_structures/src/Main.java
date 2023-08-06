import logic.Operations;
public class Main {
    public static void main(String[] args) {
        int[] generatedNumbers = Operations.generateNumbers(1000000, 1000000000);
        Operations.AVLTreeOperations(generatedNumbers);
        Operations.RBTreeOperations(generatedNumbers);
        String[] generatedTexts = Operations.generateTexts(1000000, 10);
        Operations.HashTableChainingOperations(generatedTexts, generatedNumbers);
        Operations.HashTableOpenAddressingOperations(generatedTexts, generatedNumbers);
    }
}