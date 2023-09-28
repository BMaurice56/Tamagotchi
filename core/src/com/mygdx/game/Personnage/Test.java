package com.mygdx.game.Personnage;

public class Test {

    public static void main(String[] args) {

        Dinosaure dino = new Dinosaure(1);
        dino.setWallet(100);
        dino.setFoods(700);
        dino.setBonheur(650);
        dino.setSleep(201);

        dino.Afficher_Attribut();



        dino.dormir();
        System.out.println("\n");
        dino.Afficher_Attribut();
    }

}
