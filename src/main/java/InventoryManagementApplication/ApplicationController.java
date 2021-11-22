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
import java.io.IOException;


public class ApplicationController {
    protected static final ObservableList<Item> list = FXCollections.observableArrayList(); //Holds list of user's items

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

    //Create new item with information given and add to table
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

            //Clear text fields
            idField.setText("");
            nameField.setText("");
            priceField.setText("");

        //ERRORS
        }catch (ArrayIndexOutOfBoundsException e){ //ERROR - ID field invalid
            statusMessageTxt.setText("BLANK OR INVALID ID - MUST BE IN A-XXX-XXX-XXX FORMAT (A=Letter, X=Letter/Digit) AND UNIQUE FOR EACH ITEM");

        }catch (StringIndexOutOfBoundsException e){ //NAME
            statusMessageTxt.setText("BLANK OR INVALID NAME - Must be between 2 and 256 characters");

        }catch (NumberFormatException e){ //ERROR - PRICE field invalid
            statusMessageTxt.setText("BLANK OR INVALID PRICE - MUST BE NUMERIC");
        }
    }

    //Opens window for finding a file and imports list data into chart
    //throw error if file invalid
    @FXML private void openList() {
        //Open window
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TextFile", "*.txt"),
                        new FileChooser.ExtensionFilter("JSON", "*.json"),
                        new FileChooser.ExtensionFilter("HTML", "*.html"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        String fileType = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);

        //Update Chart and Status
         try {
             list.clear();
             switch (fileType) {
                 case "txt" -> list.addAll(Functionality.openTXTFile(selectedFile));
                 case "json" -> list.addAll(Functionality.openJSONFile(selectedFile));
                 case "html" -> list.addAll(Functionality.openHTMLFile(selectedFile));
                 default -> throw new IOException();
             }
             tableView.setItems(list);
             statusMessageTxt.setText("Opened " + selectedFile);

         }catch(Exception e){ //ERROR - file invalid
            statusMessageTxt.setText("INVALID FILE - Must be in proper format");
        }
    }

    //opens download pop up and exports data
    //throws error if file invalid
    @FXML private void downloadList() {

        //Opens window
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("myList.txt");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TextFile", "*.txt"),
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("HTML", "*.html"));
        File selectedFile = fileChooser.showSaveDialog(stage);
        String fileType = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);

        //Write data to file
        try{
            switch (fileType) {
                case "txt" -> Functionality.downloadTXTFile(selectedFile, list);
                case "json" -> Functionality.downloadJSONFile(selectedFile, list);
                case "html" -> Functionality.downloadHTMLFile(selectedFile, list);
                default -> throw new IOException();
            }
            statusMessageTxt.setText("Downloaded " + selectedFile);

        }catch (IOException e){ //ERROR - file invalid
            statusMessageTxt.setText("INVALID FILE");
        }
    }

    //Deletes an item from the chart
    //throws error if no item selected
    @FXML private void deleteItem(){
        try {
            //Remove item
            Item itemSelected = tableView.getSelectionModel().getSelectedItem();
            list.remove(itemSelected);
            tableView.setItems(list);

            //Update status
            statusMessageTxt.setText("B-) Deleted " + itemSelected.getName());

        }catch (Exception e){ //ERROR
            statusMessageTxt.setText("ERROR - no item selected");
        }

    }

    //Deletes all items from chart
    @FXML private void clearAll() {
        ObservableList<Item> all = tableView.getItems();
        all.clear();
        list.clear();
        statusMessageTxt.setText("all items deleted");
    }

    //Changes the id of an item in the chart
    //throws error if not correct format/blank
    @FXML private void changeId(CellEditEvent<Item, String> cell) {
        Item itemSelected =  tableView.getSelectionModel().getSelectedItem();

        try {
            itemSelected.setId(cell.getNewValue());
            statusMessageTxt.setText(":D");

        }catch (ArrayIndexOutOfBoundsException e){ //ERROR - ID field invalid
            statusMessageTxt.setText("BLANK OR INVALID ID - MUST BE IN A-XXX-XXX-XXX FORMAT (A=Letter, X=Letter/Digit) AND UNIQUE FOR EACH ITEM");
            list.remove(itemSelected);
            list.add(new Item(itemSelected.getId(), itemSelected.getName(), itemSelected.getPrice()));
        }

    }

    //Changes the name of an item in the chart
    //throws error if name is blank
    //throws error if name too short/long
    @FXML private void changeName(CellEditEvent<Item, String> cell) {
        Item itemSelected =  tableView.getSelectionModel().getSelectedItem();

        try {
            itemSelected.setName(cell.getNewValue());
            statusMessageTxt.setText(":D");

        }catch (StringIndexOutOfBoundsException e){
            statusMessageTxt.setText("BLANK OR INVALID NAME - Must be between 2 and 256 characters");
            list.remove(itemSelected);
            list.add(new Item(itemSelected.getId(), itemSelected.getName(), itemSelected.getPrice()));
        }
    }

    //Changes the date of an item in the chart
    //throws error if price is invalid/blank
    @FXML private void changePrice(CellEditEvent<Item, String> cell) {
        Item itemSelected = tableView.getSelectionModel().getSelectedItem();

        try {
            itemSelected.setPrice(cell.getNewValue());
            list.remove(itemSelected);
            list.add(new Item(itemSelected.getId(), itemSelected.getName(), itemSelected.getPrice()));
            statusMessageTxt.setText(":D");

        }catch (NumberFormatException e){ //ERROR - PRICE field invalid
            statusMessageTxt.setText("BLANK OR INVALID PRICE - MUST BE NUMERIC");
            list.remove(itemSelected);
            list.add(new Item(itemSelected.getId(), itemSelected.getName(), itemSelected.getPrice()));
        }
    }

    //searches chart by name and/or ID then shows results
    @FXML private void search(){

        statusMessageTxt.setText("searching... clear fields to reset");
        ObservableList<Item> search = FXCollections.observableArrayList();

        for(Item item : list){
            if(item.getId().startsWith(searchIdField.getText()) && item.getName().startsWith(searchNameField.getText()))
                search.add(item);
        }
        tableView.setItems(search);
    }

    //Changes the color scheme from light to dark
    @FXML private void darkMode(){

        if(vBoxMain.getStylesheets().get(0).equals("styleDarkMode.css"))
            vBoxMain.getStylesheets().add("style.css");
        else
            vBoxMain.getStylesheets().add("styleDarkMode.css");

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
