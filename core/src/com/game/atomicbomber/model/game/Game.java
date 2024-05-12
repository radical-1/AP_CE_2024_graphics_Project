package com.game.atomicbomber.model.game;

import com.game.atomicbomber.model.Difficulty;
import com.game.atomicbomber.model.GameInformation;
import com.game.atomicbomber.model.User;
import com.game.atomicbomber.model.game.enemy_objects.*;

import java.util.ArrayList;

public class Game {
    private static Game onGame;
    private int kills;
    private float accuracy;
    private User player;
    private int Wave;
    private int shootedBombs;
    private int shootedEnemies;
    private Difficulty difficulty;
    //enemy objects
    private ArrayList<Enemy> enemies;
    //ship
    private Ship ship;

    public Game(User player) {
        kills = 0;
        shootedBombs = 0;
        shootedEnemies = 0;
        this.difficulty = player.getGameInfo().getDifficulty();
        accuracy = 0;
        this.player = player;
        Wave = 1;
        enemies = new ArrayList<>();
    }
    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    private void increaseKills(int num) {
        kills += num;
    }
    private void updateAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
    public static Game getOnGame() {
        return onGame;
    }
    public static void setOnGame(Game onGame) {
        Game.onGame = onGame;
    }
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    public void update(float delta) {
        for(Enemy enemy : enemies) {
            enemy.update(delta);
        }
    }
    public boolean isWaveDone() {
        return enemies.isEmpty();
    }
    public void updateWave() {
        Wave++;
        //TODO : update wave

    }
    public void initializeWave1() {
        //TODO : initialize wave
    }
    public void initializeWave2() {
        //TODO : initialize wave
    }
    public void initializeWave3() {
        //TODO : initialize wave
    }
    public Ship getShip() {
        return ship;
    }
}

