package com.mapbook.mapbook;

import java.util.HashMap;

/**
 * Created by g090617 on 11/13/17.
 */

public class ZipCodeBook {
    String zipCode;
    String bookID;
    String bookStatus;

    public void addBookToZipCode(String zipCode, String bookID, String bookStatus){
        this.zipCode = zipCode;
        this.bookID = bookID;
        this.bookStatus = bookStatus;
    }
}
