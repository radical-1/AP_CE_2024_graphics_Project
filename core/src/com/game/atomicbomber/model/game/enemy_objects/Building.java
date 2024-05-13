package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;

import java.util.ArrayList;

public class Building extends Enemy {
    private static final float BUILDING_WIDTH = 70;
    private static final float BUILDING_HEIGHT = 100;
    private static final int BUILDING_HITPOINT = 30;
    private static final float BUILDING_SPEED = 0;
    private static ArrayList<Texture> buildingTextures = new ArrayList<>();

    static {
        buildingTextures.add(new Texture("building.png"));
        buildingTextures.add(new Texture("building2.png"));
    }
    private int textureIndex;

    public Building(float x, float y) {
        super(x , y, BUILDING_SPEED, BUILDING_WIDTH, BUILDING_HEIGHT, BUILDING_HITPOINT);

        this.textureIndex = (int) (Math.random() * buildingTextures.size());
        sprite = new Sprite(buildingTextures.get(textureIndex));
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
