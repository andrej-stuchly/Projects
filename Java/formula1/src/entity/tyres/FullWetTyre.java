package entity.tyres;

public class FullWetTyre  extends WetTyre{
    int speed = 20;
    int grip = 80;

    public FullWetTyre(){
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getGrip() {
        return this.grip;
    }

    public String toString(){
        return "Full Wet Tyre";
    }

    public int hashCode(){
        return speed*3 - speed*grip + grip*4;
    }

    public boolean equals(InterTyre other){
        return hashCode() == other.hashCode();
    }
}
