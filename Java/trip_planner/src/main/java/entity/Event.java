package entity;

public class Event extends PlaceToVisit {
    private String date;
    private int capacity;
    private int ticketsSold;

    public Event(){}
    public Event (String name, Location location, int price){
        super(name, location, price);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }
    public void printInformation(){
        System.out.println("Event:  " + this.getName() + " - " + this.getDescription() +
                ";  Date: "+this.getDate()+";  Price: "+this.getPrice());
    }
}
