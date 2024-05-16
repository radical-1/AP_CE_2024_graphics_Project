package com.game.atomicbomber.model;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

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
    public static Avatar getRandomAvatar() {
        // Assign a random avatar
        Avatar[] avatars = Avatar.values();
        Random random = new Random();
        int randomIndex = random.nextInt(avatars.length);
        return avatars[randomIndex];
    }
    public Texture getTexture() {
        return new Texture(path);
    }
}
