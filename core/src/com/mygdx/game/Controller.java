package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.LifecycleListener;
import com.mygdx.game.Personnage.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            controller.vieTamagotchi();

            controller.updateAffichage();

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
            System.out.println("Save à faire !");
        } else {
            System.out.println("Pas de save");
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

            setAmountProgressBar("life", animal.getLife());
            setAmountProgressBar("food", animal.getFood());
            setAmountProgressBar("sleep", animal.getSleep());
            setAmountProgressBar("wash", animal.getHygiene());
            setAmountProgressBar("happy", animal.getHappiness());

            setAmountLabel("money", animal.getWallet());
            setAmountLabel("apple", animal.getNumberApple());
            setAmountLabel("goldenApple", animal.getNumberGoldenApple());

        } else {
            robot = new Robot(difficulty);
            view = new View(this, robot);

            setAmountProgressBar("battery", robot.getBattery());
            setAmountProgressBar("tank", robot.getTank());
            setAmountProgressBar("durability", robot.getDurability());
            setAmountProgressBar("maintenance", robot.getMaintenance());
            setAmountProgressBar("happy", robot.getHappiness());

            setAmountLabel("money", robot.getWallet());
            setAmountLabel("apple", robot.getNumberOil());
            setAmountLabel("goldenApple", robot.getNumberExtraOil());
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

    public void vieTamagotchi() {
        if (animal != null) {
            animal.setFood(animal.getFood() - 10);
            animal.setHygiene(animal.getHygiene() - 5);
            animal.setSleep(animal.getSleep() - 2);
            animal.setHappiness(animal.getHappiness() - 3);

            if (animal.getFood() == 0 || animal.getHappiness() == 0 || animal.getHygiene() == 0) {
                animal.setHappiness(animal.getHappiness() - 3);
                animal.setLife(animal.getLife() - 10);
            }
            if (animal.getSleep() == 0) {
                animal.setHappiness(animal.getHappiness() - 10);
            }

            if (animal.getFood() >= 600) {
                animal.setLife(animal.getLife() + 10);
            }

        } else {
            robot.setTank(robot.getTank() - 10);
            robot.setMaintenance(robot.getMaintenance() - 5);
            robot.setDurability(robot.getDurability() - 2);
            robot.setHappiness(robot.getHappiness() - 3);

            if (robot.getTank() == 0 || robot.getHappiness() == 0 || robot.getMaintenance() == 0) {
                robot.setHappiness(robot.getHappiness() - 3);
                robot.setBattery(robot.getBattery() - 10);
            }
            if (robot.getDurability() == 0) {
                robot.setHappiness(robot.getHappiness() - 10);
            }

            if (robot.getTank() >= 600) {
                robot.setBattery(robot.getBattery() + 10);
            }
        }

    }

    public void updateAffichage() {
        if (animal != null) {
            setAmountProgressBar("life", animal.getLife());
            setAmountProgressBar("food", animal.getFood());
            setAmountProgressBar("wash", animal.getHygiene());
            setAmountProgressBar("sleep", animal.getSleep());
            setAmountProgressBar("happy", animal.getHappiness());

            setAmountLabel("money", animal.getWallet());
            setAmountLabel("apple", animal.getNumberApple());
            setAmountLabel("goldenApple", animal.getNumberGoldenApple());
        } else {
            System.out.println("a faire affichage robot");
            throw new NotImplementedException();
        }
    }

    public void sleep() throws InterruptedException {
        if (animal != null) {
            animal.sleep();
        } else {
            System.out.println("A faire robot sleep");
            throw new NotImplementedException();
        }
    }

    public void work() throws InterruptedException {
        if (animal != null) {
            animal.work();
        } else {
            System.out.println("A faire robot work");
            throw new NotImplementedException();
        }
    }

    public void wash() throws InterruptedException {
        if (animal != null) {
            animal.wash();
        } else {
            System.out.println("A faire robot wash");
            throw new NotImplementedException();
        }
    }

    public void eat() {
        if (animal != null) {
            System.out.println("A faire animal eat");
            throw new NotImplementedException();
        } else {
            System.out.println("A faire robot eat");
            throw new NotImplementedException();
        }
    }

    public void buy() {
        if (animal != null) {
            System.out.println("A faire animal buy");
            throw new NotImplementedException();
        } else {
            System.out.println("A faire robot buy");
            throw new NotImplementedException();
        }
    }

    public void play() throws InterruptedException {
        if (animal != null) {
            animal.play();
        } else {
            System.out.println("A faire robot eat");
            throw new NotImplementedException();
        }
    }

    public void stopGame() {
        flagStop.set(true);
    }
}
