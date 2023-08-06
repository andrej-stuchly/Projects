package entity.tyres;

public class MediumTyre extends DryTyre{
    int speed = 50;
    int degradation = 25;

    public MediumTyre(){}

    public int getSpeed(){
        return this.speed;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getDegradation() {
        return degradation;
    }

    public String toString(){
        return "Medium Tyre";
    }

    public int hashCode(){
        return speed*5 - speed*degradation + degradation*2;
    }

    public boolean equals(InterTyre other){
        return hashCode() == other.hashCode();
    }
}
