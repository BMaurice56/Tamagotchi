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
        System.out.println("vie : "+vie +"\nfood : "+foods+"\nhygiene: "+hygiene+"\nsleep: "+sleep+"\nbonheur : "+bonheur);
    }

    public void travailler(){
        int bad=-20;
        if((bonheur+bad)<0){
            bonheur=0;
        }else {
            bonheur+=bad;
        }

    }

}
