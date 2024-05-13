package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.model.game.Game;

import java.util.ArrayList;

public class Tank extends Enemy {
    public static final float TANK_WIDTH = 60;
    public static final float TANK_HEIGHT = 30;
    public static final int TANK_HITPOINT = 10;
    private static final Texture TANK_TEXTURE = new Texture("tank1.png");
    private Sprite sprite;
    private float attackRange;
    private ArrayList<TankRocket> rockets;

    public Tank(float x, float y, float speed, float attackRange) {
        super(x, y, speed, TANK_WIDTH, TANK_HEIGHT, TANK_HITPOINT);
        this.attackRange = attackRange;
        rockets = new ArrayList<>();
        sprite = new Sprite(TANK_TEXTURE);
        sprite.setSize(Width, Height);
    }

    public void shootRocket() {
        TankRocket rocket = new TankRocket(x, y, this);
        rockets.add(rocket);
    }

    public boolean isShipInRangeForAttack(float shipX, float shipY) {
        float distance = (float) Math.sqrt(Math.pow(shipX - x, 2) + Math.pow(shipY - y, 2));
        return distance <= attackRange;
    }

    public void removeRocket(TankRocket rocket) {
        rockets.remove(rocket);
    }


    @Override
    public void update(float delta) {
        x += speed * delta;

        // Check if the tank is at the end of the screen
        if (x > AtomicBomber.WIDTH - Width) {
            // The tank is at the right end of the screen, so flip it and change its direction
            x = AtomicBomber.WIDTH - Width;
            speed = -Math.abs(speed);
            sprite.flip(true, false);
        } else if (x < 0) {
            // The tank is at the left end of the screen, so flip it and change its direction
            x = 0;
            speed = Math.abs(speed);
            sprite.flip(true, false);
        }
        if (isShipInRangeForAttack(Game.getPlayingGame().getShip().x, Game.getPlayingGame().getShip().y)) {
            shootRocket();
        }
        for (TankRocket rocket : rockets) {
            rocket.update(Game.getPlayingGame().getShip().x, Game.getPlayingGame().getShip().y, delta);
        }
        render();
    }

    @Override
    public void render() {
        sprite.setPosition(x, y);
        sprite.draw(AtomicBomber.singleton.getBatch());
    }
}
