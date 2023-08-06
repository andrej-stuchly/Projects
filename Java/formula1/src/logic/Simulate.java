package logic;

import java.util.Random;

import entity.Driver;
import entity.Team;
import entity.Race;
import entity.Calendar;
import entity.tyres.*;

public class Simulate {
    //choosing the tyres for each generated number
    public static void setDriverTyres(Driver driver, int tyreChoice){
        switch (tyreChoice) {
            case 0 -> {
                DryTyre dryTyre = new SoftTyre();
                SoftTyre softTyre = (SoftTyre) dryTyre;
                driver.setTyre(softTyre);
            }
            case 1 -> {
                DryTyre dryTyre = new MediumTyre();
                MediumTyre mediumTyre = (MediumTyre) dryTyre;
                driver.setTyre(mediumTyre);
            }
            case 2 -> {
                DryTyre dryTyre = new HardTyre();
                HardTyre hardTyre = (HardTyre) dryTyre;
                driver.setTyre(hardTyre);
            }
            case 3 -> {
                WetTyre wetTyre = new InterTyre();
                InterTyre interTyre = (InterTyre) wetTyre;
                driver.setTyre(interTyre);
            }
            case 4 -> {
                WetTyre wetTyre = new FullWetTyre();
                FullWetTyre fullWetTyre = (FullWetTyre) wetTyre;
                driver.setTyre(fullWetTyre);
            }
        }
    }

    //set tyres of drivers according to the weather
    public static void setTyres(Driver[] standings, String weather){
        int tyreChoice;
        Random random = new Random();
        for (Driver driver : standings) {
            if(weather.equals("Sunny")) {
                tyreChoice = random.nextInt(3);
            }
            else{
                tyreChoice = random.nextInt(3, 5);
            }
            setDriverTyres(driver, tyreChoice);
        }
    }

    //Fisher-Yates shuffle for random starting position of drivers
    public static void shuffleDrivers(Driver[] standings){
        int index;
        Random random = new Random();
        for (int i = standings.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            if (index != i) {
                Driver temp = standings[index];
                standings[index] = standings[i];
                standings[i] = temp;
            }
        }
    }


    //simulation of overtakes and spins/degradation
    public static void overtakes(Driver[] standings, String weather){
        Random random = new Random();
        if(weather.equals("Rainy")){ //car spin at wet conditions
            int index = 0;
            int allCars = standings.length - 1;
            while(index  < allCars) {
                int spinChance = random.nextInt(standings[index].getTyre().getGrip());
                if (spinChance < 5){
                    Driver temp = standings[index];
                    for (int i = index; i < standings.length - 1; i++) {
                        standings[i] = standings[i+1];
                    }
                    standings[standings.length-1] =  temp;
                    System.out.println(standings[standings.length-1].getName() + " spun");
                    allCars--;
                }
                index++;
            }
        }
        for (int i = 1; i < standings.length - 1; i++) {
            int speedDiff = standings[i].getTyre().getSpeed() - standings[i-1].getTyre().getSpeed();
            if(speedDiff > 0){
                int overtakeChance = random.nextInt(speedDiff);
                if(overtakeChance > 6){
                    System.out.println(standings[i].getName()+" overtook "+standings[i-1].getName());
                    Driver temp = standings[i];
                    standings[i] = standings[i-1];
                    standings[i-1] = temp;
                }
            }
        }
        if(weather.equals("Sunny")){ //dry tyre degradation
            for (int i = standings.length - 1; i > 0; i--) {
                int newSpeed = standings[i].getTyre().getSpeed() - standings[i].getTyre().getDegradation();
                standings[i].getTyre().setSpeed(newSpeed);
            }
        }
    }

    //funcion for conversion of position to points
    public static int pointsForPosition(int pos){
        return switch (pos) {
            case 1 -> 25;
            case 2 -> 18;
            case 3 -> 15;
            case 4 -> 12;
            case 5 -> 10;
            case 6 -> 8;
            case 7 -> 6;
            case 8 -> 4;
            case 9 -> 2;
            case 10 -> 1;
            default -> 0;
        };
    }

    //award teams and drivers with points
    public static void givePoints(Driver[] standings, Team[] teams){
        int position = 1;
        for(Driver driver:standings){
            for (Team team:teams){
                if(driver.getName().equals(team.getDriverOne().getName()) ||
                        driver.getName().equals(team.getDriverTwo().getName()) ){
                    driver.setPoints(driver.getPoints()+pointsForPosition(position));
                    team.setPoints(team.getPoints()+pointsForPosition(position));
                }
            }
            position++;
        }
    }

    //simulate one race
    public static void simRace(Race race, Team[] teams){
        System.out.println("\nHighlights of race in "+race.getLocation()+":");
        setTyres(race.getDriverStandings(), race.getWeather());
        shuffleDrivers(race.getDriverStandings());
        overtakes(race.getDriverStandings(), race.getWeather());
        overtakes(race.getDriverStandings(), race.getWeather());
        givePoints(race.getDriverStandings(), teams);
        race.printRaceResults();
    }

    //sorting drivers according to points
    public static void sortDrivers(Driver[] drivers) {
        for (int i = 0; i < drivers.length; i++) {
            for (int j = i + 1; j < drivers.length; j++) {
                Driver temp;
                if (drivers[i].getPoints() < drivers[j].getPoints()) {
                    temp = drivers[i];
                    drivers[i] = drivers[j];
                    drivers[j] = temp;
                }
            }
        }
    }

    //final print of championship of drivers
    public static void printDriversChampionship(Driver[] drivers, Calendar calendar){
        System.out.println("\nFinal standings of drivers:");
        for (Driver d:drivers){
            System.out.println(d.getName() + " total points: "+d.getPoints());
        }
        System.out.println("\nThe champion is "+calendar.getChampionDriver());
    }

    //sorting teams according to points
    public static void sortTeams(Team[] teams) {
        for (int i = 0; i < teams.length; i++) {
            for (int j = i + 1; j < teams.length; j++) {
                Team temp;
                if (teams[i].getPoints() < teams[j].getPoints()) {
                    temp = teams[i];
                    teams[i] = teams[j];
                    teams[j] = temp;
                }
            }
        }
    }

    //final print of championship of teams
    public static void printTeamsChampionship(Team[] teams, Calendar calendar) {
        System.out.println("\nFinal standings of teams:");
        for (Team t:teams){
            System.out.println(t.getName() + " total points: "+t.getPoints());
        }
        System.out.println("\nThe champion is "+calendar.getChampionTeam());
    }

    //simulation of whole season
    public static void simSeason(Team[] teams, Driver[] drivers, Calendar calendar){
        for(Race race: calendar.getRaces()){
            simRace(race, teams);
        }
        sortDrivers(drivers);
        calendar.setChampionDriver(drivers[0]);
        sortTeams(teams);
        calendar.setChampionTeam(teams[0]);
        printDriversChampionship(drivers, calendar);
        printTeamsChampionship(teams,calendar );
    }

    //reset all points
    public static void resetSeason(Team[] teams){
        for(Team team:teams){
            team.setPoints(0);
            team.getDriverOne().setPoints(0);
            team.getDriverTwo().setPoints(0);
        }
    }
}
