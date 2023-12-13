package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class ApplicationStart {

    public static void main(String[] arg) {

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Tamagotchi");
        //config.setWindowedMode(720, 480);
        config.setWindowedMode(900, 900);

        GameStart myGdxGame = new GameStart();

        new Lwjgl3Application(myGdxGame, config);
    }
}
