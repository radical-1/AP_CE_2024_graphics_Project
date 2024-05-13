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
    public static void updateAccuracy() {
        float newAccuracy = (float) Game.getPlayingGame().getShootEnemies() / Game.getPlayingGame().getShootBullets();
        Game.getPlayingGame().setAccuracy(newAccuracy);
    }
    public static void shoot() {
        Game.getPlayingGame().addBomb();
    }
    public static void shootCluster() {
        ClusterBomb clusterBomb = new ClusterBomb(Game.getPlayingGame().getShip().x, Game.getPlayingGame().getShip().y);
        for(int i = 0; i < clusterBomb.getBombs().size(); i++) {
            Game.getPlayingGame().addBomb(clusterBomb.getBombs().get(i));
        }
    }
    public static void shootRadioActive() {
        //TODO : Implement RadioActive Bomb
    }
}
