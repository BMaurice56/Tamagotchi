package com.mygdx.game.Personnage;

public class Test {

    public static void main(String[] args) {

        Dinausore dino = new Dinausore();
        dino.setBonheur(19);
        dino.Afficher_Attribut();
        System.out.println("\n");
        dino.travailler();
        dino.Afficher_Attribut();
    }

}
