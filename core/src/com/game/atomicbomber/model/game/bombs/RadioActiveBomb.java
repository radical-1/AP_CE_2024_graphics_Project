package com.game.atomicbomber.model.game.bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.model.game.Collision;

import java.util.ArrayList;

public class RadioActiveBomb {
    private static final float Y_ACCELERATION = -5 * 9.8f;
    private static final Sound EXPLOSION_SOUND = Gdx.audio.newSound(Gdx.files.internal("sound_effects/radio_active_explosion.mp3"));
    private static final Texture IMAGE = new Texture("radioactive.png");
    private static final int WIDTH = 80;
    private static final int HEIGHT = 100;
    private boolean exploded;
    private boolean canMove;

    private float y, x;
    private float yVelocity = 0;
    public float time = 0;
    private Sprite radioactiveBombSprite;

    public RadioActiveBomb(float x, float y) {
        this.y = y;
        this.x = x;
        exploded = false;
        canMove = true;
        radioactiveBombSprite = new Sprite(IMAGE);
        radioactiveBombSprite.setSize(WIDTH, HEIGHT);

    }

    public void update(float delta) {
        if (canMove) {
            y += (Y_ACCELERATION * delta * delta) / 2 + yVelocity * delta;
            yVelocity += Y_ACCELERATION * delta;
            if (y <= 50) {
                explode();
            }
        }
        else {
            explode();
        }
        render();
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void explode() {
        EXPLOSION_SOUND.play();

        canMove = false;

        yVelocity = 0;
        // Handle the collision. For example, remove the enemy and the bomb from the game
        Collision collision = new Collision("atomicBomb");
        collision.createCollision(this);
        canMove = false;
    }
    public boolean isExploded() {
        return exploded;
    }
    public Texture getImage() {
        return IMAGE;
    }
    public int getWidth() {
        return WIDTH;
    }
    public int getHeight() {
        return HEIGHT;
    }
    public void render() {
        radioactiveBombSprite.setPosition(x, y);
        radioactiveBombSprite.draw(AtomicBomber.singleton.getBatch());
    }
    public boolean isEnemyInRange(float enemyX, float enemyY) {
        float distance = (float) Math.sqrt(Math.pow(enemyX - x, 2) + Math.pow(enemyY - y, 2));
        return distance <= 200;
    }
    public Sprite getRadioactiveBombSprite() {
        return radioactiveBombSprite;
    }
    public void setExploded() {
        exploded = true;
    }

}
