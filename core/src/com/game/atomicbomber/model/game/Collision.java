package com.game.atomicbomber.model.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.atomicbomber.model.game.bombs.Bomb;
import com.game.atomicbomber.model.game.bombs.EnemyBullet;
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
        atomicBombFrames = new Texture[20];
        for (int i = 0; i < 20; i++) {
            atomicBombFrames[i] = new Texture("tiles/sprite" + (i + 1) + ".png");
        }
    }

    public Collision(String type) {
        if (type.equals("atomicBomb"))
            animation = new Animation<>(0.1f, atomicBombFrames[1], atomicBombFrames[1], atomicBombFrames[2], atomicBombFrames[3], atomicBombFrames[4]
                    , atomicBombFrames[5], atomicBombFrames[6], atomicBombFrames[7], atomicBombFrames[8], atomicBombFrames[9]
                    , atomicBombFrames[10], atomicBombFrames[11], atomicBombFrames[12], atomicBombFrames[13], atomicBombFrames[14]
                    , atomicBombFrames[15], atomicBombFrames[16], atomicBombFrames[17], atomicBombFrames[18], atomicBombFrames[19]);
        else if (type.equals("explosion"))
            animation = new Animation<>(0.1f, explosionFrames[0], explosionFrames[1], explosionFrames[2], explosionFrames[3], explosionFrames[4], explosionFrames[5], explosionFrames[6], explosionFrames[7]);

    }

    public Animation<Texture> getExplosionAnimation() {
        return animation;
    }

    public void createCollision(Bomb bomb) {
        bomb.getBombSprite().setRegion(this.getExplosionAnimation().getKeyFrame(bomb.time));
        bomb.getBombSprite().setSize(90, 90);
        bomb.playSoundEffect();
        if (!this.getExplosionAnimation().isAnimationFinished(bomb.time)) {
            bomb.time += Gdx.graphics.getDeltaTime();
        } else {
            bomb.time = 0;
        }
        this.getExplosionAnimation().setPlayMode(Animation.PlayMode.NORMAL);
        if (this.getExplosionAnimation().isAnimationFinished(bomb.time)) {
            bomb.setExploded();
        }
    }

    public void createCollision(RadioActiveBomb bomb) {
        bomb.getRadioactiveBombSprite().setRegion(getExplosionAnimation().getKeyFrame(bomb.time));
        bomb.getRadioactiveBombSprite().setSize(240, 240);
        if (!getExplosionAnimation().isAnimationFinished(bomb.time)) {
            bomb.time += Gdx.graphics.getDeltaTime();
        } else {
            bomb.time = 0;
        }
        getExplosionAnimation().setPlayMode(Animation.PlayMode.NORMAL);
        if (getExplosionAnimation().isAnimationFinished(bomb.time)) {
            bomb.setExploded();
        }
    }

    public void createCollision(EnemyBullet bullet) {

        bullet.getBulletSprite().setRegion(getExplosionAnimation().getKeyFrame(bullet.time));
        bullet.getBulletSprite().setSize(90, 90);
        this.getExplosionAnimation().setPlayMode(Animation.PlayMode.NORMAL);
        if (!this.getExplosionAnimation().isAnimationFinished(bullet.time)) {

            bullet.time += Gdx.graphics.getDeltaTime();
        } else {
            bullet.setExploded();
            System.out.println("Bullet time: " + bullet.time);
            bullet.time = 0;
        }
        if (this.getExplosionAnimation().isAnimationFinished(bullet.time)) {

        }
    }
}
