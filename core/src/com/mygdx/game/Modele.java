package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Personnage.*;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


class Moteur implements Runnable {

    // Drapeau qui régule le moteur
    private final AtomicBoolean flagStop, flagWait;

    // Controller de jeu
    private final Modele modele;

    /**
     * Constructeur
     *
     * @param flagStop Arrête le moteur
     * @param flagWait Fait attendre le moteur
     * @param modele   controller de jeu
     */
    public Moteur(AtomicBoolean flagStop, AtomicBoolean flagWait, Modele modele) {
        this.flagStop = flagStop;
        this.flagWait = flagWait;
        this.modele = modele;
    }

    /**
     * Méthode appelée pour exécuter le thread
     */
    public void run() {
        // Tant que le drapeau n'est pas levé, on continue
        while (!flagStop.get()) {
            // fait attendre le moteur
            try {
                TimeUnit.MILLISECONDS.sleep((long) Modele.tempsAttenteJeu);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Fait vivre le tamagotchi
            modele.vieTamagotchi();

            // Si on peut faire une action, on l'effectue
            // Permet d'éviter de rappeler la fonction si on l'a déja appelé et qu'elle effectue une action
            if (flagWait.get()) {
                modele.waiting();
            }

            // Met à jour l'affichage
            modele.updateAffichage();
        }
    }
}

/**
 * Classe Modele qui gère les données du jeu
 */
public class Modele {

    // Json object
    private final JsonValue  soundBaseReader;

    // Gestionnaire de fichier
    private final FileHandle soundFile;

    // Emplacement des éléments
    private final String pathSettingsFile = "core/src/com/mygdx/game/jsonFile/";

    private final String settingsFileName = "settings.json";

    // Drapeau qui gère le thread de jeu
    private final AtomicBoolean flagStop = new AtomicBoolean(false), flagWait = new AtomicBoolean(true);

    // Tamagotchi animale
    private Animal animal;

    // Tamagotchi robot
    private Robot robot;

    // String d'action à effecteur
    private String attente;

    // Controller de jeu
    private Controller controller;

    // Aléatoire
    private final Random random = new Random();

    public final static float tempsAttenteJeu = 100f;

    private final float upperStat_10 = 10 / (tempsAttenteJeu / 10);

    private final float lowerStat_10 = 10 / (tempsAttenteJeu / 10);

    private final float lowerStat_4 = 4 / (tempsAttenteJeu / 10);

    private final float lowerStat_3 = 3 / (tempsAttenteJeu / 10);

    private final float lowerStat_2 = 2 / (tempsAttenteJeu / 10);


    /**
     * Constructeur de gestion du son (menu)
     */
    public Modele() {
        // Lecteur de fichier
        JsonReader jsonReader = new JsonReader();

        // Permet de lire et d'interagir avec le fichier
        soundFile = new FileHandle(pathSettingsFile + settingsFileName);

        // Lecture du fichier de paramètre json
        soundBaseReader = jsonReader.parse(soundFile);
    }

    /**
     * Constructeur de jeu
     */
    public Modele(int tamagotchiWished, String nomTamagotchi, int difficulty, Object save, Controller controller) {
        this();
        this.controller = controller;

        if (save != null) {
            System.out.println("Save à faire !");
        } else {
            System.out.println("Pas de save");
        }

        switch (tamagotchiWished) {
            case (1):
                animal = new Chat(difficulty, nomTamagotchi);
                break;

            case (2):
                animal = new Chien(difficulty, nomTamagotchi);
                break;

            case (3):
                animal = new Dinosaure(difficulty, nomTamagotchi);
                break;

            case (4):
                robot = new Robot(difficulty, nomTamagotchi);
        }
    }


    /**
     * Renvoie le niveau de son du jeu
     *
     * @return float son
     */
    public float getSound() {
        // Si le fichier existe bien et contient une valeur, alors on la renvoie
        if (soundBaseReader != null && contains("sound")) {
            return soundBaseReader.getFloat("sound");
        }
        return 0.5f;
    }

