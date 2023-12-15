package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Classe MultiSkin qui permet de créer une peau pour différents types de bouton ou de texte
 * évite la redondance de toutes ces étapes dans le code
 */
public class MultiSkin extends Skin {
    // Police d'écriture

    /**
     * Constructeur
     *
     * @param type String type de button ("text", "image", "slider", "label", "textfield");
     */
    public MultiSkin(String type) throws IllegalArgumentException {
        // Appelle du constructeur de la classe mère
        super();

        final BitmapFont font = new BitmapFont(Gdx.files.internal("font/font.fnt"));

        // Stock le style
        Object style;

        // Compare le type de skin voulu
        switch (type) {
            case ("text"):
                TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
                textButtonStyle.font = font;

                style = textButtonStyle;
                break;

            case ("image"):
                style = new ImageButton.ImageButtonStyle();
                break;

            case ("slider"):
                style = new Slider.SliderStyle();
                break;

            case ("label"):
                Label.LabelStyle labelStyle = new Label.LabelStyle();
                labelStyle.font = font;

                style = labelStyle;
                break;

            case ("textfield"):
                TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
                textFieldStyle.font = font;
                textFieldStyle.fontColor = new Color(1, 1, 1, 1);

                Label oneCharSizeCalibrationThrowAway = new Label("|", new MultiSkin("label"));
                Pixmap cursorColor = new Pixmap((int) oneCharSizeCalibrationThrowAway.getWidth(),
                        (int) oneCharSizeCalibrationThrowAway.getHeight(),
                        Pixmap.Format.RGB888);
                cursorColor.setColor(Color.WHITE);
                cursorColor.fill();

                textFieldStyle.cursor = new Image(new Texture(cursorColor)).getDrawable();

                style = textFieldStyle;
                break;

            default:
                throw new IllegalArgumentException("Skin inconnu");
        }


        super.add("default", style);
    }

}
