package com.mygdx.game.Personnage;

import java.util.ArrayList;

public abstract class Tamagotchi {
    private ArrayList<Food> basket;
    private int wallet;

    private int difficulty;

    public Tamagotchi(int difficulty) {
        basket = new ArrayList<>();
        this.difficulty = difficulty;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public ArrayList<Food> getBasket() {
        return basket;
    }

    public void addBasket(Food nourriture) {
        basket.add(nourriture);
    }
    public void removePanier(int i){
        basket.remove(i);}

    public int getDifficulty() {
        return difficulty;
    }
}
