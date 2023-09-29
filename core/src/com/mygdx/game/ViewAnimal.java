package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ViewAnimal implements Screen {

    private final SpriteBatch batch = new SpriteBatch();

    private final Stage stage = new Stage(new ScreenViewport());

    private Texture livingRoom, kitchen, bathroom, garden;

    private float screenWidth, screenHeight;

    private Table livingRoomTable, kitchenTable, bathroomTable, gardenTable;

    private ImageButton leftArrow, rightArrow, settings;

    private TextButton sleep, work, wash, eat, buy, play, settings2, home, resume;

    private ProgressBar life, food, sleeping, washing, happiness;

    private int money, apple, goldenApple;


    public ViewAnimal() {

        int width = 100;

        int height = 20;


        life = new ProgressBar(0f, 1000f, 1f, false, new ProgressBar.ProgressBarStyle());

        life.getStyle().background = Utils.getColoredDrawable(width, height, Color.RED);
        life.getStyle().knob = Utils.getColoredDrawable(0, height, Color.GREEN);
        life.getStyle().knobBefore = Utils.getColoredDrawable(width, height, Color.GREEN);

        life.setPosition(450, 450);
        life.setAnimateDuration(0.0f);
        life.setValue(200f);

        life.setSize(width, height);

        createTexture();
        createButton();

        stage.addActor(life);

        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);
    }


    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

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
        //batch.draw(livingRoom, 0, 0, screenWidth, screenHeight);
        batch.end();

        // Dessine le stage
        stage.draw();
    }

    /**
     * @param width
     * @param height
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

    /**
     * Instancie les textures
     */
    public void createTexture() {
        livingRoom = new Texture("images/livingRoom.jpg");
        kitchen = new Texture("images/kitchen.jpg");
        bathroom = new Texture("images/bathroom.jpg");
        garden = new Texture("images/garden.png");
    }

    public void createButton() {
        leftArrow = new BoutonImage(new MultiSkin("image"), "images/leftArrow.png", 100, 75);
        rightArrow = new BoutonImage(new MultiSkin("image"), "images/rightArrow.png", 100, 75);
        settings = new BoutonImage(new MultiSkin("image"), "images/settingsSmall.png", 70, 70);
        sleep = new TextButton("Dormir", new MultiSkin("text"));
        work = new TextButton("Travailler", new MultiSkin("text"));
        wash = new TextButton("Se laver", new MultiSkin("text"));
        eat = new TextButton("Manger", new MultiSkin("text"));
        buy = new TextButton("Acheter", new MultiSkin("text"));
        play = new TextButton("Jouer", new MultiSkin("text"));
        settings2 = new TextButton("Settings", new MultiSkin("text"));
        home = new TextButton("Retour a l'accueil", new MultiSkin("text"));
        resume = new TextButton("Reprise", new MultiSkin("text"));
    }

}
