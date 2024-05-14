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
    private static final float HEIGHT = 36;
    private static final float SPEED = 100;

    private Sprite bulletSprite;
    public float time = 0;
    private Vector2 position;
    private Vector2 velocity;
    private float bulletTimer = 0;
    private boolean isExploded;
    private boolean canMove;

    public EnemyBullet(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2();
        isExploded = false;
        canMove = true;
        bulletSprite = new Sprite(ENEMY_BULLET_TEXTURE);
        bulletSprite.setSize(WIDTH, HEIGHT);
    }

    public void update(float delta) {
        bulletTimer += delta;

        if(canMove){
            // Calculate the direction from the bullet to the ship
            Vector2 direction = new Vector2(Game.getPlayingGame().getShip().x - position.x, Game.getPlayingGame().getShip().y - position.y);
            direction.nor(); // Normalize the direction vector to get a length of 1

            // Set the velocity in the direction of the ship


            velocity.set(direction).scl(SPEED);

            // Update the position of the bullet
            position.mulAdd(velocity, delta);
        }

        if (bulletTimer >= 5) {
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

    public Vector2 getPosition() {
        return position;
    }


    public boolean isCollidingWithShip(Ship ship) {
        Rectangle bulletRectangle = new Rectangle(position.x, position.y, bulletSprite.getWidth(), bulletSprite.getHeight());
        Rectangle shipRectangle = new Rectangle(ship.getX(), ship.getY(), ship.getWidth(), ship.getHeight());

        return bulletRectangle.overlaps(shipRectangle);
    }
// Add getters for position and velocity here if needed
}
