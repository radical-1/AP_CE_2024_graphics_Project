package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.GameController;
import com.game.atomicbomber.model.game.*;
import com.game.atomicbomber.model.User;

public class MainGameScreen implements Screen {
    private static final Texture backgroundTexture = new Texture("background.png");
    private static final Texture groundTexture = new Texture("ground.jpg");
    private Music backgroundMusic;
    private Sprite background;
    private Sprite ground;
    private TextButton pauseButton;
    private Stage stage;
    private boolean isPaused;
    private Dialog pauseDialog;
    //Info part
    private Sprite bombIcon;
    private Sprite radioActiveBombIcon;
    private Sprite clusterBombIcon;
    private Sprite killIcon;
    private Sprite waveIcon;

    private Label bombCountLabel;
    private Label radioActiveBombCountLabel;
    private Label clusterBombCountLabel;
    private Label killCountLabel;
    private Label waveNumberLabel;
    private Label difficultyLabel;


    public Game game;

    public MainGameScreen(AtomicBomber game) {
        //TODO : add things that should be in game screen like wave number and ...
        isPaused = false;
        this.game = new Game(User.getLoggedInUser());
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        backgroundMusic = User.getLoggedInUser().getGameInfo().getMusic();
        backgroundMusic.setLooping(true);

        background = new Sprite(backgroundTexture);
        background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);
        ground = new Sprite(groundTexture);
        ground.setSize(AtomicBomber.WIDTH, 75);

        pauseButton = new TextButton("Pause", game.skin);
        pauseButton.setPosition(50, AtomicBomber.HEIGHT - 150); // replace with the desired position
        pauseButton.setSize(200, 100); // replace with the desired size

        handleInfo();
        // Add the pause button to the stage
        stage.addActor(pauseButton);
        stage.addActor(bombCountLabel);
        stage.addActor(radioActiveBombCountLabel);
        stage.addActor(clusterBombCountLabel);
        stage.addActor(killCountLabel);
        stage.addActor(waveNumberLabel);
        stage.addActor(difficultyLabel);


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

    public void handleInfo() {
        // Initialize the bomb icon
        Texture bombIconTexture = new Texture("bomb_icon.png"); // Replace with the path to your bomb icon texture
        Texture radioActiveBombIconTexture = new Texture("radioactive_bomb_icon.png"); // Replace with the path to your radioactive bomb icon texture
        Texture clusterBombIconTexture = new Texture("cluster_bomb_icon.png"); // Replace with the path to your cluster bomb icon texture
        Texture killIconTexture = new Texture("kill_icon.png"); // Replace with the path to your kill icon texture
        Texture waveIconTexture = new Texture("wave_icon.png"); // Replace with the path to your wave icon texture

        bombIcon = new Sprite(bombIconTexture);
        bombIcon.setSize(40, 40); // Replace with the desired size
        bombIcon.setPosition(60, AtomicBomber.HEIGHT - 200); // Replace with the desired position
        radioActiveBombIcon = new Sprite(radioActiveBombIconTexture);
        radioActiveBombIcon.setSize(40, 40); // Replace with the desired size
        radioActiveBombIcon.setPosition(60, AtomicBomber.HEIGHT - 250); // Replace with the desired position
        clusterBombIcon = new Sprite(clusterBombIconTexture);
        clusterBombIcon.setSize(40, 40); // Replace with the desired size
        clusterBombIcon.setPosition(60, AtomicBomber.HEIGHT - 300); // Replace with the desired position
        killIcon = new Sprite(killIconTexture);
        killIcon.setSize(40, 40); // Replace with the desired size
        killIcon.setPosition(60, AtomicBomber.HEIGHT - 350); // Replace with the desired position
        waveIcon = new Sprite(waveIconTexture);
        waveIcon.setSize(40, 40); // Replace with the desired size
        waveIcon.setPosition(60, AtomicBomber.HEIGHT - 400); // Replace with the desired position

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(); // Replace with your desired font

        // Initialize
        bombCountLabel = new Label("Bombs: " + Game.getPlayingGame().getNumberOfBombs(), labelStyle);
        bombCountLabel.setPosition(bombIcon.getX() + bombIcon.getWidth() + 10, bombIcon.getY() + 10);
        bombCountLabel.setFontScale(2); // Replace with the desired font scale
        bombCountLabel.setColor(0, 2, 1, 1); // Replace with the desired color
        radioActiveBombCountLabel = new Label("Radioactive Bombs: " + Game.getPlayingGame().getNumberOfAtomicBomb(), labelStyle);
        radioActiveBombCountLabel.setPosition(radioActiveBombIcon.getX() + radioActiveBombIcon.getWidth() + 10, radioActiveBombIcon.getY() + 10);
        radioActiveBombCountLabel.setFontScale(2); // Replace with the desired font scale
        radioActiveBombCountLabel.setColor(0, 2, 1, 1); // Replace with the desired color
        clusterBombCountLabel = new Label("Cluster Bombs: " + Game.getPlayingGame().getNumberOfCluster(), labelStyle);
        clusterBombCountLabel.setPosition(clusterBombIcon.getX() + clusterBombIcon.getWidth() + 10, clusterBombIcon.getY() + 10);
        clusterBombCountLabel.setFontScale(2); // Replace with the desired font scale
        clusterBombCountLabel.setColor(0, 2, 1, 1); // Replace with the desired color
        killCountLabel = new Label("Kills: " + Game.getPlayingGame().getKills(), labelStyle);
        killCountLabel.setPosition(killIcon.getX() + killIcon.getWidth() + 10, killIcon.getY() + 10);
        killCountLabel.setFontScale(2); // Replace with the desired font scale
        killCountLabel.setColor(0, 2, 1, 1); // Replace with the desired color
        waveNumberLabel = new Label("Wave: " + Game.getPlayingGame().getWaveNumber(), labelStyle);
        waveNumberLabel.setPosition(waveIcon.getX() + waveIcon.getWidth() + 10, waveIcon.getY() + 10);
        waveNumberLabel.setFontScale(2); // Replace with the desired font scale
        waveNumberLabel.setColor(0, 2, 1, 1); // Replace with the desired
        difficultyLabel = new Label("Difficulty: " + Game.getPlayingGame().getDifficulty() , labelStyle);
        difficultyLabel.setPosition(AtomicBomber.WIDTH / 2, AtomicBomber.HEIGHT- 70);
        difficultyLabel.setFontScale(2); // Replace with the desired font scale
        difficultyLabel.setColor(2, 0, 1, 1); // Replace with the desired color


    }

