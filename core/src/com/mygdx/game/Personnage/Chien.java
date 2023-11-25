package com.mygdx.game.Personnage;

public class Chien extends Animal {
    public Chien() {
        super(1, "", 2);
    }

    public Chien(int difficulty, String nom) {
        super(difficulty, nom, 2);
    }
}
