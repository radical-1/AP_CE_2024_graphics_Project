package com.game.atomicbomber.controller;

import com.game.atomicbomber.model.User;
import com.game.atomicbomber.view.ValidInputs;

import java.util.Objects;

public class ProfileMenuController {
    public static String changeUsername(String username) {
        if (!ValidInputs.USERNAME_LENGTH.isMatch(username)) {
            return "Username is too short. It must be at least 3 characters long.";
        }
        if (!ValidInputs.ALPHANUMERIC.isMatch(username)) {
            return "Username must contain only alphanumeric characters.";
        }
        User user = User.getLoggedInUser();
        if (user.getUsername().equals(username)) {
            return "Username is the same as the old one.";
        }
        if(User.getUserByUsername(username) != null) {
            return "Username already exists.";
        }
        user.setUsername(username);
        user.save();
        return "Username Changed Successfully.";
    }

    public static String changePassword(String password) {
        User user = User.getLoggedInUser();
        if (!ValidInputs.PASSWORD_LENGTH.isMatch(password)) {
            return "Password is too short. It must be at least 8 characters long.";
        }

        if (!ValidInputs.LOWERCASE.isMatch(password)) {
            return "Password must contain at least one lowercase letter.";
        }

        if (!ValidInputs.UPPERCASE.isMatch(password)) {
            return "Password must contain at least one uppercase letter.";
        }

        if (!ValidInputs.NUMBER.isMatch(password)) {
            return "Password must contain at least one number.";
        }

        if (!ValidInputs.SPECIAL_CHAR.isMatch(password)) {
            return "Password must contain at least one special character (!@#$%^&*()).";
        }
        if (user.getPassword().equals(password)) {
            return "Password is the same as the old one.";
        }
        user.setPassword(password);
        user.save();
        return "Password Changed Successfully.";

    }

    public static void removeAccount(String username) {
        User.removeUser(Objects.requireNonNull(User.getUserByUsername(username)));
    }

    public static void logout() {
        User.setLoggedInUser(null);
    }
}
