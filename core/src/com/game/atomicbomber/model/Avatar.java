package com.game.atomicbomber.model;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public enum Avatar {
    AVATAR1("avatars/avatar1.png"),
    AVATAR2("avatars/avatar2.png"),
    AVATAR3("avatars/avatar3.png"),
    AVATAR4("avatars/avatar4.png"),
    AVATAR5("avatars/avatar5.png"),
    AVATAR6("avatars/avatar6.png"),
    AVATAR7("avatars/avatar7.png"),
    AVATAR8("avatars/avatar8.png"),
    AVATAR9("avatars/avatar9.png"),
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
