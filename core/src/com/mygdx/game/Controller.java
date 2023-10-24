package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.LifecycleListener;
import com.mygdx.game.Personnage.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class Jeu implements Runnable {

    private final AtomicBoolean flagStop, flagWait;

    private final Controller controller;


    public Jeu(AtomicBoolean flagStop, AtomicBoolean flagWait, Controller controller) {
        this.flagStop = flagStop;
        this.flagWait = flagWait;
        this.controller = controller;
    }

    public void run() {
        while (!flagStop.get()) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            controller.vieTamagotchi();

            if (flagWait.get()) {
                try {
                    controller.waiting();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            controller.updateAffichage();

        }
    }
}

/**
 * Classe qui sert de controller pour le jeu
 */
public class Controller {

    private final AtomicBoolean flagStop = new AtomicBoolean(), flagWait = new AtomicBoolean(true);

    // Vue du jeu
    private final View view;

    // Modèle du jeu
    private Modele modele;

    private Animal animal;

    private Robot robot;

    private final Thread jeu;

    private String attente;

    private final Random random = new Random();


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

        jeu = new Thread(new Jeu(flagStop, flagWait, this));

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


    /**
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
     * Modifie les valeurs du tamagotchi
     */
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

    /**
     * Met à jour l'affichage avec les bonnes valeurs du tamagotchi
     */
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

    /**
     * Calcul la valeur pour la barre de progression selon le temps d'attente
     *
     * @param tempsAttente Temps de base (ex : 10 secondes)
     * @param min          Temps actuel en milliseconde (time code depuis le 1 janvier 1970)
     * @param max          Temps final en millisecondes
     * @return float La valeur pour la barre de progression
     */
    public float getValueWaitingBar(int tempsAttente, double min, double max) {
        return (float) ((tempsAttente * 1000) - (max - min)) / tempsAttente;
    }

    public void waiting() throws InterruptedException {
        // S'il y a bien une chaine de caractère, alors une action doit être effectuée
        if (attente != null) {
            // Bloque le moteur de reappeler cette fonction
            flagWait.set(false);

            // Thread qui met à jour la barre de progression sans bloquer le jeu
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Cache les éléments inutiles de l'interface
                    view.changeVisibilityWaitingBar(false);

                    // Sépare les différentes valeurs
                    String[] values = attente.split(";");

                    // Temps d'attente
                    int time = Integer.parseInt(values[0]);

                    // Temps de fin en milliseconde
                    long temps = System.currentTimeMillis() + (time * 1_000L);

                    // Tant que l'on n'a pas attendu le temps nécessaire, on continue
                    while (temps > System.currentTimeMillis()) {
                        // Met la bonne valeur sur la barre de progression
                        view.setAmountProgressBar("waiting", getValueWaitingBar(time, System.currentTimeMillis(), temps));
                    }
                    // Reaffiche les éléments de l'interface
                    view.changeVisibilityWaitingBar(true);

                    // Fait l'action voulue pour mettre à jour les valeurs du tamagotchi
                    switch (values[2]) {
                        case "work":
                            animal.work();
                            break;

                        case "sleep":
                            animal.sleep();
                            break;

                        case "wash":
                            animal.wash();
                            break;

                        case "play":
                            animal.play();
                            break;

                        default:
                            throw new IllegalArgumentException("Action inconnu");

                    }

                    // Remet à null attente pour pouvoir effectuer la prochaine action
                    attente = null;

                    // Réautorise le moteur à appeler cette fonction
                    flagWait.set(true);
                }
            });

            // Démarre le thread
            t.start();
        }
    }

    public void sleep() {
        if (animal != null) {
            int temps = 15 + random.nextInt(4);
            attente = temps + ";Dodo;sleep";
        } else {
            System.out.println("A faire robot sleep");
        }
    }

    public void work() {
        if (animal != null) {
            attente = "12;Travaille;work";
        } else {
            System.out.println("A faire robot work");
        }
    }

    public void wash() {
        if (animal != null) {
            int temps = 12 + random.nextInt(6);
            attente = temps + ";Lavage;wash";
        } else {
            System.out.println("A faire robot wash");
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

    public void play() throws NotImplementedException {
        if (animal != null) {
            int temps = 10 + random.nextInt(6);
            attente = temps + ";Jeu;play";
        } else {
            System.out.println("A faire robot eat");
            throw new NotImplementedException();
        }
    }

    public void stopGame() {
        flagStop.set(true);
    }
}
