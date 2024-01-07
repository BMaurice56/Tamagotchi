package com.mygdx.game.Personnage;

public abstract class Tamagotchi {
    private final Panier basket;
    private int wallet;

    private final int numberTamagotchi;

    private final int difficulty;

    final String name;

    final int skin;

    int compteurPluie = 0, numeroSalle = 3;

    boolean pluie = false;

    /**
     * Constructeur
     *
     * @param difficulty       int niveau
     * @param nom              String nom
     * @param numberTamagotchi int numéro
     */
    public Tamagotchi(int difficulty, String nom, int numberTamagotchi, int skin) {
        basket = new Panier();
        this.difficulty = difficulty;
        name = nom;
        this.numberTamagotchi = numberTamagotchi;
        this.skin = skin;
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
     * Enlève une nourriture dans le panier selon son index
     *
     * @param i int index
     */
    public void removeFoodFromBasket(int i) {
        basket.removeFoodFromBasket(i);
    }

    /**
     * Assesseur niveau de difficulté
     *
     * @return int difficulté
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Assesseur numéro de la texture
     *
     * @return int numéro
     */
    public int getSkin() {
        return skin;
    }

    /**
     * Assesseur nom
     *
     * @return String nom
     */
    public String getName() {
        return name;
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

    /**
     * Définie le compteur de la pluie
     *
     * @param compteurPluie numéro
     */
    public void setCompteurPluie(int compteurPluie) {
        this.compteurPluie = compteurPluie;
    }

    /**
     * Définie le numéro de la salle
     *
     * @param numeroSalle numéro
     */
    public void setNumeroSalle(int numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    /**
     * Renvoie le compteur de pluie
     *
     * @return int
     */
    public int getCompteurPluie() {
        return compteurPluie;
    }

    /**
     * Renvoie le numéro de la salle
     *
     * @return int
     */
    public int getNumeroSalle() {
        return numeroSalle;
    }

    /**
     * Renvoie si oui ou non, il pleuvait sur le tamagotchi
     *
     * @return boolean
     */
    public boolean getPluie() {
        return pluie;
    }

    /**
     * Définie s'il pleut ou non au moment de la sauvegarde
     *
     * @param pluie boolean
     */
    public void setPluie(boolean pluie) {
        this.pluie = pluie;
    }
}
