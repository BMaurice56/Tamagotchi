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

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Vue du jeu
 */
public class ViewAnimal implements Screen {
    // https://gamedev.stackexchange.com/questions/121316/what-is-the-difference-between-sprite-and-spritebatch-specifically-in-the-conte/121340
    private final SpriteBatch batch = new SpriteBatch();

    // Stage qui gère les entrées utilisateurs (inputProcessor)
    private final Stage stage = new Stage(new ScreenViewport());

    private Texture livingRoom, kitchen, bathroom, garden;

    // Taille de la fenêtre
    private float screenWidth, screenHeight;

    // Table qui gère le placement des objets sur la fenêtre
    private Table livingRoomTable, kitchenTable, bathroomTable, gardenTable, settingsTable;

    private ImageButton leftArrow, rightArrow, settings, heartImage, foodImage, sleepImage, soapImage, happyImage, appleImage, goldenAppleImage, moneyImage;

    private TextButton sleep, work, wash, eat, buy, play, settings2, home, resume;

    // Barres de progressions
    private ProgressBar life, food, sleeping, hygiene, happiness;

    private int screen = 3, widthProgressbar, heightProgressBar, compteur;

    private Label moneyLabel, appleLabel, goldenAppleLabel;

    private final Controller controller;


    /**
     * Constructeur
     */
    public ViewAnimal(Controller controller) {
        // Récupère les dimensions de la fenêtre
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Taille des barres de progression
        widthProgressbar = (int) (screenWidth / 6);
        heightProgressBar = (int) (screenHeight / 30);

        // Définit le controller et instancie les différents éléments
        this.controller = controller;
        createButton();
        createTexture();
        createProgressBar();
        createLabel();
        createTable();
        ajoutListeners();

        // Positionne les éléments
        posAndSizeElement();

        // Affiche la table de jeu
        putTable(livingRoomTable);

        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);
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

        heartImage = new BoutonImage(new MultiSkin("image"), "images/heart.png", 225, 225);
        foodImage = new BoutonImage(new MultiSkin("image"), "images/food.png", 225, 225);
        sleepImage = new BoutonImage(new MultiSkin("image"), "images/sleep.png", 225, 225);
        soapImage = new BoutonImage(new MultiSkin("image"), "images/soap.png", 225, 225);
        happyImage = new BoutonImage(new MultiSkin("image"), "images/happy.png", 225, 225);

        appleImage = new BoutonImage(new MultiSkin("image"), "images/apple.png", 225, 225);
        goldenAppleImage = new BoutonImage(new MultiSkin("image"), "images/goldenApple.png", 225, 225);
        moneyImage = new BoutonImage(new MultiSkin("image"), "images/money.png", 225, 225);

