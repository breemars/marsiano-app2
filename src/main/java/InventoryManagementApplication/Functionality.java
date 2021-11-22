package InventoryManagementApplication;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Functionality extends ApplicationController {

    //Import TXT File
    public static ObservableList<Item> openTXTFile(File selectedFile) throws FileNotFoundException {

        try (Scanner reader = new Scanner(selectedFile)) {
            ObservableList<Item> newList = FXCollections.observableArrayList();
            reader.nextLine();

            while (reader.hasNextLine()) {
                String id = reader.next();
                String name = reader.next();
                String price = reader.next();
                newList.add(new Item(id, name, price));
            }
            return newList;
        }
    }

    //Export TXT File
    public static void downloadTXTFile(File selectedFile, ObservableList<Item> list) throws IOException {
        try (FileWriter writer = new FileWriter(selectedFile)) {
            writer.write("Serial Number\tName\tValue");
            for (Item item : list) {
                writer.write("\n" + item.getId() + "\t");
                writer.write(item.getName() + "\t");
                writer.write(item.getPrice());
            }
        }
    }

    //Import JSON File - Read data from json file into a list of objects
    public static ObservableList<Item> openJSONFile(File selectedFile) throws IOException {
        List<String> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                if (line.startsWith("  {")) {
                    line = line + br.readLine() + br.readLine() + br.readLine() + br.readLine();
                    if (line.charAt(line.length() - 1) == ',')
                        line = line.substring(0, line.length() - 1);
                    data.add(line);
                }
            }
        }
        ObservableList<Item> items = FXCollections.observableArrayList();
        Gson gson = new Gson();

        for (String x : data)
            items.add(gson.fromJson(x, Item.class));

        return items;
    }

    //Export JSON File
    public static void downloadJSONFile(File selectedFile, ObservableList<Item> list) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();

        try (FileWriter writer = new FileWriter(selectedFile)) {
            writer.write(gson.toJson(list));
        }
    }

    //Import HTML File
    public static ObservableList<Item> openHTMLFile(File selectedFile) throws FileNotFoundException {
        try (Scanner reader = new Scanner(selectedFile)) {
            ObservableList<Item> newList = FXCollections.observableArrayList();
            int counter = 1;
            String id = null;
            String name = null;

            while (reader.hasNextLine()) {
                if(reader.next().equals("<td>")) {
                    if(counter == 1) {
                        id = reader.next();
                        counter++;
                    }else if(counter == 2) {
                        name = reader.next();
                        counter++;
                    }else if(counter == 3){
                        String price = reader.next();
                        newList.add(new Item(id, name, price));
                        counter = 1;
                    }
                }
            }
            return newList;
        }
    }

    //Export HTML File
    public static void downloadHTMLFile(File selectedFile, ObservableList<Item> list) throws IOException {

        try (FileWriter writer = new FileWriter(selectedFile)) {
            writer.write("<!DOCTYPE html>\n<html>\n<body>\n<table>\n");

            for (Item item : list) {
                writer.write("<tr>\n<td> " + item.getId() + " </td>\n");
                writer.write("<td> " + item.getName() + " </td>\n");
                writer.write("<td> " + item.getPrice() + " </td>\n</tr>\n");
            }
            writer.write("<table>\n</html>\n</body>");
        }
    }

    //Check to see if ID is unique
    public static boolean isIdUnique(String id) {
        for (Item item : list)
            if (item.getId().equals(id))
                return false;
        return true;
    }

}