package com.mygdx.game.Personnage;

public abstract class Food {

    private final String name;
    private final int point;

    /**
     * Constructeur de la classe Food
     * @param name
     * @param point
     */
    public Food(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }
}
