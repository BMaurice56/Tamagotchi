package com.mygdx.game.Personnage;
import java.util.Random;
public abstract class Animal extends Tamagotchi{

    private int vie;
    private  int foods;
    private int hygiene;
    private int sleep;
    private int bonheur;


    public Animal(int difficulty){ //constructeur on initialize à 1000 tout les attribut sauf son porte mannai
        super(difficulty);
        this.vie=1000;
        this.foods=1000;
        this.hygiene=1000;
        this.sleep=1000;
        this.bonheur=1000;
    }

    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public int getFoods() {
        return foods;
    }

    public void setFoods(int foods) {
        this.foods = foods;
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

    public int getBonheur() {
        return bonheur;
    }

    public void setBonheur(int bonheur) {
        this.bonheur = bonheur;
    }

    public void Afficher_Attribut(){
        System.out.println("vie : "+vie +"\nfood : "+foods+"\nhygiene: "+hygiene+"\nsleep: "+sleep+"\nbonheur : "+bonheur+"\nwallet :"+this.getWallet());
    }

    /**
     * Auteur : Arthur
     * description : travaille pendant 12 secondes pour gagner des sous
     * @modif : wallet , bonheur , hygienne , sleep
     */
    public void travailler(){

        long temps = System.currentTimeMillis();
        while ((temps + 12_000) > System.currentTimeMillis()) {
            continue;
        }
        this.setWallet(this.getWallet()+50);
        bonheur-=getDifficulty()*100;
        hygiene-=getDifficulty()*43;
        sleep-=getDifficulty()*60;


        check();

    }

    public void check(){
        if(vie<0){
            vie=0;
        }
        if(foods<0){
            foods=0;
        }
        if(hygiene<0){
            hygiene=0;
        }
        if (sleep<0){
            sleep=0;
        }
        if (bonheur<0){
            bonheur=0;
        }
    }

    public void acheterApple(){
        int prix= 20;
        if(getWallet()>=prix){
            Food f = new Apple();
            addBasket(f);
            setWallet(getWallet()-prix);
        }
    }

    public void acheterGoldenApple(){
        int prix = 50;
        if(getWallet()>=prix){
            Food f = new GoldenApple();
            addBasket(f);
            setWallet(getWallet()-prix);
        }
    }

    /**
     *  Auteur : Arthur
     *  @param food qui prends apple ou golden apple
     *  description : suprimme food du panier et y ajoute la valeur de point associer
     * @modif change l'attribut foods , bonheur
     */
    public void manger(String food){
        for (int i = 0; i< getBasket().size(); i++){ //parcour l'attribut panier

            Food food1 = getBasket().get(i); //el actu

            if (food.equals(food1.getName())){ //quand trouver

                removePanier(i); //on suprimme l'element dans la liste
                foods+=food1.getPoint();
                long temps = System.currentTimeMillis();
                while ((temps + 5_000) > System.currentTimeMillis()) { //on attends 5 seconde
                    continue;
                }
                if (food.equals("GoldenApple")){
                    bonheur+=75;
                }
                if (bonheur>1000){
                    setBonheur(1000);
                }
                return; //on par de la methode
            }
        }
    }


    /**
     * Auteur : Arthur
     * description : methode dormir qui met votre jauge sommeil à max sauf si on a moins de 200 de sommeil
     * @modif : attribut sleep
     */
    public void dormir() {

        long temps = System.currentTimeMillis();
        Random random = new Random();

        int interval = random.nextInt(3001); //nbr entre 0 et 3000 inclus
        while ((temps + 15_000 + interval) > System.currentTimeMillis()) { //on attend entre 15 et 18 secondes
            continue;
        }
        if (sleep<=200){ //si sleep est entre 200 incul et 0
            setSleep(900-getDifficulty()*100); //som sommeil sera 800 ou 700  ou 600  en fonction de la difficulté
        }else {
            setSleep(1000); // sinon son sommeil va au max
        }
    }

    /**
     * Auteur : Arthur
     * description : methode dormir qui met votre jauge sommeil à max sauf si on a moins de 200 de sommeil
     * @modif : attribut hygiene , bonheur
     */
    public void laver(){
        long temps = System.currentTimeMillis();
        Random random = new Random();

        int interval = random.nextInt(5501); //nbr entre 0 et 5000 inclus
        while ((temps + 12_500 + interval) > System.currentTimeMillis()) { //on attend entre 12.5 et 18 secondes
            continue;
        }

        bonheur+=100;
        if(bonheur>1000){ //se laver le detend et augmente son bonheure
            setBonheur(1000);
        }

        if (hygiene<=200){ //si hygiene est entre 200 incul et 0
            setHygiene(900-getDifficulty()*85); //som hygiene sera 815 ou 730  ou 645  en fonction de la difficulté
        }else {
            setHygiene(1000); // sinon son hygiene va au max
        }
    }

    /**
     * Auteur : Arthur
     * descriptif : methode jouer qui rend + heureux le joueur mais le salit
     * @modif : bonheur , hygiene
     */
    public void jouer(){
        long temps = System.currentTimeMillis();
        Random random = new Random();

        int interval = random.nextInt(5501); //nbr entre 0 et 5000 inclus
        while ((temps + 10_000 + interval) > System.currentTimeMillis()) { //on attend entre 12.5 et 18 secondes
            continue;
        }

        hygiene -= random.nextInt(75,200);
        check();
        if (100<=bonheur &&bonheur<=200 ){ //si bonheur est entre 200 incul et 0
            setBonheur(900-getDifficulty()*100); //som bonheur sera 800 ou 700  ou 600  en fonction de la difficulté
        } else if (bonheur<100) {
            setBonheur(750-getDifficulty()*100); //som bonheur sera 600 ou 500  ou 400  en fonction de la difficulté
        } else {
            setBonheur(1000); // sinon son bonheur va au max
        }


    }


 }
