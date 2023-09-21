package com.mygdx.game.Personnage;
import java.util.ArrayList;

public abstract class Tamagotchi {
    private ArrayList<Food> panier;
    private int wallet;

    public int getWallet() {
        return wallet;
    }
    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
}
