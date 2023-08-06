package entity.tyres;

public class WetTyre extends Tyre{
    static int grip;

    public WetTyre() {
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getGrip() {
        return grip;
    }

    public int getDegradation(){
        return -1;
    }

    public String toString(){
        return "Wet Tyre";
    }

    public int hashCode(){
        return 0;
    }

    public boolean equals(WetTyre other){
        return hashCode() == other.hashCode();
    }

    public String tyreDescription(){
        return "This type of tyres has a grip which prevents the car from spinning.";
    }
}
