package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.ScreenManager;

public class MainMenuScreen implements Screen {
    private static final int BASE_Y = 300;
    private static final Texture backgroundTexture = new Texture("menu_background.jpg");


    private Sprite background;
    private Stage stage;
    private Table table;
    private Skin skin;
    private Dialog errorDialog;

    private TextButton settingsButton;
    private TextButton profileButton;
    private TextButton playButton;
    private TextButton exitButton;
    private TextButton showLeaderBoard;



    public MainMenuScreen(AtomicBomber game) {


        this.background = new Sprite();
        this.background = new Sprite(backgroundTexture);
        this.background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);

        skin = game.skin;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        // Create a table to layout the buttons
        Table table = new Table();
        table.setFillParent(true); // Make the table fill the whole stage
        table.defaults().width(300).height(120).spaceBottom(10); // Set default width, height and space between buttons

        playButton = new TextButton("Play", skin);
        playButton.setSize(300, 120);


        showLeaderBoard = new TextButton("Show LeaderBoard", skin);
        showLeaderBoard.setSize(450, 120);

        profileButton = new TextButton("go to\nProfile Menu", skin);
        profileButton.setSize(300, 120);

        settingsButton = new TextButton("Settings", skin);
        settingsButton.setSize(300, 120);

        exitButton = new TextButton("Exit", skin);
        exitButton.setSize(300, 120);

        // Add the buttons to the table
        table.add(playButton);
        table.row();
        table.add(showLeaderBoard);
        table.row();
        table.add(profileButton);
        table.row();
        table.add(settingsButton);
        table.row();
        table.add(exitButton);

        // Add the table to the stage
        stage.addActor(table);



    }
    @Override
    public void show() {
        showLeaderBoard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ScreenManager.getInstance().removeScreen("MainMenuScreen");
                ScreenManager.getInstance().setScreen("LeaderBoardScreen");
            }
        });
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ScreenManager.getInstance().removeScreen("MainMenuScreen");
                ScreenManager.getInstance().setScreen("SettingMenuScreen");
            }
        });
        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ScreenManager.getInstance().removeScreen("MainMenuScreen");
                ScreenManager.getInstance().setScreen("ProfileMenuScreen");
            }
        });
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ScreenManager.getInstance().removeScreen("MainMenuScreen");
                ScreenManager.getInstance().setScreen("MainGameScreen");
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ScreenManager.getInstance().removeScreen("MainMenuScreen");
                ScreenManager.getInstance().setScreen("RegisterScreen");
            }
        });
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.25f, 0.5f, 0.75f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        AtomicBomber.singleton.getBatch().begin();
        background.draw(AtomicBomber.singleton.getBatch());
        AtomicBomber.singleton.getBatch().end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
