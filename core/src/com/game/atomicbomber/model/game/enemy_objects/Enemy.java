package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.game.atomicbomber.AtomicBomber;

public abstract class Enemy {
    protected float Width, Height;
    protected int hitPoint;
    protected float x, y;
    protected boolean isDestroyed;
    protected float speed;
    protected Sprite sprite;

    public Enemy(float x, float y, float speed, float Width, float Height, int hitPoint) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.Width = Width;
        this.Height = Height;
        this.hitPoint = hitPoint;
        this.isDestroyed = false;

    }
    public abstract void update(float delta);
    public abstract void render();

    public float getWidth() {
        return Width;
    }

    public void setWidth(float width) {
        Width = width;
    }

    public float getHeight() {
        return Height;
    }

    public void setHeight(float height) {
        Height = height;
    }

    public int getHitPoint() {
        return hitPoint;
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

    public void setY(float y) {
        this.y = y;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void destroy() {
        isDestroyed = true;
    }

    public float getSpeed() {
        return speed;
    }
    public Sprite getSprite() {
        return sprite;
    }
    public Rectangle getBoundingRectangle() {
        return new Rectangle(x, y, Width, Height);
    }

}
