package entity;

//Singleton

public class Calendar {
    private Race[] races;
    private Driver championDriver = new Driver();
    private Team championTeam = new Team();
    private static Calendar singletonCalendar = null;
    public Calendar(Race[] races) {
        this.races = races;

    }
    public static Calendar getCalendar(Race[] races) {
        if (singletonCalendar == null)
            singletonCalendar = new Calendar(races);

        return singletonCalendar;
    }
    public Race[] getRaces() {
        return races;
    }

    public Driver getChampionDriver() {
        return championDriver;
    }

    public void setChampionDriver(Driver championDriver) {
        this.championDriver = championDriver;
    }

    public Team getChampionTeam() {
        return championTeam;
    }

    public void setChampionTeam(Team championTeam) {
        this.championTeam = championTeam;
    }

    public String toString(){
        return "Calendar of size "+getRaces().length;
    }

    public int hashCode(){
        return getRaces().length*getRaces()[0].getLocation().charAt(0);
    }

    public boolean equals(Calendar other){
        return hashCode() == other.hashCode();
    }
}
