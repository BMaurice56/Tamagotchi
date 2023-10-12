package com.mygdx.game.Personnage;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Robot extends Tamagotchi {

    private int battery;
    private int maintenance;
    private int tank;
    private int durability;
    private int happiness;

    Random random;

    public Robot(int difficulty) {
        super(difficulty);
        battery = 1000;
        tank = 1000;
        maintenance = 1000;
        durability = 1000;
        happiness = 1000;

        random = new Random();
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(int maintenance) {
        this.maintenance = maintenance;
    }

    public int getTank() {
        return tank;
    }

    public void setTank(int tank) {
        this.tank = tank;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getNumberOil() {
        int number = 0;

        for (Food food : getBasket()) {
            if (food.getName().equals("Oil")) {
                number++;
            }
        }

        return number;
    }

    public int getNumberExtraOil() {
        int number = 0;

        for (Food food : getBasket()) {
            if (food.getName().equals("ExtraOil")) {
                number++;
            }
        }

        return number;
    }

    public void check() {
        if (battery < 0) {
            battery = 0;
        }
        if (tank < 0) {
            tank = 0;
        }
        if (maintenance < 0) {
            maintenance = 0;
        }
        if (durability < 0) {
            durability = 0;
        }
        if (happiness < 0) {
            happiness = 0;
        }
        if (tank > 1000) {
            tank = 1000;
        }
    }

    public void reload() throws InterruptedException {

        int interval = random.nextInt(4);

        TimeUnit.SECONDS.sleep(15 + interval);

        if (durability <= 200) {
            setDurability(900 - getDifficulty() * 100);
        } else {
            setDurability(1000);
        }
    }

    public void updating() throws InterruptedException {

        int interval = random.nextInt(6);

        TimeUnit.SECONDS.sleep(12 + interval);

        happiness += 100;

        if (happiness > 1000) {
            setHappiness(1000);
        }

        if (maintenance <= 200) {
            setMaintenance(900 - getDifficulty() * 85);
        } else {
            setMaintenance(1000);
        }
    }

    public void dance() throws InterruptedException {

        int interval = random.nextInt(6);

        TimeUnit.SECONDS.sleep(10 + interval);

        maintenance -= random.nextInt(75, 200);

        check();

        if (100 <= happiness && happiness <= 200) {
            setHappiness(900 - getDifficulty() * 100);
        } else if (happiness < 100) {
            setHappiness(750 - getDifficulty() * 100);
        } else {
            setHappiness(1000);
        }
    }

    public void fillTank(String tank) throws InterruptedException {

        for (int i = 0; i < getBasket().size(); i++) {

            Food food1 = getBasket().get(i);

            if (tank.equals(food1.getName())) {
                removePanier(i);
                tank += food1.getPoint();

                TimeUnit.SECONDS.sleep(5);

                if (tank.equals("SuperExtraOil")) {
                    happiness += 75;
                }
                if (happiness > 1000) {
                    setHappiness(1000);
                }
                check();
            }
        }
    }

    //public void repair(){}

    public void work() throws InterruptedException {

        TimeUnit.SECONDS.sleep(12);

        setWallet(getWallet() + 50);
        happiness -= getHappiness() * 100;
        maintenance -= getMaintenance() * 43;
        durability -= getDurability() * 60;

        check();
    }

    public void buyOil() {

        Food f = new Oil();
        if (getWallet() >= f.getPrice()) {
            addBasket(f);
            setWallet(getWallet() - f.getPrice());
        }
    }

    public void buySuperExtraOil() {

        Food f = new ExtraOil();
        if (getWallet() >= f.getPrice()) {
            addBasket(f);
            setWallet(getWallet() - f.getPrice());
        }
    }

    public void Afficher_Attribut() {
        System.out.println("vie : " + battery + "\nfood : " + tank + "\nhygiene: " + maintenance + "\nsleep: " + durability + "\nbonheur : " + happiness + "\nwallet :" + this.getWallet());
    }
}
