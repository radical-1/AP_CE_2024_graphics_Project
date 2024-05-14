package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.AtomicBomber;

import java.util.ArrayList;

public class Tree extends Enemy{
    private static final float TREE_WIDTH = 30;
    private static final float TREE_HEIGHT = 80;
    private static final int TREE_HITPOINT = 0;
    private static final ArrayList<Texture> TREE_TEXTURES = new ArrayList<>();
    static {
        TREE_TEXTURES.add(new Texture("tree.png"));
        TREE_TEXTURES.add(new Texture("tree2.png"));
        TREE_TEXTURES.add(new Texture("tree3.png"));
    }



    public Tree(float x, float y) {
        super(x, y, 0, TREE_WIDTH, TREE_HEIGHT, TREE_HITPOINT);
        sprite = new Sprite(TREE_TEXTURES.get((int) (Math.random() * TREE_TEXTURES.size())));
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
