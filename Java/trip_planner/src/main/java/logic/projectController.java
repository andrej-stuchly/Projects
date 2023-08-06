package logic;

import entity.*;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class projectController {
    @FXML
    private TextField destinationText;

    //search events and landmarks by city
    @FXML
    protected void onClickSearch() throws SQLException, ClassNotFoundException {
        String city = destinationText.getText();
        Operations.currentLocations = new ArrayList<>();
        Operations.getItems("event", "city", city);
        initializeEvents();
        Operations.getItems("landmark", "city", city);
        initializeLandmarks();
    }

    //search events and landmarks by country
    @FXML
    protected void onClickDeepSearch() throws SQLException, ClassNotFoundException {
        String country = destinationText.getText();
        if(Operations.currentLocations == null) {
            Operations.currentLocations = new ArrayList<>();
        }
        Operations.getItems("event", "country", country);
        initializeEvents();
        Operations.getItems("landmark", "country", country);
        initializeLandmarks();
    }

    @FXML private TableView<Event> eventTable;
    @FXML private TableColumn<Event, String> eventName;
    @FXML private TableColumn<Event, String> eventDescription;
    @FXML private TableColumn<Event, String> eventCity;
    @FXML private TableColumn<Event, String> eventDate;
    @FXML private TableColumn<Event, Integer> eventPrice;
    @FXML private TableColumn<Event, String> eventTickets;
    @FXML private TableColumn<PlaceToVisit, Boolean> selectedEvent;


    ObservableList<Event> eventData = FXCollections.observableArrayList();

    //initialize values in Events table
    public void initializeEvents() {
        eventTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        eventData.clear();
        if (Operations.currentEvents != null && Operations.currentEvents.size() > 0) {
            eventData.addAll(Operations.currentEvents);
            eventTable.setItems(eventData);
            eventName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            eventDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
            eventDescription.setPrefWidth(350);
            eventDescription.setMinWidth(200);
            eventCity.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Event, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Event, String> cellData) {
                    String city = cellData.getValue().getLocation().getCity();
                    return new SimpleStringProperty(city);
                }
            });
            eventDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
            eventPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
            eventTickets.setCellValueFactory(cellData -> {
                Event event = cellData.getValue();
                String ticketsSold = String.valueOf(event.getTicketsSold());
                String capacity = String.valueOf(event.getCapacity());
                String value = ticketsSold + " / " + capacity;
                return new ReadOnlyStringWrapper(value);
            });
            selectedEvent.setCellFactory(col -> {
                CheckBoxTableCell<PlaceToVisit, Boolean> cell = new CheckBoxTableCell<>() {
                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            PlaceToVisit place = getTableView().getItems().get(getIndex());
                            boolean isSelected = Operations.getSelectedValue(place, "event");

                            CheckBox checkBox = new CheckBox();
                            checkBox.setSelected(isSelected);
                            checkBox.setOnAction(evt -> {
                                boolean newValue = checkBox.isSelected();
                                try {
                                    Operations.setSelectedValue(place, newValue, "event");
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            setGraphic(checkBox);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }
                    }
                };
                cell.setEditable(true);
                return cell;
            });
        }
    }

    @FXML private TableView<Landmark> landmarkTable;
    @FXML private TableColumn<Landmark, String> landmarkName;
    @FXML private TableColumn<Landmark, String> landmarkDescription;
    @FXML private TableColumn<Landmark, String> landmarkCity;
    @FXML private TableColumn<Landmark, Integer> landmarkPrice;
    @FXML private TableColumn<PlaceToVisit, Boolean> selectedLandmark;

    ObservableList<Landmark> landmarkData = FXCollections.observableArrayList();

    //initialize values in Landmarks table
    public void initializeLandmarks() {
        landmarkTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        landmarkData.clear();
        if (Operations.currentLandmarks != null && Operations.currentLandmarks.size() > 0) {
            landmarkData.addAll(Operations.currentLandmarks);
            landmarkTable.setItems(landmarkData);
            landmarkName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            landmarkDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
            landmarkDescription.setPrefWidth(450);
            landmarkDescription.setMinWidth(300);
            landmarkCity.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Landmark, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Landmark, String> cellData) {
                    String city = cellData.getValue().getLocation().getCity();
                    return new SimpleStringProperty(city);
                }
            });
        landmarkPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        selectedLandmark.setCellFactory(col -> {
            CheckBoxTableCell<PlaceToVisit, Boolean> cell = new CheckBoxTableCell<>() {
                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            PlaceToVisit place = getTableView().getItems().get(getIndex());
                            boolean isSelected = Operations.getSelectedValue(place, "landmark");

                            CheckBox checkBox = new CheckBox();
                            checkBox.setSelected(isSelected);
                            checkBox.setOnAction(evt -> {
                                boolean newValue = checkBox.isSelected();
                                try {
                                    Operations.setSelectedValue(place, newValue, "landmark");
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            setGraphic(checkBox);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }
                    }
                };
                cell.setEditable(true);
                return cell;
            });

    }
}

    @FXML
    private Button adminButton;

    //set visibility of admin tab
    public void initialize() throws SQLException, ClassNotFoundException {
        if(Operations.user.getUsername().equals("admin")) {
            adminButton.setVisible(true);
        }
        onClickDeepSearch();
    }

    //redirect to your profile
    @FXML
    public void onClickOpenProfile(ActionEvent event) throws IOException {
        Operations.openNewScreen(event, "profile.fxml");
    }

    //redirect to admin tab
    @FXML
    public void onClickOpenAdmin(ActionEvent event) throws IOException {
        Operations.openNewScreen(event, "admin.fxml");
    }
}