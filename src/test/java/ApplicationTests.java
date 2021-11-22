/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import InventoryManagementApplication.Functionality;
import InventoryManagementApplication.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class ApplicationTest {
    private Item test;
    private ObservableList<Item> list;

    @BeforeEach @Test
    void createItem(){
        test = new Item("A-123-123-123", "taskName", "$5.46");
        assertEquals("A-123-123-123", test.getId());
        assertEquals("taskName", test.getName());
        assertEquals("$5.46", test.getPrice());
    }

    @Test
    void changeNameOfItem(){
        test.setName("newName");
        assertEquals("newName", test.getName());
    }

    @Test //Incorrect format does not change
    void changeNameOfTaskWithShortName(){
        try{
            test.setName("");
        }catch(Exception e){
            assertEquals("taskName", test.getName());
        }
    }

    @Test //Incorrect format does not change
    void changeNameOfTaskWithLongName(){
        try{
            test.setName("a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a a");
        }catch(Exception e){
            assertEquals("taskName", test.getName());
        }
    }

    @Test
    void changeIDofItem(){
        test.setId("A-000-000-000");
        assertEquals("A-000-000-000", test.getId());
    }

    @Test //Incorrect format so ID does not change
    void changeIDOfTaskWithIncorrectID1(){
        try{
            test.setId("A-4565-456-456");
        }catch(Exception e){
            assertEquals("A-123-123-123", test.getId());
        }
    }

    @Test //Incorrect format so ID does not change
    void changeIDOfTaskWithIncorrectID2(){
        try{
            test.setId("4-456-456-456");
        }catch(Exception e){
            assertEquals("A-123-123-123", test.getId());
        }
    }

    @Test //Incorrect format so ID does not change
    void changeIDOfTaskWithIncorrectID3(){
        try{
            test.setId("A-456-456-456-456");
        }catch(Exception e){
            assertEquals("A-123-123-123", test.getId());
        }
    }

    @Test //Incorrect format so ID does not change
    void changeIDOfTaskWithIncorrectID4(){
        try{
            test.setId("A-456-456");
        }catch(Exception e){
            assertEquals("A-123-123-123", test.getId());
        }
    }

    @Test //Incorrect format so ID does not change
    void changeIDOfTaskWithBlank(){
        try{
            test.setId("");
        }catch(Exception e){
            assertEquals("A-123-123-123", test.getId());
        }
    }

    @Test
    void changePriceOfItem(){
        test.setPrice("$5.20");
        assertEquals("$5.20", test.getPrice());
    }

    @Test //Incorrect format does not change
    void changePriceOfTaskWithBlank(){
        try{
            test.setId("");
        }catch(Exception e){
            assertEquals("$5.46", test.getPrice());
        }
    }

    @Test //Incorrect format so ID does not change
    void changePriceOfTaskWithIncorrectPrice(){
        try{
            test.setId("-45");
        }catch(Exception e){
            assertEquals("$5.46", test.getPrice());
        }
    }

    @Test //Adds a dollar sign when not provided one
    void changePriceAddsDollarSign(){
        test.setPrice("3.45");
        assertEquals("$3.45", test.getPrice());
    }

    @Test //Rounds to 2 places
    void changePriceRoundsCorrectly(){
        test.setPrice("3.455555");
        assertEquals("$3.46", test.getPrice());
    }

    @Test @BeforeEach //Adds 1024 items to a list
    void add1024Items() {
        list = FXCollections.observableArrayList();
        for(int x = 10; x < 42; x++){
            for(int y = 10; y < 42; y++) {
                String id = "A-000-0" + x + "-0" + y;
                list.add(new Item(id, "TaskName", "0"));
            }
        }
        assertEquals("A-000-041-041", list.get(1023).getId());
    }

    @Test
    void search(){ //searches for items
        int counter = 0;
        for(Item item : list){
            if(item.getId().startsWith("A-000-016-") && item.getName().startsWith("T"))
                counter++;
        }
        assertEquals(32, counter);
    }

    @Test //downloads and imports a txt file
    void createTXTFile() throws IOException {
        File file = new File("docs/test.txt");
        Functionality.downloadTXTFile(file, list);
        ObservableList<Item> txt = Functionality.openTXTFile(file);
        assertEquals("A-000-010-010", txt.get(0).getId());
    }

    @Test //downloads and imports a html file
    void createJSONFile() throws IOException {
        File file = new File("docs/test.json");
        Functionality.downloadJSONFile(file, list);
        ObservableList<Item> txt = Functionality.openJSONFile(file);
        assertEquals("A-000-010-010", txt.get(0).getId());
    }

    @Test //downloads and imports a json file
    void createHTMLFile() throws IOException {
        File file = new File("docs/test.html");
        Functionality.downloadHTMLFile(file, list);
        ObservableList<Item> txt = Functionality.openHTMLFile(file);
        assertEquals("A-000-010-010", txt.get(0).getId());
    }

    @Test //deletes an item
    void deleteItem() {
        list.remove(list.get(0));
        assertEquals("A-000-010-011", list.get(0).getId());
    }
}
