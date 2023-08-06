package entity.tyres;

public class HardTyre extends DryTyre{
    int speed = 40;
    int degradation = 10;

    public HardTyre(){

    }

    public int getSpeed(){
        return this.speed;
    }


    public int getDegradation() {
        return degradation;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public String toString(){
        return "Hard Tyre";
    }

    public int hashCode(){
        return speed*6 - speed*degradation + degradation;
    }

    public boolean equals(InterTyre other){
        return hashCode() == other.hashCode();
    }
}
