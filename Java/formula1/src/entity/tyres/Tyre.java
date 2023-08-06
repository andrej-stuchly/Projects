package entity.tyres;

public abstract class Tyre {
    int speed;

    public abstract int getSpeed();
    public abstract void setSpeed(int speed);
    public abstract int getDegradation();
    public abstract int getGrip();

    public abstract String tyreDescription();
    public String toString(){
        return "Abstract Tyre";
    }

    public int hashCode(){
        return 0;
    }

    public boolean equals(Tyre other){
        return true;
    }
}
