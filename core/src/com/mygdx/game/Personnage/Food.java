package com.mygdx.game.Personnage;

public class Food {

    private String name;
    private int point;
    private final int price;

    public Food(String name,int point , int price){
        this.name=name;
        this.point=point;
        this.price=price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }
}
