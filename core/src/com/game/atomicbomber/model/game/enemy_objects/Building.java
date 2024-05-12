package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Building extends Enemy {

    private static ArrayList<Texture> buildingTextures = new ArrayList<>();

    static {
        buildingTextures.add(new Texture("building.png"));
        buildingTextures.add(new Texture("building2.png"));
    }
    private int textureIndex;
    private Sprite buildingSprite;

    public Building(float x, float y) {
        super(x , y, 0, 50, 50, 25);

        this.textureIndex = (int) (Math.random() * buildingTextures.size());
        buildingSprite = new Sprite(buildingTextures.get(textureIndex));
    }

    public void update(float delta) {
        super.update(delta);
        textureIndex++;
        textureIndex %= buildingTextures.size();
        buildingSprite.setTexture(buildingTextures.get(textureIndex));
    }

}
