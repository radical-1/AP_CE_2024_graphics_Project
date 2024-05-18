package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.GameController;
import com.game.atomicbomber.controller.ScreenManager;
import com.game.atomicbomber.model.GameData;
import com.game.atomicbomber.model.game.*;
import com.game.atomicbomber.model.User;
import com.game.atomicbomber.view.GameMusic;
import com.game.atomicbomber.view.KeyGuide;

public class MainGameScreen implements Screen {
    private static final Texture backgroundTexture = new Texture("background.jpg");
    private static final Texture groundTexture = new Texture("ground.jpg");
    private static final Texture freezeTexture = new Texture("freeze.png");
    private static final Texture bombIconTexture = new Texture("bomb_icon.png");
    private static final Texture radioActiveBombIconTexture = new Texture("radioactive_bomb_icon.png");
    private static final Texture clusterBombIconTexture = new Texture("cluster_bomb_icon.png");
    private static final Texture killIconTexture = new Texture("kill_icon.png");
    private static final Texture waveIconTexture = new Texture("wave_icon.png");
    private static final Texture healthIconTexture = new Texture("health_icon.png");
    private static final Texture migWarningTexture = new Texture("migwarning.png");

    private Music backgroundMusic;
    private Sprite background;
    private Sprite ground;
    private Sprite freeze;
    private TextButton pauseButton;
    private Stage stage;
    private boolean isPaused;
    private Window pauseWindow;
    private Window gameOverWindow;
    private Window changeMusicWindow;
    private Window guideWindow;
    private ProgressBar freezeBar;
    //Info part
    private Sprite bombIcon;
    private Sprite radioActiveBombIcon;
    private Sprite clusterBombIcon;
    private Sprite killIcon;
    private Sprite waveIcon;
    private Sprite healthIcon;

    private Label bombCountLabel;
    private Label radioActiveBombCountLabel;
    private Label clusterBombCountLabel;
    private Label killCountLabel;
    private Label waveNumberLabel;
    private Label difficultyLabel;
    private Label healthLabel;



    public MainGameScreen(AtomicBomber game) {

        ProgressBar.ProgressBarStyle progressBarStyle = AtomicBomber.singleton.skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class);

        // Create the ProgressBar
        freezeBar = new ProgressBar(0f, 1f, 0.1f, false, progressBarStyle);
        freezeBar.setBounds(AtomicBomber.WIDTH - 300, AtomicBomber.HEIGHT - 100, 200, 50);

        isPaused = false;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        backgroundMusic = User.getLoggedInUser().getGameInfo().getMusic();
        backgroundMusic.setLooping(true);

