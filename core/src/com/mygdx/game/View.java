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
import com.mygdx.game.Personnage.Tamagotchi;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Vue du jeu
 */
public class View implements Screen {
    // https://gamedev.stackexchange.com/questions/121316/what-is-the-difference-between-sprite-and-spritebatch-specifically-in-the-conte/121340
    private final SpriteBatch batch = new SpriteBatch();

    // Stage qui gère les entrées utilisateurs (inputProcessor)
    private final Stage stage = new Stage(new ScreenViewport());

    private Texture room1, room2, room3, room4;

    private HashMap<String, String> hashMapTexture;

    private final Tamagotchi tamagotchi;

    // Taille de la fenêtre
    private float screenWidth, screenHeight;

    // Table qui gère le placement des objets sur la fenêtre
    private Table room1Table, room2Table, room3Table, room4Table, settingsTable;

    private ImageButton leftArrow, rightArrow, settings, image1, image2, image3, image4, image5, foodImage, extraFoodImage, moneyImage, tamagotchiImage;

    private TextButton sleep, work, wash, eat, buy, play, settings2, home, resume;

    // Barres de progressions
    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, waitingBar;

    private int screen = 3, widthProgressbar, heightProgressBar;

    private Label moneyLabel, foodLabel, extraFoodLabel;

    private final Controller controller;


    /**
     * Constructeur
     *
     * @param controller Controller de jeu
     * @param tamagotchi Tamagotchi
     */
    public View(Controller controller, Tamagotchi tamagotchi) {
        this.tamagotchi = tamagotchi;

        switch (tamagotchi.getClass().getName()) {
            case ("com.mygdx.game.Personnage.Chat"):
                tamagotchiImage = new BoutonImage(new MultiSkin("image"), "images/pixelCat.png", 920, 1104);
                break;

            case ("com.mygdx.game.Personnage.Chien"):
                tamagotchiImage = new BoutonImage(new MultiSkin("image"), "images/pixelDog.png", 800, 723);
                break;

            case ("com.mygdx.game.Personnage.Dinosaure"):
                tamagotchiImage = new BoutonImage(new MultiSkin("image"), "images/pixelDinosaur.png", 1240, 1240);
                break;

            case ("com.mygdx.game.Personnage.Robot"):
                tamagotchiImage = new BoutonImage(new MultiSkin("image"), "images/pixelRobot.png", 1200, 800);
                break;

            default:
                throw new IllegalArgumentException("Tamagotchi non reconnu");
        }

        // Met à jour les variables de taille de l'écran
        updateAttributScreenSize();

        // Définit le controller et instancie les différents éléments
        this.controller = controller;
        initializeHashmap();
        createButton();
        createTexture();
        createProgressBar();
        createLabel();
        createTable();
        ajoutListeners();

        // Positionne les éléments
        posAndSizeElement();

        // Affiche la table de jeu
        putTable(room3Table);

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
                batch.draw(room1, 0, 0, screenWidth, screenHeight);
                break;
            case 2:
                batch.draw(room2, 0, 0, screenWidth, screenHeight);
                break;
            case 3:
                batch.draw(room3, 0, 0, screenWidth, screenHeight);
                break;
            case 4:
                batch.draw(room4, 0, 0, screenWidth, screenHeight);
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
        room1 = new Texture(getImageFromTamagotchi("room1"));
        room2 = new Texture(getImageFromTamagotchi("room2"));
        room3 = new Texture(getImageFromTamagotchi("room3"));
        room4 = new Texture(getImageFromTamagotchi("room4"));
    }

