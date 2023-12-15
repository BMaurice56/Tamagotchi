package com.mygdx.game.Personnage;

public class Chien extends Animal {
    public Chien() {
        super(1, "", 2, 1);
    }

    public Chien(int difficulty, String nom, int skin) {
        super(difficulty, nom, 2, skin);
    }
}
