package com.game.atomicbomber.model;

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
}
