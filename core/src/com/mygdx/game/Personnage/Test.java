package com.mygdx.game.Personnage;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        Chien PL = new Chien(1);
        PL.setWallet(300);
        PL.setFood(700);
        PL.setHappiness(99);
        PL.setSleep(201);
        PL.setHygiene(50);

        PL.Afficher_Attribut();


        PL.buyGoldenApple();
        PL.buyGoldenApple();
        PL.buyGoldenApple();
        PL.buyGoldenApple();
        PL.buyGoldenApple();

        System.out.println("panier "+PL.getBasket());
        PL.manger("GoldenApple");
        System.out.println("panier "+PL.getBasket());

        System.out.println("\n");
        PL.Afficher_Attribut();
    }

}