package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CreateSkin extends Skin {
    ImageButton.ImageButtonStyle imageButtonStyle;

    public CreateSkin() {
        super();
        imageButtonStyle = new ImageButton.ImageButtonStyle();
        super.add("default", imageButtonStyle);
    }
}
