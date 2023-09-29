package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;

/**
 * Classe qui sert de controller pour le jeu
 */
public class Controller {
    // Vue du jeu
    private ViewAnimal viewAnimal;

    // Modèle du jeu
    private Modele modele;

    /**
     * Constructeur
     *
     * @param tamagotchi    Tamagotchi sélectionné
     * @param nomTamagotchi Nom du tamagotchi
     * @param difficulty    Niveau de difficulté
     * @param save          Lancement d'une sauvegarde ou non
     */
    public Controller(int tamagotchi, String nomTamagotchi, int difficulty, Object save) {
        if (save != null) {

        } else {

        }
        System.out.println(tamagotchi + " " + nomTamagotchi + " " + difficulty);

        viewAnimal = new ViewAnimal();

        ((Game) Gdx.app.getApplicationListener()).setScreen(viewAnimal);

    }
}