    /**
     * Instancie les boutons
     */
    public void createButton() {
        leftArrow = new BoutonImage(new MultiSkin("image"), "images/leftArrow.png", 100, 75);
        rightArrow = new BoutonImage(new MultiSkin("image"), "images/rightArrow.png", 100, 75);
        settings = new BoutonImage(new MultiSkin("image"), "images/settingsSmall.png", 70, 70);

        image1 = new BoutonImage(new MultiSkin("image"), getImageFromTamagotchi("image1"), 225, 225);
        image2 = new BoutonImage(new MultiSkin("image"), getImageFromTamagotchi("image2"), 225, 225);
        image3 = new BoutonImage(new MultiSkin("image"), getImageFromTamagotchi("image3"), 225, 225);
        image4 = new BoutonImage(new MultiSkin("image"), getImageFromTamagotchi("image4"), 225, 225);
        image5 = new BoutonImage(new MultiSkin("image"), getImageFromTamagotchi("image5"), 225, 225);

        foodImage = new BoutonImage(new MultiSkin("image"), getImageFromTamagotchi("foodImage"), 225, 225);
        extraFoodImage = new BoutonImage(new MultiSkin("image"), getImageFromTamagotchi("extraFoodImage"), 225, 225);
        moneyImage = new BoutonImage(new MultiSkin("image"), getImageFromTamagotchi("moneyImage"), 225, 225);

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
        ProgressBar.ProgressBarStyle waitingProgressBarStyle = new ProgressBar.ProgressBarStyle();

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

        waitingProgressBarStyle.background = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.RED);
        waitingProgressBarStyle.knob = Utils.getColoredDrawable(0, heightProgressBar, Color.GREEN);
        waitingProgressBarStyle.knobBefore = Utils.getColoredDrawable(widthProgressbar, heightProgressBar, Color.GREEN);

        progressBar1 = new ProgressBar(0f, 1000f, 1f, false, lifeProgressBarStyle);
        progressBar2 = new ProgressBar(0f, 1000f, 1f, false, foodProgressBarStyle);
        progressBar3 = new ProgressBar(0f, 1000f, 1f, false, sleepingProgressBarStyle);
        progressBar4 = new ProgressBar(0f, 1000f, 1f, false, hygieneProgressBarStyle);
        progressBar5 = new ProgressBar(0f, 1000f, 1f, false, happinessProgressBarStyle);
        waitingBar = new ProgressBar(0f, 1000f, 1f, false, waitingProgressBarStyle);

        waitingBar.setVisible(false);
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

        room3Table = new Table();
        room3Table.add(sleep);
        room3Table.add(work).row();

        room4Table = new Table();
        room4Table.add(wash).row();

        room2Table = new Table();
        room2Table.add(eat);
        room2Table.add(buy).row();

