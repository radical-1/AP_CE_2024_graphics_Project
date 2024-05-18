package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.LeaderBoardController;
import com.game.atomicbomber.controller.ScreenManager;
import com.game.atomicbomber.model.User;

import java.util.ArrayList;

public class LeaderBoardScreen implements Screen {
    private Sprite background;
    private TextButton backButton;
    private SelectBox<String> sortSelectBox;


    private ArrayList<User> allUsers;
    private Stage stage;
    private Table leaderboardTable;

    public LeaderBoardScreen(AtomicBomber game) {
        allUsers = LeaderBoardController.sortUsersByKills(User.getAllUsers());
        Texture backgroundTexture = new Texture("leaderboard_background.png");
        background = new Sprite(backgroundTexture);
        background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT + 40);

        sortSelectBox = new SelectBox<>(game.skin);
        sortSelectBox.setItems("Sort by Kills", "Sort by Accuracy", "Sort by Difficulty");
        sortSelectBox.setSize(300, 100);
        sortSelectBox.setPosition(AtomicBomber.WIDTH - sortSelectBox.getWidth(), 0);

        backButton = new TextButton("Back", game.skin);
        backButton.setSize(200, 60);
        backButton.setPosition(0, 0);

        // Initialize the leaderboard table
        leaderboardTable = new Table();
        leaderboardTable.top();
        leaderboardTable.setFillParent(true);
        leaderboardTable.setPosition(0, -100);


        updateLeaderBoard();
        // Create a scroll pane for the table
        ScrollPane scrollPane = new ScrollPane(leaderboardTable, game.skin);
        scrollPane.setFillParent(true);
        scrollPane.setScrollingDisabled(true, false);

        // Set the position of the leaderboard
        scrollPane.setPosition(100, 500);  // replace newX and newY with the desired coordinates

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(sortSelectBox);
        stage.addActor(backButton);

        // Add the scroll pane to the stage
        stage.addActor(scrollPane);
        stage.addActor(leaderboardTable);

    }

    @Override
    public void show() {
        sortSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = sortSelectBox.getSelected();
                switch (selected) {
                    case "Sort by Kills" ->
                            allUsers = LeaderBoardController.sortUsersByKills(User.getAllUsers());
                    case "Sort by Accuracy" ->
                            allUsers = LeaderBoardController.sortByAccuracy(User.getAllUsers());
                    case "Sort by Difficulty" ->
                            allUsers = LeaderBoardController.sortUserByDifficulty(User.getAllUsers());
                }
                updateLeaderBoard();
            }
        });
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                ScreenManager.getInstance().removeScreen("LeaderBoardScreen");
                ScreenManager.getInstance().setScreen("MainMenuScreen");
            }
        });
    }

    public void updateLeaderBoard() {
        leaderboardTable.clear();
        // Create a new font
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);  // Set the desired scale

        // Create a new label style with the font
        Label.LabelStyle style = new Label.LabelStyle(font, Color.BLACK);
        Label.LabelStyle style1 = new Label.LabelStyle(font, Color.GOLD);
        Label.LabelStyle style2 = new Label.LabelStyle(font, Color.GRAY);
        Label.LabelStyle style3 = new Label.LabelStyle(font, Color.SALMON);

        // Add a header row to the table
        leaderboardTable.add(new Label("Rank", style)).expandX();
        leaderboardTable.add(new Label("Username", style)).expandX();
        leaderboardTable.add(new Label("Kills", style)).expandX();
        leaderboardTable.add(new Label("difficulty", style)).expandX();
        leaderboardTable.add(new Label("Accuracy", style)).expandX();
        leaderboardTable.add(new Label("Last Wave", style)).expandX();
        leaderboardTable.row();

        // Add a row to the table for each user
        for (int i = 0; i < Math.min(10, allUsers.size()); i++) {
            User user = allUsers.get(i);
            if (i == 0) {
                leaderboardTable.add(new Label(String.valueOf(i + 1), style1));
                leaderboardTable.add(new Label(user.getUsername(), style1));
                leaderboardTable.add(new Label(String.valueOf(user.getKills()), style1));
                leaderboardTable.add(new Label(String.valueOf(user.getDifficulty()), style1));
                leaderboardTable.add(new Label(user.getAccuracy() + "%", style1));
                leaderboardTable.add(new Label(String.valueOf(user.getLastWave()), style1));
                leaderboardTable.row();
            } else if (i == 1) {
                leaderboardTable.add(new Label(String.valueOf(i + 1), style2));
                leaderboardTable.add(new Label(user.getUsername(), style2));
                leaderboardTable.add(new Label(String.valueOf(user.getKills()), style2));
                leaderboardTable.add(new Label(String.valueOf(user.getDifficulty()), style2));
                leaderboardTable.add(new Label(user.getAccuracy() + "%", style2));
                leaderboardTable.add(new Label(String.valueOf(user.getLastWave()), style2));
                leaderboardTable.row();
            } else if (i == 2) {
            leaderboardTable.add(new Label(String.valueOf(i + 1), style3));
            leaderboardTable.add(new Label(user.getUsername(), style3));
            leaderboardTable.add(new Label(String.valueOf(user.getKills()), style3));
                leaderboardTable.add(new Label(String.valueOf(user.getDifficulty()), style3));
            leaderboardTable.add(new Label(user.getAccuracy() + "%", style3));
            leaderboardTable.add(new Label(String.valueOf(user.getLastWave()), style3));
            leaderboardTable.row();
            } else {
                leaderboardTable.add(new Label(String.valueOf(i + 1), style));
                leaderboardTable.add(new Label(user.getUsername(), style));
                leaderboardTable.add(new Label(String.valueOf(user.getKills()), style));
                leaderboardTable.add(new Label(String.valueOf(user.getDifficulty()), style));
                leaderboardTable.add(new Label(user.getAccuracy() + "%", style));
                leaderboardTable.add(new Label(String.valueOf(user.getLastWave()), style));
                leaderboardTable.row();
            }
        }
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
