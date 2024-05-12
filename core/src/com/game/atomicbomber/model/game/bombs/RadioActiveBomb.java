package com.game.atomicbomber.model.game.bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class RadioActiveBomb {
    private static final float Y_ACCELERATION = -9.8f;
    private static final Sound EXPLOSION_SOUND = Gdx.audio.newSound(Gdx.files.internal("sound_effect/radio_active_explosion.mp3"));
    private static final Texture IMAGE = new Texture("radioactive.jpg");
    private static ArrayList<Texture> EXPLOSION = new ArrayList<>();
    static {
        EXPLOSION.add(new Texture("nukestem.png"));
        EXPLOSION.add(new Texture("nuketop1.png"));
        EXPLOSION.add(new Texture("nuketop2.png"));
        EXPLOSION.add(new Texture("nuketop3.png"));
        EXPLOSION.add(new Texture("nuketop4.png"));
    }
    private int stateOfExplosion = -1;
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;
    private boolean exploded;

    private float y, x;
    private float yVelocity = 0;
    private float xVelocity = 0;
    private Sprite radioactiveBombSprite;

    public RadioActiveBomb(float y, float x) {
        this.y = y;
        this.x = x;
        exploded = false;
        radioactiveBombSprite = new Sprite(IMAGE);
    }

    public void update(float delta) {
        yVelocity += Y_ACCELERATION * delta;
        y += yVelocity * delta;
        x += xVelocity * delta;
        if(isExploded()) {
            explode();
        }
        else if(y < 0) {
            exploded = true;
            stateOfExplosion = 0;
            radioactiveBombSprite.setTexture(EXPLOSION.get(0));
            EXPLOSION_SOUND.play();
        }
    }
    public boolean isExploded() {
        return exploded;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void explode() {
        stateOfExplosion++;
        if(stateOfExplosion < EXPLOSION.size()) {
            radioactiveBombSprite.setTexture(EXPLOSION.get(stateOfExplosion));
        } else {
            this.remove();
        }
    }

    private void remove() {
        //TODO : remove from game

    }

    public Texture getImage() {
        if(stateOfExplosion == -1) {
            return IMAGE;
        } else if(stateOfExplosion < EXPLOSION.size()) {
            return EXPLOSION.get(stateOfExplosion);
        } else {
            return null;
        }
    }
    public int getWidth() {
        return WIDTH;
    }
    public int getHeight() {
        return HEIGHT;
    }


}