    /**
     * Enregistre le niveau de son du jeu
     *
     * @param son float son
     */
    public void setSound(float son) {
        soundFile.writeString("{\n \"sound\":" + son + "\n}", false);
    }


    /**
     * Vérifie si une clef est présent dans le fichier json
     *
     * @param name nom
     * @return boolean true si contient le nom
     */
    public boolean contains(String name) {
        return soundBaseReader.has(name);
    }

    /**
     * Accesseur du tamagotchi de jeu
     *
     * @return Tamagotchi
     */
    public Tamagotchi getTamagotchi() {
        if (animal != null) {
            return animal;
        } else {
            return robot;
        }
    }

    /**
     * Modifie les valeurs du tamagotchi
     */
    public void vieTamagotchi() {
        if (animal != null) {
            animal.setFood(animal.getFood() - lowerStat_4);
            animal.setHygiene(animal.getHygiene() - lowerStat_4);
            animal.setSleep(animal.getSleep() - lowerStat_2);
            animal.setHappiness(animal.getHappiness() - lowerStat_3);

            if (animal.getFood() == 0 || animal.getHappiness() == 0 || animal.getHygiene() == 0) {
                animal.setHappiness(animal.getHappiness() - lowerStat_3);
                animal.setLife(animal.getLife() - lowerStat_10);
            }
            if (animal.getSleep() == 0) {
                animal.setHappiness(animal.getHappiness() - lowerStat_10);
            }

            if (animal.getFood() >= 600) {
                animal.setLife(animal.getLife() + upperStat_10);
            }

            if (animal.getLife() == 0) {
                Gdx.app.exit();
            }

        } else {
            robot.setTank(robot.getTank() - lowerStat_4);
            robot.setSoftware(robot.getSoftware() - lowerStat_4);
            robot.setDurability(robot.getDurability() - lowerStat_2);
            robot.setHappiness(robot.getHappiness() - lowerStat_3);

            if (robot.getTank() == 0 || robot.getHappiness() == 0 || robot.getSoftware() == 0) {
                robot.setHappiness(robot.getHappiness() - lowerStat_3);
                robot.setBattery(robot.getBattery() - lowerStat_10);
            }
            if (robot.getDurability() == 0) {
                robot.setHappiness(robot.getHappiness() - lowerStat_10);
            }

            if (robot.getTank() >= 600) {
                robot.setBattery(robot.getBattery() + upperStat_10);
            }

            if (robot.getBattery() == 0) {
                Gdx.app.exit();
            }
        }

    }

