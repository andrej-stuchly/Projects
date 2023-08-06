import logic.CreateData;
import logic.Simulate;

import entity.Driver;
import entity.Team;
import entity.Calendar;

public class Main {
    public static void main(String[] args) {
        Driver[] drivers = CreateData.createDrivers();
        Team[] teams = CreateData.createTeams(drivers);
        Calendar calendar = CreateData.createCalendar(drivers);
        Simulate.simSeason(teams, drivers, calendar);
        Simulate.resetSeason(teams);
    }
}   