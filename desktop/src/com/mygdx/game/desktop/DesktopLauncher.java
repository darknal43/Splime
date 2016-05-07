package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import driver.GameLoopFactory;
import tools.Constants;

public class DesktopLauncher implements Constants {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int)Constants.WIDTH;
		config.height = (int)Constants.HEIGHT;
		new LwjglApplication(GameLoopFactory.getMainGameLoop(), config);
	}
}