        background = new Sprite(backgroundTexture);
        background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);
        freeze = new Sprite(freezeTexture);
        freeze.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);
        ground = new Sprite(groundTexture);
        ground.setSize(AtomicBomber.WIDTH, 80);


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
        stage.addActor(healthLabel);
        stage.addActor(freezeBar);


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
        healthIcon = new Sprite(healthIconTexture);
        healthIcon.setSize(40, 40); // Replace with the desired size
        healthIcon.setPosition(60, AtomicBomber.HEIGHT - 450); // Replace with the desired position

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
        difficultyLabel = new Label("Difficulty: " + Game.getPlayingGame().getDifficulty(), labelStyle);
        difficultyLabel.setPosition(AtomicBomber.WIDTH / 2, AtomicBomber.HEIGHT - 70);
        difficultyLabel.setFontScale(2); // Replace with the desired font scale
        difficultyLabel.setColor(2, 0, 1, 1); // Replace with the desired color
        healthLabel = new Label("Health: " + Game.getPlayingGame().getShip().getHealth(), labelStyle);
        healthLabel.setPosition(healthIcon.getX() + healthIcon.getWidth() + 10, healthIcon.getY() + 10);
        healthLabel.setFontScale(2); // Replace with the desired font scale
        healthLabel.setColor(0, 2, 1, 1); // Replace with the desired color

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.H) && Game.getPlayingGame().isGameOver()) {
            isPaused = false;
            backgroundMusic.play();
            GameController.increaseHealth();
            GameController.goBackToGame();
            gameOverWindow.remove();
            return;
        }
        if (isPaused) {
            stage.act(delta);
            stage.draw();
            return;
        }
        if(Game.getPlayingGame().isNewWave()) {
            stage.act(delta);
            stage.draw();
            showWaveCompletion();
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.TAB)){
            GameController.freeze();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            GameController.goToNextWave();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.G)){
            GameController.AddOneRadioactiveBomb();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)){
            GameController.AddOneClusterBomb();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.T)){
            Game.getPlayingGame().spawnOneTankInRandomPlace();
        }

        //Moving part

        Gdx.gl.glClearColor(0.25f, 0.5f, 0.75f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);


        AtomicBomber.singleton.getBatch().begin();
        drawObjects(delta);
        AtomicBomber.singleton.getBatch().end();
        updateButtons();

        stage.act(delta);
        stage.draw();


    }
    private void drawObjects(float delta) {
        background.draw(AtomicBomber.singleton.getBatch());
        ground.draw(AtomicBomber.singleton.getBatch());
        if (Game.getPlayingGame().isFroze()) {
            freeze.draw(AtomicBomber.singleton.getBatch());
        }
        if(Game.getPlayingGame().isMigWarning()){
            AtomicBomber.singleton.getBatch().draw(migWarningTexture, AtomicBomber.WIDTH - 200, AtomicBomber.HEIGHT - 200, 100, 100);
        }
        bombIcon.draw(AtomicBomber.singleton.getBatch());
        radioActiveBombIcon.draw(AtomicBomber.singleton.getBatch());
        clusterBombIcon.draw(AtomicBomber.singleton.getBatch());
        killIcon.draw(AtomicBomber.singleton.getBatch());
        waveIcon.draw(AtomicBomber.singleton.getBatch());
        healthIcon.draw(AtomicBomber.singleton.getBatch());
        Game.getPlayingGame().getShip().render(delta);
        Game.getPlayingGame().update(delta);
        freezeBar.setValue(Game.getPlayingGame().getFreezeBarValue());
    }

    private void updateButtons() {
        bombCountLabel.setText("Bombs: " + Game.getPlayingGame().getNumberOfBombs());
        radioActiveBombCountLabel.setText("Radioactive Bombs: " + Game.getPlayingGame().getNumberOfAtomicBomb());
        clusterBombCountLabel.setText("Cluster Bombs: " + Game.getPlayingGame().getNumberOfCluster());
        killCountLabel.setText("Kills: " + Game.getPlayingGame().getKills());
        waveNumberLabel.setText("Wave: " + Game.getPlayingGame().getWaveNumber());
        healthLabel.setText("Health: " + Game.getPlayingGame().getShip().getHealth());
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
        if (Game.getPlayingGame().getShip().getHealth() <= 50) {
            healthLabel.setColor(1, 0, 0, 1);
        } else {
            healthLabel.setColor(0, 2, 1, 1);
        }
        if (Game.getPlayingGame().isGameOver()) {
            handleGameOver();

        }
    }

    private void handleGameOver() {
        isPaused = true;
        backgroundMusic.stop();
        gameOverWindow = new Window("Game Over", AtomicBomber.singleton.skin);
        gameOverWindow.setResizable(false);
        gameOverWindow.setSize(400, 200);
        gameOverWindow.setPosition(AtomicBomber.WIDTH / 2 - gameOverWindow.getWidth() / 2, AtomicBomber.HEIGHT / 2 - gameOverWindow.getHeight() / 2); // Center the window

        TextButton endGameButton = new TextButton("End Game", AtomicBomber.singleton.skin);
        gameOverWindow.add(endGameButton);
        endGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameData.saveGameData(Game.getPlayingGame());
                User.getLoggedInUser().updateInfo();
                Game.setPlayingGame(null);
                dispose();
                ScreenManager.getInstance().removeScreen("MainGameScreen");
                ScreenManager.getInstance().setScreen("MainMenuScreen");
            }
        });
        stage.addActor(gameOverWindow);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {
        // Create the pause window
        pauseWindow = new Window("Game Paused", AtomicBomber.singleton.skin);
        pauseWindow.setResizable(false);
        pauseWindow.setSize(600, 600);
        pauseWindow.setPosition(AtomicBomber.WIDTH / 2 - pauseWindow.getWidth() / 2, AtomicBomber.HEIGHT / 2 - pauseWindow.getHeight() / 2); // Center the window

        // Create buttons for each action

        TextButton changeMusicButton = new TextButton("Change Music", AtomicBomber.singleton.skin);
        TextButton stopMusicButton = new TextButton("Stop Music", AtomicBomber.singleton.skin);
        TextButton showGuideButton = new TextButton("Show Guide", AtomicBomber.singleton.skin);
        TextButton resumeButton = new TextButton("Resume", AtomicBomber.singleton.skin);
        TextButton saveAndExitButton = new TextButton("Save and Exit", AtomicBomber.singleton.skin);
        TextButton exitWithoutSavingButton = new TextButton("Exit Without Saving", AtomicBomber.singleton.skin);
        // Add buttons to the window
        pauseWindow.add(changeMusicButton);
        pauseWindow.row();
        pauseWindow.add(stopMusicButton);
        pauseWindow.row();
        pauseWindow.add(showGuideButton);
        pauseWindow.row();
        pauseWindow.add(resumeButton);
        pauseWindow.row();
        pauseWindow.add(saveAndExitButton);
        pauseWindow.row();
        pauseWindow.add(exitWithoutSavingButton);

        // Change music button
        changeMusicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeMusic();
            }
        });
        stopMusicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(User.getLoggedInUser().getGameInfo().isMute())
                    User.getLoggedInUser().getGameInfo().unMute();
                else
                    User.getLoggedInUser().getGameInfo().mute();

            }
        });
        // Show guide button
        showGuideButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleGuideWindow();
            }
        });

        // Resume button
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Resume the game
                resume();
            }
        });
        // Save and Exit button
        saveAndExitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameData.saveGameData(Game.getPlayingGame());
                User.getLoggedInUser().updateInfo();
                dispose();
                ScreenManager.getInstance().removeScreen("MainGameScreen");
                ScreenManager.getInstance().setScreen("MainMenuScreen");
            }
        });
        // Exit without saving button
        exitWithoutSavingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                Game.setPlayingGame(null);
                ScreenManager.getInstance().removeScreen("MainGameScreen");
                ScreenManager.getInstance().setScreen("MainMenuScreen");
            }
        });
        // Add window to the stage
        stage.addActor(pauseWindow);

        // Pause the game
        isPaused = true;
        backgroundMusic.pause();
    }

    @Override
    public void resume() {
        isPaused = false;
        backgroundMusic.play();
        pauseWindow.remove();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        backgroundMusic.dispose();

    }

    private void changeMusic() {
        // Hide the pause window
        pauseWindow.setVisible(false);

        // Create the music selection window
        Window musicSelectionWindow = new Window("Select Music", AtomicBomber.singleton.skin);
        musicSelectionWindow.setResizable(false);
        musicSelectionWindow.setSize(600, 600);
        musicSelectionWindow.setPosition(AtomicBomber.WIDTH / 2 - musicSelectionWindow.getWidth() / 2, AtomicBomber.HEIGHT / 2 - musicSelectionWindow.getHeight() / 2); // Center the window

        // Create buttons for each music option
        TextButton music1Button = new TextButton("Khoroos Khoroos", AtomicBomber.singleton.skin);
        TextButton music2Button = new TextButton("Baby Shark", AtomicBomber.singleton.skin);
        TextButton music3Button = new TextButton("Babaei to Bazigooshi", AtomicBomber.singleton.skin);
        TextButton music4Button = new TextButton("Wheels on The Bus", AtomicBomber.singleton.skin);

        // Add buttons to the window
        musicSelectionWindow.add(music1Button);
        musicSelectionWindow.row();
        musicSelectionWindow.add(music2Button);
        musicSelectionWindow.row();
        musicSelectionWindow.add(music3Button);
        musicSelectionWindow.row();
        musicSelectionWindow.add(music4Button);
        musicSelectionWindow.row();

        // Add listeners to the music buttons
        music1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User.getLoggedInUser().getGameInfo().changeMusic(GameMusic.KHOROOS);
                backgroundMusic = User.getLoggedInUser().getGameInfo().getMusic();
            }
        });
        music2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User.getLoggedInUser().getGameInfo().changeMusic(GameMusic.BABY_SHARK);
                backgroundMusic = User.getLoggedInUser().getGameInfo().getMusic();
            }
        });
        music3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User.getLoggedInUser().getGameInfo().changeMusic(GameMusic.BABEI);
                backgroundMusic = User.getLoggedInUser().getGameInfo().getMusic();
            }
        });
        music4Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User.getLoggedInUser().getGameInfo().changeMusic(GameMusic.WHEELS_ON_THE_BUS);
                backgroundMusic = User.getLoggedInUser().getGameInfo().getMusic();
            }
        });

        // Create a close button
        TextButton closeButton = new TextButton("Close", AtomicBomber.singleton.skin);
        backgroundMusic = User.getLoggedInUser().getGameInfo().getMusic();
        musicSelectionWindow.add(closeButton);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Hide the music selection window and show the pause window again
                musicSelectionWindow.remove();
                musicSelectionWindow.setVisible(false);
                pauseWindow.setVisible(true);
            }
        });

        // Add the music selection window to the stage
        stage.addActor(musicSelectionWindow);
    }

    private void handleGuideWindow() {
        guideWindow = new Window("Guide for Keys", AtomicBomber.singleton.skin);
        guideWindow.setResizable(false);
        guideWindow.setSize(600, 800);
        guideWindow.setPosition(AtomicBomber.WIDTH / 2 - guideWindow.getWidth() / 2, AtomicBomber.HEIGHT / 2 - guideWindow.getHeight() / 2); // Center the window

        // Create a label for each key guide
        for (KeyGuide keyGuide : KeyGuide.values()) {
            // Create a button for the key guide label
            TextButton keyGuideButton = new TextButton(keyGuide.getKey(), AtomicBomber.singleton.skin);
            keyGuideButton.setColor(Color.MAROON);
            keyGuideButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Create a dialog that displays the explanation of the key guide
                    Dialog keyGuideDialog = new Dialog("Key Guide", AtomicBomber.singleton.skin);
                    keyGuideDialog.text(keyGuide.getExplanation());
                    keyGuideDialog.button("Close");
                    keyGuideDialog.show(stage);
                }
            });
            guideWindow.add(keyGuideButton);
            guideWindow.row();
        }

        // Create a close button
        TextButton closeButton = new TextButton("Close", AtomicBomber.singleton.skin);
        closeButton.setColor(Color.CYAN);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guideWindow.clear();
                guideWindow.remove();
                pauseWindow.setVisible(true);
            }
        });

        // Add the close button to the window
        guideWindow.add(closeButton);

        // Add the guide window to the stage
        stage.addActor(guideWindow);
    }
    private void showWaveCompletion() {
        // Pause the game
        isPaused = true;

        // Create the wave completion window
        Window waveCompletionWindow = new Window("Wave Completed", AtomicBomber.singleton.skin);
        waveCompletionWindow.setResizable(false);
        waveCompletionWindow.setSize(600, 600);
        waveCompletionWindow.setPosition(AtomicBomber.WIDTH / 2 - waveCompletionWindow.getWidth() / 2, AtomicBomber.HEIGHT / 2 - waveCompletionWindow.getHeight() / 2); // Center the window

        // Create a label for the wave number and accuracy
        Label waveNumberLabel = new Label("Wave: " + (Game.getPlayingGame().getWaveNumber() - 1), AtomicBomber.singleton.skin);
        Label accuracyLabel = new Label("Accuracy: " + String.format("%.2f", Game.getPlayingGame().getAccuracy() * 100) + "%", AtomicBomber.singleton.skin);

        // Add the labels to the window
        waveCompletionWindow.add(waveNumberLabel);
        waveCompletionWindow.row();
        waveCompletionWindow.add(accuracyLabel);
        waveCompletionWindow.row();

        // Create a close button
        TextButton closeButton = new TextButton("Close", AtomicBomber.singleton.skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Game.getPlayingGame() == null) {
                    return;
                }
                if(Game.getPlayingGame().getWaveNumber() == 4) {
                    GameData.saveGameData(Game.getPlayingGame());
                    User.getLoggedInUser().updateInfo();
                    Game.setPlayingGame(null);
                    dispose();
                    ScreenManager.getInstance().removeScreen("MainGameScreen");
                    ScreenManager.getInstance().setScreen("MainMenuScreen");
                    return;
                }
                // Hide the wave completion window
                Game.getPlayingGame().newWaveDone();
                waveCompletionWindow.remove();

                // Resume the game
                isPaused = false;
            }
        });

        // Add the close button to the window
        waveCompletionWindow.add(closeButton);

        // Add the wave completion window to the stage
        stage.addActor(waveCompletionWindow);

        // Hide the window after 4 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(Game.getPlayingGame() == null) {
                    return;
                }
                if(Game.getPlayingGame().getWaveNumber() == 4) {
                    GameData.saveGameData(Game.getPlayingGame());
                    User.getLoggedInUser().updateInfo();
                    Game.setPlayingGame(null);
                    dispose();
                    ScreenManager.getInstance().removeScreen("MainGameScreen");
                    ScreenManager.getInstance().setScreen("MainMenuScreen");
                    return;
                }
                waveCompletionWindow.remove();
                Game.getPlayingGame().newWaveDone();
                // Resume the game
                isPaused = false;
            }
        }, 4);
    }
}
