package com.mygdx.game;

import com.badlogic.gdx.Game;

/**
 * Classe qui démarre le jeu
 */
public class GameStart extends Game {

    private static int getVersion() {
        String version = System.getProperty("java.version");
        if (version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if (dot != -1) {
                version = version.substring(0, dot);
            }
        }
        return Integer.parseInt(version);
    }

    public void messageVersion(int version) {
        System.out.println("ATTENTION !!!");
        System.out.println("Ce jeu tourne avec une version java 17 au minimum");
        System.out.println("Toute version inférieur est incompatible");
        System.out.println("Votre version : " + version);
        System.out.println("Jeu stoppé");
        System.exit(1);
    }

    @Override
    public void create() {
        int version = getVersion();

        if (version < 17) {
            messageVersion(version);
        }

        setScreen(new ScreenMenu(true, null));
    }
}