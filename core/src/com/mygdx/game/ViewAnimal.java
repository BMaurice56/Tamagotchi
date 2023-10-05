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
    private final SpriteBatch batch = new SpriteBatch();

    // Stage qui gère les entrées utilisateurs (inputProcessor)
    private final Stage stage = new Stage(new ScreenViewport());

    private Texture livingRoom, kitchen, bathroom, garden;

    // Taille de la fenêtre
    private float screenWidth, screenHeight;

    // Table qui gère le placement des objets sur la fenêtre
    private Table livingRoomTable, kitchenTable, bathroomTable, gardenTable, settingsTable, informationTable;

    private ImageButton leftArrow, rightArrow, settings, heartImage, foodImage, sleepImage, soapImage, happyImage, appleImage, goldenAppleImage, moneyImage;

    private TextButton sleep, work, wash, eat, buy, play, settings2, home, resume;

    // Barres de progressions
    private ProgressBar life, food, sleeping, hygiene, happiness;

    private int money = 10000, apple = 300, goldenApple = 200, screen = 3, widthProgressbar = 100, heightProgressBar = 20;

    private Label moneyLabel, appleLabel, goldenAppleLabel;

    private Controller controller;


    /**
     * Constructeur
     */
    public ViewAnimal(Controller controller) {
        this.controller = controller;
        createButton();
        createTexture();
        createProgressBar();
        createLabel();
        createTable();
        ajoutListeners();

        float progressBarValue = 500f;

        life.setValue(200f);
        food.setValue(progressBarValue);
        sleeping.setValue(progressBarValue);
        hygiene.setValue(progressBarValue);
        happiness.setValue(progressBarValue);

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

        // Récupère les dimensions de la fenêtre
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Flèche de changement d'écran
        leftArrow.setPosition(5, screenHeight / 2);
        rightArrow.setPosition(screenWidth - 5 - rightArrow.getWidth(), screenHeight / 2);

        // Paramètre
        settings.setPosition(10, 10);

        // Taille des barres de progression
        widthProgressbar = (int) (screenWidth / 6);
        heightProgressBar = (int) (screenHeight / 30);

        life.setSize(widthProgressbar, heightProgressBar);
        food.setSize(widthProgressbar, heightProgressBar);
        sleeping.setSize(widthProgressbar, heightProgressBar);
        hygiene.setSize(widthProgressbar, heightProgressBar);
        happiness.setSize(widthProgressbar, heightProgressBar);

        // Position de la table d'information
        informationTable.setPosition(450, 450);

        // Position des tables
        livingRoomTable.setPosition(screenWidth - 200, 30);
        kitchenTable.setPosition(screenWidth - 175, 30);
        bathroomTable.setPosition(screenWidth - 150, 30);
        gardenTable.setPosition(screenWidth - 150, 30);
        informationTable.setPosition(160, screenHeight - 180);


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
        life = new ProgressBar(0f, 1000f, 1f, false, new ProgressBar.ProgressBarStyle());
        food = new ProgressBar(0f, 1000f, 1f, false, new ProgressBar.ProgressBarStyle());
        sleeping = new ProgressBar(0f, 1000f, 1f, false, new ProgressBar.ProgressBarStyle());
        hygiene = new ProgressBar(0f, 1000f, 1f, false, new ProgressBar.ProgressBarStyle());
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

        hygiene.getStyle().background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        hygiene.getStyle().knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        hygiene.getStyle().knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        happiness.getStyle().background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        happiness.getStyle().knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        happiness.getStyle().knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

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

        float widthImage = 50f, heightImage = 50f;

        informationTable = new Table();
        informationTable.add(heartImage).width(widthImage).height(heightImage);
        informationTable.add(life).row();

        informationTable.add(foodImage).width(widthImage).height(heightImage);
        informationTable.add(food).row();

        informationTable.add(sleepImage).width(widthImage).height(heightImage);
        informationTable.add(sleeping).row();

        informationTable.add(soapImage).width(widthImage).height(heightImage);
        informationTable.add(hygiene).row();

        informationTable.add(happyImage).width(widthImage).height(heightImage);
        informationTable.add(happiness).row();

        informationTable.add(moneyImage).width(widthImage).height(heightImage);
        informationTable.add(moneyLabel).row();

        informationTable.add(appleImage).width(widthImage).height(heightImage);
        informationTable.add(appleLabel);
        informationTable.add(goldenAppleImage).width(widthImage).height(heightImage);
        informationTable.add(goldenAppleLabel).row();
    }

    /**
     * Instancie les Labels
     */
    public void createLabel() {
        moneyLabel = new Label("", new MultiSkin("label"));
        appleLabel = new Label("", new MultiSkin("label"));
        goldenAppleLabel = new Label("", new MultiSkin("label"));

        setAmountLabel("money", money);
        setAmountLabel("apple", apple);
        setAmountLabel("goldenApple", goldenApple);
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
        stage.addActor(informationTable);
        stage.addActor(leftArrow);
        stage.addActor(rightArrow);
        stage.addActor(settings);
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