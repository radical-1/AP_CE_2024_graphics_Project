package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.atomicbomber.AtomicBomber;

public class Truck extends Enemy {
    private static final float TRUCK_WIDTH = 130;
    private static final float TRUCK_HEIGHT = 75;
    private static final float TRUCK_SPEED = 30;
    private static final int TRUCK_HITPOINT = 10;
    private static final Texture truckTexture = new Texture("truck.png");

    public Truck(float x, float y) {
        super(x, y, TRUCK_SPEED, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_HITPOINT);
        this.isDestroyed = false;
        sprite = new Sprite(truckTexture);
        sprite.setSize(getWidth(), getHeight());

    }
    @Override
    public void update(float delta) {
        x += speed * delta;
        if(x + getWidth() >= AtomicBomber.WIDTH && speed > 0) {
            speed = -speed;
            sprite.flip(true, false);
        } else if ( x <= 0 && speed < 0) {
            speed = -speed;
            sprite.flip(true, false);
        }
        render();
    }

    @Override
    public void render() {
        sprite.setPosition(x, y);
        sprite.draw(AtomicBomber.singleton.getBatch());
    }
}
