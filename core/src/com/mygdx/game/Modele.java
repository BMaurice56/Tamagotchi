package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Personnage.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


class Moteur implements Runnable {

    // Drapeau qui régule le moteur
    private final AtomicBoolean flagStop, flagWait, flagSave, flagPluie;

    private final AtomicInteger compteurPluie;

    // Controller de jeu
    private final Modele modele;

    // Compteur
    private int compteur = 0, durationPluie;

    private final Random random = new Random();

    /**
     * Constructeur
     *
     * @param flagStop      Arrête le moteur
     * @param flagWait      Fait attendre le moteur
     * @param flagSave      Sauvegarde ou non le jeu
     * @param flagPluie     Active la pluie
     * @param compteurPluie Sauvegarde le nombre de tours de boucle pour la pluie
     * @param modele        controller de jeu
     */
    public Moteur(AtomicBoolean flagStop, AtomicBoolean flagWait, AtomicBoolean flagSave, AtomicBoolean flagPluie, AtomicInteger compteurPluie, int difficulte, Modele modele) {
        this.flagStop = flagStop;
        this.flagWait = flagWait;
        this.flagSave = flagSave;
        this.flagPluie = flagPluie;
        this.compteurPluie = compteurPluie;
        switch (difficulte) {
            case (1):
                durationPluie = 10;
                break;

            case (2):
                durationPluie = 15;
                break;

            case (3):
                durationPluie = 20;
                break;
        }

        this.modele = modele;
    }

    /**
     * Méthode appelée pour exécuter le thread
     */
    public void run() {
        float nombreEntreSauvegarde = Modele.tempsEntreSauvegarde * 1000 / Modele.tempsAttenteJeu;
        float nombreEntrePluie = random.nextInt(Modele.tempsMinimalPluie, Modele.tempsMaximalPluie) * 1000 / Modele.tempsAttenteJeu;

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
            // Permet d'éviter de rappeler la fonction si on l'a déja appelé et qu'elle effectue une action actuellement
            if (flagWait.get()) {
                modele.waiting();
            }

            if (compteur >= nombreEntreSauvegarde) {
                // Si pas d'action en cours, donc on peut sauvegarder
                if (flagSave.get()) {
                    System.out.println("Sauvegarde automatique effectué");
                    compteur = 0;
                    modele.save();
                }
            } else {
                compteur++;
            }

            // Met la pluie
            if (compteurPluie.get() >= nombreEntrePluie) {
                // Si pas d'action en cours, donc on peut effectuer la pluie
                if (flagSave.get()) {
                    flagPluie.set(true);
                    nombreEntrePluie = random.nextInt(Modele.tempsMinimalPluie, Modele.tempsMaximalPluie) * 1000 / Modele.tempsAttenteJeu;
                    compteurPluie.set(0);
                }

                // Enlève la pluie au bout de 10/15/20 secondes
            } else if (compteurPluie.get() == durationPluie * 1000 / Modele.tempsAttenteJeu) {
                // Si présence de pluie, alors on l'arrête
                if (flagPluie.get()) {
                    flagPluie.set(false);
                    compteurPluie.set(0);

                    // Sinon on continue
                } else {
                    compteurPluie.set(compteurPluie.get() + 1);
                }

            } else {
                compteurPluie.set(compteurPluie.get() + 1);
            }
        }
    }
}

/**
 * Classe Modele qui gère les données du jeu
 */
public class Modele {

    // Json object
    private final JsonValue soundBaseReader;

    // Gestionnaire du fichier de son
    private final FileHandle soundFile;

    // Gestionnaire du fichier de sauvegarde
    private FileHandle saveFileParty;

    // Object Json pour la conversion des objets en json
    private final Json json;

    // Drapeau qui gère le thread de jeu
    private final AtomicBoolean flagStop = new AtomicBoolean(false), flagWait = new AtomicBoolean(true), flagSave = new AtomicBoolean(true);

    // Drapeau qui active ou non la pluie
    private AtomicBoolean flagPluie;

    // Stock le compteur de pluie même si le jeu est arrêté
    private final AtomicInteger compteurPluie = new AtomicInteger(0);

    // Tamagotchi animale
    private Animal animal;

    // Tamagotchi robot
    private Robot robot;

    // String d'action à effecteur
    private String attente;

    // Controller de jeu
    private Controller controller;

    // Temps en milliseconde
    public final static float tempsAttenteJeu = 50f;

    // Temps en seconde entre chaque sauvegarde
    public final static int tempsEntreSauvegarde = 5;

    // Temps en seconde minimum avant pluie
    public final static int tempsMinimalPluie = 30;

    // Temps en seconde maximum avant pluie
    public final static int tempsMaximalPluie = 60;

    public final float upperStat_10 = tempsAttenteJeu * 10 / 1000;

    public final float lowerStat_10 = tempsAttenteJeu * 10 / 1000;

    public final float lowerStat_4 = tempsAttenteJeu * 4 / 1000;

    public final float lowerStat_3 = tempsAttenteJeu * 3 / 1000;

    public final float lowerStat_2 = tempsAttenteJeu * 2 / 1000;


    /**
     * Constructeur de gestion des fichiers (menu)
     */
    public Modele() {
        // Lecteur de fichier
        JsonReader jsonReader = new JsonReader();

        // Permet de lire et d'interagir avec le fichier
        soundFile = new FileHandle("core/src/com/mygdx/game/jsonFile/settings.json");

        // Lecture du fichier de paramètre json
        soundBaseReader = jsonReader.parse(soundFile);

        // Gestion des fichiers json
        json = new Json();
    }

