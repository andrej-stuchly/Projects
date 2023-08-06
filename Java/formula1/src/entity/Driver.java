package entity;

import entity.tyres.Tyre;

public class Driver{
    private String name;
    private int points = 0;
    private Tyre tyre;

    public Driver(){}
    public Driver(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Tyre getTyre() {
        return tyre;
    }

    public void setTyre(Tyre tyre) {
        this.tyre = tyre;
    }

    public String toString(){
        return "Driver "+getName();
    }

    public int hashCode(){
        return getName().length()*getName().charAt(0);
    }

    public boolean equals(Driver other){
        return hashCode() == other.hashCode();
    }
}







