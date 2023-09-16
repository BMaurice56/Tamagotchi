package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Tamagotchi extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;

    float screenWidth;
    float screenHeight;

    Stage stage;

    TextButton textButton;

    @Override
    public void create() {

        // Définition de la police d'écriture
        BitmapFont font = new BitmapFont(Gdx.files.internal("font/font.fnt"));

        // Création d'un Skin pour définir le style du bouton
        Skin skin = new Skin(); // Vous pouvez personnaliser le style ici

        // Définition du style du bouton (TextButtonStyle)
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        skin.add("default", textButtonStyle); // Enregistrez le style sous le nom "default"
        textButtonStyle.font = font; // Définissez la police du texte ici

        // Création du bouton
        textButton = new TextButton("Appuyez sur le bouton", skin);

        textButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                updateButton(textButton, "Appuyez sur le bouton");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                updateButton(textButton, "Bouton Texte enfoncer");
                return true;
            }
        });

        // Création des objets et ajout de l'image de fond
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        background = new Texture("background.jpg");

        // Ajout du bouton en tant qu'acteur
        stage.addActor(textButton);

        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * Met à jour le texte du bouton
     *
     * @param bouton  textButton à mettre à jour
     * @param message nouveau texte
     */
    public void updateButton(TextButton bouton, String message) {
        bouton.setText(message);
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
        textButton.setPosition(screenWidth / 2 - textButton.getMinWidth() / 2, screenHeight / 2 - textButton.getMinHeight() / 2);

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
