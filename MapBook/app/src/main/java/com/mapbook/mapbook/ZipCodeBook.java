package com.mapbook.mapbook;

import java.util.HashMap;

/**
 * Created by g090617 on 11/13/17.
 */

public class ZipCodeBook {
    String zipCode;
    String bookID;
    String bookStatus;
    public HashMap<String, String> zipBookMap;

    public void addBookToZipCode(String zipCode, String bookID, String bookStatus){
        this.zipCode = zipCode;
        this.bookID = bookID;
        this.bookStatus = bookStatus;
        this.zipBookMap = new HashMap<>();

    }

    public void addBookToZip(String zipCode, String bookID){
        zipBookMap.put(zipCode, bookID);
    }
}
