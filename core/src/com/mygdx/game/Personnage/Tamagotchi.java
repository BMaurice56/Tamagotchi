package com.mygdx.game.Personnage;

public abstract class Tamagotchi {
    private final Panier basket;
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
        basket = new Panier();
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

    public Panier getPanier() {
        return basket;
    }

    public void addBasket(Food nourriture) {
        basket.addBasket(nourriture);
    }

    /**
     * Méthode qui enlève une nourriture dans le panier selon son index
     *
     * @param i int index
     */
    public void removeFoodFromBasket(int i) {
        basket.removeFoodFromBasket(i);
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
        return basket.getNumberOfFood(food);
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
