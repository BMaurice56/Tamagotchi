package com.mygdx.game.Personnage;

public class Test {

    public static void main(String[] args) {

        Dinosaure dino = new Dinosaure();
        dino.setBonheur(19);
        dino.Afficher_Attribut();
        System.out.println("\n");
        dino.travailler();
        dino.Afficher_Attribut();
    }

}
