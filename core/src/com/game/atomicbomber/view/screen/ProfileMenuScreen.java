package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.ProfileMenuController;
import com.game.atomicbomber.controller.ScreenManager;
import com.game.atomicbomber.model.User;

import java.awt.*;

public class ProfileMenuScreen extends Menu implements Screen {

    private TextField usernameField;
    private TextField passwordField;
    private TextButton changeUsernameButton;
    private TextButton changePasswordButton;

    private TextButton removeAccountButton;
    private TextButton logoutButton;
    private TextButton goToAvatarMenuButton;

    private Texture background;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture currentAvatar;
    private User user;
    private Stage stage;
    private TextButton backButton;
    private Skin skin;
    private Dialog errorDialog;

    AtomicBomber game;

    public ProfileMenuScreen(AtomicBomber game) {
        background = new Texture("avatar_menu_background.jpg");
        this.game = game;
        batch = game.getBatch();
        font = new BitmapFont();
        user = User.getLoggedInUser();
        currentAvatar = user.getAvatar();

        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        show();
    }

    @Override
    public void show() {
        // Initialize the text fields and buttons
        usernameField = new TextField("", skin);
        usernameField.setMessageText("Enter your username");
        passwordField = new TextField("", skin);
        passwordField.setMessageText("Enter your password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        changeUsernameButton = new TextButton("Change\nUsername", skin);
        changePasswordButton = new TextButton("Change\nPassword", skin);

        removeAccountButton = new TextButton("Remove Account", skin);
        logoutButton = new TextButton("Logout", skin);
        goToAvatarMenuButton = new TextButton("Go to Avatar Menu", skin);

        // Set the size of the buttons
        removeAccountButton.setSize(400, 100);
        logoutButton.setSize(400, 100);
        goToAvatarMenuButton.setSize(500, 100);
        usernameField.setSize(400, 100);
        passwordField.setSize(400, 100);
        changeUsernameButton.setSize(300, 100);
        changePasswordButton.setSize(300, 100);

        // Set the position of the buttons
        removeAccountButton.setPosition(100, 500);
        usernameField.setPosition(10, 400);
        passwordField.setPosition(10, 300);
        changeUsernameButton.setPosition(400, 400);
        changePasswordButton.setPosition(400, 300);
        goToAvatarMenuButton.setPosition(100, 200);
        logoutButton.setPosition(100, 50);



        removeAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removeAccountHandler();
            }
        });


        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User.setLoggedInUser(null);
                ScreenManager.getInstance().setScreen("RegisterScreen");
            }
        });

        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeUsernameHandler();
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changePasswordHandler();
            }
        });
        goToAvatarMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ScreenManager.getInstance().removeScreen("ProfileMenuScreen");
                ScreenManager.getInstance().setScreen("AvatarMenuScreen");
            }
        });

        // Add the buttons to the stage
        stage.addActor(removeAccountButton);
        stage.addActor(usernameField);
        stage.addActor(passwordField);
        stage.addActor(changeUsernameButton);
        stage.addActor(changePasswordButton);
        stage.addActor(logoutButton);
        stage.addActor(goToAvatarMenuButton);
    }

    private void removeAccountHandler() {
        //TODO : debug needed
        Dialog dialog = new Dialog("Confirmation", skin);
        dialog.text("Are you sure you want to remove your account?");
        // Add "Yes" button
        dialog.button("Yes", true).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                User.removeUser(user);
                User.setLoggedInUser(null);
                ScreenManager.getInstance().setScreen("RegisterScreen");
            }
        });

        // Add "No" button
        dialog.button("No", false).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                dialog.hide();
            }
        });

        dialog.show(stage);
    }

    public void changeUsernameHandler() {
        String username = usernameField.getText();
        String message = ProfileMenuController.changeUsername(username);
        showError(message);
    }
    public void changePasswordHandler() {
        String password = passwordField.getText();
        String message = ProfileMenuController.changePassword(password);
        showError(message);
    }
    private void showError(String message) {
        errorDialog = new Dialog("Error", skin);
        errorDialog.setColor(Color.YELLOW);
        errorDialog.button("OK");
        errorDialog.text(message);
        errorDialog.show(stage);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                errorDialog.hide();
            }
        }, 4);
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
        background.dispose();
    }

}
