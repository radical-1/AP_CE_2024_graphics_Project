package com.game.atomicbomber.model;

import com.game.atomicbomber.model.game.Game;

public class GameData {
    private int kills;
    private float accuracy;
    private int lastWave;
    private Difficulty difficulty;

    public GameData(int kills, float accuracy, int lastWave, Difficulty difficulty) {
        this.kills = kills;
        this.accuracy = accuracy;
        this.lastWave = lastWave;
        this.difficulty = difficulty;
    }

    public int getKills() {
        return kills;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public int getLastWave() {
        return lastWave;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
    public static void saveGameData(Game game) {
        int kills = Game.getPlayingGame().getKills();
        float accuracy = Game.getPlayingGame().getAccuracy();
        int lastWave = Game.getPlayingGame().getWaveNumber();
        Difficulty difficulty = Game.getPlayingGame().getDifficulty();
        User.getLoggedInUser().addGame(new GameData(kills, accuracy, lastWave, difficulty));
    }
}
