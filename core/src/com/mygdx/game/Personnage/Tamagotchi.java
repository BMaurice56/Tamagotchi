package com.mygdx.game.Personnage;
import java.util.ArrayList;

public abstract class Tamagotchi {
    private ArrayList<Food> panier;
    private int wallet;

    private int difficulty;

    public Tamagotchi(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getWallet() {
        return wallet;
    }
    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
}
