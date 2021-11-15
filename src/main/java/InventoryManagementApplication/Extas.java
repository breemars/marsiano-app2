package InventoryManagementApplication;

public class Extas extends ApplicationController{
    //move method
    public static boolean isIdUnique(String id){
        for(Item item : list)
            if(item.getId().equals(id))
                return false;
        return true;
    }
}
