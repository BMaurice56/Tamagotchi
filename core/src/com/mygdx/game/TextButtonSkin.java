package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TextButtonSkin extends Skin {


    public TextButtonSkin() {
        super();
        BitmapFont font = new BitmapFont(Gdx.files.internal("font/font2.fnt"));

        // Définition du style du bouton (TextButtonStyle)
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font; // Définissez la police du texte ici

        super.add("default", textButtonStyle); // Enregistrez le style sous le nom "default"
    }
}
