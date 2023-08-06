package entity;

import java.util.ArrayList;

public class User {
    private String username;
    private ArrayList<PlaceToVisit> selectedEvents = new ArrayList<>();
    private ArrayList<PlaceToVisit> selectedLandmarks = new ArrayList<>();
    private int totalPrice = 0;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<PlaceToVisit> getSelectedEvents() {
        return selectedEvents;
    }

    public void setSelectedEvents(ArrayList<PlaceToVisit> selectedEvents) {
        this.selectedEvents = selectedEvents;
    }

    public ArrayList<PlaceToVisit> getSelectedLandmarks() {
        return selectedLandmarks;
    }

    public void setSelectedLandmarks(ArrayList<PlaceToVisit> selectedLandmarks) {
        this.selectedLandmarks = selectedLandmarks;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
