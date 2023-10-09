package com.mygdx.game.Personnage;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Robot extends Tamagotchi{

    private int battery;
    private int maintenance;
    private int tank;
    private int durability;
    private int happiness;

    Random random;

    public Robot(int difficulty) {
        super(difficulty);
        battery = 1000;
        maintenance = 1000;
        tank = 1000;
        durability = 1000;
        happiness = 1000;

        random = new Random();
    }

    public int getBattery(){ return battery; }

    public void setBattery(int battery){ this.battery = battery; }

    public int getMaintenance(){ return maintenance; }

    public void setMaintenance(int maintenance){ this.maintenance = maintenance; }

    public int getTank(){ return tank; }

    public void setTank(int tank){ this.tank = tank; }

    public int getDurability(){ return durability; }

    public void setDurability(int durability){ this.durability = durability; }

    public int getHappiness(){ return happiness; }

    public void setHappiness(int happiness){ this.happiness = happiness; }

    public void reload() throws InterruptedException{

        int interval = random.nextInt(4);

        TimeUnit.SECONDS.sleep(15 + interval);

        if (battery <= 200){
            setBattery(900 - getDifficulty() * 100);
        }else{
            setBattery(1000);
        }
    }
}
