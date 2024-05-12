package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.ScreenManager;

public class MainMenuScreen implements Screen {
    private static final int BASE_Y = 300;

    AtomicBomber game;

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
        this.game = game;

        this.background = new Sprite();
        Texture backgroundTexture = new Texture("RegisterBackground.jfif");
        this.background = new Sprite(backgroundTexture);
        this.background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);

        skin = game.skin;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        showLeaderBoard = new TextButton("Show LeaderBoard", skin);
        showLeaderBoard.setSize(450, 100);
        showLeaderBoard.setPosition((AtomicBomber.WIDTH - showLeaderBoard.getWidth()) / 2, BASE_Y + showLeaderBoard.getHeight() + 120);

        settingsButton = new TextButton("Settings", skin);
        settingsButton.setSize(200, 60);
        settingsButton.setPosition((AtomicBomber.WIDTH - settingsButton.getWidth()) / 2, BASE_Y + settingsButton.getHeight() + 40);

        profileButton = new TextButton("go to\nProfile Menu", skin);
        profileButton.setSize(300, 120);
        profileButton.setPosition((AtomicBomber.WIDTH - profileButton.getWidth()) / 2, BASE_Y - profileButton.getHeight() + 20);

        playButton = new TextButton("Play", skin);
        playButton.setSize(200, 60);
        playButton.setPosition((AtomicBomber.WIDTH - playButton.getWidth()) / 2, BASE_Y - playButton.getHeight() - 120);

        exitButton = new TextButton("Exit", skin);
        exitButton.setSize(200, 60);
        exitButton.setPosition((AtomicBomber.WIDTH - exitButton.getWidth()) / 2, BASE_Y - exitButton.getHeight() - 200);


        table.addActor(settingsButton);
        table.addActor(profileButton);
        table.addActor(playButton);
        table.addActor(exitButton);
        table.addActor(showLeaderBoard);


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
        game.getBatch().begin();
        background.draw(game.getBatch());
        game.getBatch().end();
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
