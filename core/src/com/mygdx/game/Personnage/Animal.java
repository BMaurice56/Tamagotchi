package com.mygdx.game.Personnage;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Animal extends Tamagotchi {

    private int life;
    private int food;
    private int hygiene;
    private int sleep;
    private int happiness;

    Random random;

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

        random = new Random();
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        if (life>1000){
            this.life = 1000;
        }else {
            this.life = life;
        }

    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        if (food>1000){
            this.food = 1000;
        }
        this.food = food;
    }

    public int getHygiene() {
        return hygiene;
    }

    public void setHygiene(int hygiene) {
        this.hygiene = hygiene;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
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
     * Travaille pendant 12 secondes pour gagner de l'argent
     *
     * @modif : wallet , bonheur , hygiene , sleep
     */
    public void travailler() throws InterruptedException {

        TimeUnit.SECONDS.sleep(12);

        setWallet(getWallet() + 50);
        happiness -= getDifficulty() * 100;
        hygiene -= getDifficulty() * 43;
        sleep -= getDifficulty() * 60;


        check();

    }

    /**
     * Méthode qui vérifie si les attributs sont < 0
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
        if (food>1000){
            food=1000;
        }
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
     * @modif change l'attribut foods , bonheur
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
                if (happiness > 1000) {
                    setHappiness(1000);
                }
                check();
            }
        }
    }


    /**
     * Auteur : Arthur
     * description : methode dormir qui met votre jauge sommeil à max sauf si on a moins de 200 de sommeil
     *
     * @modif : attribut sleep
     */
    public void dormir() throws InterruptedException {

        int interval = random.nextInt(4); //nbr entre 0 et 3

        TimeUnit.SECONDS.sleep(15 + interval);

        if (sleep <= 200) {
            setSleep(900 - getDifficulty() * 100); //som sommeil sera 800 ou 700  ou 600  en fonction de la difficulté
        } else {
            setSleep(1000); // sinon son sommeil va au max
        }
    }

    /**
     * Auteur : Arthur
     * description : methode dormir qui met votre jauge sommeil à max sauf si on a moins de 200 de sommeil
     *
     * @modif : attribut hygiene , bonheur
     */
    public void laver() throws InterruptedException {

        int interval = random.nextInt(6); //nbr entre 0 et 5000 inclus

        TimeUnit.SECONDS.sleep(12 + interval);

        happiness += 100;
        if (happiness > 1000) { //se laver le détend et augmente son bonheur
            setHappiness(1000);
        }

        if (hygiene <= 200) {
            setHygiene(900 - getDifficulty() * 85);
        } else {
            setHygiene(1000);
        }
    }

    /**
     * Auteur : Arthur
     * descriptif : methode jouer qui rend + heureux le joueur, mais le salit
     *
     * @modif : bonheur , hygiene
     */
    public void jouer() throws InterruptedException {

        int interval = random.nextInt(6); //nbr entre 0 et 5 inclus

        TimeUnit.SECONDS.sleep(10 + interval);

        hygiene -= random.nextInt(75, 200);

        check();

        if (100 <= happiness && happiness <= 200) {
            setHappiness(900 - getDifficulty() * 100); //som bonheur sera 800 ou 700  ou 600  en fonction de la difficulté
        } else if (happiness < 100) {
            setHappiness(750 - getDifficulty() * 100); //som bonheur sera 600 ou 500  ou 400  en fonction de la difficulté
        } else {
            setHappiness(1000); // sinon son bonheur va au max
        }


    }

}
