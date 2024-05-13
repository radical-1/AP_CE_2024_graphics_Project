package com.game.atomicbomber.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public enum GameMusic {
    BABY_SHARK("music/babyshark.mp3"),
    BABEI("music/babaei.mp3"),
    KHOROOS("music/khoroos.mp3"),
    ;
    private final String path;
    private Music music;

    GameMusic(String path) {
        this.path = path;
    }
    public Music getMusic() {
        if(music == null)
            music = Gdx.audio.newMusic(Gdx.files.internal(path));
        return music;
    }
    public void mute() {
        getMusic().setVolume(0);
    }
    public void unMute() {
        getMusic().setVolume(1);
    }
}
