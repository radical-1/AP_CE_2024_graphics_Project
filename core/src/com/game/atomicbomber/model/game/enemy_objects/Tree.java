package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Tree extends Enemy{
    private static final ArrayList<Texture> TREE_TEXTURES = new ArrayList<>();
    static {
        TREE_TEXTURES.add(new Texture("tree.png"));
        TREE_TEXTURES.add(new Texture("tree2.png"));
        TREE_TEXTURES.add(new Texture("tree3.png"));
    }

    private Sprite treeSprite;

    public Tree(float x, float y) {
        super(x, y, 0, 10, 30, 0);
        treeSprite = new Sprite(TREE_TEXTURES.get((int) (Math.random() * TREE_TEXTURES.size())));
    }

}
