package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BoutonImage extends ImageButton {

    private final Texture imageTexture;

    public BoutonImage(Skin skin, String image, int width, int height) {
        super(skin);

        imageTexture = new Texture(Gdx.files.internal(image));

        super.setSize(width, height);
        super.getStyle().imageUp = super.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(imageTexture));

    }

    // Accesseur pour la texture de l'image
    public Texture getImageTexture() {
        return imageTexture;
    }
}
