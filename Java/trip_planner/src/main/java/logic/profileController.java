package logic;

import entity.PlaceToVisit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class profileController {
    @FXML private TableView<PlaceToVisit> table;
    ObservableList<PlaceToVisit> data = FXCollections.observableArrayList();
    @FXML private TableColumn<PlaceToVisit, String> name;
    @FXML private TableColumn<PlaceToVisit, Integer> price;
    @FXML private Label priceLabel;

    //initialize values for tables of the PlacesToVisit of user
    @FXML
    public void initialize() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        priceLabel.setText("The price for your trip: "+Integer.toString(Operations.user.getTotalPrice()));
        data.clear();
        if (Operations.user.getSelectedEvents() != null && Operations.user.getSelectedEvents().size() > 0) {
            data.addAll(Operations.user.getSelectedEvents());
            table.setItems(data);
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        if (Operations.user.getSelectedLandmarks() != null && Operations.user.getSelectedLandmarks().size() > 0) {
            data.addAll(Operations.user.getSelectedLandmarks());
            table.setItems(data);
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        int size = Operations.user.getSelectedEvents().size()+Operations.user.getSelectedLandmarks().size();
        table.setFixedCellSize(25);
        table.setPrefHeight(table.getFixedCellSize() * (size+1));
    }

    //redirect to main screen
    @FXML
    public void onClickReturnToProject(ActionEvent event) throws IOException {
        Operations.openNewScreen(event, "project.fxml");
    }
}
