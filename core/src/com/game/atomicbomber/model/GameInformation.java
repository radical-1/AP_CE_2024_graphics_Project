package com.game.atomicbomber.model;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.game.atomicbomber.view.GameMusic;

public class GameInformation {
    private Difficulty difficulty;
    private boolean isMute; // true : mute , false : not mute
    private boolean isGrayScale; // true : grayScale , false : with color
    private boolean typeOfKeys; // true : arrowKeys , false : for a-w-d-s
    public GameMusic music;

    public GameInformation() {
        this.difficulty = Difficulty.EASY;
        this.isMute = false;
        this.isGrayScale = false;
        this.typeOfKeys = true;
        this.music = GameMusic.BABEI;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isMute() {
        return isMute;
    }

    public void mute() {
        this.isMute = true;
        music.mute();
    }
    public void unMute() {
        this.isMute = false;
        music.unMute();
    }
    public boolean isGrayScale() {
        return isGrayScale;
    }
    //TODO : complete grayscale part
    public void grayScale() {
        isGrayScale = true;
    }
    public void colorScale() {
        isGrayScale = false;
    }

    public boolean isTypeOfKeys() {
        return typeOfKeys;
    }

    public void setTypeOfKeys(boolean typeOfKeys) {
        this.typeOfKeys = typeOfKeys;
    }
    public Music getMusic() {
        return music.getMusic();
    }
    public void setMusic(GameMusic music) {
        this.music = music;
    }
    public int getUpKey() {
        if(typeOfKeys) {
            return Input.Keys.UP;
        } else {
            return Input.Keys.W;
        }
    }
    public int getDownKey() {
        if(typeOfKeys) {
            return Input.Keys.DOWN;
        } else {
            return Input.Keys.S;
        }
    }
    public int getLeftKey() {
        if(typeOfKeys) {
            return Input.Keys.LEFT;
        } else {
            return Input.Keys.A;
        }
    }
    public int getRightKey() {
        if(typeOfKeys) {
            return Input.Keys.RIGHT;
        } else {
            return Input.Keys.D;
        }
    }
}
