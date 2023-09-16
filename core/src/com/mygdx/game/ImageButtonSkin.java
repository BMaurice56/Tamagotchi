package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ImageButtonSkin extends Skin {

    public ImageButtonSkin() {
        super();
        ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
        super.add("default", imageButtonStyle);
    }
}
