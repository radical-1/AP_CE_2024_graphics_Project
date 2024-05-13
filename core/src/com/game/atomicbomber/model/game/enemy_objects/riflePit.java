package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;

public class riflePit extends Enemy {
    public static final float RIFLE_PIT_WIDTH = 60;
    public static final float RIFLE_PIT_HEIGHT = 60;
    public static final int RIFLE_PIT_HITPOINT = 10;
    private static final Texture RIFLE_PIT_TEXTURE = new Texture("rifle_pit.png");


    private Sprite sprite;

    public riflePit(float x, float y) {
        super(x, y, 0, RIFLE_PIT_WIDTH, RIFLE_PIT_HEIGHT, RIFLE_PIT_HITPOINT);
        sprite = new Sprite(RIFLE_PIT_TEXTURE);
        sprite.setSize(getWidth(), getHeight());
    }

    @Override
    public void update(float delta) {
        render();
    }

    @Override
    public void render() {
        sprite.setPosition(x, y);
        sprite.draw(AtomicBomber.singleton.getBatch());
    }
}
