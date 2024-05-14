package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.model.game.Game;

import java.util.ArrayList;

public class Mig extends Enemy {
    public static final float MIG_WIDTH = 90;
    public static final float MIG_HEIGHT = 40;
    public static final int MIG_HITPOINT = 10;
    public static final float MIG_SPEED = 100;
    private static final Texture MIG_TEXTURE = new Texture("mig.png");
    private static final Texture MIG_WARNING_TEXTURE = new Texture("migwarning.png");

    private boolean warningShown;
    private float warningTime;

    private float attackRangeRadius;

    private ArrayList<MigRocket> rockets;

    public Mig(float x, float y, float speed,float attackRange) {
        super(x, y, speed, MIG_WIDTH, MIG_HEIGHT, MIG_HITPOINT);
        this.attackRangeRadius = attackRange;
        sprite = new Sprite(MIG_TEXTURE);
        sprite.setSize(getWidth(), getHeight());
        rockets = new ArrayList<>();
        warningShown = false;
        warningTime = 0;
    }

    public boolean isOutOfScreen() {
        if(warningShown)
            return false;

        return x < - 3 * getWidth() || x > AtomicBomber.WIDTH + 3 * getWidth() || y < - 3 * getHeight() || y > AtomicBomber.HEIGHT + 3 * getHeight();
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

    public void removeRocket(MigRocket rocket) {
        rockets.remove(rocket);
    }

    @Override
    public void update(float delta) {
        if(warningShown && warningTime < 5){
            warningTime += delta;
            return;
        }
        if(warningShown && warningTime >= 5) {
            sprite.setTexture(MIG_TEXTURE);
            warningShown = false;

        }
        x -= MIG_SPEED * delta;
        if (isShipInRangeForAttack(Game.getPlayingGame().getShip().x, Game.getPlayingGame().getShip().y)) {
            shootRocket();
        }
        for (MigRocket rocket : new ArrayList<>(rockets)) {
            rocket.update(Game.getPlayingGame().getShip().x, Game.getPlayingGame().getShip().y, delta);
        }
        if(isOutOfScreen()){
            isDestroyed = true;
        }
        render();
    }
    @Override
    public void render() {
        sprite.setPosition(x, y);
        sprite.draw(AtomicBomber.singleton.getBatch());
    }
}
