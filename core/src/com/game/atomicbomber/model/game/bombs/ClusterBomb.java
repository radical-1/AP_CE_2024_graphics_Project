package com.game.atomicbomber.model.game.bombs;

import java.util.ArrayList;

public class ClusterBomb {
    private ArrayList<Bomb> bombs;

    public ClusterBomb(float x, float y) {
        bombs = new ArrayList<>();
        bombs.add(new Bomb(x, y, -20, -10));
        bombs.add(new Bomb(x, y, -10, -10));
        bombs.add(new Bomb(x, y, 0, -10));
        bombs.add(new Bomb(x, y, 10, -10));
        bombs.add(new Bomb(x, y, 20, -10));
    }
    public void update(float delta) {
        for (Bomb bomb : bombs) {
            bomb.update(delta);
        }
    }
    public ArrayList<Bomb> getBombs() {
        return bombs;
    }
}
