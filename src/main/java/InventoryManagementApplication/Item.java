package InventoryManagementApplication;
/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Breanna Marsiano
 */

import java.text.DecimalFormat;

public class Item {
    private String id;
    private String name;
    private String price;

    //Constructor
    public Item(String id, String name, String price){
        setId(id);
        setName(name);
        setPrice(price);
    }

    //Update ID of item
    //Format A-XXX-XXX-XXX and unique 0-234-678-101112
    public void setId(String id) throws ArrayIndexOutOfBoundsException{

        if(id.length() != 13 || !Character.isLetter(id.charAt(0)))
            throw new ArrayIndexOutOfBoundsException();

        for (int x = 0; x < 3; x++) {
            if (id.charAt((x * 4) + 1) != '-')
                throw new ArrayIndexOutOfBoundsException();

            for (int y = 1; y <= 3; y++) {
                char ch = id.charAt((x * 4) + 1 + y);
                if (!Character.isLetter(ch) && !Character.isDigit(ch))
                    throw new ArrayIndexOutOfBoundsException();
            }
        }
        if(!Extas.isIdUnique(id))
            throw new ArrayIndexOutOfBoundsException("GET FUCKED");

        this.id = id;
    }



    //Update name of item
    //2 and 256 characters inclusive
    public void setName(String name) throws StringIndexOutOfBoundsException{
        if(name.length() < 2 || name.length() > 256)
            throw new StringIndexOutOfBoundsException();
        this.name = name;
    }

    //Update price of task
    //0 or greater
    //works with dollar or no dollar sign
    public void setPrice(String price) throws NumberFormatException{

        if(price.length() == 0)
            throw new NumberFormatException();

        double priceDouble;
        if(price.charAt(0) == '$')
            priceDouble = Double.parseDouble(price.substring(1));
        else
            priceDouble = Double.parseDouble(price);

        if(priceDouble < 0)
            throw new NumberFormatException();

        DecimalFormat df = new DecimalFormat("###########################0.00");
        this.price = "$" + df.format(priceDouble);
    }

    //gets the task id
    public String getId(){
        return id;
    }


    //gets the task name
    public String getName(){
        return name;
    }

    //gets the task price
    public String getPrice(){
        return price;
    }

}
