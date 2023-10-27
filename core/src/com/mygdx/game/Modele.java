package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Personnage.*;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
                TimeUnit.MILLISECONDS.sleep(100);
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
    JsonValue soundBaseReader;

    // Gestionnaire de fichier
    FileHandle soundFile;

    // Emplacement des éléments
    String emplacement = "core/src/com/mygdx/game/jsonFile";

    // Drapeau qui gère le thread de jeu
    private final AtomicBoolean flagStop = new AtomicBoolean(), flagWait = new AtomicBoolean(true);

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

    /**
     * Constructeur de gestion du son (menu)
     */
    public Modele() {
    }

    /**
     * Constructeur de jeu
     */
    public Modele(int tamagotchiWished, String nomTamagotchi, int difficulty, Object save, Controller controller) {
        this.controller = controller;

        // Lecteur de fichier
        JsonReader jsonReader = new JsonReader();

        // Permet de lire et d'interagir avec le fichier
        soundFile = new FileHandle(emplacement + "settings.json");

        // Lecture du fichier de paramètre json
        soundBaseReader = jsonReader.parse(soundFile);

        if (save != null) {
            System.out.println("Save à faire !");
        } else {
            System.out.println("Pas de save");
        }

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

            case (4):
                robot = new Robot(difficulty);
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
            animal.setFood((float) (animal.getFood() - 0.4));
            animal.setHygiene((float) (animal.getHygiene() - 0.4));
            animal.setSleep((float) (animal.getSleep() - 0.2));
            animal.setHappiness((float) (animal.getHappiness() - 0.3));

            if (animal.getFood() == 0 || animal.getHappiness() == 0 || animal.getHygiene() == 0) {
                animal.setHappiness((float) (animal.getHappiness() - 0.3));
                animal.setLife(animal.getLife() - 1);
            }
            if (animal.getSleep() == 0) {
                animal.setHappiness(animal.getHappiness() - 1);
            }

            if (animal.getFood() >= 600) {
                animal.setLife(animal.getLife() + 1);
            }

            if (animal.getLife() == 0) {
                Gdx.app.exit();
            }

        } else {
            robot.setTank((float) (robot.getTank() - 0.4));
            robot.setMaintenance((float) (robot.getMaintenance() - 0.4));
            robot.setDurability((float) (robot.getDurability() - 0.2));
            robot.setHappiness((float) (robot.getHappiness() - 0.3));

            if (robot.getTank() == 0 || robot.getHappiness() == 0 || robot.getMaintenance() == 0) {
                robot.setHappiness((float) (robot.getHappiness() - 0.2));
                robot.setBattery(robot.getBattery() - 1);
            }
            if (robot.getDurability() == 0) {
                robot.setHappiness(robot.getHappiness() - 1);
            }

            if (robot.getTank() >= 600) {
                robot.setBattery(robot.getBattery() + 1);
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
                    controller.changeVisibilityWaitingBar(false);

                    // Sépare les différentes valeurs
                    String[] values = attente.split(";");

                    // Temps d'attente
                    int time = Integer.parseInt(values[0]);

                    // Temps de fin en milliseconde
                    long temps = System.currentTimeMillis() + (time * 1_000L);

                    // Tant que l'on n'a pas attendu le temps nécessaire, on continue
                    while (temps > System.currentTimeMillis()) {
                        // Met la bonne valeur sur la barre de progression
                        controller.setAmountProgressBar("waiting", getValueWaitingBar(time, System.currentTimeMillis(), temps));
                    }
                    // Reaffiche les éléments de l'interface
                    controller.changeVisibilityWaitingBar(true);

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
     * Définit la valeur de la barre de progression voulue
     *
     * @param progressBar Barre de progression
     * @param amount      Montant
     */
    public void setAmountProgressBar(String progressBar, float amount) {
        controller.setAmountProgressBar(progressBar, amount);
    }

    /**
     * * Définit la valeur du label voulu
     *
     * @param label  Label
     * @param amount Montant
     */
    public void setAmountLabel(String label, int amount) {
        controller.setAmountLabel(label, amount);
    }

    /**
     * Fait dormir le tamagotchi
     */
    public void sleep() {
        if (animal != null) {
            int temps = 13 + random.nextInt(4);
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
            int temps = 10 + random.nextInt(6);
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

        if (animal != null) {
            setAmountProgressBar("life", animal.getLife());
            setAmountProgressBar("food", animal.getFood());
            setAmountProgressBar("sleep", animal.getSleep());
            setAmountProgressBar("wash", animal.getHygiene());
            setAmountProgressBar("happy", animal.getHappiness());

            setAmountLabel("money", animal.getWallet());
            setAmountLabel("apple", animal.getNumberApple());
            setAmountLabel("goldenApple", animal.getNumberGoldenApple());

        } else {
            setAmountProgressBar("battery", robot.getBattery());
            setAmountProgressBar("tank", robot.getTank());
            setAmountProgressBar("durability", robot.getDurability());
            setAmountProgressBar("maintenance", robot.getMaintenance());
            setAmountProgressBar("happy", robot.getHappiness());

            setAmountLabel("money", robot.getWallet());
            setAmountLabel("apple", robot.getNumberOil());
            setAmountLabel("goldenApple", robot.getNumberExtraOil());
        }

        // Moteur de jeu
        Thread Moteur = new Thread(new Moteur(flagStop, flagWait, this));
        Moteur.start();
    }
}
