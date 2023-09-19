package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SelectTamagotchi implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final Texture background = new Texture("background.jpg");

    private float screenWidth, screenHeight;

    private final Stage stage = new Stage(new ScreenViewport());

    private final BoutonImage pixelcat, pixeldog, pixeldinosaur, pixelrobot, selectedImage;

    private final TextField nomTamagotchi = new TextField("Nom", new MultiSkin("textfield"));

    private final Table selectTable;

    private int tamagotchiselectionner;

    public SelectTamagotchi() {

        // Boutons de selection du Tamagotchi
        pixelcat = new BoutonImage(new MultiSkin("image"), "pixelcat.png", 920, 1104);
        pixeldog = new BoutonImage(new MultiSkin("image"), "pixeldog.png", 800, 723);
        pixeldinosaur = new BoutonImage(new MultiSkin("image"), "pixeldinosaur.png", 1240, 1240);
        pixelrobot = new BoutonImage(new MultiSkin("image"), "pixelrobot.png", 1200, 800);

        addButtonListeners();

        selectTable = new Table();
        selectTable.setFillParent(true);

        selectTable.add(pixelcat).width(200).height(200).left();
        selectTable.add(pixeldog).width(200).height(200).left().row();
        selectTable.add(pixeldinosaur).width(200).height(200).left();
        selectTable.add(pixelrobot).width(200).height(200).left().row();


        selectedImage = new BoutonImage(new MultiSkin("image"), "pixelcat.png", 920, 1104);
        selectedImage.setVisible(false); // Par défaut, l'acteur est invisible


        selectTable.add(selectedImage).width(200).height(200).right().row();
        Label labelnomTamagotchi = new Label("Nom du Tamagotchi : ", new MultiSkin("label"));
        selectTable.add(labelnomTamagotchi).right();
        selectTable.add(nomTamagotchi).right().row();


        stage.addActor(selectTable);
        //stage.addActor(selectedImage);


        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);
    }

    public void imageSelected(BoutonImage boutonImage) {
        stage.clear();

        // Copie la texture de boutonImage dans selectedImage
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(boutonImage.getImageTexture()));
        selectedImage.getStyle().imageUp = selectedImage.getStyle().imageDown = drawable;

        selectedImage.setVisible(true);

        selectTable.getCell(selectedImage).setActor(selectedImage);


        stage.addActor(selectTable);
        stage.draw();
    }

    public void addButtonListeners() {
        pixelcat.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageSelected(pixelcat); // Appel de la méthode avec le bouton sélectionné
                tamagotchiselectionner = 1;
                return true;
            }
        });

        pixeldog.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageSelected(pixeldog); // Appel de la méthode avec le bouton sélectionné
                tamagotchiselectionner = 2;
                return true;
            }
        });

        pixeldinosaur.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageSelected(pixeldinosaur); // Appel de la méthode avec le bouton sélectionné
                tamagotchiselectionner = 3;
                return true;
            }
        });

        pixelrobot.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageSelected(pixelrobot); // Appel de la méthode avec le bouton sélectionné
                tamagotchiselectionner = 4;
                return true;
            }
        });
    }


    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // Efface l'écran
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Récupère les dimensions de la fenêtre
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        //selectedImage.setPosition(screenWidth / 2 - 100, 100);
        //nameTable.setPosition(screenWidth / 2 - 400, 30);

        // Dessine l'image de fond
        batch.begin();
        batch.draw(background, 0, 0, screenWidth, screenHeight);
        batch.end();

        // Dessine le stage
        stage.draw();
    }

    /**
     * @param width  largeur de l'écran
     * @param height hauteur de l'écran
     * @see ApplicationListener#resize(int, int)
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
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

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

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}

