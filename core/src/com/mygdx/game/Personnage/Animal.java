package com.mygdx.game.Personnage;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Animal extends Tamagotchi {

    private int life;
    private int food;
    private int hygiene;
    private int sleep;
    private int happiness;

    private final Random random = new Random();

    /**
     * Constructeur
     * initialise à 1000 tous les attributs sauf son porte-monnaie
     *
     * @param difficulty Niveau de difficulty
     */
    public Animal(int difficulty) {
        super(difficulty);
        life = 1000;
        food = 1000;
        hygiene = 1000;
        sleep = 1000;
        happiness = 1000;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
        check();
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
        check();
    }

    public int getHygiene() {
        return hygiene;
    }

    public void setHygiene(int hygiene) {
        this.hygiene = hygiene;
        check();
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
        check();
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
        check();
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

    public void Afficher_Attribut() {
        System.out.println("vie : " + life + "\nfood : " + food + "\nhygiene: " + hygiene + "\nsleep: " + sleep + "\nbonheur : " + happiness + "\nwallet :" + this.getWallet());
    }

    /**
     * Méthode qui vérifie si les attributs sont < 0 et > 1000
     */
    public void check() {
        if (life < 0) {
            life = 0;
        }
        if (food < 0) {
            food = 0;
        }
        if (hygiene < 0) {
            hygiene = 0;
        }
        if (sleep < 0) {
            sleep = 0;
        }
        if (happiness < 0) {
            happiness = 0;
        }
        if (life > 1000) {
            life = 1000;
        }
        if (food > 1000) {
            food = 1000;
        }
        if (hygiene > 1000) {
            hygiene = 1000;
        }
        if (sleep > 1000) {
            sleep = 1000;
        }
        if (happiness > 1000) {
            happiness = 1000;
        }
    }


    /**
     * Travaille pendant 12 secondes pour gagner de l'argent
     * Modifie : wallet, bonheur, hygiene, sleep
     */
    public void work() {
        setWallet(getWallet() + 50);
        happiness -= getDifficulty() * 100;
        hygiene -= getDifficulty() * 43;
        sleep -= getDifficulty() * 60;
        check();
    }


    /**
     * buy an apple
     */
    public void buyApple() {
        Food f = new Apple();
        if (getWallet() >= f.getPrice()) {
            addBasket(f);
            setWallet(getWallet() - f.getPrice());
        }
    }

    /**
     * buy a golden apple
     */
    public void buyGoldenApple() {
        Food f = new GoldenApple();
        if (getWallet() >= f.getPrice()) {
            addBasket(f);
            setWallet(getWallet() - f.getPrice());
        }
    }

    /**
     * Supprime food du panier et y ajoute la valeur de point associer
     *
     * @param food apple or golden apple
     *             modifie : foods, bonheur
     */
    public void manger(String food) throws InterruptedException {
        for (int i = 0; i < getBasket().size(); i++) { //parcourt l'attribut panier

            Food food1 = getBasket().get(i); //el actu

            if (food.equals(food1.getName())) { //quand trouver

                removePanier(i); //on supprime l'élément dans la liste
                food += food1.getPoint();

                TimeUnit.SECONDS.sleep(5);

                if (food.equals("GoldenApple")) {
                    happiness += 75;
                }
                check();

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
        if (sleep <= 200) {
            setSleep(900 - getDifficulty() * 100); //som sommeil sera 800 ou 700  ou 600  en fonction de la difficulté
        } else {
            setSleep(1000); // sinon son sommeil va au max
        }

        check();
    }

    /**
     * Auteur : Arthur
     * description : methode dormir qui met votre jauge sommeil à max sauf si on a moins de 200 de sommeil
     * Modifie : hygiene, bonheur
     */
    public void wash() {
        happiness += 100;

        if (hygiene <= 200) {
            setHygiene(900 - getDifficulty() * 85);
        } else {
            setHygiene(1000);
        }

        check();
    }

    /**
     * Auteur : Arthur
     * descriptif : methode jouer qui rend + heureux le joueur, mais le salin
     * Modifie : bonheur, hygiene
     */
    public void play() {
        hygiene -= random.nextInt(75, 200);

        if (100 <= happiness && happiness <= 200) {
            setHappiness(900 - getDifficulty() * 100); //som bonheur sera 800 ou 700  ou 600  en fonction de la difficulté
        } else if (happiness < 100) {
            setHappiness(750 - getDifficulty() * 100); //som bonheur sera 600 ou 500  ou 400  en fonction de la difficulté
        } else {
            setHappiness(1000); // sinon son bonheur va au max
        }

        check();
    }
}
