package logic;

import entity.Driver;
import entity.Team;
import entity.Race;
import entity.Calendar;

public class CreateData {
    //creation of drivers data
    public static Driver[] createDrivers(){
        Driver[] drivers = new Driver[20];
        drivers[0] = new Driver("George Russell");
        drivers[1] = new Driver("George Russell");
        drivers[2] = new Driver("Max Verstappen");
        drivers[3] = new Driver("Sergio Perez");
        drivers[4] = new Driver("Charles Leclerc");
        drivers[5] = new Driver("Carlos Sainz");
        drivers[6] = new Driver("Lando Norris");
        drivers[7] = new Driver("Daniel Ricciardo");
        drivers[8] = new Driver("Fernando Alonso");
        drivers[9] = new Driver("Esteban Ocon");
        drivers[10] = new Driver("Pierre Gasly");
        drivers[11] = new Driver("Yuki Tsunoda");
        drivers[12] = new Driver("Sebastian Vettel");
        drivers[13] = new Driver("Lance Stroll");
        drivers[14] = new Driver("Alexander Albon");
        drivers[15] = new Driver("Nicolas Latifi");
        drivers[16] = new Driver("Valtteri Bottas");
        drivers[17] = new Driver("Gunayu Zhou");
        drivers[18] = new Driver("Mick Schumacher");
        drivers[19] = new Driver("Kevin Magnussen");
        return drivers;
    }

    //creation of teams data
    public static Team[] createTeams(Driver[] drivers){
        Team [] teams =  new Team[10];
        teams[0] = new Team("Mercedes", drivers[0], drivers[1]);
        teams[1] = new Team("Red Bull", drivers[2], drivers[3]);
        teams[2] = new Team("Ferrari", drivers[4], drivers[5]);
        teams[3] = new Team("McLaren", drivers[6], drivers[7]);
        teams[4] = new Team("Alpine", drivers[8], drivers[9]);
        teams[5] = new Team("AlphaTauri", drivers[10], drivers[11]);
        teams[6] = new Team("Aston Martin", drivers[12], drivers[13]);
        teams[7] = new Team("Williams", drivers[14], drivers[15]);
        teams[8] = new Team("Alfa Romeo", drivers[16], drivers[17]);
        teams[9] = new Team("Haas", drivers[18], drivers[19]);
        return teams;
    }

    //creation of calendar with races
    public static Calendar createCalendar(Driver[] drivers){
        Race[] races = new Race[22];
        races[0] = new Race("Bahrain", drivers);
        races[1] = new Race("Saudi Arabia", drivers);
        races[2] = new Race("Australia", drivers);
        races[3] = new Race("Italy - Imola", drivers);
        races[4] = new Race("USA - Miami", drivers);
        races[5] = new Race("Spain", drivers);
        races[6] = new Race("Monaco", drivers);
        races[7] = new Race("Azerbaijan", drivers);
        races[8] = new Race("Canada", drivers);
        races[9] = new Race("Great Britain", drivers);
        races[10] = new Race("Austria", drivers);
        races[11] = new Race("France", drivers);
        races[12] = new Race("Hungary", drivers);
        races[13] = new Race("Belgium", drivers);
        races[14] = new Race("Netherlands", drivers);
        races[15] = new Race("Italy - Monza", drivers);
        races[16] = new Race("Singapore", drivers);
        races[17] = new Race("Japan", drivers);
        races[18] = new Race("USA - Austin", drivers);
        races[19] = new Race("Mexico", drivers);
        races[20] = new Race("Brazil", drivers);
        races[21] = new Race("Abu Dhabi", drivers);
        return Calendar.getCalendar(races);
    }
}
