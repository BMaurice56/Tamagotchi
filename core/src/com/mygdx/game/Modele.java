package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Modele {

    private ViewAnimal viewAnimal;

    private Controller controller;

    public Modele(int tamagotchi, String nomTamagotchi, int difficulty, Object save) {
        if (save != null) {

        } else {

        }
        System.out.println(tamagotchi + " " + nomTamagotchi + " " + difficulty);

        viewAnimal = new ViewAnimal();

        ((Game) Gdx.app.getApplicationListener()).setScreen(viewAnimal);

        //jeu();
    }

    public void jeu() {
        boolean exit = false;



        while (!exit) {
            System.out.println(Gdx.graphics.getDeltaTime());
        }
    }

}
