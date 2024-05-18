package com.game.atomicbomber.model.game.bombs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.model.game.Collision;
import com.game.atomicbomber.model.game.Game;
import com.game.atomicbomber.model.game.Ship;

public class EnemyBullet {
    private static final Texture ENEMY_BULLET_TEXTURE = new Texture("enemy_bullet.png");
    private static final float WIDTH = 25;
    private static final float HEIGHT = 25;
    private static final float SPEED = 500;


    private Sprite bulletSprite;
    public float time = 0;
    private Vector2 position;
    private float bulletTimer = 0;
    private boolean isExploded;
    private boolean canMove;
    private float angle;

    public EnemyBullet(float x, float y) {
        Ship ship = Game.getPlayingGame().getShip();
        position = new Vector2(x, y);
        isExploded = false;
        canMove = true;
        bulletSprite = new Sprite(ENEMY_BULLET_TEXTURE);
        bulletSprite.setSize(WIDTH, HEIGHT);
        angle = (float) Math.atan2(ship.y - position.y, ship.x - position.x);
        bulletSprite.setRotation((float) Math.toDegrees(angle));
    }

    public void update(float delta) {
        bulletTimer += delta;
        if(canMove) {
            position.x += SPEED * Math.cos(angle) * delta;
            position.y += SPEED * Math.sin(angle) * delta;

        }

        Ship ship = Game.getPlayingGame().getShip();
        if (isCollidingWithShip(ship) && ship.getTimeSinceLastBomb() > 3) {
            explode();
            ship.reduceHealth();
            ship.resetTimeSinceLastBomb();
        } else if (bulletTimer >= 5) {
            explode();
        }
        render();
    }

    public void explode() {
        canMove = false;
        Collision collision = new Collision("explosion");
        collision.createCollision(this);
    }

    public void setExploded() {
        isExploded = true;
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void render() {
        bulletSprite.setPosition(position.x, position.y);
        bulletSprite.draw(AtomicBomber.singleton.getBatch());
    }

    public Sprite getBulletSprite() {
        return bulletSprite;
    }
    public boolean isCollidingWithShip(Ship ship) {
        // Get the rectangle representing the ship
        Rectangle shipRect = new Rectangle(ship.getX(), ship.getY(), ship.getWidth()/ 3, ship.getHeight()/ 3);

        // Get the rectangle representing the enemy bullet
        Rectangle bulletRect = new Rectangle(position.x, position.y, WIDTH /3 , HEIGHT/3);

        // Check if the rectangles overlap
        return shipRect.overlaps(bulletRect);
    }
}
