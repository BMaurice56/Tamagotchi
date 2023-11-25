package com.mygdx.game.Personnage;

import java.util.ArrayList;

public abstract class Tamagotchi {
    private final ArrayList<Food> basket;
    private int wallet;

    private final int numberTamagotchi;

    private final int difficulty;

    private final String name;

    public Tamagotchi(int difficulty, String nom, int numberTamagotchi) {
        basket = new ArrayList<>();
        this.difficulty = difficulty;
        name = nom;
        this.numberTamagotchi = numberTamagotchi;
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

    public void removePanier(int i) {
        basket.remove(i);
    }

    public int getDifficulty() {
        return difficulty;
    }


    public int getNumberOfFood(String food) {
        int number = 0;

        for (int i = 0; i < getBasket().size(); i++) {
            if (basket.get(i).getName().equals(food)) {
                number++;
            }
        }

        return number;
    }

    public int getNumberTamagotchi() {
        return numberTamagotchi;
    }
}
