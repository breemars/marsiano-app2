package InventoryManagementApplication;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Item {
    private String id;
    private SimpleStringProperty name; //or String?
    private String price;

    //Constructor
    public Item(String id, String name, String price){
    }

    //Update ID of item
    //Format A-XXX-XXX-XXX and unique
    public void setId(String id){

    }

    //Update name of item
    //2 and 256 characters inclusive
    public void setName(String name){

    }

    //Update price of task
    //0 or greater
    //works with dollar or no dollar sign
    public void setPrice(String price){

    }

    //gets the task id
    public String getId(){
        return "";
    }


    //gets the task name
    public String getName(){
        return "";
    }

    //gets the task price
    public String getPrice(){
        return "";
    }

}
