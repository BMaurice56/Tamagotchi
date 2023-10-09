package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.LifecycleListener;
import com.mygdx.game.Personnage.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class Jeu implements Runnable {

    private final AtomicBoolean flagStop;

    private final Animal animal;

    private Robot robot;

    private final Controller controller;


    public Jeu(AtomicBoolean flagStop, Animal animal, Controller controller) {
        this.flagStop = flagStop;
        this.animal = animal;
        this.controller = controller;
    }

    public void run() {
        while (!flagStop.get()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            controller.setAmountProgressBar("life", animal.getLife());
            controller.setAmountProgressBar("food", animal.getFood());
            controller.setAmountProgressBar("wash", animal.getHygiene());
            controller.setAmountProgressBar("sleep", animal.getSleep());
            controller.setAmountProgressBar("happy", animal.getHappiness());

            controller.setAmountLabel("money", animal.getWallet());
            controller.setAmountLabel("apple", animal.getNumberApple());
            controller.setAmountLabel("goldenApple", animal.getNumberGoldenApple());

            animal.setLife(animal.getLife() - 10);
            animal.setFood(animal.getFood() - 10);
            animal.setHygiene(animal.getHygiene() - 10);
            animal.setSleep(animal.getSleep() - 10);
            animal.setHappiness(animal.getHappiness() - 10);

            animal.setWallet(animal.getWallet() + 1);
            animal.addBasket(new Apple());
            animal.addBasket(new Apple());
            animal.addBasket(new GoldenApple());


        }
    }
}

/**
 * Classe qui sert de controller pour le jeu
 */
public class Controller {

    private final AtomicBoolean flagStop = new AtomicBoolean();

    // Vue du jeu
    private final View view;

    // Modèle du jeu
    private Modele modele;

    private Animal animal;

    private Robot robot;

    private final Thread jeu;


    /**
     * Constructeur
     *
     * @param tamagotchiWished Tamagotchi sélectionné
     * @param nomTamagotchi    Nom du tamagotchi
     * @param difficulty       Niveau de difficulté
     * @param save             Lancement d'une sauvegarde ou non
     */
    public Controller(int tamagotchiWished, String nomTamagotchi, int difficulty, Object save) throws InterruptedException {
        if (save != null) {
            System.out.println("toto");
        } else {
            System.out.println("aah");
        }

        if (1 <= tamagotchiWished && tamagotchiWished <= 3) {
            switch (tamagotchiWished) {
                case (1):
                    animal = new Chat(difficulty);
                    break;

                case (2):
                    animal = new Chien(difficulty);
                    break;

                case (3):
                    animal = new Dinosaure(difficulty);
                    break;
            }

            view = new View(this, animal);
        } else {
            robot = new Robot(difficulty);
            view = new View(this, robot);
        }


        ((Game) Gdx.app.getApplicationListener()).setScreen(view);

        jeu = new Thread(new Jeu(flagStop, animal, this));

        jeu.start();

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

    }


    public void setAmountLabel(String label, int amount) {
        view.setAmountLabel(label, amount);
    }

    public void setAmountProgressBar(String progressBar, float amount) {
        view.setAmountProgressBar(progressBar, amount);
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
        flagStop.set(true);
    }
}
