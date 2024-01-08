package com.mygdx.game.Personnage;

import java.util.ArrayList;

public class Panier {

    // Panier de nourriture du tamagotchi
    private final ArrayList<Food> basket = new ArrayList<>();

    public Panier() {

    }


    /**
     * Methode qui ajoute en fin de liste la Food
     *
     * @param nourriture Food Objet
     */
    public void addBasket(Food nourriture) {
        basket.add(nourriture);
    }


    /**
     * @return ArrayList<Food>
     */
    public ArrayList<Food> getBasket() {
        return basket;
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

}
