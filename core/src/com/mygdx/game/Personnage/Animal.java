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
        this.vie=1000;
        this.foods=1000;
        this.hygiene=1000;
        this.sleep=1000;
        this.bonheur=1000;
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
        while ((temps + 5_000) > System.currentTimeMillis()) {
            continue;
        }
        this.setWallet(this.getWallet()+50);
        bonheur-=getDifficulty()*100;
        hygiene-=getDifficulty()*43;
        sleep-=getDifficulty()*60;


        check();

    }

    public void check(){
        if(vie<0){
            vie=0;
        }
        if(foods<0){
            foods=0;
        }
        if(hygiene<0){
            hygiene=0;
        }
        if (sleep<0){
            sleep=0;
        }
        if (bonheur<0){
            bonheur=0;
        }
    }

    public void acheterApple(){
        int prix= 20;
        if(getWallet()>=prix){
            Food f = new Apple();
            ajoutPanier(f);
            setWallet(getWallet()-prix);
        }
    }
    public void acheterGoldenApple(){
        int prix = 50;
        if(getWallet()>=prix){
            Food f = new GoldenApple();
            ajoutPanier(f);
            setWallet(getWallet()-prix);
        }
    }


 }
