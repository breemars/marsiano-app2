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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ApplicationController {
    protected static final ObservableList<Item> list = FXCollections.observableArrayList(); //Holds list of user's Tasks

    // GUI Fields
    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> idColumn;
    @FXML private TableColumn<Item, String> nameColumn;
    @FXML private TableColumn<Item, String> priceColumn;

    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField priceField;

    @FXML private TextField searchIdField;
    @FXML private TextField searchNameField;

    @FXML private Label statusMessageTxt;

    @FXML private VBox vBoxMain;

    //Create new task with information given and add to table
    //throw error if any fields blank
    //throw error if invalid formats
    //throw error if id not unique
    @FXML private void newItem() {

        try {
            //Create new item and add to table
            Item newItem = new Item(idField.getText(), nameField.getText(), priceField.getText());
            list.add(newItem);
            tableView.setItems(list);

            //Update status
            statusMessageTxt.setText(":D");

            //Clear text fields ADD MORE
            //nameField.setText("");

        //ERRORS
        }catch (ArrayIndexOutOfBoundsException e){ //ERROR - ID field invalid
            statusMessageTxt.setText("INVALID ID - MUST BE M/D/YYYY OR BLANK");

        }catch (StringIndexOutOfBoundsException e){ //NAME
            statusMessageTxt.setText("INVALID NAME - Must be between 2 and 256 characters");

        }catch (NumberFormatException e){ //ERROR - PRICE field invalid
            statusMessageTxt.setText("INVALID PRICE - MUST BE M/D/YYYY OR BLANK");
        }
    }

    //Opens window for finding a file and imports list data into chart
    //throw error if file invalid
    @FXML private void openList() {
        //Open window
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TextFile", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        //Import File Data
        try(Scanner reader = new Scanner(selectedFile)){

            ObservableList<Item> newList = FXCollections.observableArrayList();
            //ObservableList<Item> backupList = list; nope its just a pointer
            list.clear();

            while (reader.hasNextLine()) {
                String id = reader.nextLine();
                String name = reader.nextLine();
                String price = reader.nextLine();
                newList.add(new Item(id, name, price));
                System.out.println(id + " " + name + " " + price);
            }
            //Update Chart and Status
          //  list.clear();
           list.addAll(newList);
           tableView.setItems(list);
            statusMessageTxt.setText("Opened " + selectedFile);

        } catch (Exception e) { //ERROR - file invalid
            statusMessageTxt.setText("INVALID FILE - Must be in proper format");
           // statusMessageTxt.setStyle("-fx-text-fill: red;"); it does work doe, create seperate method?
        }
    }

    //opens download pop up and exports data
    //throws error if file invalid
    @FXML private void downloadList() {

        //Opens window
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("myList.txt");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TextFile", "*.txt"));

        File selectedFile = fileChooser.showSaveDialog(stage);

        //Write data to txt file
        try (FileWriter writer = new FileWriter(selectedFile)) {
            for (Item item : list) {
                writer.write(item.getId() + "\n");
                writer.write(item.getName() + "\n");
                writer.write(item.getPrice() + "\n");
            }
            statusMessageTxt.setText("Downloaded " + selectedFile);

        }catch (IOException e){ //ERROR - file invalid
            statusMessageTxt.setText("INVALID FILE");

        }
    }

    //Deletes a task from the chart
    //throws error if no task selected
    @FXML private void deleteItem(){
        try {
            //Remove task
            Item itemSelected = tableView.getSelectionModel().getSelectedItem();
            list.remove(itemSelected);
            tableView.setItems(list);

            //Update status
            statusMessageTxt.setText("B-) Deleted " + itemSelected.getName());

        }catch (Exception e){ //ERROR
            statusMessageTxt.setText("ERROR - no item selected");
        }

    }

    //Deletes all tasks from chart
    @FXML private void clearAll() {
        ObservableList<Item> allTasks = tableView.getItems();
        allTasks.clear();
        list.clear();
        statusMessageTxt.setText("all items deleted");
    }

    //Changes the id of a task in the chart
    //throws error if not correct format/blank
    @FXML private void changeId(CellEditEvent<Item, String> cell) {
        Item itemSelected =  tableView.getSelectionModel().getSelectedItem();

        try {
            itemSelected.setId(cell.getNewValue());
            statusMessageTxt.setText(":D");

        }catch (ArrayIndexOutOfBoundsException e){ //ERROR - ID field invalid
            statusMessageTxt.setText("INVALID ID - MUST BE M/D/YYYY OR BLANK");
            list.remove(itemSelected);
            list.add(new Item(itemSelected.getId(), itemSelected.getName(), itemSelected.getPrice()));
        }

    }

    //Changes the name of a task in the chart
    //throws error if name is blank
    //throws error if name too short/long
    @FXML private void changeName(CellEditEvent<Item, String> cell) {
        Item itemSelected =  tableView.getSelectionModel().getSelectedItem();

        try {
            itemSelected.setName(cell.getNewValue());
            statusMessageTxt.setText(":D");

        }catch (StringIndexOutOfBoundsException e){
            statusMessageTxt.setText("INVALID NAME - Must be between 2 and 256 characters");
            list.remove(itemSelected);
            list.add(new Item(itemSelected.getId(), itemSelected.getName(), itemSelected.getPrice()));
            //tableView.setItems(list);
        }
    }

    //Changes the date of a task in the chart
    //throws error if price is invalid/blank
    @FXML private void changePrice(CellEditEvent<Item, String> cell) {
        Item taskSelected = tableView.getSelectionModel().getSelectedItem();

        try {
            taskSelected.setPrice(cell.getNewValue());
            list.remove(taskSelected);
            list.add(new Item(taskSelected.getId(), taskSelected.getName(), taskSelected.getPrice()));
            statusMessageTxt.setText(":D");

        }catch (NumberFormatException e){ //ERROR - PRICE field invalid
            statusMessageTxt.setText("INVALID PRICE - MUST BE M/D/YYYY OR BLANK");
            list.remove(taskSelected);
            list.add(new Item(taskSelected.getId(), taskSelected.getName(), taskSelected.getPrice()));
        }
    }

    //searches chart by ID and shows results
    @FXML private void searchById(){
        statusMessageTxt.setText("searching... clear field to reset");

        ObservableList<Item> search = FXCollections.observableArrayList();

        for(Item task : list){
            if(task.getId().startsWith(searchIdField.getText()))
                search.add(task);
        }
        tableView.setItems(search);
    }

    //searches chart by name and shows results
    @FXML private void searchByName(){

        statusMessageTxt.setText("searching... clear field to reset");

        ObservableList<Item> search = FXCollections.observableArrayList();

        for(Item task : list){
            if(task.getName().startsWith(searchNameField.getText()))
                search.add(task);
        }
        tableView.setItems(search);
    }

    //Update the progress bar when changes are made
    @FXML private void darkMode(){
        if(vBoxMain.getStylesheets().get(0).equals("style.css"))
            vBoxMain.getStylesheets().add("styleDarkMode.css");
        else
            vBoxMain.getStylesheets().add("style.css");

        vBoxMain.getStylesheets().remove(0);
    }

    //Initial construction of the table
    public void initialize() {
        //set up the columns in the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Name and date fields editable
        tableView.setEditable(true);
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());

    }
}
