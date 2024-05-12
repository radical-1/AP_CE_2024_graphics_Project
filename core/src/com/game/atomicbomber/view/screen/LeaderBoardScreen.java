package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.LeaderBoardController;
import com.game.atomicbomber.controller.ScreenManager;
import com.game.atomicbomber.model.User;

import java.util.ArrayList;

public class LeaderBoardScreen implements Screen {
    private Sprite background;
    private TextButton backButton;
    private SelectBox<String> sortSelectBox;

    private AtomicBomber game;
    private ArrayList<User> allUsers;
    private Stage stage;
    private Table leaderboardTable;

    public LeaderBoardScreen(AtomicBomber game) {
        this.game = game;
        allUsers = LeaderBoardController.sortUsersByKills(User.getAllUsers());
        Texture backgroundTexture = new Texture("avatar_menu_background.jpg");
        background = new Sprite(backgroundTexture);
        background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT + 40);

        sortSelectBox = new SelectBox<>(game.skin);
        sortSelectBox.setItems("Sort by Kills", "Sort by Accuracy", "Sort by Difficulty");
        sortSelectBox.setPosition(0, 100);

        backButton = new TextButton("Back", game.skin);
        backButton.setSize(200, 60);
        backButton.setPosition(0, 0);

        // Initialize the leaderboard table
        leaderboardTable = new Table();
        leaderboardTable.top();
        leaderboardTable.setFillParent(true);

        updateLeaderBoard();
        // Create a scroll pane for the table
        ScrollPane scrollPane = new ScrollPane(leaderboardTable, game.skin);
        scrollPane.setFillParent(true);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(0, AtomicBomber.HEIGHT - 100);

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
                            allUsers = LeaderBoardController.sortUserByDifficulty(User.getAllUsers());
                    case "Sort by Difficulty" ->
                            allUsers = LeaderBoardController.sortByAccuracy(User.getAllUsers());
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
        // Add a header row to the table
        leaderboardTable.add(new Label("Rank", game.skin)).expandX();
        leaderboardTable.add(new Label("Username", game.skin)).expandX();
        leaderboardTable.add(new Label("Score", game.skin)).expandX();
        leaderboardTable.row();

        // Create a red font for the rank 1 user
        BitmapFont redFont = new BitmapFont();
        redFont.setColor(Color.RED);
        Label.LabelStyle redStyle = new Label.LabelStyle(redFont, Color.RED);
        BitmapFont greenFont = new BitmapFont();
        greenFont.setColor(Color.GREEN);
        Label.LabelStyle greenStyle = new Label.LabelStyle(greenFont, Color.GREEN);

        // Add a row to the table for each user
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            if (i < 3) { // Rank 1 to 3 users are red
                leaderboardTable.add(new Label(String.valueOf(i + 1), redStyle));
                leaderboardTable.add(new Label(user.getUsername(), redStyle));
                leaderboardTable.add(new Label(String.valueOf(user.getScore()), redStyle));
            } else { // Rank 4 and below users are green
                leaderboardTable.add(new Label(String.valueOf(i + 1), greenStyle));
                leaderboardTable.add(new Label(user.getUsername(), greenStyle));
                leaderboardTable.add(new Label(String.valueOf(user.getScore()), greenStyle));
            }
            leaderboardTable.row();
        }
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
