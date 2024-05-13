package com.game.atomicbomber.model.game;

import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.model.Difficulty;
import com.game.atomicbomber.model.User;
import com.game.atomicbomber.model.game.bombs.Bomb;
import com.game.atomicbomber.model.game.enemy_objects.*;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private static Game playingGame;
    private int kills;
    private float accuracy;
    private User player;
    private Wave currentWave;
    private int waveNumber;
    private int shootBullets;
    private int shootEnemies;
    private Difficulty difficulty;
    //enemy objects
    private ArrayList<Enemy> enemies;
    //ship
    private Ship ship;
    private ArrayList<Bomb> bombs;

    public Game(User player) {
        kills = 0;
        shootBullets = 0;
        shootEnemies = 0;
        this.difficulty = player.getGameInfo().getDifficulty();
        accuracy = 0;
        this.player = player;
        setPlayingGame(this);
        waveNumber = 1;
        ship = new Ship();
        enemies = new ArrayList<>();
        bombs = new ArrayList<>();
        startWave();


    }

    private void startWave() {
        switch (waveNumber) {
            case 1 :
                currentWave = new Wave(3, 2, 1, 1, 6,false, difficulty);
                break;
            case 2 :
                currentWave = new Wave(5, 3, 2, 2, 8,false, difficulty);
                break;
            case 3 :
                currentWave = new Wave(7, 4, 3, 3, 10,true, difficulty);
                break;
        }

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

    public void increaseKills(int num) {
        kills += num;
    }

    private void updateAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public static Game getPlayingGame() {
        return playingGame;
    }

    public static void setPlayingGame(Game playingGame) {
        Game.playingGame = playingGame;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    public void update(float delta) {

        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }
        for (Bomb bomb : new ArrayList<>(bombs)) {
            bomb.update(delta);
            if(bomb.isOutOfScreen()) {
                bombs.remove(bomb);
            }
            if(bomb.isExploded()) {
                bombs.remove(bomb);
            }
        }
        if (isWaveDone()) {
            waveNumber++;
            // Wait 5 seconds before starting the next wave
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            startWave();
                        }
                    },
                    5000
            );

        }
    }

    public boolean isWaveDone() {
        return enemies.isEmpty();
    }


    public Ship getShip() {
        return ship;
    }

    public int getShootBullets() {
        return shootBullets;
    }

    public void increaseShootBullets() {
        shootBullets++;
    }

    public int getShootEnemies() {
        return shootEnemies;
    }

    public void increaseShootEnemies() {
        shootEnemies++;
    }
    public void addBomb() {
        bombs.add(new Bomb(ship.x, ship.y, ship.getX_Speed() / 10, ship.getY_Speed() / 10));
    }
    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }
}

