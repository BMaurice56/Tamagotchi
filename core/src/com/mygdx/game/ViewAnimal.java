package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Vue du jeu
 */
public class ViewAnimal implements Screen {
    // https://gamedev.stackexchange.com/questions/121316/what-is-the-difference-between-sprite-and-spritebatch-specifically-in-the-conte/121340
    private SpriteBatch batch = new SpriteBatch();

    // Stage qui gère les entrées utilisateurs (inputProcessor)
    private final Stage stage = new Stage(new ScreenViewport());

    private Texture livingRoom, kitchen, bathroom, garden;

    // Taille de la fenêtre
    private float screenWidth, screenHeight;

    // Table qui gère le placement des objets sur la fenêtre
    private Table livingRoomTable, kitchenTable, bathroomTable, gardenTable;

    private ImageButton leftArrow, rightArrow, settings;

    private TextButton sleep, work, wash, eat, buy, play, settings2, home, resume;

    // Barres de progressions
    private ProgressBar life, food, sleeping, washing, happiness;

    private int money, apple, goldenApple, screen = 3, widthProgressbar = 100, heightProgressBar = 20;


    /**
     * Constructeur
     */
    public ViewAnimal() {

        createTexture();
        createButton();
        createProgressBar();
        ajoutListeners();

        float progressBarValue = 500f;

        life.setValue(200f);
        food.setValue(progressBarValue);
        sleeping.setValue(progressBarValue);
        washing.setValue(progressBarValue);
        happiness.setValue(progressBarValue);

        stage.addActor(life);
        stage.addActor(food);
        stage.addActor(sleeping);
        stage.addActor(washing);
        stage.addActor(happiness);
        stage.addActor(leftArrow);
        stage.addActor(rightArrow);


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

        // Flèche de changement d'écran
        leftArrow.setPosition(5, screenHeight / 2);
        rightArrow.setPosition(screenWidth - 5 - rightArrow.getWidth(), screenHeight / 2);


        // Taille des barres de progression
        widthProgressbar = (int) (screenWidth / 6);
        heightProgressBar = (int) (screenHeight / 30);

        life.setSize(widthProgressbar, heightProgressBar);
        food.setSize(widthProgressbar, heightProgressBar);
        sleeping.setSize(widthProgressbar, heightProgressBar);
        washing.setSize(widthProgressbar, heightProgressBar);
        happiness.setSize(widthProgressbar, heightProgressBar);


        // Position des barres de progressions
        float shift = -heightProgressBar - 10f;
        float X = 10f;
        float Y = screenHeight;

        life.setPosition(X, Y + shift);
        food.setPosition(X, Y + shift * 2);
        sleeping.setPosition(X, Y + shift * 3);
        washing.setPosition(X, Y + shift * 4);
        happiness.setPosition(X, Y + shift * 5);

        // Dessine l'image de fond
        batch.begin();
        switch (screen) {
            case 1:
                batch.draw(garden, 0, 0, screenWidth, screenHeight);
                break;
            case 2:
                batch.draw(kitchen, 0, 0, screenWidth, screenHeight);
                break;
            case 3:
                batch.draw(livingRoom, 0, 0, screenWidth, screenHeight);
                break;
            case 4:
                batch.draw(bathroom, 0, 0, screenWidth, screenHeight);
                break;
            // Ajoutez d'autres cas pour d'autres images de fond si nécessaire
        }
        batch.end();

        // Dessine le stage
        stage.draw();
    }

    /**
     * Méthode appelée quand la fenêtre est redimensionnée
     *
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
        batch.dispose();
        stage.dispose();
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

    /**
     * Instancie les boutons
     */
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

    /**
     * Instancie les barres de progression
     */
    public void createProgressBar() {

        life = new ProgressBar(0f, 1000f, 1f, false, new ProgressBar.ProgressBarStyle());
        food = new ProgressBar(0f, 1000f, 1f, false, new ProgressBar.ProgressBarStyle());
        sleeping = new ProgressBar(0f, 1000f, 1f, false, new ProgressBar.ProgressBarStyle());
        washing = new ProgressBar(0f, 1000f, 1f, false, new ProgressBar.ProgressBarStyle());
        happiness = new ProgressBar(0f, 1000f, 1f, false, new ProgressBar.ProgressBarStyle());

        life.getStyle().background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        life.getStyle().knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        life.getStyle().knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        food.getStyle().background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        food.getStyle().knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        food.getStyle().knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        sleeping.getStyle().background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        sleeping.getStyle().knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        sleeping.getStyle().knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        washing.getStyle().background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        washing.getStyle().knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        washing.getStyle().knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        happiness.getStyle().background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        happiness.getStyle().knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        happiness.getStyle().knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

    }

    /**
     * Ajoutes les écouteurs des boutons
     */
    public void ajoutListeners() {
        leftArrow.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftArrowClick();
                return true;
            }
        });

        rightArrow.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightArrowClick();
                return true;
            }
        });
    }

    /**
     * Action de la flèche gauche
     */
    public void leftArrowClick() {
        screen -= 1;
        switch (screen) {
            case (1):
                leftArrow.setVisible(false);
                break;
            case (3):
                rightArrow.setVisible(true);
                break;
        }
    }

    /**
     * Action de la flèche droite
     */
    public void rightArrowClick() {
        screen += 1;
        switch (screen) {
            case (2):
                leftArrow.setVisible(true);
                break;
            case (4):
                rightArrow.setVisible(false);
                break;
        }
    }
}
