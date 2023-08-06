package entity.tyres;

public class InterTyre extends WetTyre {
     int speed = 30;
     int grip = 50;

     public InterTyre(){

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
          return "Inter Tyre";
     }

     public int hashCode(){
          return speed*4 - speed*grip + grip*3;
     }

     public boolean equals(InterTyre other){
          return hashCode() == other.hashCode();
     }
}
