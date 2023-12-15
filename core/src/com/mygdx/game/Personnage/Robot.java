package com.mygdx.game.Personnage;

import java.util.Random;

public class Robot extends Tamagotchi {

    private float battery;
    private float tank;
    private float durability;
    private float software;
    private float happiness;

    private Random random;

    public Robot() {
        super(1, "", 4, 1);
    }

    public Robot(int difficulty, String nom, int skin) {
        super(difficulty, nom, 4, skin);
        battery = 1000;
        tank = 1000;
        software = 1000;
        durability = 1000;
        happiness = 1000;

        random = new Random();
    }

    public float getBattery() {
        return battery;
    }

    public void setBattery(float b) {
        battery = b;

        if (battery < 0) {
            battery = 0;
        } else if (battery > 1000) {
            battery = 1000;
        }
    }

    public float getSoftware() {
        return software;
    }

    public void setSoftware(float s) {
        software = s;

        if (software < 0) {
            software = 0;
        } else if (software > 1000) {
            software = 1000;
        }
    }

    public float getTank() {
        return tank;
    }

    public void setTank(float t) {
        tank = t;

        if (tank < 0) {
            tank = 0;
        } else if (tank > 1000) {
            tank = 1000;
        }
    }

    public float getDurability() {
        return durability;
    }

    public void setDurability(float d) {
        durability = d;

        if (durability < 0) {
            durability = 0;
        } else if (durability > 1000) {
            durability = 1000;
        }
    }

    public float getHappiness() {
        return happiness;
    }

    public void setHappiness(float h) {
        happiness = h;

        if (happiness < 0) {
            happiness = 0;
        } else if (happiness > 1000) {
            happiness = 1000;
        }
    }

    public int getNumberOil() {
        return getPanier().getNumberOfFood("Oil");
    }

    public int getNumberExtraOil() {

        return getPanier().getNumberOfFood("ExtraOil");
    }


    public void maintenance() {
        if (getDurability() <= 200) {
            setDurability(900 - getDifficulty() * 100);
        } else {
            setDurability(1000);
        }
    }

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

    public void fillTank(String tank) {

        for (int i = 0; i < getPanier().getBasket().size(); i++) {

            Food food1 = getPanier().getBasket().get(i);

            if (tank.equals(food1.getName())) {
                removeFoodFromBasket(i);

                setTank(getTank() + food1.getPoint());

                if (tank.equals("SuperExtraOil")) {
                    setHappiness(getHappiness() + 75);
                }

                return;
            }
        }
    }

    public void work() {
        setWallet(getWallet() + 50);

        setHappiness(getHappiness() - getDifficulty() * 100);
        setSoftware(getSoftware() - getDifficulty() * 43);
        setDurability(getDurability() - getDifficulty() * 60);
    }

    public void buyOil() {
        if (getWallet() >= Oil.price) {
            addBasket(new Oil());
            setWallet(getWallet() - Oil.price);
        }
    }

    public void buyExtraOil() {
        if (getWallet() >= ExtraOil.price) {
            addBasket(new ExtraOil());
            setWallet(getWallet() - ExtraOil.price);
        }
    }
}
