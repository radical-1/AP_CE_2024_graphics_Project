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
    private static final float SPEED = 200;

    private Sprite bulletSprite;
    public float time = 0;
    private Vector2 position;
    private float bulletTimer = 0;
    private boolean isExploded;
    private boolean canMove;


    public EnemyBullet(float x, float y) {
        position = new Vector2(x, y);
        isExploded = false;
        canMove = true;
        bulletSprite = new Sprite(ENEMY_BULLET_TEXTURE);
        bulletSprite.setSize(WIDTH, HEIGHT);
    }

    public void update(float delta) {
        bulletTimer += delta;
        if(canMove) {
            float playerXPosition = Game.getPlayingGame().getShip().x;
            float playerYPosition = Game.getPlayingGame().getShip().y;

            float angle = (float) Math.atan2(playerYPosition - position.y, playerXPosition - position.x);
            position.x += SPEED * Math.cos(angle) * delta;
            position.y += SPEED * Math.sin(angle) * delta;

            handleRotation();
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

    public void handleRotation() {
        float playerX = Game.getPlayingGame().getShip().x;
        float playerY = Game.getPlayingGame().getShip().y;

        float angle = (float) Math.atan2(playerY - position.y, playerX - position.x);
        angle = (float) Math.toDegrees(angle);
        bulletSprite.setRotation(angle);

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
        Rectangle shipRect = new Rectangle(ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight());

        // Get the rectangle representing the enemy bullet
        Rectangle bulletRect = new Rectangle(position.x, position.y, WIDTH, HEIGHT);

        // Check if the rectangles overlap
        return shipRect.overlaps(bulletRect);
    }
}
