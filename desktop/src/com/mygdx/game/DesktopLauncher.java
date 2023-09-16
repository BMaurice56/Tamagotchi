package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {

    public static void main(String[] arg) {

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(30);
        config.setTitle("Tamagotchi");
        config.setWindowedMode(800, 800);

        Tamagotchi myGdxGame = new Tamagotchi();

        new Lwjgl3Application(myGdxGame, config);
    }
}