package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe qui sert de controller pour le jeu
 */
public class Controller {

    private AtomicBoolean flag = new AtomicBoolean();

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

        viewAnimal = new ViewAnimal(this);

        ((Game) Gdx.app.getApplicationListener()).setScreen(viewAnimal);

        party(flag);

    }


    public void party(AtomicBoolean flag) {
        while (!flag.get()) {
            System.out.println("toto");
        }
    }

    public void setAmountLabel(String label, int amount) {
        viewAnimal.setAmountLabel(label, amount);
    }

    public void setAmountProgressBar(String progressBar, float amount) {
        viewAnimal.setAmountProgressBar(progressBar, amount);
    }

    public void sleep() {
    }

    public void work() {
    }

    public void wash() {
    }

    public void eat() {
    }

    public void buy() {
    }

    public void play() {
    }

    public void stopGame() {
        flag.set(true);
    }
}
