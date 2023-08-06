package entity;

import logic.Simulate;
import java.util.Random;

public class Race {
    private final String location;
    private Driver[] driverStandings;
    private String weather;

    public Race(String location, Driver[] drivers) {
        this.location = location;
        this.driverStandings = drivers.clone();
        Random random = new Random();
        int index = random.nextInt(4);
        if(index == 0){
            setWeather("Rainy");
        }
        else{setWeather("Sunny");}
    }

    public String getLocation() {
        return location;
    }

    public Driver[] getDriverStandings() {
        return driverStandings;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public final void printRaceResults(){
        int position = 1;
        System.out.println("\nStandings of race in "+ location +", Weather: "+weather);
        for(Driver driver: driverStandings){
            System.out.println(position+". "+driver.getName() + " \n\tTyre choice: "+ driver.getTyre().toString()
                    +"\n\tPoints for race: "+Simulate.pointsForPosition(position));
            position++;
        }
    }

    public String toString(){
        return "Race in "+getLocation();
    }

    public int hashCode(){
        return getLocation().length()*getLocation().charAt(0);
    }

    public boolean equals(Race other){
        return hashCode() == other.hashCode();
    }
}















