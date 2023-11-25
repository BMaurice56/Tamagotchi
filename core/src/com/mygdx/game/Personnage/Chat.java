package com.mygdx.game.Personnage;

public class Chat extends Animal {
    public Chat() {
        super(1, "", 1);
    }

    public Chat(int difficulty, String nom) {
        super(difficulty, nom, 1);
    }
}
