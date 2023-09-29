package com.mygdx.game;

import com.badlogic.gdx.Game;

public class GameStart extends Game {

    @Override
    public void create() {
        //setScreen(new ScreenMenu());
        //setScreen(new SelectTamagotchi());
        setScreen(new ViewAnimal());
    }
}