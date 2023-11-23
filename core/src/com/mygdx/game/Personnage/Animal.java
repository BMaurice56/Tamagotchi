package com.mygdx.game.Personnage;

import java.util.Random;

public abstract class Animal extends Tamagotchi {

    private float life;
    private float food;
    private float hygiene;
    private float sleep;
    private float happiness;

    private final Random random = new Random();

    /**
     * Constructeur
     * initialise à 1000 tous les attributs sauf son porte-monnaie
     *
     * @param difficulty Niveau de difficulty
     */
    public Animal(int difficulty, String nom) {
        super(difficulty, nom);
        life = 1000;
        food = 1000;
        hygiene = 1000;
        sleep = 1000;
        happiness = 1000;
    }

    public float getLife() {
        return life;
    }

    public void setLife(float l) {
        life = l;

        if (life < 0) {
            life = 0;
        } else if (life > 1000) {
            life = 1000;
        }
    }

    public float getFood() {
        return food;
    }

    public void setFood(float f) {
        food = f;

        if (food < 0) {
            food = 0;
        } else if (food > 1000) {
            food = 1000;
        }
    }

    public float getHygiene() {
        return hygiene;
    }

    public void setHygiene(float h) {
        hygiene = h;

        if (hygiene < 0) {
            hygiene = 0;
        } else if (hygiene > 1000) {
            hygiene = 1000;
        }
    }

    public float getSleep() {
        return sleep;
    }

    public void setSleep(float s) {
        sleep = s;

        if (sleep < 0) {
            sleep = 0;
        } else if (sleep > 1000) {
            sleep = 1000;
        }
    }

    public float getHappiness() {
        return happiness;
    }

    public void setHappiness(float h) {
        happiness = h;

        if (happiness < 0) {
            happiness = 0;
        } else if (happiness > 1000) {
            happiness = 1000;
        }
    }

    public int getNumberApple() {
        int number = 0;

        for (Food food : getBasket()) {
            if (food.getName().equals("Apple")) {
                number++;
            }
        }

        return number;
    }

    public int getNumberGoldenApple() {
        int number = 0;

        for (Food food : getBasket()) {
            if (food.getName().equals("GoldenApple")) {
                number++;
            }
        }

        return number;
    }

    /**
     * Travaille pendant 12 secondes pour gagner de l'argent
     * Modifie : wallet, bonheur, hygiene, sleep
     */
    public void work() {
        setWallet(getWallet() + 50);

        setHappiness(getHappiness() - getDifficulty() * 100);
        setHygiene(getHygiene() - getDifficulty() * 43);
        setSleep(getSleep() - getDifficulty() * 60);
    }


    /**
     * Supprime food du panier et y ajoute la valeur de point associer
     *
     * @param food apple or golden apple
     *             modifie : foods, bonheur
     */
    public void eat(String food) {
        for (int i = 0; i < getBasket().size(); i++) { //parcourt l'attribut panier

            Food food1 = getBasket().get(i); //el actu

            if (food.equals(food1.getName())) { //quand trouver
                setFood(getFood() + food1.getPoint());
                if (food.equals("GoldenApple")) {
                    setHappiness(getHappiness() + 75);
                }
                removePanier(i); //on supprime l'élément dans la liste

                return;
            }
        }
    }


    /**
     * Auteur : Arthur
     * description : methode dormir qui met votre jauge sommeil à max sauf si on a moins de 200 de sommeil
     * modifie : sleep
     */
    public void sleep() {
        if (getSleep() <= 200) {
            setSleep(900 - getDifficulty() * 100); //som sommeil sera 800 ou 700 ou 600  en fonction de la difficulté
        } else {
            setSleep(1000); // sinon son sommeil va au max
        }
    }

    /**
     * Auteur : Arthur
     * description : methode dormir qui met votre jauge sommeil à max sauf si on a moins de 200 de sommeil
     * Modifie : hygiene, bonheur
     */
    public void wash() {
        setHappiness(getHappiness() + 100);

        if (getHygiene() <= 200) {
            setHygiene(900 - getDifficulty() * 85);
        } else {
            setHygiene(1000);
        }

    }

    /**
     * Auteur : Arthur
     * descriptif : methode jouer qui rend + heureux le joueur, mais le salin
     * Modifie : bonheur, hygiene
     */
    public void play() {
        setHygiene(getHygiene() - random.nextInt(75, 200));

        if (100 <= getHappiness() && getHappiness() <= 200) {
            setHappiness(900 - getDifficulty() * 100); //som bonheur sera 800 ou 700  ou 600  en fonction de la difficulté
        } else if (getHappiness() < 100) {
            setHappiness(750 - getDifficulty() * 100); //som bonheur sera 600 ou 500  ou 400  en fonction de la difficulté
        } else {
            setHappiness(1000); // sinon son bonheur va au max
        }
    }

    /**
     * buy an apple
     */
    public void buyApple() {
        if (getWallet() >= Apple.price) {
            addBasket(new Apple());
            setWallet(getWallet() - Apple.price);
        }
    }

    /**
     * buy a golden apple
     */
    public void buyGoldenApple() {
        if (getWallet() >= GoldenApple.price) {
            addBasket(new GoldenApple());
            setWallet(getWallet() - GoldenApple.price);
        }
    }
}
