package com.game.atomicbomber;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.atomicbomber.controller.ScreenManager;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;

public class AtomicBomber extends Game {
	public static AtomicBomber singleton;
	public static NativeFileChooser fileChooser;
	public static NativeFileChooserConfiguration fileChooserConfiguration;
	public Skin skin;
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;

	private SpriteBatch batch;
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().setScreen("RegisterScreen");
		skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
		singleton = this;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
	public SpriteBatch getBatch() {
		return this.batch;
	}

}
