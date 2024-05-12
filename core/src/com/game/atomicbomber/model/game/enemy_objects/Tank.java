package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;

import java.util.ArrayList;

public class Tank extends Enemy {

    private static final Texture TANK_TEXTURE = new Texture("tank.png");
    private Sprite tankSprite;
    private float attackRange;
    private ArrayList<TankRocket> rockets;

    public Tank(float x, float y, float speed, float attackRange, float Width, float Height, int hitPoint) {
        super(x, y, speed, Width, Height, hitPoint);
        this.attackRange = attackRange;
        rockets = new ArrayList<>();
        tankSprite = new Sprite(TANK_TEXTURE);
    }

    public void shootRocket() {
        TankRocket rocket = new TankRocket(x, y, this);
        rockets.add(rocket);
    }

    public boolean isShipInRangeForAttack(float shipX, float shipY) {
        float distance = (float) Math.sqrt(Math.pow(shipX - x, 2) + Math.pow(shipY - y, 2));
        return distance <= attackRange;
    }

    public void update(float shipX, float shipY, float delta) {
        if (isShipInRangeForAttack(shipX, shipY)) {
            shootRocket();
        }
        for (TankRocket rocket : rockets) {
            rocket.update(shipX, shipY, delta);
        }
        super.update(delta);
        if(x + getWidth() >= AtomicBomber.WIDTH && speed > 0) {
            speed = -speed;
            tankSprite.flip(true, false);
        } else if ( x <= 0 && speed < 0) {
            speed = -speed;
            tankSprite.flip(true, false);
        }
    }
    public void removeRocket(TankRocket rocket) {
        rockets.remove(rocket);
    }


}
