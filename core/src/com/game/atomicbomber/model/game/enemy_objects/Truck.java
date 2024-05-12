package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.atomicbomber.AtomicBomber;

public class Truck extends Enemy {

    private static final Texture truckTexture = new Texture("truck.png");

    private float speed;
    private float x, y;
    private boolean isDestroyed;
    private Sprite truckSprite;

    public Truck(float x, float y, float speed) {
        super(x, y, speed, 50, 30, 15);
        this.isDestroyed = false;
        this.speed = speed;
        truckSprite = new Sprite(truckTexture);
    }
    public void update() {
        super.update(speed);
        if(x + getWidth() >= AtomicBomber.WIDTH && speed > 0) {
            speed = -speed;
            truckSprite.flip(true, false);
        } else if ( x <= 0 && speed < 0) {
            speed = -speed;
            truckSprite.flip(true, false);
        }
    }
}
