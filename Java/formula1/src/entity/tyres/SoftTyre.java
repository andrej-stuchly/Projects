package entity.tyres;

public class SoftTyre extends DryTyre{
    int speed = 60;
    int degradation = 40;

    public SoftTyre(){
    }

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
        return "Soft Tyre";
    }

    public int hashCode(){
        return speed*4 - speed*degradation + degradation*3;
    }

    public boolean equals(InterTyre other){
        return hashCode() == other.hashCode();
    }
}
