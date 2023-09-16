package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {
    private SpriteBatch batch;
    private Texture background;

    private float screenWidth, screenHeight;

    private Stage stage;

    private BoutonImage playbutton, settingsbutton;

    private TextButton newgamebutton, savegamebutton;

    public MenuScreen() {
        // Accueil
        playbutton = new BoutonImage(new ImageButtonSkin(), "play.png", 200, 50);
        settingsbutton = new BoutonImage(new ImageButtonSkin(), "settings.png", 200, 50);

        // Gestion de la partie
        newgamebutton = new TextButton("Nouvelle partie", new TextButtonSkin());
        savegamebutton = new TextButton("partie sauvegardée", new TextButtonSkin());

        playbutton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                stage = new Stage(new ScreenViewport());
                stage.addActor(newgamebutton);
                stage.addActor(savegamebutton);
                Gdx.input.setInputProcessor(stage);
                return true;
            }
        });

        newgamebutton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage = new Stage(new ScreenViewport());
                stage.addActor(playbutton);
                stage.addActor(settingsbutton);
                Gdx.input.setInputProcessor(stage);
                return true;
            }

        });

        // Création des objets et ajout de l'image de fond
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        background = new Texture("background.jpg");

        // Ajout du bouton en tant qu'acteur
        stage.addActor(playbutton);
        stage.addActor(settingsbutton);

        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

    }

    // Gère le rendu de l'affichage
    @Override
    public void render(float delta) {
        // Efface l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Récupère les dimensions de la fenêtre
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Définie le placement du bouton
        playbutton.setPosition(screenWidth / 2 - playbutton.getMinWidth() / 2, screenHeight / 2 - playbutton.getMinHeight() / 2 + playbutton.getHeight() / 2);
        settingsbutton.setPosition(screenWidth / 2 - settingsbutton.getMinWidth() / 2, screenHeight / 2 - settingsbutton.getMinHeight() / 2 - settingsbutton.getHeight() / 2);

        newgamebutton.setPosition(screenWidth / 2 - newgamebutton.getMinWidth() / 2, screenHeight / 2 - newgamebutton.getMinHeight() / 2 + newgamebutton.getHeight() / 2);
        savegamebutton.setPosition(screenWidth / 2 - savegamebutton.getMinWidth() / 2, screenHeight / 2 - savegamebutton.getMinHeight() / 2 - savegamebutton.getHeight() / 2);

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

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }
}
