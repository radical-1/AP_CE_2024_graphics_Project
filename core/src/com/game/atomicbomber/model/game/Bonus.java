package com.game.atomicbomber.model.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;

public class Bonus {
    private static final Texture CLUSTER_BOMB_TEXTURE = new Texture("bonuscluster.png");
    private static final Texture RADIO_ACTIVE_BOMB_TEXTURE = new Texture("bonusnuke.png");
    private static final Texture BOMB_TEXTURE = new Texture("bonusbomb.png");
    private static final float BONUS_WIDTH = 30;
    private static final float BONUS_HEIGHT = 30;
    private static final float Y_SPEED = 60;
    private Sprite sprite;
    private float x;
    private float y;
    private float speedX;
    private int type;

    public Bonus(float x, float y, int type) {
        this.x = x;
        this.y = y;
        sprite = new Sprite(getTexture(type));
        sprite.setSize(BONUS_WIDTH, BONUS_HEIGHT);
        speedX = (float) (Math.random() * 20 - 10);
        this.type = type;
    }
    private Texture getTexture(int type) {
        //Type 1 : Cluster Bomb
        if (type == 1)
            return CLUSTER_BOMB_TEXTURE;
        //Type 2 : Radio Active Bomb
        else if (type == 2)
            return RADIO_ACTIVE_BOMB_TEXTURE;
        //Type 3 : Bomb
        else if (type == 3)
            return BOMB_TEXTURE;

        else return null;
    }
    public void update(float delta) {
        y += Y_SPEED * delta;
        x += speedX * delta;
        render();
    }
    private void render() {
        sprite.setPosition(x, y);
        sprite.draw(AtomicBomber.singleton.getBatch());
    }
    public boolean isOutOfScreen() {
        return y < 30 || x < -60 || x > AtomicBomber.WIDTH + 60;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public int getType() {
        //Type 1 : Cluster Bomb & Type 2 : Atomic Bomb & Type 3 : Bomb
        return type;
    }
    public boolean isCaptured() {
        float shipX = Game.getPlayingGame().getShip().x;
        float shipY = Game.getPlayingGame().getShip().y;
        float shipWidth = Game.getPlayingGame().getShip().SHIP_WIDTH;
        float shipHeight = Game.getPlayingGame().getShip().SHIP_HEIGHT;
        return x > shipX && x < shipX + shipWidth && y > shipY && y < shipY + shipHeight;
    }
    public void handleCapture() {
        if (type == 1) {
            Game.getPlayingGame().increaseCluster();
        } else if (type == 2) {
            Game.getPlayingGame().increaseAtomicBomb();
        } else if (type == 3) {
            Game.getPlayingGame().increaseBomb();
        }
    }
}
