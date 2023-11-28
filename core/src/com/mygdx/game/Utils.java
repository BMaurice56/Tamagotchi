package com.mygdx.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Utilities for GFX components.
 *
 * @author sboychen
 */
public class Utils {
    private Utils() {
    }

    /**
     * Crée une image avec la taille / couleur voulue
     *
     * @param width  longueur de l'image
     * @param height largeur de l'image
     * @param color  couleur de l'image
     * @return {@link Drawable} avec la taille et la couleur voulue
     */
    public static Drawable getColoredDrawable(int width, int height, Color color) {
        // Crée une pixelMap
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

        pixmap.dispose();

        return drawable;
    }
}
