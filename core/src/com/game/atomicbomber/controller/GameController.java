package com.game.atomicbomber.controller;

import com.game.atomicbomber.model.game.Game;
import com.game.atomicbomber.model.game.bombs.ClusterBomb;

public class GameController {
    public static void increaseKills(int num) {
        Game.getPlayingGame().increaseKills(num);
    }
    public static void increaseShootBullets() {
        Game.getPlayingGame().increaseShootBullets();
    }
    public static void shoot() {
        if(Game.getPlayingGame().getNumberOfBombs() >= 1 && Game.getPlayingGame().timeSinceLastBomb >= 1) {
            Game.getPlayingGame().addBomb();
            Game.getPlayingGame().decreaseBomb();
            Game.getPlayingGame().resetBombTimer();
        }
    }
    public static void shootCluster() {
        if(Game.getPlayingGame().timeSinceLastCluster >= 1 && Game.getPlayingGame().getNumberOfCluster() >= 1){
            ClusterBomb clusterBomb = new ClusterBomb(Game.getPlayingGame().getShip().x, Game.getPlayingGame().getShip().y);
            for (int i = 0; i < clusterBomb.getBombs().size(); i++) {
                Game.getPlayingGame().addBomb(clusterBomb.getBombs().get(i));
            }
            Game.getPlayingGame().timeSinceLastCluster = 0;
            Game.getPlayingGame().decreaseCluster();
            Game.getPlayingGame().resetBombTimer();
        }
    }
    public static void shootRadioActive() {
        if(Game.getPlayingGame().getNumberOfAtomicBomb() >= 1) {
            Game.getPlayingGame().addRadioActiveBomb();
            Game.getPlayingGame().decreaseAtomicBomb();
        }
    }
    public static void increaseHealth() {
        Game.getPlayingGame().getShip().increaseHealth();
    }
    public static void goBackToGame() {
        Game.getPlayingGame().goBackToGame();
    }
    public static void freeze() {
        if(Game.getPlayingGame().canFreeze()) {
            Game.getPlayingGame().freeze();
            Game.getPlayingGame().resetFreezeBarValue();
        }

    }

    public static void showCopletionDialog() {

    }

    public static void goToNextWave() {
        Game.getPlayingGame().clearEnemies();
    }

    public static void AddOneRadioactiveBomb() {
        Game.getPlayingGame().increaseAtomicBomb();
    }

    public static void AddOneClusterBomb() {
        Game.getPlayingGame().increaseCluster();
    }
}
