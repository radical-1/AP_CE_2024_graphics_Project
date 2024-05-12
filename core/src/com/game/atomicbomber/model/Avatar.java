package com.game.atomicbomber.model;

import com.badlogic.gdx.graphics.Texture;

public enum Avatar {
    AVATAR1("avatars/avatar1.jpg"),
    AVATAR2("avatars/avatar2.png"),
    AVATAR3("avatars/avatar3.png"),
    AVATAR4("avatars/avatar4.jfif"),
    AVATAR5("avatars/avatar5.png"),
//    AVATAR6("avatars/avatar6.jpg"),
//    AVATAR7("avatars/avatar7.jpg"),
//    AVATAR8("avatars/avatar8.jpg"),
//    AVATAR9("avatars/avatar9.jpg"),
//    AVATAR10("avatars/avatar10.jpg")
    ;

    private final String path;

    Avatar(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    public static String getRandomAvatar() {
        return values()[(int) (Math.random() * values().length)].getPath();
    }
    public Texture getTexture() {
        return new Texture(path);
    }
}
