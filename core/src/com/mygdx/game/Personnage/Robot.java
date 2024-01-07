package com.mygdx.game.Personnage;

import java.util.Random;

public class Robot extends Tamagotchi {

    private float battery;
    private float tank;
    private float durability;
    private float software;
    private float happiness;

    private final Random random = new Random();

    /**
     * Constructeur pour la sauvegarde
     */
    public Robot() {
        super(1, "", 4, 1);
    }

    /**
     * Constructeur
     * initialise à 1000 tous les attributs sauf son porte-monnaie
     *
     * @param difficulty Niveau de difficulty
     * @param nom        Nom
     * @param skin       Numéro de l'apparence
     */
    public Robot(int difficulty, String nom, int skin) {
        super(difficulty, nom, 4, skin);
        battery = 1000;
        tank = 1000;
        software = 1000;
        durability = 1000;
        happiness = 1000;
    }

    /**
     * Getter batterie
     *
     * @return float valeur
     */
    public float getBattery() {
        return battery;
    }

    /**
     * Setter batterie
     *
     * @param b float valeur
     */
    public void setBattery(float b) {
        battery = b;

        if (battery < 0) {
            battery = 0;
        } else if (battery > 1000) {
            battery = 1000;
        }
    }

    /**
     * Getter logiciel
     *
     * @return float valeur
     */
    public float getSoftware() {
        return software;
    }

    /**
     * Setter logiciel
     *
     * @param s float valeur
     */
    public void setSoftware(float s) {
        software = s;

        if (software < 0) {
            software = 0;
        } else if (software > 1000) {
            software = 1000;
        }
    }

    /**
     * Getter réservoir
     *
     * @return float valeur
     */
    public float getTank() {
        return tank;
    }

    /**
     * Setter réservoir
     *
     * @param t float valeur
     */
    public void setTank(float t) {
        tank = t;

        if (tank < 0) {
            tank = 0;
        } else if (tank > 1000) {
            tank = 1000;
        }
    }

    /**
     * Getter durabilité
     *
     * @return float valeur
     */
    public float getDurability() {
        return durability;
    }

    /**
     * Setter durabilité
     *
     * @param d float valeur
     */
    public void setDurability(float d) {
        durability = d;

        if (durability < 0) {
            durability = 0;
        } else if (durability > 1000) {
            durability = 1000;
        }
    }

    /**
     * Getter joie
     *
     * @return float valeur
     */
    public float getHappiness() {
        return happiness;
    }

    /**
     * Setter joie
     *
     * @param h float valeur
     */
    public void setHappiness(float h) {
        happiness = h;

        if (happiness < 0) {
            happiness = 0;
        } else if (happiness > 1000) {
            happiness = 1000;
        }
    }

    /**
     * Renvoie le nombre d'huiles
     *
     * @return int nombre
     */
    public int getNumberOil() {
        return getPanier().getNumberOfFood("Oil");
    }

    /**
     * Renvoie le nombre de super huiles
     *
     * @return int nombre
     */
    public int getNumberSuperOil() {
        return getPanier().getNumberOfFood("SuperOil");
    }


    /**
     * Maintenance du robot
     */
    public void maintenance() {
        if (getDurability() <= 200) {
            setDurability(900 - getDifficulty() * 100);
        } else {
            setDurability(1000);
        }
    }

    /**
     * Mise à jour du robot
     */
    public void updating() {

        setHappiness(getHappiness() + 100);

        if (getHappiness() > 1000) {
            setHappiness(1000);
        }

        if (getSoftware() <= 200) {
            setSoftware(900 - getDifficulty() * 85);
        } else {
            setSoftware(1000);
        }
    }

    /**
     * Partie de jeu du robot
     */
    public void jouer() {

        setSoftware(getSoftware() - random.nextInt(75, 200));

        if (100 <= getHappiness() && getHappiness() <= 200) {
            setHappiness(900 - getDifficulty() * 100);
        } else if (getHappiness() < 100) {
            setHappiness(750 - getDifficulty() * 100);
        } else {
            setHappiness(1000);
        }
    }

    /**
     * Remplie le réservoir du robot
     *
     * @param tank Huile voulu
     */
    public void fillTank(String tank) {

        for (int i = 0; i < getPanier().getBasket().size(); i++) {

            Food food1 = getPanier().getBasket().get(i);

            if (tank.equals(food1.getName())) {
                removeFoodFromBasket(i);

                setTank(getTank() + food1.getPoint());

                if (tank.equals("SuperOil")) {
                    setHappiness(getHappiness() + 75);
                }

                return;
            }
        }
    }

    /**
     * Travail du robot
     */
    public void work() {
        setWallet(getWallet() + 50);

        setHappiness(getHappiness() - getDifficulty() * 100);
        setSoftware(getSoftware() - getDifficulty() * 43);
        setDurability(getDurability() - getDifficulty() * 60);
    }

    /**
     * Achat d'huile
     */
    public void buyOil() {
        if (getWallet() >= Oil.price) {
            addBasket(new Oil());
            setWallet(getWallet() - Oil.price);
        }
    }

    /**
     * Achat de super huile
     */
    public void buySuperOil() {
        if (getWallet() >= SuperOil.price) {
            addBasket(new SuperOil());
            setWallet(getWallet() - SuperOil.price);
        }
    }
}
