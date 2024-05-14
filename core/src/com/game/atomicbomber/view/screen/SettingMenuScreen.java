package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.ScreenManager;
import com.game.atomicbomber.model.Difficulty;
import com.game.atomicbomber.model.User;

public class SettingMenuScreen implements Screen {
    private static final Texture backgroundTexture = new Texture("setting_menu_background.jpg");

    private Stage stage;
    private Sprite background;
    private Table table;

    private User user;
    private TextButton muteButton;
    private Slider difficultySlider;
    private CheckBox grayscaleCheckBox;
    private Slider movementTypeSlider;
    private TextButton backButton;
    private Label difficultyLabel;
    private Label movementTypeLabel;

    private ShaderProgram grayscaleShader;

    public SettingMenuScreen(AtomicBomber game) {
        grayscaleShader = new ShaderProgram(Gdx.files.internal("vertex.glsl"), Gdx.files.internal("grayscale.glsl"));
        if (!grayscaleShader.isCompiled()) {
            Gdx.app.error("Shader", "Compilation failed:\n" + grayscaleShader.getLog());
        }
        user = User.getLoggedInUser();
        
        this.background = new Sprite();
        this.background = new Sprite(backgroundTexture);
        this.background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().width(300).height(120).spaceBottom(10);

        muteButton = new TextButton(user.getGameInfo().isMute() ? "UnMute" : "Mute", game.skin);
        muteButton.setSize(200, 60);

        difficultySlider = new Slider(0, 2, 1, false, game.skin);
        difficultySlider.setValue(User.getLoggedInUser().getGameInfo().getDifficulty().ordinal());
        difficultySlider.setSize(200, 60);
        // Initialize the difficulty label with the current difficulty
        difficultyLabel = new Label("Difficulty: " + user.getGameInfo().getDifficulty().toString(), game.skin);

        movementTypeSlider = new Slider(0, 1, 1, false, game.skin);
        movementTypeSlider.setValue(User.getLoggedInUser().getGameInfo().isTypeOfKeys() ? 0 : 1);
        movementTypeSlider.setSize(200, 60);
        // Initialize the movement type label with the current movement type
        movementTypeLabel = new Label("Movement Type: " + (user.getGameInfo().isTypeOfKeys() ? "Arrow Keys" : "WASD"), game.skin);

        grayscaleCheckBox = new CheckBox("Grayscale Mode", game.skin);
        grayscaleCheckBox.setChecked(User.getLoggedInUser().getGameInfo().isGrayScale());

        backButton = new TextButton("Back", game.skin);
        backButton.setSize(200, 60);
        // Add the slider to the stage
        table.add(muteButton);
        table.row();
        table.add(movementTypeSlider);
        table.add(movementTypeLabel);
        table.row();
        table.add(difficultySlider);
        table.add(difficultyLabel);
        table.row();
        table.add(grayscaleCheckBox);
        table.row();
        table.add(backButton);
        table.row();
        stage.addActor(table);
    }
    @Override
    public void show() {

        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 // toggle the mute status
                if (!user.getGameInfo().isMute()) {
                    muteButton.setText("UnMute");
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
                difficultyLabel.setText("Difficulty: " + difficulty.toString());
            }
        });
        movementTypeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Set the movement type based on the slider value
                boolean typeOfKeys = movementTypeSlider.getValue() == 0;
                user.getGameInfo().setTypeOfKeys(typeOfKeys);
                movementTypeLabel.setText("Movement Type: " + (typeOfKeys ? "Arrow Keys" : "WASD"));
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

        if (user.getGameInfo().isGrayScale()) {
            AtomicBomber.singleton.getBatch().setShader(grayscaleShader);
        } else {
            AtomicBomber.singleton.getBatch().setShader(null);
        }
        AtomicBomber.singleton.getBatch().begin();
        background.draw(AtomicBomber.singleton.getBatch());
        AtomicBomber.singleton.getBatch().end();
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
