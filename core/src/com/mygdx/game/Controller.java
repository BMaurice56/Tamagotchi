package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.mygdx.game.Personnage.*;
import com.badlogic.gdx.LifecycleListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class Jeu implements Runnable {

    // Drapeau qui régule le moteur
    private final AtomicBoolean flagStop, flagWait;

    // Controller de jeu
    private final Controller controller;

    /**
     * Constructeur
     *
     * @param flagStop   Arrête le moteur
     * @param flagWait   Fait attendre le moteur
     * @param controller controller de jeu
     */
    public Jeu(AtomicBoolean flagStop, AtomicBoolean flagWait, Controller controller) {
        this.flagStop = flagStop;
        this.flagWait = flagWait;
        this.controller = controller;
    }

    /**
     * Méthode appelée pour exécuter le thread
     */
    public void run() {
        // Tant que le drapeau n'est pas levé, on continue
        while (!flagStop.get()) {
            // fait attendre le moteur
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Fait vivre le tamagotchi
            controller.vieTamagotchi();

            // Si on peut faire une action, on l'effectue
            // Permet d'éviter de rappeler la fonction si on l'a déja appelé et qu'elle effectue une action
            if (flagWait.get()) {
                controller.waiting();
            }

            // Met à jour l'affichage
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
            animal.setFood(animal.getFood() - 5);
            animal.setHygiene(animal.getHygiene() - 4);
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

    /**
     * Méthode appelée par le moteur de jeu
     * Si attente != null, alors il y a une action à effectuer
     */
    public void waiting() {
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
                        case "eat":
                            animal.eat("Apple");
                            break;

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

    /**
     * Fait dormir le tamagotchi
     */
    public void sleep() {
        if (animal != null) {
            int temps = 15 + random.nextInt(4);
            attente = temps + ";Dodo;sleep";
        } else {
            System.out.println("A faire robot sleep");
        }
    }

    /**
     * Fait travailler le tamagotchi
     */
    public void work() {
        if (animal != null) {
            attente = "12;Travaille;work";
        } else {
            System.out.println("A faire robot work");
        }
    }

    /**
     * Fait se laver le tamagotchi
     */
    public void wash() {
        if (animal != null) {
            int temps = 12 + random.nextInt(6);
            attente = temps + ";Lavage;wash";
        } else {
            System.out.println("A faire robot wash");
        }
    }

    /**
     * Fait manger le tamagotchi
     */
    public void eat() {
        if (animal != null) {
            System.out.println("A faire animal eat");
            attente = "5;Alimentation;eat";
        } else {
            System.out.println("A faire robot eat");
        }
    }

    /**
     * Permet d'acheter de la nourriture
     */
    public void buy() {
        if (animal != null) {
            System.out.println("A faire animal buy");
            int amount = animal.getWallet();
            if (amount >= Apple.price) {
                animal.setWallet(amount - Apple.price);
                animal.addBasket(new Apple());
            }

        } else {
            System.out.println("A faire robot buy");
        }
    }

    /**
     * Fait jouer le tamagotchi
     */
    public void play() {
        if (animal != null) {
            int temps = 10 + random.nextInt(6);
            attente = temps + ";Jeu;play";
        } else {
            System.out.println("A faire robot eat");
        }
    }

    /**
     * Arrête le jeu
     */
    public void stopGame() {
        flagStop.set(true);
    }

    /**
     * Démarre le jeu
     */
    public void startGame() {
        flagStop.set(false);
        attente = null;

        // Moteur de jeu
        Thread jeu = new Thread(new Jeu(flagStop, flagWait, this));
        jeu.start();
    }
}
