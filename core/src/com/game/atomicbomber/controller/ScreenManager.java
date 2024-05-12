package com.game.atomicbomber.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.view.screen.*;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private static ScreenManager instance;
    private AtomicBomber game;
    private Map<String, Screen> screens;

    private ScreenManager() {
        screens = new HashMap<>();

    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(AtomicBomber game) {
        this.game = game;
    }

    public void addScreen(String name) {
        switch (name) {
            case "RegisterScreen" -> screens.put(name, new RegisterScreen(game));
            case "MainMenuScreen" -> screens.put(name, new MainMenuScreen(game));
            case "MainGameScreen" -> screens.put(name, new MainGameScreen(game));
            case "AvatarMenuScreen" -> screens.put(name, new AvatarMenuScreen(game));
            case "ProfileMenuScreen" -> screens.put(name, new ProfileMenuScreen(game));
            case "SettingMenuScreen" -> screens.put(name, new SettingMenuScreen(game));
            case "LeaderBoardScreen" -> screens.put(name, new LeaderBoardScreen(game));
            default -> throw new IllegalArgumentException("Invalid screen name");
        }
    }

    public void setScreen(String name) {
        if (!screens.containsKey(name)) {
            getInstance().addScreen(name);
        }
        game.setScreen(screens.get(name));
    }
    public void removeScreen(String name) {
        screens.remove(name);
    }
}