    @Override
    public void render(float delta) {
        //TODO : handle pause
        if (isPaused) {
            stage.act(delta);
            stage.draw();
            return;
        }
        //Shooting part
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            GameController.shoot();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            GameController.shootCluster();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            GameController.shootRadioActive();
        }

        //Moving part

        Gdx.gl.glClearColor(0.25f, 0.5f, 0.75f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);


        AtomicBomber.singleton.getBatch().begin();
        background.draw(AtomicBomber.singleton.getBatch());
        ground.draw(AtomicBomber.singleton.getBatch());
        bombIcon.draw(AtomicBomber.singleton.getBatch());
        radioActiveBombIcon.draw(AtomicBomber.singleton.getBatch());
        clusterBombIcon.draw(AtomicBomber.singleton.getBatch());
        killIcon.draw(AtomicBomber.singleton.getBatch());
        waveIcon.draw(AtomicBomber.singleton.getBatch());
        game.getShip().render(delta);
        game.update(delta);
        AtomicBomber.singleton.getBatch().end();
        bombCountLabel.setText("Bombs: " + Game.getPlayingGame().getNumberOfBombs());
        radioActiveBombCountLabel.setText("Radioactive Bombs: " + Game.getPlayingGame().getNumberOfAtomicBomb());
        clusterBombCountLabel.setText("Cluster Bombs: " + Game.getPlayingGame().getNumberOfCluster());
        killCountLabel.setText("Kills: " + Game.getPlayingGame().getKills());
        waveNumberLabel.setText("Wave: " + Game.getPlayingGame().getWaveNumber());
        if (Game.getPlayingGame().getNumberOfBombs() < 3) {
            bombCountLabel.setColor(1, 0, 0, 1);
        } else {
            bombCountLabel.setColor(0, 2, 1, 1);
        }
        if (Game.getPlayingGame().getNumberOfAtomicBomb() <= 0) {
            radioActiveBombCountLabel.setColor(1, 0, 0, 1);
        } else {
            radioActiveBombCountLabel.setColor(0, 2, 1, 1);
        }
        if (Game.getPlayingGame().getNumberOfCluster() <= 1) {
            clusterBombCountLabel.setColor(1, 0, 0, 1);
        } else {
            clusterBombCountLabel.setColor(0, 2, 1, 1);
        }
        if (Game.getPlayingGame().getKills() < 10) {
            killCountLabel.setColor(1, 0, 0, 1);
        } else {
            killCountLabel.setColor(0, 2, 1, 1);
        }


        stage.act(delta);
        stage.draw();


    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {
        // Create the pause dialog
        pauseDialog = new Dialog("Game Paused", AtomicBomber.singleton.skin);
        pauseDialog.text("The game is paused. Click OK to resume.");
        pauseDialog.button("OK", true); // the second parameter is the result returned when this button is clicked
        pauseDialog.setMovable(false);
        pauseDialog.setResizable(false);
        pauseDialog.show(stage);

        // Pause the game
        isPaused = true;
        backgroundMusic.pause();


        // Add a result listener to the pause dialog
        pauseDialog.button("resume", new InputListener() {
            public void clicked(InputEvent event, float x, float y) {
                // Resume the game when the OK button is clicked
                resume();
            }
        });
    }

    @Override
    public void resume() {
        isPaused = false;
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
