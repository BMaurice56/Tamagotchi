package com.mygdx.game.Personnage;

public class Test {

    public static void main(String[] args) {

        Dinosaure dino = new Dinosaure();
        dino.setWallet(100);
        dino.Afficher_Attribut();

        System.out.println("\n");
        dino.acheterApple();
        dino.acheterGoldenApple();
        dino.Afficher_Attribut();
        System.out.println(dino.getPanier());
    }

}
