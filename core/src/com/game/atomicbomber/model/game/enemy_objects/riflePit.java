package com.game.atomicbomber.model.game.enemy_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class riflePit extends Enemy {

    private static final Texture RIFLE_PIT_TEXTURE = new Texture("rifle_pit.png");


    private Sprite riflePitSprite;

    public riflePit(float x, float y) {
        super(x , y, 0, 50, 50, 15);
        riflePitSprite = new Sprite(RIFLE_PIT_TEXTURE);
    }
    public void update(float delta) {
        super.update(delta);
    }

}
