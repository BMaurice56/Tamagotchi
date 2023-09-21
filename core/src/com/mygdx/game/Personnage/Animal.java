package com.mygdx.game.Personnage;
import java.util.ArrayList;
public abstract class Animal extends Tamagotchi{

    private int vie;
    private  int foods;
    private int hygiene;
    private int sleep;
    private int bonheur;


    public Animal(int difficulty){
        super(difficulty);
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

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getBonheur() {
        return bonheur;
    }

    public void setBonheur(int bonheur) {
        this.bonheur = bonheur;
    }

    public void Afficher_Attribut(){
        System.out.println("vie : "+vie +"\nfood : "+foods+"\nhygiene: "+hygiene+"\nsleep: "+sleep+"\nbonheur : "+bonheur+"\nwallet :"+this.getWallet());
    }

    public void travailler(){
        long temps = System.currentTimeMillis();
        int bad=-10;
        int coins=5+this.getWallet();

        bonheur+=bad;
        this.setWallet(coins);


        while ((temps + 10_000) > System.currentTimeMillis()) {
            continue;
        }
    }

    protected void check(){
        if(vie<0){
            vie=0;
        }
        if(food<0){
            food
        }
    }

}
