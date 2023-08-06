package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;


public class adminController {
    @FXML private RadioButton event;
    @FXML private RadioButton landmark;
    @FXML
    private final ToggleGroup toggleGroup = new ToggleGroup();
    @FXML
    private TextField nameText;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField countryText;
    @FXML
    private TextField cityText;
    @FXML
    private TextField priceText;
    @FXML
    private HBox dateBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private HBox soldBox;
    @FXML
    private TextField soldText;
    @FXML
    private HBox capacityBox;
    @FXML
    private TextField capacityText;

    //initialize table in admin tab to insert data
    @FXML
    private void initialize() {
        event.setToggleGroup(toggleGroup);
        landmark.setToggleGroup(toggleGroup);
        event.setSelected(true);

        dateBox.managedProperty().bind(dateBox.visibleProperty());
        dateBox.visibleProperty().bind(event.selectedProperty());

        soldBox.managedProperty().bind(soldBox.visibleProperty());
        soldBox.visibleProperty().bind(event.selectedProperty());

        capacityBox.managedProperty().bind(capacityBox.visibleProperty());
        capacityBox.visibleProperty().bind(event.selectedProperty());

        landmark.selectedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                datePicker.setValue(null);
                soldText.clear();
                capacityText.clear();
            }
        });
    }

    //control input values, send to insert to database
    @FXML
    private void insertPlaceToVisit(ActionEvent evt) throws ClassNotFoundException {
        String table = "event";
        RadioButton button = (RadioButton) toggleGroup.getSelectedToggle();
        if(button.getText().equals("Landmark")){
            table = "landmark";
        }
        String name = nameText.getText().isEmpty() ? null : nameText.getText();
        String description = descriptionText.getText().isEmpty() ? null : descriptionText.getText();
        String country = countryText.getText().isEmpty() ? null : countryText.getText();
        String city = cityText.getText().isEmpty() ? null : cityText.getText();
        Integer price = priceText.getText().isEmpty() ? null : Integer.parseInt(priceText.getText());
        String date = datePicker.getValue() == null ? null : datePicker.getValue().toString(); //zly typ
        Integer sold = soldText.getText().isEmpty() ? null : Integer.parseInt(soldText.getText());
        Integer capacity = capacityText.getText().isEmpty() ? null : Integer.parseInt(capacityText.getText());
        Operations.insertPlaceToVisit(evt, table, name, description, country, city, price, date, sold, capacity);
    }

    //navigation
    @FXML
    public void onClickReturnToProject(ActionEvent event) throws IOException {
        Operations.openNewScreen(event, "project.fxml");
    }
}
