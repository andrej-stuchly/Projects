package entity.tyres;

public class DryTyre extends Tyre {
    static int degradation;

    public DryTyre(){

    }
    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed(int speed){this.speed = speed;}

    public int getDegradation(){
        return degradation;
    }

    public int getGrip(){
        return -1;
    }

    public String toString(){
        return "Dry Tyre";
    }

    public int hashCode(){
        return 0;
    }

    public boolean equals(DryTyre other){
        return hashCode() == other.hashCode();
    }

    public String tyreDescription(){
        return "This type of tyres has a degradation which damages tire over the time period.";
    }
}
