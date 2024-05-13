package com.game.atomicbomber.controller;


import com.game.atomicbomber.model.User;
import com.game.atomicbomber.view.ValidInputs;

public class RegisterLoginController {
    public static void register(String username, String password) {
        User user = new User(username, password);
        user.save();
    }

    public static void login(String username, String password) {
        User.setLoggedInUser(User.getUserByUsername(username));
    }

    public static String validatePasswordStrength(String password) {
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

        return "Password is strong.";
    }

    public static String validateUsername(String username) {
        if (!ValidInputs.USERNAME_LENGTH.isMatch(username)){
            return "Username is too short. It must be at least 3 characters long.";
        }

        if (!ValidInputs.ALPHANUMERIC.isMatch(username)) {
            return "Username must contain only alphanumeric characters.";
        }

        return "Username is valid.";
    }

}
