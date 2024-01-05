package com.mygdx.game;

import com.badlogic.gdx.Game;

/**
 * Classe qui démarre le jeu
 */
public class GameStart extends Game {

    @Override
    public void create() {
        setScreen(new ScreenMenu(1, null));
        //setScreen(new SelectTamagotchi());
    }
}