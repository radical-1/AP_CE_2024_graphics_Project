package com.game.atomicbomber.model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.model.game.bombs.Bomb;
import com.game.atomicbomber.model.game.bombs.RadioActiveBomb;

public class Collision {

    private static Animation<Texture> animation;
    private static Texture[] explosionFrames;
    static {
        explosionFrames = new Texture[8];
        explosionFrames[0] = new Texture("blast1.png");
        explosionFrames[1] = new Texture("blast2.png");
        explosionFrames[2] = new Texture("blast3.png");
        explosionFrames[3] = new Texture("blast4.png");
        explosionFrames[4] = new Texture("blast5.png");
        explosionFrames[5] = new Texture("blast6.png");
        explosionFrames[6] = new Texture("blast7.png");
        explosionFrames[7] = new Texture("blast8.png");



    }
    private static Texture[] atomicBombFrames;
    static {
        atomicBombFrames = new Texture[5];
        atomicBombFrames[0] = new Texture("nuketop1.png");
        atomicBombFrames[1] = new Texture("nuketop2.png");
        atomicBombFrames[2] = new Texture("nuketop3.png");
        atomicBombFrames[3] = new Texture("nuketop4.png");
        atomicBombFrames[4] = new Texture("nuketop5.png");
    }
    public Collision(String type) {
        if(type.equals("atomicBomb"))
            animation = new Animation<>(0.1f, atomicBombFrames[0], atomicBombFrames[1], atomicBombFrames[2], atomicBombFrames[3], atomicBombFrames[4]);
        else if(type.equals("explosion"))
            animation = new Animation<>(0.1f, explosionFrames[0], explosionFrames[1], explosionFrames[2], explosionFrames[3], explosionFrames[4], explosionFrames[5], explosionFrames[6], explosionFrames[7]);

    }
    public Animation<Texture> getExplosionAnimation() {
        return animation;
    }
    public void createCollision(Bomb bomb) {
        bomb.getBombSprite().setRegion(this.getExplosionAnimation().getKeyFrame(bomb.time));
        bomb.getBombSprite().setSize(90 , 90 );
        if(!this.getExplosionAnimation().isAnimationFinished(bomb.time)) {
            bomb.time += Gdx.graphics.getDeltaTime();
        } else {
           bomb.time = 0;
        }
        this.getExplosionAnimation().setPlayMode(Animation.PlayMode.NORMAL);
        if (this.getExplosionAnimation().isAnimationFinished(bomb.time)) {
            bomb.setExploded();
            System.out.println(bomb.isExploded());
            //TODO : Remove the bomb from the game
        }
    }
    public void createCollision(RadioActiveBomb bomb) {
        bomb.getRadioactiveBombSprite().setRegion(getExplosionAnimation().getKeyFrame(bomb.time));
        bomb.getRadioactiveBombSprite().setSize(120 , 120);
        if(!getExplosionAnimation().isAnimationFinished(bomb.time)) {
            bomb.time += Gdx.graphics.getDeltaTime();
        } else {
            bomb.time = 0;
        }
        getExplosionAnimation().setPlayMode(Animation.PlayMode.NORMAL);
        if (getExplosionAnimation().isAnimationFinished(bomb.time)) {
            bomb.setExploded();
        }
    }



}
