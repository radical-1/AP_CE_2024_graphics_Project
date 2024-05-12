package com.game.atomicbomber.model.game.bombs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;

import java.awt.*;

public class Bomb {
    private static final Texture bombTexture = new Texture("bomb.png");
    private static final Texture explosionTexture = new Texture("explosion.png");
    private static final Texture explosionDoneTexture = new Texture("explosion_done.png");


    private static final float WIDTH = 12;
    private static final float HEIGHT = 30;
    private static final float Y_ACCELERATION = 9.8f;
    private static final float X_ACCELERATION = 0.0f;


    private final float X_Velocity;
    private float Y_Velocity;
    private float x;
    private float y;
    private boolean isExploded;
    private boolean isExploding;
    private boolean isExplodingDone;
    private Sprite bombSprite;

    public Bomb(float x, float y, float X_Velocity, float Y_Velocity) {
        this.x = x;
        this.y = y;
        this.isExploded = false;
        this.isExploding = false;
        this.isExplodingDone = false;
        this.X_Velocity = X_Velocity;
        this.Y_Velocity = Y_Velocity;
        bombSprite = new Sprite(bombTexture);
    }

    public void update(float delta) {
        if (isExploding) {
            //TODO : explode
            isExploding = false;
            isExplodingDone = true;
            bombSprite.setTexture(explosionDoneTexture);
        } else if(isExplodingDone) {
            isExploded = true;
        } else {
            y += (float) (Y_Velocity * delta + 0.5 * Y_ACCELERATION * delta * delta);
            Y_Velocity -= Y_ACCELERATION * delta;
            x += X_Velocity * delta;
        }
    }

    public boolean isOutOfScreen() {
        return y < 0 || x < 0 || x > AtomicBomber.WIDTH;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getWidth() {
        return WIDTH;
    }
    public float getHeight() {
        return HEIGHT;
    }
    public void explode() {
        isExploding = true;
        bombSprite.setTexture(explosionTexture);
    }

}
