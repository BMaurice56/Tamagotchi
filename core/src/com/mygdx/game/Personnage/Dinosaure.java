package com.mygdx.game.Personnage;

public class Dinosaure extends Animal {

    public Dinosaure() {
        super(1, "", 3, 1);
    }

    public Dinosaure(int difficulty, String nom, int skin) {
        super(difficulty, nom, 3, skin);
    }


}