    /**
     * Met à jour l'affichage avec les bonnes valeurs du tamagotchi
     */
    public void updateAffichage() {
        if (animal != null) {
            controller.setAmountProgressBar("life", animal.getLife());
            controller.setAmountProgressBar("food", animal.getFood());
            controller.setAmountProgressBar("sleep", animal.getSleep());
            controller.setAmountProgressBar("wash", animal.getHygiene());
            controller.setAmountProgressBar("happy", animal.getHappiness());

            controller.setAmountLabel("money", animal.getWallet());
            controller.setAmountLabel("apple", animal.getNumberApple());
            controller.setAmountLabel("goldenApple", animal.getNumberGoldenApple());
        } else {
            controller.setAmountProgressBar("battery", robot.getBattery());
            controller.setAmountProgressBar("tank", robot.getTank());
            controller.setAmountProgressBar("durability", robot.getDurability());
            controller.setAmountProgressBar("update", robot.getSoftware());
            controller.setAmountProgressBar("happy", robot.getHappiness());

            controller.setAmountLabel("money", robot.getWallet());
            controller.setAmountLabel("oil", robot.getNumberOil());
            controller.setAmountLabel("superOil", robot.getNumberExtraOil());

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
    public float calculValueWaitingBar(int tempsAttente, double min, double max) {
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
                    // Sépare les différentes valeurs
                    String[] values = attente.split(";");

                    // Cache les éléments inutiles de l'interface
                    controller.actionEnCourTamagotchi(false, values[1]);

                    // Temps d'attente
                    int time = Integer.parseInt(values[0]);

                    // Temps de fin en milliseconde
                    long temps = System.currentTimeMillis() + (time * 1_000L);

                    // Tant que l'on n'a pas attendu le temps nécessaire, on continue
                    while (temps > System.currentTimeMillis()) {
                        // Met la bonne valeur sur la barre de progression
                        controller.setAmountProgressBar("waiting", calculValueWaitingBar(time, System.currentTimeMillis(), temps));
                    }
                    // Reaffiche les éléments de l'interface
                    controller.actionEnCourTamagotchi(true, "");

                    // Fait l'action voulue pour mettre à jour les valeurs du tamagotchi
                    switch (values[2]) {
                        case "work":
                            if (animal != null) {
                                animal.work();
                            } else {
                                robot.work();
                            }
                            break;

                        case "sleep":
                            if (animal != null) {
                                animal.sleep();
                            } else {
                                robot.maintenance();
                            }
                            break;

                        case "wash":
                            if (animal != null) {
                                animal.wash();
                            } else {
                                robot.updating();
                            }
                            break;

                        case "play":
                            if (animal != null) {
                                animal.play();
                            } else {
                                robot.dance();
                            }
                            break;

                        case "eat":
                            if (animal != null) {
                                animal.eat(values[3]);
                            } else {
                                robot.fillTank(values[3]);
                            }
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
        int temps = 13 + random.nextInt(4);
        attente = temps + ";Dodo;sleep";
    }

    /**
     * Fait travailler le tamagotchi
     */
    public void work() {
        attente = "12;Travaille;work";
    }

    /**
     * Fait se laver le tamagotchi
     */
    public void wash() {
        int temps = 10 + random.nextInt(6);
        attente = temps + ";Lavage;wash";
    }

    /**
     * Fait manger le tamagotchi
     */
    public void eat(String food) {
        attente = "5;Alimentation;eat;" + food;
    }


    /**
     * Permet d'acheter de la nourriture
     */
    public void buy(String food) {
        switch (food) {
            case "Apple":
                animal.buyApple();
                break;

            case "GoldenApple":
                animal.buyGoldenApple();
                break;

            case "Oil":
                robot.buyOil();
                break;

            case "ExtraOil":
                robot.buyExtraOil();
                break;
        }
    }

    /**
     * Fait jouer le tamagotchi
     */
    public void play() {
        int temps = 10 + random.nextInt(6);
        attente = temps + ";Jeu;play";
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

        if (animal != null) {
            controller.setAmountProgressBar("life", animal.getLife());
            controller.setAmountProgressBar("food", animal.getFood());
            controller.setAmountProgressBar("sleep", animal.getSleep());
            controller.setAmountProgressBar("wash", animal.getHygiene());
            controller.setAmountProgressBar("happy", animal.getHappiness());

            controller.setAmountLabel("money", animal.getWallet());
            controller.setAmountLabel("apple", animal.getNumberApple());
            controller.setAmountLabel("goldenApple", animal.getNumberGoldenApple());

        } else {
            controller.setAmountProgressBar("battery", robot.getBattery());
            controller.setAmountProgressBar("tank", robot.getTank());
            controller.setAmountProgressBar("durability", robot.getDurability());
            controller.setAmountProgressBar("maintenance", robot.getSoftware());
            controller.setAmountProgressBar("happy", robot.getHappiness());

            controller.setAmountLabel("money", robot.getWallet());
            controller.setAmountLabel("apple", robot.getNumberOil());
            controller.setAmountLabel("goldenApple", robot.getNumberExtraOil());
        }

        // Moteur de jeu
        Thread Moteur = new Thread(new Moteur(flagStop, flagWait, this));
        Moteur.start();
    }
}
