package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Modele {

    private View view;

    private Controller controller;

    public Modele(int tamagotchi, String nomTamagotchi, int difficulty, Object save) {
        if (save != null) {

        } else {

        }
        System.out.println(tamagotchi + " " + nomTamagotchi + " " + difficulty);

        view = new View();

        ((Game) Gdx.app.getApplicationListener()).setScreen(view);
    }


}
