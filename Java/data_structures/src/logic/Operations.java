package logic;

import entity.*;
import java.util.Random;

public class Operations {
    public static void AVLTreeOperations(int[] numbers) {
        AVLTree tree = AVLTree.createTree();
        long startTime = System.currentTimeMillis();
        for (int number : numbers) {
            tree.insert(number, "Node " + number);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Trvanie vkladania do AVL stromu pre "+numbers.length+" nodov: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        for (int number : numbers) {
            tree.search(number);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Trvanie vyhľadávania v AVL strome pre "+numbers.length+" nodov: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        for(int number:numbers){
            tree.remove(number);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Trvanie mazania z AVL stromu pre "+numbers.length+" nodov: " + (endTime - startTime) + " ms\n");
    }
    public static void RBTreeOperations(int[] numbers) {
        RBTree tree = RBTree.createTree();
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < numbers.length; i++){
            tree.insert(numbers[i], "Node "+numbers[i]);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Trvanie vkladania do RB stromu pre "+numbers.length+" nodov: " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        for (int number : numbers) {
            tree.search(number);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Trvanie vyhľadávania v RB strome pre "+numbers.length+" nodov: " + (endTime - startTime) + " ms");
    }

    public static void HashTableChainingOperations(String[] generatedTexts, int[] generatedNumbers){
        long startTime = System.currentTimeMillis();
        HashChaining hashTable = new HashChaining(1);
        for(int i = 0; i < generatedTexts.length; i++){
            hashTable.put(generatedTexts[i],generatedNumbers[i]);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Trvanie vkladania do 2D hashovacej tabuľky s reťazením pre "+generatedTexts.length+" nodov: " +
                (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        for(int i = 0; i < generatedTexts.length; i++){
            hashTable.get(generatedTexts[i]);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Trvanie hľadania v 2D hashovacej tabuľke s reťazením pre "+generatedTexts.length+" nodov: " +
                (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        for(int i = 0; i < generatedTexts.length; i++){
            hashTable.remove(generatedTexts[i]);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Trvanie mazania z 2D hashovacej tabuľky s reťazením pre "+generatedTexts.length+" nodov: " +
                (endTime - startTime) + " ms\n");
    }
    public static void HashTableOpenAddressingOperations(String[] generatedTexts, int[] generatedNumbers){
        long startTime = System.currentTimeMillis();
        HashOpenAddressing hashTable = new HashOpenAddressing(1 );
        for(int i = 0; i < generatedTexts.length; i++){
            hashTable.put(generatedTexts[i],generatedNumbers[i]);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Trvanie vkladania do 2D hashovacej tabuľky s otvoreným adresovaním pre "+generatedTexts.length+" nodov: " +
                (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        for(int i = 0; i < generatedTexts.length; i++){
            hashTable.get(generatedTexts[i]);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Trvanie hľadania v 2D hashovacej tabuľke s otvoreným adresovaním pre "+generatedTexts.length+" nodov: " +
                (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
        for(int i = 0; i < generatedTexts.length; i++){
            hashTable.remove(generatedTexts[i]);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Trvanie mazania z 2D hashovacej tabuľky s otvoreným adresovaním pre "+generatedTexts.length+" nodov: " +
                (endTime - startTime) + " ms\n");
    }

    public static int[] generateNumbers(int amount, int bound){
        int[] numbers = new int[amount];
        Random rand = new Random();
        for(int i = 0; i < amount; i++){
            int randomNumber = rand.nextInt(bound);
            numbers[i] = randomNumber;
        }
        System.out.println(amount + " čísel bolo vygenerovaných do poľa\n");
        return numbers;
    }

    public static String[] generateTexts(int amount, int length){
        String[] texts = new String[amount];
        Random rand = new Random();
        for(int i = 0; i < amount; i++){
            StringBuilder text = new StringBuilder();
            for(int j = 0; j < length; j++){
                int randomNumber = rand.nextInt(65, 90);
                text.append((char) randomNumber);
            }
            texts[i] = text.toString();
        }
        System.out.println("\n"+ amount + " textov bolo vygenerovaných do poľa\n");
        return texts;
    }
}