        sleep = new TextButton("Dormir  ", new MultiSkin("text"));
        work = new TextButton("Travailler", new MultiSkin("text"));
        wash = new TextButton("Se laver", new MultiSkin("text"));
        eat = new TextButton("Manger  ", new MultiSkin("text"));
        buy = new TextButton("Acheter", new MultiSkin("text"));
        play = new TextButton("Jouer", new MultiSkin("text"));
        resume = new TextButton("Reprise", new MultiSkin("text"));
        settings2 = new TextButton("Settings", new MultiSkin("text"));
        home = new TextButton("Retour a l'accueil", new MultiSkin("text"));
    }

    /**
     * Instancie les barres de progression
     */
    public void createProgressBar() {
        ProgressBar.ProgressBarStyle lifeProgressBarStyle = new ProgressBar.ProgressBarStyle();
        ProgressBar.ProgressBarStyle foodProgressBarStyle = new ProgressBar.ProgressBarStyle();
        ProgressBar.ProgressBarStyle sleepingProgressBarStyle = new ProgressBar.ProgressBarStyle();
        ProgressBar.ProgressBarStyle hygieneProgressBarStyle = new ProgressBar.ProgressBarStyle();
        ProgressBar.ProgressBarStyle happinessProgressBarStyle = new ProgressBar.ProgressBarStyle();

        lifeProgressBarStyle.background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        lifeProgressBarStyle.knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        lifeProgressBarStyle.knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        foodProgressBarStyle.background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        foodProgressBarStyle.knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        foodProgressBarStyle.knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        sleepingProgressBarStyle.background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        sleepingProgressBarStyle.knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        sleepingProgressBarStyle.knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        hygieneProgressBarStyle.background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        hygieneProgressBarStyle.knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        hygieneProgressBarStyle.knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        happinessProgressBarStyle.background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        happinessProgressBarStyle.knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        happinessProgressBarStyle.knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        life = new ProgressBar(0f, 1000f, 1f, false, lifeProgressBarStyle);
        food = new ProgressBar(0f, 1000f, 1f, false, foodProgressBarStyle);
        sleeping = new ProgressBar(0f, 1000f, 1f, false, sleepingProgressBarStyle);
        hygiene = new ProgressBar(0f, 1000f, 1f, false, hygieneProgressBarStyle);
        happiness = new ProgressBar(0f, 1000f, 1f, false, happinessProgressBarStyle);
    }

    /**
     * Instancie les Tables
     */
    public void createTable() {
        settingsTable = new Table();
        settingsTable.setFillParent(true);

        settingsTable.add(resume).row();
        settingsTable.add(settings2).row();
        settingsTable.add(home).row();

        livingRoomTable = new Table();
        livingRoomTable.add(sleep);
        livingRoomTable.add(work).row();

        bathroomTable = new Table();
        bathroomTable.add(wash).row();

        kitchenTable = new Table();
        kitchenTable.add(eat);
        kitchenTable.add(buy).row();

        gardenTable = new Table();
        gardenTable.add(play).row();
    }

    /**
     * Instancie les Labels
     */
    public void createLabel() {
        moneyLabel = new Label("0", new MultiSkin("label"));
        appleLabel = new Label("0", new MultiSkin("label"));
        goldenAppleLabel = new Label("0", new MultiSkin("label"));
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

        settings.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                stage.addActor(settingsTable);
                return true;
            }
        });

        resume.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (screen) {
                    case (1):
                        putTable(gardenTable);
                        break;
                    case (2):
                        putTable(kitchenTable);
                        break;
                    case (3):
                        putTable(livingRoomTable);
                        break;
                    case (4):
                        putTable(bathroomTable);
                        break;
                }
                return true;
            }
        });

        home.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.stopGame();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ScreenMenu());
                return true;
            }
        });

        sleep.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.sleep();
                return true;
            }
        });

        work.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.work();
                return true;
            }
        });

        wash.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.wash();
                return true;
            }
        });


        eat.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.eat();
                return true;
            }
        });

        buy.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.buy();
                return true;
            }
        });

        play.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                controller.play();
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
                putTable(gardenTable);
                leftArrow.setVisible(false);
                break;

            case (2):
                putTable(kitchenTable);
                break;

            case (3):
                rightArrow.setVisible(true);
                putTable(livingRoomTable);
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
                putTable(kitchenTable);
                break;

            case (3):
                putTable(livingRoomTable);
                break;

            case (4):
                rightArrow.setVisible(false);
                putTable(bathroomTable);
                break;
        }
    }

    /**
     * Méthode qui affiche la table de jeu
     */
    public void putGameTable() {
        stage.clear();
        stage.addActor(leftArrow);
        stage.addActor(rightArrow);
        stage.addActor(settings);
        stage.addActor(life);
        stage.addActor(heartImage);
        stage.addActor(foodImage);
        stage.addActor(sleepImage);
        stage.addActor(soapImage);
        stage.addActor(happyImage);
        stage.addActor(moneyImage);
        stage.addActor(appleImage);
        stage.addActor(goldenAppleImage);

        stage.addActor(life);
        stage.addActor(food);
        stage.addActor(sleeping);
        stage.addActor(hygiene);
        stage.addActor(happiness);

        stage.addActor(moneyLabel);
        stage.addActor(appleLabel);
        stage.addActor(goldenAppleLabel);
    }

    /**
     * Met la table voulue sur l'écran
     *
     * @param table Table souhaitée
     */
    public void putTable(Table table) {
        putGameTable();
        stage.addActor(table);
    }

    /**
     * Méthode qui définit le montant des labels
     *
     * @param label  Label voulu
     * @param amount Montant voulu
     * @throws IllegalArgumentException Si label inconnu
     */
    public void setAmountLabel(String label, int amount) throws IllegalArgumentException {
        String value = amount + " ";

        switch (label) {
            case ("money"):
                moneyLabel.setText(value);
                break;
            case ("apple"):
                appleLabel.setText(value);
                break;
            case ("goldenApple"):
                goldenAppleLabel.setText(value);
                break;
            default:
                throw new IllegalArgumentException("Nom de label inconnu");
        }
    }

    /**
     * Méthode qui définit le niveau des barres de progression
     *
     * @param progressBar Barre de progression voulu
     * @param amount      niveau voulu
     * @throws IllegalArgumentException Si barre de progression inconnue
     */
    public void setAmountProgressBar(String progressBar, float amount) throws IllegalArgumentException {
        switch (progressBar) {
            case ("life"):
                life.setValue(amount);
                break;

            case ("food"):
                food.setValue(amount);
                break;

            case ("sleep"):
                sleeping.setValue(amount);
                break;

            case ("wash"):
                hygiene.setValue(amount);
                break;

            case ("happy"):
                happiness.setValue(amount);
                break;
            default:
                throw new IllegalArgumentException("Nom de barre de progression inconnu");
        }
    }

    /**
     * Place les éléments sur l'écran
     * Réajuste la taille de certains éléments selon la taille de l'écran
     */
    public void posAndSizeElement() {
        // Taille des barres de progression
        life.setSize(widthProgressbar, heightProgressBar);
        food.setSize(widthProgressbar, heightProgressBar);
        sleeping.setSize(widthProgressbar, heightProgressBar);
        hygiene.setSize(widthProgressbar, heightProgressBar);
        happiness.setSize(widthProgressbar, heightProgressBar);

        // Taille des images
        float widthImage = 50f, heightImage = 50f;

        heartImage.setSize(widthImage, heightImage);
        foodImage.setSize(widthImage, heightImage);
        sleepImage.setSize(widthImage, heightImage);
        soapImage.setSize(widthImage, heightImage);
        happyImage.setSize(widthImage, heightImage);
        moneyImage.setSize(widthImage, heightImage);
        appleImage.setSize(widthImage, heightImage);
        goldenAppleImage.setSize(widthImage, heightImage);

        // Flèche de changement d'écran

        float adjustPositionArrow = 150;

        leftArrow.setPosition(5, screenHeight / 2 - adjustPositionArrow);
        rightArrow.setPosition(screenWidth - 5 - rightArrow.getWidth(), screenHeight / 2 - adjustPositionArrow);

        // Paramètre
        settings.setPosition(10, 10);

        // Position des tables
        livingRoomTable.setPosition(screenWidth - 200, 30);
        kitchenTable.setPosition(screenWidth - 175, 30);
        bathroomTable.setPosition(screenWidth - 150, 30);
        gardenTable.setPosition(screenWidth - 150, 30);


        // Placement des images et des labels
        float X = 10;
        float Y = screenHeight;
        float shiftX = widthImage + 10;
        float shiftY = heightImage + 10;
        float adjustProgressBar = 9;
        float adjustGoldenApple = 70;

        heartImage.setPosition(X, Y - shiftY);
        foodImage.setPosition(X, Y - shiftY * 2);
        sleepImage.setPosition(X, Y - shiftY * 3);
        soapImage.setPosition(X, Y - shiftY * 4);
        happyImage.setPosition(X, Y - shiftY * 5);
        moneyImage.setPosition(X, Y - shiftY * 6);
        appleImage.setPosition(X, Y - shiftY * 7);
        goldenAppleImage.setPosition(X + shiftX + adjustGoldenApple, Y - shiftY * 7);

        life.setPosition(X + shiftX, Y + adjustProgressBar - shiftY);
        food.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 2);
        sleeping.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 3);
        hygiene.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 4);
        happiness.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 5);

        moneyLabel.setPosition(X + shiftX, Y - shiftY * 6);
        appleLabel.setPosition(X + shiftX, Y - shiftY * 7);
        goldenAppleLabel.setPosition(X + shiftX * 2 + adjustGoldenApple, Y - shiftY * 7);
    }

    /**
     * Méthode appelée quand la fenêtre est redimensionnée
     *
     * @param width  largeur
     * @param height hauteur
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        // Met à jour les nouvelles dimensions de la fenêtre
        screenWidth = width;
        screenHeight = height;

        // Taille des barres de progression
        widthProgressbar = (int) (screenWidth / 6);
        heightProgressBar = (int) (screenHeight / 30);

        if (heightProgressBar > 50) {
            heightProgressBar = 50;
        }

        // Met à jour la position des éléments
        posAndSizeElement();

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
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}