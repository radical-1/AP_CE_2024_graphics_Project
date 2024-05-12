package com.game.atomicbomber.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.game.atomicbomber.AtomicBomber;
import com.game.atomicbomber.controller.ScreenManager;
import com.game.atomicbomber.model.User;
import com.game.atomicbomber.view.ValidInputs;

import com.game.atomicbomber.controller.RegisterLoginController;


public class RegisterScreen implements Screen {


    private Sprite background;
    private Stage stage;
    private Skin skin;
    private Dialog errorDialog;
    private Label passwordStrengthLabel;// Label to display password strength
    private static final Table table = new Table();
    AtomicBomber game;

    private TextButton loginButton;
    private TextButton registerButton;
    private TextButton playAsGuest;

    public RegisterScreen(AtomicBomber game) {

        this.background = new Sprite();
        Texture backgroundTexture = new Texture("RegisterBackground.jfif");
        this.background = new Sprite(backgroundTexture);
        this.background.setSize(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);

        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        table.setFillParent(true);


        registerButton = new TextButton("Register", skin);
        loginButton = new TextButton("Login", skin);
        playAsGuest = new TextButton("Play as Guest", skin);

        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Enter your username");
        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("Enter your password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');


        passwordStrengthLabel = new Label("", skin); // Initialize the password strength label
        passwordStrengthLabel.setColor(Color.BLUE); // Set the color of the password strength message to blue

        passwordField.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                updatePasswordStrength(passwordField.getText());
                return super.keyTyped(event, character);
            }
        });

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String usernameValidation = RegisterLoginController.validateUsername(username);
                String passwordStrength = RegisterLoginController.validatePasswordStrength(password);
                // Check if the fields are not empty
                if (username.isEmpty() || password.isEmpty()) {
                    showError("Username and password must not be empty.");
                    return;
                }
                if (User.getUserByUsername(username) != null) {
                    showError("Username already exists.");
                    return;
                }
                // Check if the username and password meet certain length requirements
                if (!"Username is valid.".equals(usernameValidation)) {
                    showError(usernameValidation);
                    return;

                }

                // Check the strength of the password
                if (!"Password is strong.".equals(passwordStrength)) {
                    showError(passwordStrength);
                    return;
                }
                handleSuccessfulRegistration(username, password);
                // If all checks pass, handle the registration

            }
        });
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    showError("Username and password must not be empty.");
                    return;
                }
                User user = User.getUserByUsername(usernameField.getText());
                if (user == null) {
                    showError("User does not exist.");
                    return;
                }
                if (!user.isPasswordCorrect(passwordField.getText())) {
                    showError("Incorrect password.");
                    return;
                }

                handleSuccessfulLogin(usernameField.getText(), passwordField.getText());
            }
        });
        playAsGuest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                User.setLoggedInUser(new User("_Geust_", ""));
                ScreenManager.getInstance().removeScreen("RegisterScreen");
                ScreenManager.getInstance().setScreen("MainMenuScreen");
            }
        });

        table.add(new Label("Password State : ", skin));
        table.add(passwordStrengthLabel).colspan(2).row();
        table.add(new Label("Username: ", skin));
        table.add(usernameField).width(500).height(100).row();
        table.add(new Label("Password: ", skin));
        table.add(passwordField).width(500).height(100).row();


        // Set the position of the buttons
        float registerButtonX = (float) Gdx.graphics.getWidth() / 2 - registerButton.getWidth() / 2;
        float registerButtonY = (float) Gdx.graphics.getHeight() / 2 - registerButton.getHeight() / 2 - 200;
        float loginButtonX = (float) Gdx.graphics.getWidth() / 2 - loginButton.getWidth() / 2 + 270;
        float loginButtonY = (float) Gdx.graphics.getHeight() / 2 - loginButton.getHeight() / 2 - 200;
        float playAsGuestX = (float) Gdx.graphics.getWidth() / 2 - playAsGuest.getWidth() / 2 + 100;
        float playAsGuestY = (float) Gdx.graphics.getHeight() / 2 - playAsGuest.getHeight() / 2 - 290;
        // Set the size of the buttons
        registerButton.setSize(250, 100);
        loginButton.setSize(150, 100);
        playAsGuest.setSize(350, 80);
        // Add the buttons to the stage
        registerButton.setPosition(registerButtonX, registerButtonY);
        loginButton.setPosition(loginButtonX, loginButtonY);
        playAsGuest.setPosition(playAsGuestX, playAsGuestY);
        stage.addActor(table);
        stage.addActor(registerButton);
        stage.addActor(loginButton);
        stage.addActor(playAsGuest);

    }

    @Override
    public void show() {

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
        table.clear();
    }

    private void showError(String message) {
        errorDialog = new Dialog("Error", skin);
        errorDialog.setColor(Color.RED);
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

    private void updatePasswordStrength(String password) {
        passwordStrengthLabel.setText("");
        if (password.length() < 5 || !ValidInputs.NUMBER.isMatch(password)) {
            passwordStrengthLabel.setColor(Color.RED);
            passwordStrengthLabel.setText("Weak");
        } else if (password.length() < 8 || !ValidInputs.SPECIAL_CHAR.isMatch(password)) {
            passwordStrengthLabel.setColor(Color.ORANGE);
            passwordStrengthLabel.setText("Not Bad");
        } else if (!ValidInputs.UPPERCASE.isMatch(password) || !ValidInputs.LOWERCASE.isMatch(password)) {
            passwordStrengthLabel.setColor(Color.YELLOW);
            passwordStrengthLabel.setText("Medium");
        } else {
            passwordStrengthLabel.setColor(Color.GREEN);
            passwordStrengthLabel.setText("Strong");
        }

    }

    private void handleSuccessfulRegistration(String username, String password) {

        RegisterLoginController.register(username, password);
        showError("Registration successful!");

    }

    private void handleSuccessfulLogin(String username, String password) {
        // Read the existing users from the file
        RegisterLoginController.login(username, password);
        showError("Login successful!");

        dispose();
        ScreenManager.getInstance().removeScreen("RegisterScreen");
        ScreenManager.getInstance().setScreen("MainMenuScreen");

    }

}