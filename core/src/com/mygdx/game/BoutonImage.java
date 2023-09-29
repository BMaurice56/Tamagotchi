package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Classe qui permet d'instancier un bouton composé d'une image
 */
public class BoutonImage extends ImageButton {

    // Texture de l'image
    private final Texture imageTexture;

    /**
     * Constructeur
     *
     * @param skin   peau de l'image
     * @param image  chemin de l'image
     * @param width  largeur de l'image
     * @param height hauteur de l'image
     */
    public BoutonImage(Skin skin, String image, int width, int height) {
        // Constructeur de la classe mère
        super(skin);

        // Texture du bouton
        imageTexture = new Texture(Gdx.files.internal(image));

        // Définition de la taille et de la texture
        super.setSize(width, height);
        super.getStyle().imageUp = super.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(imageTexture));
    }

    /**
     * Assesseur de la texture du bouton
     *
     * @return texture
     */
    public Texture getImageTexture() {
        return imageTexture;
    }
}
