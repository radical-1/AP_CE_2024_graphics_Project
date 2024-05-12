package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.ScreenManager;
import com.game.atomicbomber.model.Difficulty;
import com.game.atomicbomber.model.User;

public class SettingMenuScreen implements Screen {

    private Stage stage;
    private Sprite background;
    private Table table;

    AtomicBomber game;
    private User user;
    private TextButton muteButton;
    private Slider difficultySlider;
    private CheckBox grayscaleCheckBox;
    private Slider movementTypeSlider;
    private TextButton backButton;

    public SettingMenuScreen(AtomicBomber game) {
        user = User.getLoggedInUser();
        this.game = game;
        this.background = new Sprite();
        Texture backgroundTexture = new Texture("RegisterBackground.jfif");
        this.background = new Sprite(backgroundTexture);
        this.background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);


        muteButton = new TextButton("Mute", game.skin);
        muteButton.setSize(200, 60);
        muteButton.setPosition((AtomicBomber.WIDTH - muteButton.getWidth()) / 2, AtomicBomber.HEIGHT - 50);

        backButton = new TextButton("Back", game.skin);
        backButton.setSize(200, 60);
        backButton.setPosition((AtomicBomber.WIDTH - backButton.getWidth()) / 2, 50);


        difficultySlider = new Slider(0, 2, 1, false, game.skin);
        difficultySlider.setValue(User.getLoggedInUser().getGameInfo().getDifficulty().ordinal());
        difficultySlider.setSize(200, 60);
        difficultySlider.setPosition((AtomicBomber.WIDTH - difficultySlider.getWidth()) / 2, AtomicBomber.HEIGHT - 150);

        movementTypeSlider = new Slider(0, 1, 1, false, game.skin);
        movementTypeSlider.setValue(User.getLoggedInUser().getGameInfo().isTypeOfKeys() ? 0 : 1);
        movementTypeSlider.setSize(200, 60);
        movementTypeSlider.setPosition((AtomicBomber.WIDTH - movementTypeSlider.getWidth()) / 2, AtomicBomber.HEIGHT - 250);

        grayscaleCheckBox = new CheckBox("Grayscale Mode", game.skin);
        grayscaleCheckBox.setChecked(User.getLoggedInUser().getGameInfo().isGrayScale());
        grayscaleCheckBox.setPosition((AtomicBomber.WIDTH - grayscaleCheckBox.getWidth()) / 2, AtomicBomber.HEIGHT - 350);

        // Add the slider to the stage
        table.addActor(muteButton);
        table.addActor(backButton);
        table.addActor(difficultySlider);
        table.addActor(grayscaleCheckBox);
        stage.addActor(table);
    }
    @Override
    public void show() {

        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 // toggle the mute status
                if (!user.getGameInfo().isMute()) {
                    muteButton.setText("Unmute");
                    user.getGameInfo().mute();

                } else {
                    muteButton.setText("Mute");
                    user.getGameInfo().unMute();
                }
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ScreenManager.getInstance().removeScreen("SettingMenuScreen");
                ScreenManager.getInstance().setScreen("MainMenuScreen");
            }
        });
        difficultySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Set the game difficulty based on the slider value
                Difficulty difficulty = Difficulty.values()[(int) difficultySlider.getValue()];
                user.getGameInfo().setDifficulty(difficulty);
            }
        });
        movementTypeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Set the movement type based on the slider value
                boolean typeOfKeys = movementTypeSlider.getValue() == 0;
                user.getGameInfo().setTypeOfKeys(typeOfKeys);
            }
        });

        grayscaleCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Toggle the grayscale mode based on the checkbox status
                if (grayscaleCheckBox.isChecked()) {
                    user.getGameInfo().grayScale();
                } else {
                    user.getGameInfo().colorScale();
                }
            }
        });


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.25f, 0.5f, 0.75f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        background.draw(game.getBatch());
        game.getBatch().end();
        stage.act(delta);
        stage.draw();

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
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
