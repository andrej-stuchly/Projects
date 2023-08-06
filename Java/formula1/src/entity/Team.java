package entity;

public class Team {
    private String Name;
    private Driver driverOne;
    private Driver driverTwo;
    private Integer points = 0;

    public Team(){}
    public Team(String name, Driver driverOne, Driver driverTwo) {
        this.Name = name;
        this.driverOne = driverOne;
        this.driverTwo = driverTwo;
    }

    public String getName() {
        return Name;
    }


    public Driver getDriverOne() {
        return driverOne;
    }


    public Driver getDriverTwo() {
        return driverTwo;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String toString(){
        return "Team "+getName();
    }

    public int hashCode(){
        return getName().length()*getName().charAt(0);
    }

    public boolean equals(Team other){
        return hashCode() == other.hashCode();
    }
}
