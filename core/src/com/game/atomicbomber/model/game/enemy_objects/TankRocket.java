package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;

import java.util.ArrayList;

public class TankRocket {
    public static final float WIDTH = 25;
    public static final float HEIGHT = 10;
    public static final float SPEED = 300;
    private static final Texture ROCKET_TEXTURE = new Texture("rocket.png");
    private static ArrayList<Texture> airblast = new ArrayList<>();

    static {
        airblast.add(new Texture("airblast1.png"));
        airblast.add(new Texture("airblast2.png"));
        airblast.add(new Texture("airblast3.png"));
        airblast.add(new Texture("airblast4.png"));
    }
    private int airblastIndex = -1;
    private float x, y;
    private Sprite rocketSprite;
    private Tank tank;

    public TankRocket(float x, float y, Tank tank) {
        this.x = x;
        this.y = y;
        rocketSprite = new Sprite(ROCKET_TEXTURE);
        rocketSprite.setSize(WIDTH, HEIGHT);
        this.tank = tank;
    }


    public void update(float shipX, float shipY, float delta) {
        // Calculate the angle between the MigRocket and the ship
        float angle = (float) Math.atan2(shipY - y, shipX - x) * 180 / (float) Math.PI;

        // Set the rotation of the rocketSprite to the calculated angle
        rocketSprite.setRotation(angle);

        // Calculate the distance to move in each direction
        float distanceX = (float) Math.cos(Math.toRadians(angle)) * SPEED * delta;
        float distanceY = (float) Math.sin(Math.toRadians(angle)) * SPEED * delta;

        // Update the position of the MigRocket
        x += distanceX;
        y += distanceY;

        // Update the position of the rocketSprite
        rocketSprite.setPosition(x, y);
        if(isOutOfScreen()) {
            remove();
        }
        if(isHit(shipX, shipY)) {
            handleHit();
        }
    }
    public boolean isOutOfScreen() {
        return x > AtomicBomber.WIDTH || x < 0 || y > AtomicBomber.WIDTH || y < 0;
    }
    public void remove() {
        tank.removeRocket(this);
    }
    public boolean isHit(float shipX, float shipY) {
        return shipX < x + WIDTH && shipX + WIDTH > x && shipY < y + HEIGHT && shipY + HEIGHT > y;
    }
    public void handleHit() {
        //TODO : handle game over
        remove();
    }
}
