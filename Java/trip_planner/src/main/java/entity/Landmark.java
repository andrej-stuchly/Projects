package entity;

public class Landmark extends PlaceToVisit {

    public Landmark(){}
    public Landmark (String name, Location location, int price){
        super(name, location, price);
    }

    public void printInformation(){
        System.out.println("Landmark:  " + this.getName() + " - " + this.getDescription());
    }
}
