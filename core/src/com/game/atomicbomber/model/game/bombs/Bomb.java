package com.game.atomicbomber.model.game.bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.GameController;
import com.game.atomicbomber.model.game.Collision;
import com.game.atomicbomber.model.game.enemy_objects.Enemy;

import java.awt.*;

public class Bomb {
    private static final Texture bombTexture = new Texture("bomb64.png");
    private static final float WIDTH = 15;
    private static final float HEIGHT = 15;
    private static final float Y_ACCELERATION = -6 * 9.8f;
    public float time = 0;


    private float X_Velocity;
    private float Y_Velocity;
    private float x;
    private float y;
    private boolean isExploded;
    private boolean canMove;
    private Sprite bombSprite;
    private Sound explosionSound;
    public Bomb(float x, float y, float X_velocity, float Y_Velocity) {
        this.x = x;
        this.y = y;
        this.isExploded = false;
        this.canMove = true;
        this.Y_Velocity = -150;
        this.X_Velocity = X_velocity * 10;
        bombSprite = new Sprite(bombTexture);
        bombSprite.setSize(WIDTH, HEIGHT);
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sound_effects/explosionSound.mp3"));
    }

    public void update(float delta) {
        if (canMove){
            y += (float) (Y_Velocity * delta + 0.5 * Y_ACCELERATION * delta * delta);
            Y_Velocity += Y_ACCELERATION * delta;
            x += X_Velocity * delta;
            // Set the rotation of the sprite based on the velocity
        }
        else
            explode();
        render();
    }

    private void render() {
        bombSprite.setPosition(x, y);
        bombSprite.draw(AtomicBomber.singleton.getBatch());
    }

    public boolean isOutOfScreen() {
        return y < 30 || x < -100 || x > AtomicBomber.WIDTH + 100;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getWidth() {
        return WIDTH;
    }
    public float getHeight() {
        return HEIGHT;
    }
    public void explode() {
        canMove = false;
        X_Velocity = 0;
        Y_Velocity = 0;
        // Handle the collision. For example, remove the enemy and the bomb from the game
        Collision collision = new Collision("explosion");
        collision.createCollision(this);


    }
    public void setExploded() {
        isExploded = true;
    }
    public boolean isExploded() {
        return isExploded;
    }
    public Rectangle getBoundingRectangle() {
        return new Rectangle(x, y, WIDTH, HEIGHT );
    }
    public Sprite getBombSprite() {
        return bombSprite;
    }
    public void playSoundEffect() {
        explosionSound.play();
    }
    public void stopSoundEffect() {
        explosionSound.stop();
    }
}
