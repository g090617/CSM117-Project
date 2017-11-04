package com.mapbook.mapbook;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by g090617 on 11/3/17.
 */

@IgnoreExtraProperties
public class User {
    public String email;
    public String userID;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userID, String email) {
        this.email = email;
        this.userID = userID;
    }
}
