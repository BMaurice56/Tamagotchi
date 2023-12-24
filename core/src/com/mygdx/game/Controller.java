package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.mygdx.game.Personnage.Robot;
import com.mygdx.game.Personnage.Animal;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.LifecycleListener;
import com.mygdx.game.Personnage.Tamagotchi;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe qui sert de controller pour le jeu
 */
public class Controller {

    // Vue du jeu
    private final View view;

    // Modèle du jeu
    private final Modele modele;

    // Aléatoire
    private final Random random = new Random();

    /**
     * Constructeur
     *
     * @param tamagotchiWished Tamagotchi sélectionné
     * @param nomTamagotchi    Nom du tamagotchi
     * @param difficulty       Niveau de difficulté
     * @param save             Lancement d'une sauvegarde ou non
     * @param numberSave       Numéro de la sauvegarde
     * @param skin             Numéro de l'apparence
     */
    public Controller(int tamagotchiWished, String nomTamagotchi, int difficulty, boolean save, int numberSave, int skin) {
        AtomicBoolean flagPluie = new AtomicBoolean(false);

        modele = new Modele(tamagotchiWished, nomTamagotchi, difficulty, save, numberSave, this, skin, flagPluie);

        view = new View(this, modele.getTamagotchi(), flagPluie);

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
                Tamagotchi tamagotchi = modele.getTamagotchi();

                if (tamagotchi instanceof Animal) {
                    Animal animal = (Animal) tamagotchi;
                    stopGame(!(animal.getLife() <= 0));
                } else {
                    Robot robot = (Robot) tamagotchi;
                    stopGame(!(robot.getBattery() <= 0));
                }

            }
        });

        startGame();
    }

    /**
     * Modifie la visibilité des éléments de l'affichage selon les actions effectuées
     *
     * @param visibility True ou false selon où on en est dans l'action
     * @param action     Action à afficher
     */
    public void actionEnCourTamagotchi(boolean visibility, String action) {
        view.actionTamagotchiChangeVisibility(visibility, action);
    }

    /**
     * Renvoi le numéro de l'écran où se trouve le tamagotchi
     *
     * @return int numéro
     */
    public int getRoomTamagotchi() {
        return view.getNumberRoom();
    }

    /**
     * Fait dormir le tamagotchi
     */
    public void sleep() {
        int temps = 12 + random.nextInt(4);

        if (modele.getTamagotchi().getNumberTamagotchi() != 4) {
            modele.doAction(temps, "Dodo", "sleep", "");
        } else {
            modele.doAction(temps, "Maintenance", "sleep", "");
        }
    }

    /**
     * Fait travailler le tamagotchi
     */
    public void work() {
        modele.doAction(12, "Travaille", "work", "");
    }

    /**
     * Fait se laver / mettre à jour le tamagotchi
     */
    public void wash() {
        int temps = 10 + random.nextInt(4);

        if (modele.getTamagotchi().getNumberTamagotchi() != 4) {
            modele.doAction(temps, "Lavage", "wash", "");
        } else {
            modele.doAction(temps, "Mise a jour", "wash", "");
        }
    }

    /**
     * Fait manger le tamagotchi
     *
     * @param food String nourriture voulu
     */
    public void eat(String food) {
        Tamagotchi tamagotchi = modele.getTamagotchi();

        if (tamagotchi.getNumberOfFood(food) != 0) {
            if (tamagotchi.getNumberTamagotchi() != 4) {
                modele.doAction(5, "Alimentation", "eat", food);
            } else {
                modele.doAction(5, "Remplissage", "eat", food);
            }
        }
    }

    /**
     * Permet d'acheter de la nourriture
     *
     * @param food String nourriture voulu
     */
    public void buy(String food) {
        modele.buy(food);
    }

    /**
     * Fait jouer le tamagotchi
     */
    public void play() {
        int temps = 10 + random.nextInt(4);

        modele.doAction(temps, "Jeu", "play", "");
    }

    /**
     * Renvoie les données du tamagotchi pour la vue
     *
     * @param valeur String donnée voulu
     * @return float valeur
     */
    public float getDataTamagotchi(String valeur) {
        return modele.getValueTamagotchi(valeur);
    }

    /**
     * Renvoi le niveau de son
     *
     * @return float valeur
     */
    public float getLevelSound() {
        return modele.getSound();
    }

    /**
     * Définie et enregistre le niveau du son
     *
     * @param son float niveau
     */
    public void setLevelSound(float son) {
        modele.setSound(son);
    }

    /**
     * Affichage le message de la mort du tamagotchi
     *
     * @param file FileHandle gestionnaire du fichier
     */
    public void mortTamagotchi(FileHandle file) {
        deleteSave(file);
        view.messageMortTamagotchi();
    }

    /**
     * Arrête le jeu
     *
     * @param save True ou false pour activer ou non la sauvegarde
     */
    public void stopGame(boolean save) {
        modele.stopGame(save);
    }

    /**
     * Démarre le jeu
     */
    public void startGame() {
        modele.startGame();
    }


    /**
     * Supprime le fichier de sauvegarde passé en paramètre
     *
     * @param file FileHandle gestionnaire du fichier
     */
    public static void deleteSave(FileHandle file) {
        boolean suppression = file.delete();

        // Si la sauvegarde n'est pas supprimée dès le premier appelle de fonction, on retente
        while (!suppression) {
            suppression = file.delete();
        }
    }
}
