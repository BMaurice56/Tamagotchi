package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.LifecycleListener;

/**
 * Classe qui sert de controller pour le jeu
 */
public class Controller {

    // Vue du jeu
    private final View view;

    // Modèle du jeu
    private final Modele modele;


    /**
     * Constructeur
     *
     * @param tamagotchiWished Tamagotchi sélectionné
     * @param nomTamagotchi    Nom du tamagotchi
     * @param difficulty       Niveau de difficulté
     * @param save             Lancement d'une sauvegarde ou non
     */
    public Controller(int tamagotchiWished, String nomTamagotchi, int difficulty, boolean save, int numberSave) {
        modele = new Modele(tamagotchiWished, nomTamagotchi, difficulty, save, numberSave, this);

        view = new View(this, modele.getTamagotchi());

        // Définit l'écran de jeu
        ((Game) Gdx.app.getApplicationListener()).setScreen(view);

        // Permet de savoir si l'utilisateur quitte le jeu pour stopper la partie
        Gdx.app.addLifecycleListener(new LifecycleListener() {
            @Override
            public void pause() {
            }

            @Override
            public void resume() {
            }

            @Override
            public void dispose() {
                stopGame();
            }
        });

        startGame();
    }


    /*
     * Définit la valeur du label voulu
     *
     * @param label  Label
     * @param amount Montant
     */
    public void setAmountLabel(String label, int amount) {
        view.setAmountLabel(label, amount);
    }

    /**
     * Définit la valeur de la barre de progression voulue
     *
     * @param progressBar Barre de progression
     * @param amount      Montant
     */
    public void setAmountProgressBar(String progressBar, float amount) {
        view.setAmountProgressBar(progressBar, amount);
    }

    /**
     * Modifie la visibilité des éléments de l'affichage selon les actions effectuées
     *
     * @param visibility true ou false selon où on en est dans l'action
     */
    public void actionEnCourTamagotchi(boolean visibility, String action) {
        view.actionTamagotchiVisibility(visibility, action);
    }

    /**
     * Fait dormir le tamagotchi
     */
    public void sleep() {
        modele.sleep();
    }

    /**
     * Fait travailler le tamagotchi
     */
    public void work() {
        modele.work();
    }

    /**
     * Fait se laver le tamagotchi
     */
    public void wash() {
        modele.wash();
    }

    /**
     * Fait manger le tamagotchi
     */
    public void eat(String food) {
        if (modele.getTamagotchi().getNumberOfFood(food) != 0) {
            modele.eat(food);
        }
    }

    /**
     * Permet d'acheter de la nourriture
     */
    public void buy(String food) {
        modele.buy(food);
    }

    /**
     * Fait jouer le tamagotchi
     */
    public void play() {
        modele.play();
    }

    /**
     * Arrête le jeu
     */
    public void stopGame() {
        modele.stopGame();
    }

    /**
     * Démarre le jeu
     */
    public void startGame() {
        modele.startGame();
    }

    public void save() {
        modele.save();
    }
}