    /**
     * Constructeur de jeu
     */
    public Modele(int tamagotchiWished, String nomTamagotchi, int difficulty, boolean save, int numSave, Controller controller, int skin, AtomicBoolean flagPluie) {
        this();
        this.controller = controller;
        this.flagPluie = flagPluie;

        // Fichier de sauvegarde
        saveFileParty = Gdx.files.local("/core/src/com/mygdx/game/jsonFile/save" + numSave + ".json");

        if (save) {
            String tamagotchi = saveFileParty.readString();
            switch (tamagotchiWished) {
                case (1):
                    animal = json.fromJson(Chat.class, tamagotchi);
                    break;

                case (2):
                    animal = json.fromJson(Chien.class, tamagotchi);
                    break;

                case (3):
                    animal = json.fromJson(Dinosaure.class, tamagotchi);
                    break;

                case (4):
                    robot = json.fromJson(Robot.class, tamagotchi);
                    break;
            }
        } else {
            switch (tamagotchiWished) {
                case (1):
                    animal = new Chat(difficulty, nomTamagotchi, skin);
                    break;

                case (2):
                    animal = new Chien(difficulty, nomTamagotchi, skin);
                    break;

                case (3):
                    animal = new Dinosaure(difficulty, nomTamagotchi, skin);
                    break;

                case (4):
                    robot = new Robot(difficulty, nomTamagotchi, skin);
                    break;
            }
        }

        if (tamagotchiWished == 4) {
            flagPluie.set(robot.getPluie());
            compteurPluie.set(robot.getCompteurPluie());
        } else {
            flagPluie.set(animal.getPluie());
            compteurPluie.set(animal.getCompteurPluie());
        }
    }


    /**
     * Renvoie le niveau de son du jeu
     * Renvoie 0.5 si pas de fichier de son ou de valeur
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

            if (animal.getFood() >= 600 && animal.getHappiness() > 0 && animal.getHygiene() > 0 && animal.getSleep() > 0) {
                animal.setLife(animal.getLife() + upperStat_10);
            }

            if (animal.getLife() <= 0) {
                stopGame(false);
                // On attend que le thread wait se termine complètement (Sinon elle change l'écran)
                while (!flagWait.get()) {
                    continue;
                }
                controller.mortTamagotchi(saveFileParty);
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

            if (robot.getTank() >= 600 && robot.getHappiness() > 0 && robot.getSoftware() > 0 && robot.getDurability() > 0) {
                robot.setBattery(robot.getBattery() + upperStat_10);
            }

            if (robot.getBattery() <= 0) {
                stopGame(false);
                // On attend que la fonction wait se termine complètement (Sinon elle change l'écran)
                while (!flagWait.get()) {
                    continue;
                }
                controller.mortTamagotchi(saveFileParty);
            }
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
                    while (temps > System.currentTimeMillis() && !flagStop.get()) {
                        // Met la bonne valeur sur la barre de progression
                        controller.setAmountProgressBar("waiting", calculValueWaitingBar(time, System.currentTimeMillis(), temps));
                    }
                    // Reaffiche les éléments de l'interface
                    controller.actionEnCourTamagotchi(true, "");

                    // Bloque la sauvegarde
                    flagSave.set(false);

                    // Permet de savoir si l'on quitte le jeu pendant l'action
                    if (!flagStop.get()) {
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
                                    robot.jouer();
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
                    }
                    // Réautorise la sauvegarde
                    flagSave.set(true);

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
     * Réalise l'action passée en argument
     *
     * @param time   temps d'attente
     * @param titre  titre de l'action
     * @param action action a effectué
     * @param food   (optionnel) nourriture voulue
     */
    public void doAction(int time, String titre, String action, String food) {
        attente = time + ";" + titre + ";" + action + ";" + food;
    }


    /**
     * Permet d'acheter de la nourriture
     */
    public void buy(String food) {
        flagSave.set(false);

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

        flagSave.set(true);
    }

    /**
     * Arrête le jeu
     */
    public void stopGame(boolean save) {
        flagStop.set(true);

        if (save) {
            save();
        }
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
        Thread Moteur = new Thread(new Moteur(flagStop, flagWait, flagSave, flagPluie, compteurPluie, getTamagotchi().getDifficulty(), this));
        Moteur.start();
    }

    /**
     * Sauvegarde la partie dans un fichier
     */
    public void save() {
        if (animal != null) {
            animal.setCompteurPluie(compteurPluie.get());
            animal.setNumeroSalle(controller.getRoomTamagotchi());
            animal.setPluie(flagPluie.get());
        } else {
            robot.setCompteurPluie(compteurPluie.get());
            robot.setNumeroSalle(controller.getRoomTamagotchi());
            robot.setPluie(flagPluie.get());
        }

        // String contenant les informations pour le fichier json
        String tamagotchi;

        if (animal != null) {
            tamagotchi = "{numberTamagotchi:" + animal.getNumberTamagotchi() + "," + json.toJson(animal).substring(1);
        } else {
            tamagotchi = "{numberTamagotchi:" + robot.getNumberTamagotchi() + "," + json.toJson(robot).substring(1);
        }

        // Écriture du fichier
        saveFileParty.writeString(tamagotchi, false);
    }
}
