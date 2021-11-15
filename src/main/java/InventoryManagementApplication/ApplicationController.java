package InventoryManagementApplication;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ApplicationController {
    private final ObservableList<Item> list = FXCollections.observableArrayList(); //Holds list of user's Tasks

    // GUI Fields
    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> idColumn;
    @FXML private TableColumn<Item, String> nameColumn;
    @FXML private TableColumn<Item, String> priceColumn;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private Label statusMessageTxt;

    //Create new task with information given and add to table
    //throw error if any fields blank
    //throw error if invalid formats
    //throw error if id not unique
    @FXML private void newItem() {


    }

    //Opens window for finding a file and imports list data into chart
    //throw error if file invalid
    @FXML private void openList() {


    }

    //opens download pop up and exports data
    //throws error if file invalid
    @FXML private void downloadList() {


    }


    //Deletes a task from the chart
    //throws error if no task selected
    @FXML private void deleteItem(){

    }

    //Deletes all tasks from chart
    @FXML private void clearAll() {

    }

    //Changes the id of a task in the chart
    //throws error if not correct format/blank
    @FXML private void changeID(CellEditEvent<Item, String> cell) {

    }

    //Changes the name of a task in the chart
    //throws error if name is blank
    //throws error if name too short/long
    @FXML private void changeName(CellEditEvent<Item, String> cell) {

    }

    //Changes the date of a task in the chart
    //throws error if price is invalid/blank
    @FXML private void changePrice(CellEditEvent<Item, String> cell) {

    }

    //searches chart by ID and shows results
    @FXML private void searchById(){

    }

    //searches chart by name and shows results
    @FXML private void searchByName(){

    }

    //Update the progress bar when changes are made
    @FXML private void darkMode(){

    }

    //Initial construction of the table
    public void initialize() {

    }
}
