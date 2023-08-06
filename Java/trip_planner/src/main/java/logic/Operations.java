package logic;

import entity.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Operations {
    public static ArrayList<Event> currentEvents;
    public static ArrayList<Landmark> currentLandmarks;
    public static ArrayList<Location> currentLocations;
    public static User user = null;

    //redirect to new screen
    public static void openNewScreen(ActionEvent event, String fxml) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(GUI.class.getResource(fxml)));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(String.valueOf(GUI.class.getResource("style.css")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if(stage != null) {
            stage.setScene(scene);
            stage.show();
        }
    }

    //set user and his selected places to visit after logging in
    public static User setUser(ResultSet userRs) throws SQLException {
        User user = new User(userRs.getString(2));
        String userEvents = userRs.getString(4).replace("{", "").replace("}", "");
        String userLandmarks = userRs.getString(5).replace("{", "").replace("}", "");
        try (Connection conn = DriverManager.getConnection(System.getenv("postgresURL"),
                System.getenv("postgresUsername"),System.getenv("postgresPassword"))){
            PreparedStatement st = null;
            ResultSet rs = null;
            if (userEvents.length() != 0) {
                st = conn.prepareStatement("SELECT * FROM public.event" +
                        " WHERE id IN (" + userEvents + ") ORDER BY id ASC ");
                rs = st.executeQuery();
                while (rs.next()) {
                    PlaceToVisit newEvent = new PlaceToVisit();
                    newEvent.setId(rs.getInt(1));
                    newEvent.setName(rs.getString(2));
                    newEvent.setPrice(rs.getInt(6));
                    user.setTotalPrice(user.getTotalPrice()+ newEvent.getPrice());
                    user.getSelectedEvents().add(newEvent);
                }
            }
            if (userLandmarks .length() != 0) {
                st = conn.prepareStatement("SELECT * FROM public.landmark" +
                        " WHERE id IN (" + userLandmarks + ") ORDER BY id ASC ");
                rs = st.executeQuery();
                while (rs.next()) {
                    PlaceToVisit newLandmark = new PlaceToVisit();
                    newLandmark.setId(rs.getInt(1));
                    newLandmark.setName(rs.getString(2));
                    newLandmark.setPrice(rs.getInt(6));
                    user.setTotalPrice(user.getTotalPrice()+ newLandmark.getPrice());
                    user.getSelectedLandmarks().add(newLandmark);
                }
            }
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    //login control whether user exists
    public static void logIn(String username, String password, ActionEvent event) throws ClassNotFoundException {
        Operations.user = null;
        Class.forName("org.postgresql.Driver");
        try (Connection conn = DriverManager.getConnection(System.getenv("postgresURL"),
                System.getenv("postgresUsername"),System.getenv("postgresPassword"))){
            PreparedStatement st = conn.prepareStatement("SELECT * FROM public.login" +
                    " WHERE username " + " = ? AND password" + " = ? ORDER BY id ASC ");
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Operations.user = setUser(rs);
                Operations.openNewScreen(event, "project.fxml");
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            printSQLException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //signing up and creating new user
    public static void signUp(String username, String password, ActionEvent event) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection conn = DriverManager.getConnection(System.getenv("postgresURL"),
                System.getenv("postgresUsername"),System.getenv("postgresPassword"))){
            PreparedStatement st = conn.prepareStatement("SELECT * FROM public.login" +
                    " WHERE  username " + " = ? ORDER BY id ASC ");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                PreparedStatement insertSt = conn.prepareStatement("INSERT INTO public.login (username, password) " +
                        "VALUES (?, ?)");
                insertSt.setString(1, username);
                insertSt.setString(2, password);
                int rows = insertSt.executeUpdate();
                System.out.println(rows);
                insertSt.close();
                Operations.openNewScreen(event, "login.fxml");
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            printSQLException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //process event data from database
    public static Object processEvent(ResultSet rs) throws SQLException {
        Event newEvent = new Event();
        newEvent.setId(rs.getInt(1));
        newEvent.setName(rs.getString(2));
        newEvent.setDescription(rs.getString(3));
        String city = rs.getString(5);
        if (currentLocations == null) {
            currentLocations = new ArrayList<>();
        } else {
            for (Location loc : currentLocations) {
                if (loc.getCity().equals(city)) {
                    newEvent.setLocation(loc);
                    break;
                }
            }
        }
        if(newEvent.getLocation() == null){
            Location newLocation = new Location(city, rs.getString(4));
            currentLocations.add(newLocation);
            newEvent.setLocation(newLocation);
        }
        newEvent.setPrice(rs.getInt(6));
        newEvent.setDate(rs.getString(7));
        newEvent.setTicketsSold(rs.getInt(8));
        newEvent.setCapacity(rs.getInt(9));
        return newEvent;
    }

    //process landmark data from database
    public static Object processLandmark(ResultSet rs) throws SQLException {
        Landmark newLandmark = new Landmark();
        newLandmark.setId(rs.getInt(1));
        newLandmark.setName(rs.getString(2));
        newLandmark.setDescription(rs.getString(3));
        String city = rs.getString(5);
        if (currentLocations == null) {
            currentLocations = new ArrayList<>();
        } else {
            for (Location loc : currentLocations) {
                if (loc.getCity().equals(city)) {
                    newLandmark.setLocation(loc);
                    break;
                }
            }
        }
        if(newLandmark.getLocation() == null){
            Location newLocation = new Location(city, rs.getString(4));
            currentLocations.add(newLocation);
            newLandmark.setLocation(newLocation);
        }
        newLandmark.setPrice(rs.getInt(6));
        return newLandmark;
    }

    //get data from database for any PlaceToVisit
    public static void getItems(String tableName, String locationType, String locationValue) throws ClassNotFoundException {
        ArrayList<Object> currentItems = null;
        Class.forName("org.postgresql.Driver");
        try (Connection conn = DriverManager.getConnection(System.getenv("postgresURL"),
                System.getenv("postgresUsername"),System.getenv("postgresPassword"))){
            PreparedStatement st;
            if (locationValue.equals("")) {
                st = conn.prepareStatement("SELECT * FROM public." + tableName + " ORDER BY id ASC");
            } else {
                st = conn.prepareStatement("SELECT * FROM public." + tableName + " WHERE " + locationType + " = ? ORDER BY id ASC");
                st.setString(1, locationValue);
            }
            ResultSet rs = st.executeQuery();
            currentItems = new ArrayList<>();
            while (rs.next()) {
                if(tableName.equals("event")) {
                    currentItems.add(processEvent(rs));
                } else {
                    currentItems.add(processLandmark(rs));
                }
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        if(tableName.equals("event")) {
            ArrayList<Event> newCurrentEvents = new ArrayList<>();
            assert currentItems != null;
            for (Object item : currentItems) {
                newCurrentEvents.add((Event) item);
            }
            currentEvents = newCurrentEvents;
        } else {
            ArrayList<Landmark> newCurrentLandmarks = new ArrayList<>();
            assert currentItems != null;
            for (Object item : currentItems) {
                newCurrentLandmarks.add((Landmark) item);
            }
            currentLandmarks = newCurrentLandmarks;
        }
    }

    //insert PlaceToVisit to database
    public static void insertPlaceToVisit(ActionEvent event, String table, String name, String description, String country,
                                          String city, Integer price, String date, Integer sold,
                                          Integer capacity) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection conn = DriverManager.getConnection(System.getenv("postgresURL"),
                System.getenv("postgresUsername"),System.getenv("postgresPassword"))){
            PreparedStatement st;
                if(table.equals("event")) {
                    if (date == null) {
                        st = conn.prepareStatement("INSERT INTO public.event (name, description, country, city, price, date, sold, capacity) " +
                                "VALUES (?, ?, ?, ?, ?, null, ?, ?)");
                        if (sold == null) {st.setNull(6, java.sql.Types.INTEGER);}
                        else {st.setInt(6,sold);}
                        if (capacity == null) {st.setNull(7, java.sql.Types.INTEGER);}
                        else {st.setInt(7,capacity);}
                    } else {
                        st = conn.prepareStatement("INSERT INTO public.event (name, description, country, city, price, date, sold, capacity) " +
                                "VALUES (?, ?, ?, ?, ?, to_date(?, 'YYYY-MM-DD'), ?, ?)");
                        st.setString(6, date);
                        if (sold == null) {st.setNull(7, java.sql.Types.INTEGER);}
                        else {st.setInt(7,sold+1);}
                        if (capacity == null) {st.setNull(8, java.sql.Types.INTEGER);}
                        else {st.setInt(8,capacity);}
                    }
                    st.setString(1, name);
                    st.setString(2, description);
                    st.setString(3, country);
                    st.setString(4, city);
                    if(price == null){st.setNull(5, java.sql.Types.INTEGER);}
                    else{st.setInt(5, price);}

                }
                else{
                    st = conn.prepareStatement("INSERT INTO public.landmark (name, description, country, city, price)" +
                            " VALUES (?, ?, ?, ?, ?)");
                    st.setString(1, name);
                    st.setString(2, description);
                    st.setString(3, country);
                    st.setString(4, city);
                    if(price == null){st.setNull(5, java.sql.Types.INTEGER);}
                    else{st.setInt(5, price);}
                }
                int rows = st.executeUpdate();
                st.close();
                Operations.openNewScreen(event, "admin.fxml");
        } catch (SQLException e) {
            printSQLException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //return whether the PlaceToVisit is selected for trip by user or not
    public static Boolean getSelectedValue(PlaceToVisit place, String type){
        if(type.equals("event")){
            for (PlaceToVisit e: user.getSelectedEvents()){
                if(e.getId().equals(place.getId())){
                    return true;
                }
            }
        }
        else {
            for (PlaceToVisit l: user.getSelectedLandmarks()){
                if(l.getId().equals(place.getId())){
                    return true;
                }
            }
        }
        return false;
    }

    //select PlaceToVisit for user and update it in Java app
    public static void setSelectedValue(PlaceToVisit place, boolean newValue, String type) throws ClassNotFoundException {
        if (type.equals("event")) {
            ArrayList<PlaceToVisit> selectedEvents = user.getSelectedEvents();
            if (newValue) {
                selectedEvents.add(place);
                user.setTotalPrice(user.getTotalPrice()+place.getPrice());
            } else {
                for (PlaceToVisit e: user.getSelectedEvents()){
                    if(e.getId().equals(place.getId())){
                        selectedEvents.remove(e);
                        user.setTotalPrice(user.getTotalPrice() - place.getPrice());
                        break;
                    }
                }
            }
            user.setSelectedEvents(selectedEvents);
            updateDatabase(selectedEvents, null);
        } else {
            ArrayList<PlaceToVisit> selectedLandmarks = user.getSelectedLandmarks();
            if (newValue) {
                selectedLandmarks.add(place);
                user.setTotalPrice(user.getTotalPrice()+place.getPrice());
            } else {
                for (PlaceToVisit l: user.getSelectedLandmarks()){
                    if(l.getId().equals(place.getId())){
                        selectedLandmarks.remove(l);
                        user.setTotalPrice(user.getTotalPrice() - place.getPrice());
                        break;
                    }
                }
            }
            user.setSelectedLandmarks(selectedLandmarks);
            updateDatabase(null, selectedLandmarks);
        }
    }

    //update selected value in database
    private static void updateDatabase(ArrayList<PlaceToVisit> selectedEvents, ArrayList<PlaceToVisit> selectedLandmarks) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection conn = DriverManager.getConnection(System.getenv("postgresURL"),
                        System.getenv("postgresUsername"),System.getenv("postgresPassword"))){
            Statement stmt = conn.createStatement();
            String query = "UPDATE public.login SET ";
            if (selectedEvents != null) {
                String eventIds = selectedEvents.stream().map(place -> place.getId().toString()).collect(Collectors.joining(",", "{", "}"));
                query += "events = '" + eventIds + "'";
            }
            if (selectedLandmarks != null) {
                String landmarkIds = selectedLandmarks.stream().map(place -> place.getId().toString()).collect(Collectors.joining(",", "{", "}"));
                query += "landmarks = '" + landmarkIds + "'";
            }
            query += " WHERE username = '" + user.getUsername()+"'";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //SOQL exception
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
