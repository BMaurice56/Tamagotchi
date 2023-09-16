package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Tamagotchi extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;

    float screenWidth, screenHeight;

    Stage stage;

    BoutonImage boutonImage, boutonImage2;

    @Override
    public void create() {

        boutonImage = new BoutonImage(new CreateSkin(), "play.png", 200, 50);
        boutonImage2 = new BoutonImage(new CreateSkin(), "settings.png", 200, 50);

        // Création des objets et ajout de l'image de fond
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        background = new Texture("background.jpg");

        // Ajout du bouton en tant qu'acteur
        stage.addActor(boutonImage);
        stage.addActor(boutonImage2);

        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);

    }

    // Gère le rendu de l'affichage
    @Override
    public void render() {
        // Efface l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Récupère les dimensions de la fenêtre
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Définie le placement du bouton
        boutonImage.setPosition(screenWidth / 2 - boutonImage.getMinWidth() / 2, screenHeight / 2 - boutonImage.getMinHeight() / 2 + 25);
        boutonImage2.setPosition(screenWidth / 2 - boutonImage2.getMinWidth() / 2, screenHeight / 2 - boutonImage2.getMinHeight() / 2 - 25);

        // Dessine l'image de fond
        batch.begin();
        batch.draw(background, 0, 0, screenWidth, screenHeight);
        batch.end();

        // Dessine le stage
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        stage.dispose();
    }

    /**
     * Méthode appelée lorsque la fenêtre est redimensionnée
     * Met à jour l'affichage
     *
     * @param width  la nouvelle largeur en pixels
     * @param height la nouvelle hauteur en pixels
     */
    @Override
    public void resize(int width, int height) {
        // Met à jour les nouvelles dimensions de la fenêtre
        screenWidth = width;
        screenHeight = height;

        // Met à jour la projection du SpriteBatch
        batch.getProjectionMatrix().setToOrtho2D(0, 0, screenWidth, screenHeight);

        // Appelle la méthode resize du Stage
        stage.getViewport().update(width, height, true);
    }
}
