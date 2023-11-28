package com.mygdx.game.Personnage;

import java.util.ArrayList;

public abstract class Tamagotchi {
    private final ArrayList<Food> basket;
    private int wallet;

    private final int numberTamagotchi;

    private final int difficulty;

    final String name;

    /**
     * Constructeur
     *
     * @param difficulty       int niveau
     * @param nom              String nom
     * @param numberTamagotchi int numéro
     */
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

    /**
     * Méthode qui enlève une nourriture dans le panier selon son index
     *
     * @param i int index
     */
    public void removeFoodFromBasket(int i) {
        basket.remove(i);
    }

    /**
     * Getter niveau de difficulté
     *
     * @return int difficulté
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Renvoi le nombre de nourritures selon son nom
     *
     * @param food String nom de la nourriture
     * @return int leurs nombres
     */
    public int getNumberOfFood(String food) {
        int number = 0;

        for (int i = 0; i < getBasket().size(); i++) {
            if (basket.get(i).getName().equals(food)) {
                number++;
            }
        }

        return number;
    }

    /**
     * Renvoie le nombre du tamagotchi
     *
     * @return int son nombre
     */
    public int getNumberTamagotchi() {
        return numberTamagotchi;
    }
}
