package com.game.atomicbomber.model;

import com.badlogic.gdx.graphics.Texture;

import com.game.atomicbomber.model.game.Game;
import com.game.atomicbomber.view.GameMusic;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.io.IOException;
import java.nio.file.*;


public class User {
    private static User loggedInUser;
    private String username;
    private String password;
    private int score;
    private int highscore;
    private Avatar avatar;
    private GameInformation gameInfo;
    private ArrayList<GameData> games;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        this.highscore = 0;
        this.avatar = Avatar.AVATAR1;
        this.gameInfo = new GameInformation();
        this.games = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public void setUsername(String username) {
        File oldFile = new File("Data/Users/" + this.username);
        File newFile = new File("Data/Users/" + username);
        oldFile.renameTo(newFile);
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Texture getAvatar() {
        return avatar.getTexture();
    }
    public GameInformation getGameInfo() {
        return gameInfo;
    }
    public static User getUserByUsername(String username) {
        File file = new File("Data/Users/" + username + "/data.json");
        if(!file.exists())
            return null;
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(file);
            User user = gson.fromJson(reader, User.class);
            reader.close();
            return user;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        File folder = new File("Data/Users");
        for (File userFolder : Objects.requireNonNull(folder.listFiles(File::isDirectory))) {
            users.add(getUserByUsername(userFolder.getName()));
        }
        return users;
    }

    public void save() {
        File file = new File("Data/Users/" + username + "/data.json");
        Gson gson = new Gson();
        if(file.exists()) {
            file.delete();

        } else {
            file.getParentFile().mkdirs();
        }
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(this, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }
    public static void removeUser(User user) {
        try {
            deleteDirectory(Paths.get("Data/Users/" + user.getUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void deleteDirectory(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }
        Files.delete(path);
    }
    public int getKills() {

        int kills = 0;
        for(GameData game : games) {
            kills += game.getKills();
        }
        return kills;
    }
    public int getDifficulty() {
        int difficulty = 0;
        for(GameData game : games) {
            switch (game.getDifficulty()) {
                case EASY:
                    difficulty += game.getKills();
                    break;
                case MEDIUM:
                    difficulty += 2 * game.getKills();
                    break;
                case HARD:
                    difficulty += 3 * game.getKills();
                    break;
            }
        }
        return difficulty;
    }
    public void removeGame(Game game) {
        games.remove(game);
    }
    public int getAccuracy() {
        float accuracy = 0;
        for(GameData game : games) {
            accuracy += game.getAccuracy();
        }
        return (int)((accuracy / games.size()) * 100);
    }

}
