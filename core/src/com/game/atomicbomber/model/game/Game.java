package com.game.atomicbomber.model.game;

import com.game.atomicbomber.controller.GameController;
import com.game.atomicbomber.model.Difficulty;
import com.game.atomicbomber.model.User;
import com.game.atomicbomber.model.game.bombs.Bomb;
import com.game.atomicbomber.model.game.bombs.RadioActiveBomb;
import com.game.atomicbomber.model.game.enemy_objects.*;

import java.util.ArrayList;

public class Game {
    private static Game playingGame;
    private int kills;
    private float accuracy;
    private User player;
    private Wave currentWave;
    private int waveNumber;
    private int shootBullets;
    private int shootEnemies;
    public float timeSinceLastBomb;
    public float timeSinceLastCluster;
    private Difficulty difficulty;
    private int numberOfCluster;
    private int numberOfRadioActiveBomb;
    private int numberOfBombs;
    //enemy objects
    private ArrayList<Enemy> enemies;
    //ship
    private Ship ship;
    //bombs
    private ArrayList<Bomb> bombs;
    private ArrayList<RadioActiveBomb> radioActiveBombs;
    //Bonuses
    private ArrayList<Bonus> bonuses;

    public Game(User player) {
        kills = 0;
        shootBullets = 0;
        shootEnemies = 0;
        this.difficulty = player.getGameInfo().getDifficulty();
        accuracy = 0;
        timeSinceLastBomb = 0;
        timeSinceLastCluster = 0;
        this.player = player;
        setPlayingGame(this);
        waveNumber = 1;
        ship = new Ship();
        enemies = new ArrayList<>();
        bombs = new ArrayList<>();
        radioActiveBombs = new ArrayList<>();
        bonuses = new ArrayList<>();
        numberOfCluster = 1;
        numberOfRadioActiveBomb = 0;
        numberOfBombs = 10;
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
            default:
                //TODO : Exit game
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
        timeSinceLastBomb += delta;
        timeSinceLastCluster += delta;
        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }
        for (Bomb bomb : new ArrayList<>(bombs)) {
            bomb.update(delta);
            if(bomb.isOutOfScreen()) {
                bomb.explode();
            }
            if(bomb.isExploded()) {
                bombs.remove(bomb);
            }
        }
        for(RadioActiveBomb bomb : new ArrayList<>(radioActiveBombs)) {
            bomb.update(delta);
            if(bomb.isExploded()) {
                handleRadioActiveCollision(bomb);
                radioActiveBombs.remove(bomb);
            }
        }
        for (Bonus bonus : new ArrayList<>(bonuses)) {
            bonus.update(delta);
            if(bonus.isCaptured()) {
                bonus.handleCapture();
                bonuses.remove(bonus);
            } else if(bonus.isOutOfScreen()) {
                bonuses.remove(bonus);
            }
        }
        // Check for collisions
        for (Enemy value : new ArrayList<>(enemies)) {
            for (Bomb item :new ArrayList<>(bombs)) {
                if (isCollision(value, item)) {
                    handleCollision(value, item);
                }
            }
        }
        if (isWaveDone()) {
            waveNumber++;
            startWave();
            //TODO : show wave win message

        }
    }
    private boolean isCollision(Enemy enemy, Bomb bomb) {
        // Check if the bounding boxes of the enemy and the bomb overlap
        return enemy.getBoundingRectangle().overlaps(bomb.getBoundingRectangle());
    }

    private void handleCollision(Enemy enemy, Bomb bomb) {
        bomb.explode();
        enemies.remove(enemy);
        if(enemy instanceof Building) {
            bonuses.add(new Bonus(enemy.getX(), enemy.getY(),2));
        } else if(enemy instanceof riflePit) {
            bonuses.add(new Bonus(enemy.getX(), enemy.getY(),1));
        } else {
            bonuses.add(new Bonus(enemy.getX(), enemy.getY(),3));
        }
        GameController.increaseKills(enemy.getHitPoint());
    }
    private void handleRadioActiveCollision(RadioActiveBomb bomb) {
        bomb.explode();
        for (Enemy enemy : new ArrayList<>(enemies)){
            if(bomb.isEnemyInRange(enemy.getX(), enemy.getY())){
                enemies.remove(enemy);
                if (enemy instanceof Building) {
                    bonuses.add(new Bonus(enemy.getX(), enemy.getY(), 2));
                } else if (enemy instanceof riflePit) {
                    bonuses.add(new Bonus(enemy.getX(), enemy.getY(), 1));
                } else {
                    bonuses.add(new Bonus(enemy.getX(), enemy.getY(), 3));
                }
                GameController.increaseKills(enemy.getHitPoint());
            }
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
    public void addRadioActiveBomb() {
        radioActiveBombs.add(new RadioActiveBomb(ship.x, ship.y));
    }
    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }
    public void increaseCluster() {
        numberOfCluster++;
    }
    public void decreaseCluster() {
        numberOfCluster--;
    }
    public void increaseAtomicBomb() {
        numberOfRadioActiveBomb++;
    }
    public void decreaseAtomicBomb() {
        numberOfRadioActiveBomb--;
    }
    public int getNumberOfCluster() {
        return numberOfCluster;
    }
    public int getNumberOfAtomicBomb() {
        return numberOfRadioActiveBomb;
    }
    public int getWaveNumber() {
        return waveNumber;
    }
    public void increaseBomb() {
        numberOfBombs += 5;
    }
    public void decreaseBomb() {
        numberOfBombs--;
    }
    public int getNumberOfBombs() {
        return numberOfBombs;
    }
    public void resetBombTimer() {
        timeSinceLastBomb = 0;
    }
    public void resetClusterTimer() {
        timeSinceLastCluster = 0;
    }

}

