package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BoutonImage extends ImageButton {

    String image;

    Skin skin;

    int width;

    int height;

    public BoutonImage(Skin skin, String image, int width, int height) {
        super(skin);

        this.skin = skin;
        this.image = image;
        this.width = width;
        this.height = height;

        super.setSize(width, height);
        super.getStyle().imageUp = super.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(image))));

        super.addListener(new InputListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Press a Button");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Pressed Image Button");
                return true;
            }
        });
    }
}
