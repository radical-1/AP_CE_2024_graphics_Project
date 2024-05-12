package com.game.atomicbomber.view.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.ScreenManager;
import com.game.atomicbomber.model.Avatar;
import com.game.atomicbomber.model.User;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

import java.io.File;
import java.io.FileFilter;

public class AvatarMenuScreen implements Screen {
    private TextButton[] avatarButtons = new TextButton[5];

    private Sprite background;
    private TextButton chooseFileButton;

    private SpriteBatch batch;
    private BitmapFont font;
    private Texture currentAvatar;
    private User user;
    private Stage stage;
    private TextButton backButton;
    private Skin skin;

    AtomicBomber game;

    public AvatarMenuScreen(AtomicBomber game) {
        Texture backgroundTexture = new Texture("avatar_menu_background.jpg");
        this.background = new Sprite(backgroundTexture);
        background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);
        this.game = game;
        batch = game.getBatch();
        font = new BitmapFont();
        user = User.getLoggedInUser();
        currentAvatar = user.getAvatar();


        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

        this.backButton = new TextButton("Back", skin);

        backButton.setPosition(0, AtomicBomber.HEIGHT - backButton.getHeight());
        chooseNewAvatar();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ScreenManager.getInstance().removeScreen("AvatarMenuScreen");
                ScreenManager.getInstance().setScreen("MainMenuScreen");
            }
        });
        stage.addActor(backButton);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(currentAvatar,Gdx.graphics.getWidth() - 300,Gdx.graphics.getHeight() - 300, 300, 300);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    public void chooseNewAvatar() {
        float x;
        float y;
        for (int i = 0; i < avatarButtons.length; i++) {
            final Avatar avatar = Avatar.values()[i];
            avatarButtons[i] = new TextButton("Avatar " + (i + 1), skin);
            avatarButtons[i].setSize(250, 100);
            if(i < 3) {
                x = i * (avatarButtons[i].getWidth() + 30);
                y = 10;
            } else {
                x = (i - 3) * (avatarButtons[i].getWidth() + 30);
                y = 10 + avatarButtons[i].getHeight() + 10;
            }
            avatarButtons[i].setPosition(x, y);
            avatarButtons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    user.setAvatar(avatar);
                    currentAvatar = avatar.getTexture();
                }
            });
            stage.addActor(avatarButtons[i]);
        }
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        currentAvatar.dispose();
    }

}