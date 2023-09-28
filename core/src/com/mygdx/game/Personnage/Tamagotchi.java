package com.mygdx.game.Personnage;

import java.util.ArrayList;

public abstract class Tamagotchi {
    private ArrayList<Food> panier;
    private int wallet;

    private int difficulty;

    public Tamagotchi(int difficulty) {
        panier = new ArrayList<>();
        this.difficulty = difficulty;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public ArrayList<Food> getPanier() {
        return panier;
    }

    public void ajoutPanier(Food nourriture) {
        panier.add(nourriture);
    }

    public int getDifficulty() {
        return difficulty;
    }
}
