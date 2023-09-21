package com.mygdx.game.Personnage;

public class Animal {

    private int vie;
    private  int foods;
    private int hygiene;
    private int sleep;
    private int bonheur;


    public Animal(){
        this.vie=100;
        this.foods=100;
        this.hygiene=100;
        this.sleep=100;
        this.bonheur=100;
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public int getFoods() {
        return foods;
    }

    public void setFoods(int foods) {
        this.foods = foods;
    }

    public int getHygiene() {
        return hygiene;
    }

    public void setHygiene(int hygiene) {
        this.hygiene = hygiene;
    }

    public int getSleep() {
        return sleep;
    }

}
