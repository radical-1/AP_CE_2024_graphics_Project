package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.model.game.Ship;
import com.game.atomicbomber.model.game.*;
import com.game.atomicbomber.model.User;

import java.util.ArrayList;

public class MainGameScreen implements Screen  {

    private Music backgroundMusic;
    private Sprite background;
    private TextButton pauseButton;
    private Stage stage;
    AtomicBomber game;
    private boolean isPaused;
    private Dialog pauseDialog;

    public Game gameModel;

    public MainGameScreen(AtomicBomber game) {

        isPaused = false;
        gameModel = new Game(User.getLoggedInUser());
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        backgroundMusic = User.getLoggedInUser().getGameInfo().getMusic();
        Texture backgroundTexture = new Texture("background.png");
        backgroundMusic.setLooping(true);
        background = new Sprite(backgroundTexture);
        background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);

        pauseButton = new TextButton("Pause", game.skin);
        pauseButton.setPosition(50, AtomicBomber.HEIGHT - 50); // replace with the desired position
        pauseButton.setSize(200, 100); // replace with the desired size


        // Add the pause button to the stage
        stage.addActor(pauseButton);
    }

    @Override
    public void show() {
        // Add a click listener to the pause button
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Pause the game
                pause();
            }
        });
        backgroundMusic.play();
    }


    @Override
    public void render(float delta) {
        if(isPaused) {
            stage.act(delta);
            stage.draw();
            return;
        }
        //Shooting part
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            //TODO : for bombs
        }
        //TODO : Update bullets
        //Moving part

        Gdx.gl.glClearColor(0.25f, 0.5f, 0.75f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);


        game.getBatch().begin();
        background.draw(game.getBatch());

        gameModel.getShip().render(delta, game);
        game.getBatch().end();
        stage.act(delta);
        stage.draw();



    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {
        // Create the pause dialog
        pauseDialog = new Dialog("Game Paused", game.skin);
        pauseDialog.text("The game is paused. Click OK to resume.");
        pauseDialog.button("OK", true); // the second parameter is the result returned when this button is clicked
        pauseDialog.setMovable(false);
        pauseDialog.setResizable(false);
        pauseDialog.show(stage);

        // Pause the game
        isPaused = true;
        backgroundMusic.pause();


        // Add a result listener to the pause dialog
        pauseDialog.button("resume",new InputListener() {
            public void clicked(InputEvent event, float x, float y) {
                // Resume the game when the OK button is clicked
                isPaused = false;
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();
        background.getTexture().dispose();
    }

}