        room1Table = new Table();
        room1Table.add(play).row();
    }

    /**
     * Instancie les Labels
     */
    public void createLabel() {
        moneyLabel = new Label("0", new MultiSkin("label"));
        foodLabel = new Label("0", new MultiSkin("label"));
        extraFoodLabel = new Label("0", new MultiSkin("label"));
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
                controller.stopGame(); // stop le jeu
                return true;
            }
        });

        resume.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (screen) {
                    case (1):
                        putTable(room1Table);
                        break;
                    case (2):
                        putTable(room2Table);
                        break;
                    case (3):
                        putTable(room3Table);
                        break;
                    case (4):
                        putTable(room4Table);
                        break;
                }
                controller.startGame(); // Reprend le jeu
                return true;
            }
        });

        home.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
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
                putTable(room1Table);
                leftArrow.setVisible(false);
                break;

            case (2):
                putTable(room2Table);
                break;

            case (3):
                rightArrow.setVisible(true);
                putTable(room3Table);
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
                putTable(room2Table);
                break;

            case (3):
                putTable(room3Table);
                break;

            case (4):
                rightArrow.setVisible(false);
                putTable(room4Table);
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
        stage.addActor(progressBar1);
        stage.addActor(image1);
        stage.addActor(image2);
        stage.addActor(image3);
        stage.addActor(image4);
        stage.addActor(image5);
        stage.addActor(moneyImage);
        stage.addActor(foodImage);
        stage.addActor(extraFoodImage);

        stage.addActor(progressBar1);
        stage.addActor(progressBar2);
        stage.addActor(progressBar3);
        stage.addActor(progressBar4);
        stage.addActor(progressBar5);

        stage.addActor(moneyLabel);
        stage.addActor(foodLabel);
        stage.addActor(extraFoodLabel);

        stage.addActor(tamagotchiImage);
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
    public void setAmountLabel(String label, int amount) {
        switch (label) {
            case "money":
                moneyLabel.setText(String.valueOf(amount));
                break;

            case "apple":
            case "oil":
                foodLabel.setText(String.valueOf(amount));
                break;

            case "goldenApple":
            case "extraOil":
                extraFoodLabel.setText(String.valueOf(amount));
                break;

        }
    }

    /**
     * Méthode qui définit le niveau des barres de progression
     *
     * @param progressBar Barre de progression voulu
     * @param amount      niveau voulu (0 <= amount <= 1000)
     * @throws IllegalArgumentException Si barre de progression inconnue
     */
    public void setAmountProgressBar(String progressBar, float amount) {
        if (amount >= 0 || amount <= 1000) {
            switch (progressBar) {
                case "life":
                case "battery":
                    progressBar1.setValue(amount);
                    break;

                case "food":
                case "tank":
                    progressBar2.setValue(amount);
                    break;


                case "sleep":
                case "durability":
                    progressBar3.setValue(amount);
                    break;

                case "wash":
                case "maintenance":
                    progressBar4.setValue(amount);
                    break;

                case "happy":
                    progressBar5.setValue(amount);
                    break;

                case "waiting":
                    waitingBar.setValue(amount);
                    break;
            }
        }
    }

    /**
     * Place les éléments sur l'écran
     * Réajuste la taille de certains éléments selon la taille de l'écran
     */
    public void posAndSizeElement() {
        // Taille des barres de progression
        progressBar1.setSize(widthProgressbar, heightProgressBar);
        progressBar2.setSize(widthProgressbar, heightProgressBar);
        progressBar3.setSize(widthProgressbar, heightProgressBar);
        progressBar4.setSize(widthProgressbar, heightProgressBar);
        progressBar5.setSize(widthProgressbar, heightProgressBar);
        waitingBar.setSize(widthProgressbar, heightProgressBar);

        // Taille des images
        float widthImage = 50f, heightImage = 50f;

        image1.setSize(widthImage, heightImage);
        image2.setSize(widthImage, heightImage);
        image3.setSize(widthImage, heightImage);
        image4.setSize(widthImage, heightImage);
        image5.setSize(widthImage, heightImage);
        moneyImage.setSize(widthImage, heightImage);
        foodImage.setSize(widthImage, heightImage);
        extraFoodImage.setSize(widthImage, heightImage);

        // Tamagotchi
        float widthTamagotchi = 150;
        float heightTamagotchi = 150;

        tamagotchiImage.setSize(widthTamagotchi, heightTamagotchi);
        tamagotchiImage.setPosition(screenWidth / 2 - widthTamagotchi / 2, 70);


        // Flèche de changement d'écran

        float adjustPositionArrow = 150;

        leftArrow.setPosition(5, screenHeight / 2 - adjustPositionArrow);
        rightArrow.setPosition(screenWidth - 5 - rightArrow.getWidth(), screenHeight / 2 - adjustPositionArrow);

        // Paramètre
        settings.setPosition(10, 10);

        // Position des tables
        room1Table.setPosition(screenWidth - 150, 30);
        room2Table.setPosition(screenWidth - 175, 30);
        room3Table.setPosition(screenWidth - 200, 30);
        room4Table.setPosition(screenWidth - 150, 30);


        // Placement des images et des labels
        float X = 10;
        float Y = screenHeight;
        float shiftX = widthImage + 10;
        float shiftY = heightImage + 10;
        float adjustProgressBar = 9;
        float adjustGoldenApple = 70;

        image1.setPosition(X, Y - shiftY);
        image2.setPosition(X, Y - shiftY * 2);
        image3.setPosition(X, Y - shiftY * 3);
        image4.setPosition(X, Y - shiftY * 4);
        image5.setPosition(X, Y - shiftY * 5);
        moneyImage.setPosition(X, Y - shiftY * 6);
        foodImage.setPosition(X, Y - shiftY * 7);
        extraFoodImage.setPosition(X + shiftX + adjustGoldenApple, Y - shiftY * 7);

        progressBar1.setPosition(X + shiftX, Y + adjustProgressBar - shiftY);
        progressBar2.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 2);
        progressBar3.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 3);
        progressBar4.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 4);
        progressBar5.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 5);

        waitingBar.setPosition(screenWidth / 2 - (float) widthProgressbar / 2, screenHeight / 2 - (float) heightProgressBar / 2);

        moneyLabel.setPosition(X + shiftX, Y - shiftY * 6);
        foodLabel.setPosition(X + shiftX, Y - shiftY * 7);
        extraFoodLabel.setPosition(X + shiftX * 2 + adjustGoldenApple, Y - shiftY * 7);
    }

    /**
     * Récupère la taille de la fenêtre et met à jour les attributs
     */
    public void updateAttributScreenSize() {
        // Récupère les dimensions de la fenêtre
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Taille des barres de progression
        widthProgressbar = (int) (screenWidth / 6);
        heightProgressBar = (int) (screenHeight / 30);

        if (heightProgressBar > 50) {
            heightProgressBar = 50;
        }
    }

    /**
     * Initialise le dictionnaire qui contient le répertoire des images selon le tamagotchi
     */
    public void initializeHashmap() {
        hashMapTexture = new HashMap<>();

        if (tamagotchi.getClass().getName().equals("com.mygdx.game.Personnage.Robot")) {
            hashMapTexture.put("image1", "images/battery.png");
            hashMapTexture.put("image2", "images/tank.png");
            hashMapTexture.put("image3", "images/durability.png");
            hashMapTexture.put("image4", "images/woolWrench.png");

            hashMapTexture.put("foodImage", "images/oil.png");
            hashMapTexture.put("extraFoodImage", "images/extraOil.png");

            hashMapTexture.put("room1", "images/garden.png");
            hashMapTexture.put("room2", "images/kitchen.jpg");
            hashMapTexture.put("room3", "images/livingRoom.jpg");
            hashMapTexture.put("room4", "images/bathroom.jpg");

        } else {
            hashMapTexture.put("image1", "images/heart.png");
            hashMapTexture.put("image2", "images/food.png");
            hashMapTexture.put("image3", "images/sleep.png");
            hashMapTexture.put("image4", "images/soap.png");

            hashMapTexture.put("foodImage", "images/apple.png");
            hashMapTexture.put("extraFoodImage", "images/goldenApple.png");

            hashMapTexture.put("room1", "images/garden.png");
            hashMapTexture.put("room2", "images/kitchen.jpg");
            hashMapTexture.put("room3", "images/livingRoom.jpg");
            hashMapTexture.put("room4", "images/bathroom.jpg");
        }

        hashMapTexture.put("image5", "images/happy.png");
        hashMapTexture.put("moneyImage", "images/money.png");
    }

    /**
     * Renvoie la texture voulu selon la clef
     *
     * @param key clef
     * @return String chemin de la texture
     */
    public String getImageFromTamagotchi(String key) {
        if (hashMapTexture.containsKey(key)) {
            return hashMapTexture.get(key);
        } else {
            throw new NoSuchElementException("Clef inconnu du dictionnaire");
        }

    }

    /**
     * Modifie l'affichage pour permettre l'attente de l'action en cours
     *
     * @param visibility boolean Modifie l'affichage
     */
    public void changeVisibilityWaitingBar(boolean visibility) {
        // Ajoute la barre à l'affichage
        stage.addActor(waitingBar);

        // Enlève les éléments inutiles de l'interface
        leftArrow.setVisible(visibility);
        rightArrow.setVisible(visibility);
        settings.setVisible(visibility);

        sleep.setVisible(visibility);
        work.setVisible(visibility);
        wash.setVisible(visibility);
        eat.setVisible(visibility);
        buy.setVisible(visibility);
        play.setVisible(visibility);

        tamagotchiImage.setVisible(visibility);

        // Rend la barre visible
        waitingBar.setVisible(!visibility);

        // Si on remet l'affichage, il faut remettre la bonne table
        if (visibility) {
            switch (screen) {
                case (1):
                    putTable(room1Table);
                    leftArrow.setVisible(false);
                    break;

                case (2):
                    putTable(room2Table);
                    break;

                case (3):
                    putTable(room3Table);
                    break;

                case (4):
                    rightArrow.setVisible(false);
                    putTable(room4Table);
                    break;
            }

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
        updateAttributScreenSize();

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