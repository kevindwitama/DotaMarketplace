package com.github.kevindwitama.dotamarketplace.utils;

import com.github.kevindwitama.dotamarketplace.helpers.DBHelper;
import com.github.kevindwitama.dotamarketplace.models.User;

/**
 * Final Project ISYS6203 Mobile Application Development
 * Lab BL11 / XB11
 * <p>
 * Dota Marketplace
 * <p>
 * Contributed by
 * 2201825535 - Kevin Dwitama Putra
 * 2201836330 - Natasha Anugrah
 */

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
