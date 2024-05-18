package com.game.atomicbomber;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;
import games.spooky.gdx.nativefilechooser.desktop.DesktopFileChooser;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setTitle("Atomic Bomber");
		config.setWindowedMode(AtomicBomber.WIDTH, AtomicBomber.HEIGHT);
		config.setResizable(false);
		AtomicBomber.fileChooser = new DesktopFileChooser();
		AtomicBomber.fileChooserConfiguration = new NativeFileChooserConfiguration();
		new Lwjgl3Application(new AtomicBomber(), config);
	}
}
