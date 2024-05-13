package com.game.atomicbomber.model.game.bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;

import java.awt.*;

public class Bomb {
    private static final Texture bombTexture = new Texture("bomb.png");
    private static final Texture explosionTexture = new Texture("explosion.png");
    private static final Texture explosionDoneTexture = new Texture("explosion_done.png");
    private static final float WIDTH = 30;
    private static final float HEIGHT = 8;
    private static final float Y_ACCELERATION = -6 * 9.8f;


    private final float X_Velocity;
    private float Y_Velocity;
    private float x;
    private float y;
    private boolean isExploded;
    private boolean isExploding;
    private boolean isExplodingDone;
    private Sprite bombSprite;
    private Sound explosionSound;

    public Bomb(float x, float y, float X_velocity, float Y_Velocity) {
        this.x = x;
        this.y = y;
        this.isExploded = false;
        this.isExploding = false;
        this.isExplodingDone = false;

        this.Y_Velocity = -150;
        this.X_Velocity = X_velocity * 10;
        bombSprite = new Sprite(bombTexture);
        bombSprite.setSize(WIDTH, HEIGHT);
        if(X_velocity < 0) {

            bombSprite.flip(true, false);
        } else {

        }
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sound_effects/explosionSound.mp3"));
    }

    public void update(float delta) {
        if (isExploding) {
            isExploding = false;
            isExplodingDone = true;
            bombSprite.setTexture(explosionDoneTexture);
        } else if(isExplodingDone) {
            isExploded = true;
        }
        else {
            y += (float) (Y_Velocity * delta + 0.5 * Y_ACCELERATION * delta * delta);
            Y_Velocity += Y_ACCELERATION * delta;
            x += X_Velocity * delta;
            // Set the rotation of the sprite based on the velocity
            float angle = (float) Math.toDegrees(Math.atan2(Y_Velocity, X_Velocity));
            if(X_Velocity < 0)
                bombSprite.setRotation(angle + 180);
            else
                bombSprite.setRotation(angle);

        }
        render();
    }

    private void render() {
        bombSprite.setPosition(x, y);

        bombSprite.draw(AtomicBomber.singleton.getBatch());
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
        explosionSound.play();
    }

    public boolean isExploded() {
        return isExploded;
    }
}
