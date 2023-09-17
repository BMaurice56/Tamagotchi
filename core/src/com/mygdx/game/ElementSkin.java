package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ElementSkin extends Skin {

    /**
     * Constructeur
     *
     * @param type String type de button ("texte", "image", "slider");
     */
    public ElementSkin(String type) throws IllegalArgumentException {
        super();
        Object style;

        if ("texte".equals(type)) {
            // Police d'écriture
            BitmapFont font = new BitmapFont(Gdx.files.internal("font/font2.fnt"));

            // Définition du style du bouton (TextButtonStyle)
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = font; // Définissez la police du texte ici
            style = textButtonStyle; // Affectez le style au bouton texte

        } else if ("image".equals(type)) {
            style = new ImageButton.ImageButtonStyle(); // Affectez le style au bouton image

        } else if ("slider".equals(type)) {
            style = new Slider.SliderStyle(); // Affectez le style au slider

        } else {
            throw new IllegalArgumentException("Skin inconnu");
        }

        super.add("default", style);
    }

}
