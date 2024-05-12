package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Mig {
    private static final float MIG_SPEED = 100;
    private static final float MIG_WIDTH = 120;
    private static final float MIG_HEIGHT = 40;
    private static final Texture MIG_TEXTURE = new Texture("mig.png");
    private static final Texture MIG_WARNING_TEXTURE = new Texture("mig_warning.png");

    private float attackRangeRadius;
    private float x, y;
    private Sprite migSprite;
    private ArrayList<MigRocket> rockets;

    public Mig(float x, float y, float attackRange) {
        this.x = x;
        this.y = y;
        this.attackRangeRadius = attackRange;
        migSprite = new Sprite(MIG_TEXTURE);
        rockets = new ArrayList<>();
    }
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public boolean isOutOfScreen() {
        return x < -MIG_WIDTH;
    }
    public void shootRocket() {
        //TODO : shoot rocket
        MigRocket rocket = new MigRocket(x, y, this);
        rockets.add(rocket);
    }
    public boolean isShipInRangeForAttack(float shipX, float shipY) {
        float distance = (float) Math.sqrt(Math.pow(shipX - x, 2) + Math.pow(shipY - y, 2));
        return distance <= attackRangeRadius;
    }

    public void update(float shipX, float shipY, float delta) {
        x -= MIG_SPEED;
        if (isShipInRangeForAttack(shipX, shipY)) {
            shootRocket();
        }
        for (MigRocket rocket : rockets) {
            rocket.update(shipX, shipY, delta);
        }
    }
    public void removeRocket(MigRocket rocket) {
        rockets.remove(rocket);
    }

}
