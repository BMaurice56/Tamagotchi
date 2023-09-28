package com.mygdx.game.Personnage;

public class Food {
    private String name;
    private int point;

    public Food(String name,int point){
        this.name=name;
        this.point=point;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }
}
