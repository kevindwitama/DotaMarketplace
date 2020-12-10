package com.ba11groupj.madproject.utils;

import com.ba11groupj.madproject.helpers.DBHelper;
import com.ba11groupj.madproject.models.User;

public class UserUtils {

    public boolean checkIfRegistered(String username, String password, DBHelper database) {
        for (User u : database.fetchUsers()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public int lookupUserId(String username, String password, DBHelper database) {
        for (User u : database.fetchUsers()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u.getId();
            }
        }
        return 0;
    }
}
