package com.game.atomicbomber.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ValidInputs {
    LOWERCASE(".*[a-z].*"),
    UPPERCASE(".*[A-Z].*"),
    NUMBER(".*[0-9].*"),
    SPECIAL_CHAR(".*[!@#$%^&*()].*"),
    ALPHANUMERIC("[a-zA-Z0-9]*"),
    USERNAME("[a-zA-Z0-9]*"),
    PASSWORD(".*[a-z].*.*[A-Z].*.*[0-9].*.*[!@#$%^&*()].*"),
    USERNAME_LENGTH("^[a-zA-Z0-9]{3,}$"),
    PASSWORD_LENGTH("^[a-zA-Z0-9!@#$%^&*()]{8,}$"),
    ;
    String regex;
    ValidInputs(String regex) {
        this.regex = regex;
    }
    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.matches();
        return matcher;
    }
    public boolean isMatch(String input) {
        return getMatcher(input).matches();
    }
    public boolean isFind(String input) {
        return getMatcher(input).find();
    }
}
