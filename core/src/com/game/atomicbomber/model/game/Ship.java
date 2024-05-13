package com.game.atomicbomber.model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;

import java.util.ArrayList;

public class Ship {
    private static final float Y_ACCELERATION = -3 * 9.8f;
    private static final float AIR_RESISTANCE = -2 * 7f;
    private static ArrayList<Texture> SHIP_TEXTURES = new ArrayList<>();

    static {
        SHIP_TEXTURES.add(new Texture("dino_spaceship.png"));
        SHIP_TEXTURES.add(new Texture("Spaceship.png"));
    }

    public static final int SHIP_WIDTH = 80;
    public static final int SHIP_HEIGHT = 60;

    private float X_Speed;
    private float Y_Speed;

    public float x;
    public float y;
    private float direction;
    private Sprite spaceshipSprite;


    public Ship() {
        x = (float) (AtomicBomber.WIDTH / 2 - SHIP_WIDTH / 2);
        y = (float) (AtomicBomber.HEIGHT / 2 - SHIP_HEIGHT / 2);
        int random = (int) (Math.random() * 2);
        spaceshipSprite = new Sprite(SHIP_TEXTURES.get(random));
        spaceshipSprite.setSize(SHIP_WIDTH, SHIP_HEIGHT);
        spaceshipSprite.setOriginCenter();
        direction = 0;
        X_Speed = 0;
        Y_Speed = 0;
    }

    public void render(float delta) {
        // Update speed based on user input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (X_Speed > -200)
                X_Speed -= 250 * delta;
            if (direction < 180) {
                direction += 2;
                spaceshipSprite.rotate(2);
            } else if (direction > 180) {
                direction -= 2;
                spaceshipSprite.rotate(-2);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (X_Speed < 200)
                X_Speed += 100 * delta;
            if (direction > 0 && direction <= 180) {
                direction -= 2;
                spaceshipSprite.rotate(-2);
            } else if (direction < 360 && direction > 180) {
                direction += 2;
                spaceshipSprite.rotate(2);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {

            if (Y_Speed < 200)
                Y_Speed += 100 * delta;
            if (direction > 90) {
                direction -= 2;
                spaceshipSprite.rotate(-2);
            } else if (direction < 90) {
                direction += 2;
                spaceshipSprite.rotate(2);
            }

        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

            if (Y_Speed > -200)
                Y_Speed -= 100 * delta;
            if (direction > 270) {
                direction -= 2;
                spaceshipSprite.rotate(-2);
            } else if (direction < 270) {
                direction += 2;
                spaceshipSprite.rotate(2);
            }

        }

        // Apply air resistance
        if (X_Speed > 0) {
            X_Speed += AIR_RESISTANCE * delta;
        } else if (X_Speed < 0) {
            X_Speed -= AIR_RESISTANCE * delta;
        }
        if (Y_Speed > 0) {
            Y_Speed += AIR_RESISTANCE * delta;
        } else if (Y_Speed < 0) {
            Y_Speed -= AIR_RESISTANCE * delta;
        }
        Y_Speed += Y_ACCELERATION * delta;

        // Update position
        x += X_Speed * delta;
        if(y + Y_Speed * delta > 80 && y + Y_Speed * delta < AtomicBomber.HEIGHT - SHIP_HEIGHT)
            y += Y_Speed * delta;
        if(y + Y_Speed * delta <= 80 ||
                y + Y_Speed * delta >= AtomicBomber.HEIGHT - SHIP_HEIGHT)
            Y_Speed = 0;

        direction += 360;
        direction %= 360;

        if (x < -80) {
            x = AtomicBomber.WIDTH + 70;
        } else if (x > AtomicBomber.WIDTH + 80) {
            x = -70;
        }


        // Update sprite
        spaceshipSprite.setPosition(x, y);
        spaceshipSprite.setRotation(direction);
        spaceshipSprite.draw(AtomicBomber.singleton.getBatch());

    }

    public float getX_Speed() {
        return X_Speed;
    }
    public float getY_Speed() {
        return Y_Speed;
    }
}
