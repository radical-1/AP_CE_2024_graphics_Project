package com.game.atomicbomber.controller;

import com.game.atomicbomber.model.User;

import java.util.ArrayList;

public class LeaderBoardController {
    public static ArrayList<User> sortUsersByKills(ArrayList<User> users) {
        users.sort((o1, o2) -> o2.getKills() - o1.getKills());
        return users;
    }
    public static ArrayList<User> sortUserByDifficulty(ArrayList<User> users) {
        users.sort((o1, o2) -> o2.getDifficulty() - o1.getDifficulty());
        return users;
    }
    public static ArrayList<User> sortByAccuracy(ArrayList<User> users) {
        users.sort((o1, o2) -> o2.getAccuracy() - o1.getAccuracy());
        return users;
    }
}
