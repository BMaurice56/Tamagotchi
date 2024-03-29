package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.Personnage.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Vue du jeu
 */
public class View implements Screen {
    // https://gamedev.stackexchange.com/questions/121316/what-is-the-difference-between-sprite-and-spritebatch-specifically-in-the-conte/121340
    private final SpriteBatch batch = new SpriteBatch();

    // Stage qui gère les entrées utilisateurs (inputProcessor)
    private final Stage stage = new Stage(new ScreenViewport());

    // Image de fond
    private Texture room1, room2, room3, room4;

    // Image de fond de pluie (jardin)
    private Animation<TextureRegion> room1Rain;

    // Stock les noms de fichiers selon le type de tamagotchi
    private HashMap<String, String> hashMapImageAndText;

    // Tamagotchi
    private final Tamagotchi tamagotchi;

    // Taille de la fenêtre
    private float screenWidth, screenHeight, elapsed;

    // Table qui gère le placement des objets sur la fenêtre
    private Table room1Table, room2Table, room3Table, room4Table, pauseTable, settingsTable, deathTamagotchi, ruleTable;

    // Image du jeu
    private ImageButton leftArrow, rightArrow, settings, image1, image2, image3, image4, image5, foodImage, extraFoodImage, moneyImage, buyEatFoodImage, buyEatExtraFoodImage, quitBuyEatMenu;

    // Image du tamagotchi
    private ImageButton tamagotchiImage;

    // Bouton texte d'action
    private TextButton sleep, work, wash, eat, buy, play, settings2, home, resume, goMenuDeath, backButton;

    // Barres de progressions
    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, waitingBar;

    // Style des barres de progressions
    private ProgressBar.ProgressBarStyle lifeProgressBarStyle, foodProgressBarStyle, sleepingProgressBarStyle, hygieneProgressBarStyle, happinessProgressBarStyle, waitingProgressBarStyle;

    // Numéro de l'écran et taille des barres
    private int screen, widthProgressbar, heightProgressBar;

    // Label
    private Label moneyLabel, foodLabel, extraFoodLabel, action, whichFood, death, priceFood, priceSuperFood, rule, listRule, goBackFromRule;

    // Controller de jeu
    private final Controller controller;

    // Différencie l'achat ou la consommation de nourriture
    private boolean eatOrBuy = false, previousWeather;

    // Active la pluie ou non
    private final AtomicBoolean flagPluie;

    // Son du tamagotchi
    private Music soundTamagotchi;

    // Musique du jeu
    private final Music gameMusique;

    // Gestion du son
    private final Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, new Skin(Gdx.files.internal("skin/uiskin.json")));


    /**
     * Constructeur
     *
     * @param controller Controller de jeu
     * @param tamagotchi Tamagotchi
     * @param flagPluie  Active la pluie
     */
    public View(Controller controller, Tamagotchi tamagotchi, AtomicBoolean flagPluie) {
        this.flagPluie = flagPluie;
        this.tamagotchi = tamagotchi;

        int skin = tamagotchi.getSkin();

        switch (tamagotchi.getClass().getName()) {
            case ("com.mygdx.game.Personnage.Chat"):
                tamagotchiImage = new BoutonImage(new MultiSkin("image"), "images/pixelCat" + skin + ".png", 500, 500);
                soundTamagotchi = Gdx.audio.newMusic(controller.getSoundFile("musics/catMeow.mp3"));
                break;

            case ("com.mygdx.game.Personnage.Chien"):
                tamagotchiImage = new BoutonImage(new MultiSkin("image"), "images/pixelDog" + skin + ".png", 500, 500);
                soundTamagotchi = Gdx.audio.newMusic(controller.getSoundFile("musics/dogBark.mp3"));
                break;

            case ("com.mygdx.game.Personnage.Dinosaure"):
                tamagotchiImage = new BoutonImage(new MultiSkin("image"), "images/pixelDinosaur" + skin + ".png", 500, 500);
                soundTamagotchi = Gdx.audio.newMusic(controller.getSoundFile("musics/raptorCall.mp3"));
                break;

            case ("com.mygdx.game.Personnage.Robot"):
                tamagotchiImage = new BoutonImage(new MultiSkin("image"), "images/pixelRobot" + skin + ".png", 500, 500);
                soundTamagotchi = Gdx.audio.newMusic(controller.getSoundFile("musics/R2D2.mp3"));
                break;
        }

        gameMusique = Gdx.audio.newMusic(controller.getSoundFile("musics/BeautifulSong.wav"));
        gameMusique.setLooping(true);

        volumeSlider.setValue(controller.getLevelSound());
        soundTamagotchi.setVolume(controller.getLevelSound());
        gameMusique.setVolume(controller.getLevelSound());

        gameMusique.play();

        // Met à jour les variables de taille de l'écran
        updateAttributScreenSizeProgressBar();

        // Définit le controller et instancie les différents éléments
        this.controller = controller;
        initializeHashmap();
        createButton();
        createTexture();
        createProgressBar();
        createLabel();
        createTable();
        addListeners();

        // Positionne les éléments
        posAndSizeElement();

        previousWeather = flagPluie.get();
        screen = tamagotchi.getNumeroSalle();

        // Affiche la table de jeu
        switch (screen) {
            case (1):
                if (previousWeather) {
                    putGameTable();
                } else {
                    putTable(room1Table);
                }
                leftArrow.setVisible(false);
                break;

            case (2):
                putTable(room2Table);
                break;

            case (3):
                putTable(room3Table);
                break;

            case (4):
                putTable(room4Table);
                rightArrow.setVisible(false);
                break;
        }

        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);
    }


    /**
     * Instancie les textures
     */
    public void createTexture() {
        room1 = new Texture(getImageOrTextFromTamagotchi("room1"));
        room2 = new Texture(getImageOrTextFromTamagotchi("room2"));
        room3 = new Texture(getImageOrTextFromTamagotchi("room3"));
        room4 = new Texture(getImageOrTextFromTamagotchi("room4"));

        room1Rain = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(getImageOrTextFromTamagotchi("room1Rain")).read());
    }

    /**
     * Instancie les boutons
     */
    public void createButton() {
        leftArrow = new BoutonImage(new MultiSkin("image"), "images/leftArrowWhite.png", 100, 75);
        rightArrow = new BoutonImage(new MultiSkin("image"), "images/rightArrowWhite.png", 100, 75);
        settings = new BoutonImage(new MultiSkin("image"), "images/settingsSmall.png", 70, 70);

        image1 = new BoutonImage(new MultiSkin("image"), getImageOrTextFromTamagotchi("image1"), 225, 225);
        image2 = new BoutonImage(new MultiSkin("image"), getImageOrTextFromTamagotchi("image2"), 225, 225);
        image3 = new BoutonImage(new MultiSkin("image"), getImageOrTextFromTamagotchi("image3"), 225, 225);
        image4 = new BoutonImage(new MultiSkin("image"), getImageOrTextFromTamagotchi("image4"), 225, 225);
        image5 = new BoutonImage(new MultiSkin("image"), getImageOrTextFromTamagotchi("image5"), 225, 225);

        foodImage = new BoutonImage(new MultiSkin("image"), getImageOrTextFromTamagotchi("foodImage"), 225, 225);
        extraFoodImage = new BoutonImage(new MultiSkin("image"), getImageOrTextFromTamagotchi("extraFoodImage"), 225, 225);
        buyEatFoodImage = new BoutonImage(new MultiSkin("image"), getImageOrTextFromTamagotchi("foodImage"), 225, 225);
        buyEatExtraFoodImage = new BoutonImage(new MultiSkin("image"), getImageOrTextFromTamagotchi("extraFoodImage"), 225, 225);
        moneyImage = new BoutonImage(new MultiSkin("image"), getImageOrTextFromTamagotchi("moneyImage"), 225, 225);

        quitBuyEatMenu = new BoutonImage(new MultiSkin("image"), "images/croix.png", 225, 225);

        home = new TextButton("Retour a l'accueil", new MultiSkin("text"));

        sleep = new TextButton(getImageOrTextFromTamagotchi("sleep"), new MultiSkin("text"));
        work = new TextButton(getImageOrTextFromTamagotchi("work"), new MultiSkin("text"));
        wash = new TextButton(getImageOrTextFromTamagotchi("wash"), new MultiSkin("text"));
        eat = new TextButton(getImageOrTextFromTamagotchi("eat"), new MultiSkin("text"));
        buy = new TextButton(getImageOrTextFromTamagotchi("buy"), new MultiSkin("text"));
        play = new TextButton(getImageOrTextFromTamagotchi("play"), new MultiSkin("text"));
        resume = new TextButton("Reprise", new MultiSkin("text"));
        settings2 = new TextButton("Settings", new MultiSkin("text"));
        goMenuDeath = new TextButton("Retour au centre", new MultiSkin("text"));
        backButton = new TextButton("Retour au menu", new MultiSkin("text"));
    }

    /**
     * Instancie les barres de progression
     */
    public void createProgressBar() {
        createStyleProgressBar();

        progressBar1 = new ProgressBar(0f, 1000f, 1f, false, lifeProgressBarStyle);
        progressBar2 = new ProgressBar(0f, 1000f, 1f, false, foodProgressBarStyle);
        progressBar3 = new ProgressBar(0f, 1000f, 1f, false, sleepingProgressBarStyle);
        progressBar4 = new ProgressBar(0f, 1000f, 1f, false, hygieneProgressBarStyle);
        progressBar5 = new ProgressBar(0f, 1000f, 1f, false, happinessProgressBarStyle);
        waitingBar = new ProgressBar(0f, 1000f, 1f, false, waitingProgressBarStyle);

        waitingBar.setVisible(false);
    }

    /**
     * Instancie les styles pour les barres de progression
     */
    public void createStyleProgressBar() {
        lifeProgressBarStyle = new ProgressBar.ProgressBarStyle();
        foodProgressBarStyle = new ProgressBar.ProgressBarStyle();
        sleepingProgressBarStyle = new ProgressBar.ProgressBarStyle();
        hygieneProgressBarStyle = new ProgressBar.ProgressBarStyle();
        happinessProgressBarStyle = new ProgressBar.ProgressBarStyle();
        waitingProgressBarStyle = new ProgressBar.ProgressBarStyle();

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
    }

    /**
     * Instancie les Tables
     */
    public void createTable() {
        pauseTable = new Table();
        pauseTable.setFillParent(true);

        pauseTable.add(resume).row();
        pauseTable.add(settings2).row();
        pauseTable.add(home).row();

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

        deathTamagotchi = new Table();
        deathTamagotchi.setFillParent(true);

        deathTamagotchi.add(death).row();
        deathTamagotchi.add(goMenuDeath).row();

        settingsTable = new Table();
        settingsTable.setFillParent(true);

        settingsTable.add(volumeSlider).row();
        settingsTable.add(rule).row();
        settingsTable.add(backButton).row();

        ruleTable = new Table();
        ruleTable.setFillParent(true);

        ruleTable.add(listRule).row();
        ruleTable.add(goBackFromRule).row();
    }

    /**
     * Instancie les Labels
     */
    public void createLabel() {
        moneyLabel = new Label("0", new MultiSkin("label"));
        foodLabel = new Label("0", new MultiSkin("label"));
        extraFoodLabel = new Label("0", new MultiSkin("label"));

        action = new Label("0", new MultiSkin("label"));
        action.setVisible(false);

        whichFood = new Label("Quelle nourriture voulez-vous ?", new MultiSkin("label"));
        death = new Label("    Votre Tamagotchi est mort.\nIl est important d'en prendre soin.\n", new MultiSkin("label"));

        rule = new Label("Regles du jeu", new MultiSkin("label"));
        listRule = new Label(controller.getRule(), new MultiSkin("label"));
        goBackFromRule = new Label("Retour en arriere", new MultiSkin("label"));

        if (tamagotchi instanceof Animal) {
            priceFood = new Label(Apple.price + "$", new MultiSkin("label"));
            priceSuperFood = new Label(GoldenApple.price + "$", new MultiSkin("label"));
        } else {
            priceFood = new Label(Oil.price + "$", new MultiSkin("label"));
            priceSuperFood = new Label(SuperOil.price + "$", new MultiSkin("label"));
        }

    }

    /**
     * Ajoutes les écouteurs des boutons
     */
    public void addListeners() {
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
                stage.addActor(pauseTable);
                controller.stopGame(true); // stop le jeu
                return true;
            }
        });

        settings2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                stage.addActor(settingsTable);
                return true;
            }
        });

        rule.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (screen) {
                    case (1):
                        room1 = ScreenMenu.darkenImage(room1);
                        break;

                    case (2):
                        room2 = ScreenMenu.darkenImage(room2);
                        break;

                    case (3):
                        room3 = ScreenMenu.darkenImage(room3);
                        break;

                    case (4):
                        room4 = ScreenMenu.darkenImage(room4);
                        break;
                }

                stage.clear();
                stage.addActor(ruleTable);

                return true;
            }
        });

        goBackFromRule.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (screen) {
                    case (1):
                        room1 = new Texture(getImageOrTextFromTamagotchi("room1"));
                        break;

                    case (2):
                        room2 = new Texture(getImageOrTextFromTamagotchi("room2"));
                        break;

                    case (3):
                        room3 = new Texture(getImageOrTextFromTamagotchi("room3"));
                        break;

                    case (4):
                        room4 = new Texture(getImageOrTextFromTamagotchi("room4"));
                        break;
                }

                stage.clear();
                stage.addActor(settingsTable);

                return true;
            }
        });

        resume.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftArrow.setVisible(true);
                rightArrow.setVisible(true);

                switch (screen) {
                    case (1):
                        leftArrow.setVisible(false);
                        if (flagPluie.get()) {
                            putGameTable();
                        } else {
                            putTable(room1Table);
                        }
                        break;
                    case (2):
                        putTable(room2Table);
                        break;
                    case (3):
                        putTable(room3Table);
                        break;
                    case (4):
                        putTable(room4Table);
                        rightArrow.setVisible(false);
                        break;
                }
                controller.startGame(); // Reprend le jeu
                return true;
            }
        });

        home.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soundTamagotchi.dispose();
                gameMusique.dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ScreenMenu(true, null));
                return true;
            }
        });

        goMenuDeath.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soundTamagotchi.dispose();
                gameMusique.dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ScreenMenu(true, null));
                return true;
            }
        });

        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                stage.addActor(pauseTable);
                return true;
            }
        });

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = volumeSlider.getValue();
                soundTamagotchi.setVolume(volume);
                gameMusique.setVolume(volume);
                controller.setLevelSound(volume);
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
                priceFood.setVisible(false);
                priceSuperFood.setVisible(false);

                // Empêche le changement d'écran lord du choix de nourriture
                leftArrow.setVisible(false);
                rightArrow.setVisible(false);

                buyOrEatScreen();
                return true;
            }
        });

        buy.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                eatOrBuy = true;
                priceFood.setVisible(true);
                priceSuperFood.setVisible(true);

                // Empêche le changement d'écran lord du choix de nourriture
                leftArrow.setVisible(false);
                rightArrow.setVisible(false);

                buyOrEatScreen();
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

        buyEatFoodImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (tamagotchi instanceof Animal) {
                    if (eatOrBuy) {
                        controller.buy("Apple");
                    } else {
                        controller.eat("Apple");
                    }
                } else {
                    if (eatOrBuy) {
                        controller.buy("Oil");
                    } else {
                        controller.eat("Oil");
                    }
                }

                return true;
            }
        });

        buyEatExtraFoodImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (tamagotchi instanceof Animal) {
                    if (eatOrBuy) {
                        controller.buy("GoldenApple");
                    } else {
                        controller.eat("GoldenApple");
                    }
                } else {
                    if (eatOrBuy) {
                        controller.buy("SuperOil");
                    } else {
                        controller.eat("SuperOil");
                    }
                }

                return true;
            }
        });

        quitBuyEatMenu.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                eatOrBuy = false;
                putTable(room2Table);
                leftArrow.setVisible(true);
                rightArrow.setVisible(true);
                return true;
            }
        });

        tamagotchiImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soundTamagotchi.play();

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
                if (flagPluie.get()) {
                    putGameTable();
                } else {
                    putTable(room1Table);
                }

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
     * Affiche à l'écran la possibilité d'achat ou d'alimentation du tamagotchi
     */
    public void buyOrEatScreen() {
        stage.clear();

        stage.addActor(settings);
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

        stage.addActor(whichFood);
        stage.addActor(quitBuyEatMenu);
        stage.addActor(buyEatFoodImage);
        stage.addActor(buyEatExtraFoodImage);
        stage.addActor(priceFood);
        stage.addActor(priceSuperFood);
    }

    /**
     * Méthode qui définit le montant des labels
     *
     * @param label  Label voulu
     * @param amount Montant voulu
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
            case "superOil":
                extraFoodLabel.setText(String.valueOf(amount));
                break;

        }
    }

    /**
     * Méthode qui définit le niveau des barres de progression
     *
     * @param progressBar Barre de progression voulu
     * @param amount      niveau voulu (0 <= amount <= 1000)
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
                case "update":
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
        float fontScale = screenHeight * (1f / 900f);
        float ajustementLabelFood = 1 / (fontScale / 5);

        // Correction de bug windows
        if (fontScale <= 0) {
            fontScale = 1;
        }

        moneyLabel.setFontScale(fontScale);
        foodLabel.setFontScale(fontScale);
        extraFoodLabel.setFontScale(fontScale);

        eat.getLabel().setFontScale(fontScale);
        buy.getLabel().setFontScale(fontScale);
        wash.getLabel().setFontScale(fontScale);
        play.getLabel().setFontScale(fontScale);
        work.getLabel().setFontScale(fontScale);
        sleep.getLabel().setFontScale(fontScale);
        resume.getLabel().setFontScale(fontScale);
        settings2.getLabel().setFontScale(fontScale);
        home.getLabel().setFontScale(fontScale);
        goMenuDeath.getLabel().setFontScale(fontScale);
        backButton.getLabel().setFontScale(fontScale);
        death.setFontScale(fontScale);

        rule.setFontScale(fontScale);
        listRule.setFontScale(fontScale / Modele.coefficientAffichageRegle);
        goBackFromRule.setFontScale(fontScale);

        action.setFontScale(fontScale);

        // Placement affichage nourriture
        whichFood.setFontScale(fontScale);
        priceFood.setFontScale(fontScale);
        priceSuperFood.setFontScale(fontScale);

        // Taille des barres de progression
        progressBar1.setSize(widthProgressbar, heightProgressBar);
        progressBar2.setSize(widthProgressbar, heightProgressBar);
        progressBar3.setSize(widthProgressbar, heightProgressBar);
        progressBar4.setSize(widthProgressbar, heightProgressBar);
        progressBar5.setSize(widthProgressbar, heightProgressBar);
        waitingBar.setSize(widthProgressbar, heightProgressBar);

        createStyleProgressBar();

        progressBar1.setStyle(lifeProgressBarStyle);
        progressBar2.setStyle(foodProgressBarStyle);
        progressBar3.setStyle(sleepingProgressBarStyle);
        progressBar4.setStyle(hygieneProgressBarStyle);
        progressBar5.setStyle(happinessProgressBarStyle);
        waitingBar.setStyle(waitingProgressBarStyle);

        // Taille des images
        float widthImage = screenHeight * (50f / 900f), heightImage = screenHeight * (50f / 900f);

        image1.setSize(widthImage, heightImage);
        image2.setSize(widthImage, heightImage);
        image3.setSize(widthImage, heightImage);
        image4.setSize(widthImage, heightImage);
        image5.setSize(widthImage, heightImage);
        moneyImage.setSize(widthImage, heightImage);
        foodImage.setSize(widthImage, heightImage);
        extraFoodImage.setSize(widthImage, heightImage);
        settings.setSize(widthImage, heightImage);

        // Tamagotchi
        float widthTamagotchi = screenHeight * (150f / 900f);
        float heightTamagotchi = screenHeight * (150f / 900f);

        tamagotchiImage.setSize(widthTamagotchi, heightTamagotchi);
        tamagotchiImage.setPosition(screenWidth / 2 - widthTamagotchi / 2, screenHeight * (60f / 900f));

        // Flèche de changement d'écran
        float adjustPositionArrow = 150;
        float arrowXSize = screenHeight * (115f / 900f);
        float arrowYSize = screenHeight * (86.25f / 900f);

        leftArrow.setSize(arrowXSize, arrowYSize);
        rightArrow.setSize(arrowXSize, arrowYSize);

        leftArrow.setPosition(5, screenHeight / 2 - (adjustPositionArrow - 50));
        rightArrow.setPosition(screenWidth - 5 - rightArrow.getWidth(), leftArrow.getY());

        float facteur = 0.03f;

        // Calculer le coefficient en fonction de la hauteur de la fenêtre
        // Utilisez pour les petites tailles d'écran
        float coefficient = 1 + facteur * (900f - screenHeight);

        // Position des tables
        room1Table.setPosition(screenWidth - room1Table.getMinWidth(), 35 - 1 * coefficient);
        room2Table.setPosition(screenWidth - room2Table.getMinWidth() + room2Table.getMinWidth() * (100f / 263), room1Table.getY());
        room3Table.setPosition(screenWidth - room3Table.getMinWidth() + room3Table.getMinWidth() * (100f / 300), room1Table.getY());
        room4Table.setPosition(screenWidth - room4Table.getMinWidth() + room4Table.getMinWidth() * (40f / 123), room1Table.getY());

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
        settings.setPosition(10, 10);

        progressBar1.setPosition(X + shiftX, Y + adjustProgressBar - shiftY);
        progressBar2.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 2);
        progressBar3.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 3);
        progressBar4.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 4);
        progressBar5.setPosition(X + shiftX, Y + adjustProgressBar - shiftY * 5);

        // Action du Tamagotchi
        action.setPosition(screenWidth / 2 - action.getMinWidth() / 2, screenHeight / 2 + (float) heightProgressBar / 2);
        waitingBar.setPosition(screenWidth / 2 - (float) widthProgressbar / 2, action.getY() - heightProgressBar * 1.3f);

        moneyLabel.setPosition(X + shiftX, Y - shiftY * 6 - (1 / (fontScale / 4)));
        foodLabel.setPosition(X + shiftX, Y - shiftY * 7 - ajustementLabelFood);
        extraFoodLabel.setPosition(X + shiftX * 2 + adjustGoldenApple, Y - shiftY * 7 - ajustementLabelFood);

        quitBuyEatMenu.setSize(screenWidth * 50f / 900f, screenHeight * 50f / 900f);

        float xSizeBuyEatImage = screenWidth * 70f / 900f;
        float ySizeBuyEatImage = screenHeight * 70f / 900f;

        buyEatFoodImage.setSize(xSizeBuyEatImage, ySizeBuyEatImage);
        buyEatExtraFoodImage.setSize(xSizeBuyEatImage, ySizeBuyEatImage);

        float middleX = screenWidth / 2;
        float middleY = screenHeight / 2;

        facteur = 0.023f;

        // Calculer le coefficient en fonction de la hauteur de la fenêtre
        // Utilisez pour les petites tailles d'écran
        coefficient = 1 + facteur * (900f - screenHeight);

        whichFood.setPosition(middleX - whichFood.getMinWidth() / 2f - 10f, middleY);
        quitBuyEatMenu.setPosition(whichFood.getX() + whichFood.getMinWidth(), whichFood.getY() + 1 * coefficient);

        buyEatFoodImage.setPosition(whichFood.getX(), whichFood.getY() - ySizeBuyEatImage + 1 * coefficient);
        buyEatExtraFoodImage.setPosition(buyEatFoodImage.getX() + whichFood.getMinWidth() / 1.5f, buyEatFoodImage.getY());

        priceFood.setPosition(buyEatFoodImage.getX() + xSizeBuyEatImage, buyEatFoodImage.getY() + ySizeBuyEatImage / 2 - priceFood.getMinHeight() / 2 - 1 * coefficient);
        priceSuperFood.setPosition(buyEatExtraFoodImage.getX() + xSizeBuyEatImage, priceFood.getY());

    }

    /**
     * Récupère la taille de la fenêtre et met à jour les attributs
     */
    public void updateAttributScreenSizeProgressBar() {
        // Récupère les dimensions de la fenêtre
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Taille des barres de progression
        widthProgressbar = (int) (screenWidth / 6);
        heightProgressBar = (int) (screenHeight / 30);
    }

    /**
     * Initialise le dictionnaire qui contient le répertoire des images selon le tamagotchi
     */
    public void initializeHashmap() {
        hashMapImageAndText = new HashMap<>();

        if (tamagotchi instanceof Animal) {
            hashMapImageAndText.put("image1", "images/heart.png");
            hashMapImageAndText.put("image2", "images/food.png");
            hashMapImageAndText.put("image3", "images/sleep.png");
            hashMapImageAndText.put("image4", "images/soap.png");

            hashMapImageAndText.put("foodImage", "images/apple.png");
            hashMapImageAndText.put("extraFoodImage", "images/goldenApple.png");

            hashMapImageAndText.put("room1", "images/garden.png");
            hashMapImageAndText.put("room1Rain", "images/gardenRain.gif");
            hashMapImageAndText.put("room2", "images/kitchen.jpg");
            hashMapImageAndText.put("room3", "images/livingRoom.jpg");
            hashMapImageAndText.put("room4", "images/bathroom.jpg");

            hashMapImageAndText.put("sleep", "Dormir  ");
            hashMapImageAndText.put("work", "Travailler");
            hashMapImageAndText.put("wash", "Se laver");
            hashMapImageAndText.put("eat", "Manger  ");
            hashMapImageAndText.put("buy", "Acheter");
            hashMapImageAndText.put("play", "Jouer");

        } else {
            hashMapImageAndText.put("image1", "images/battery.png");
            hashMapImageAndText.put("image2", "images/tank.png");
            hashMapImageAndText.put("image3", "images/durability.png");
            hashMapImageAndText.put("image4", "images/software.png");

            hashMapImageAndText.put("foodImage", "images/oil.png");
            hashMapImageAndText.put("extraFoodImage", "images/superOil.png");

            hashMapImageAndText.put("room1", "images/garden.png");
            hashMapImageAndText.put("room1Rain", "images/gardenRain.gif");
            hashMapImageAndText.put("room2", "images/gasStation.png");
            hashMapImageAndText.put("room3", "images/workshop.jpeg");
            hashMapImageAndText.put("room4", "images/server.png");

            hashMapImageAndText.put("sleep", "Maintenance  ");
            hashMapImageAndText.put("work", "Travailler");
            hashMapImageAndText.put("wash", "Mettre a jour");
            hashMapImageAndText.put("eat", "Remplir  ");
            hashMapImageAndText.put("buy", "Acheter");
            hashMapImageAndText.put("play", "Jouer");
        }

        hashMapImageAndText.put("image5", "images/happy.png");
        hashMapImageAndText.put("moneyImage", "images/money.png");
    }

    /**
     * Renvoie la texture ou le texte voulu selon la clef
     *
     * @param key clef
     * @return String chemin de la texture ou du texte
     */
    public String getImageOrTextFromTamagotchi(String key) {
        return hashMapImageAndText.get(key);
    }

    /**
     * Renvoie le numéro de l'écran où se trouve le tamagotchi
     *
     * @return int numéro
     */
    public int getNumberRoom() {
        return screen;
    }

    /**
     * Modifie l'affichage pour permettre l'attente de l'action en cours
     *
     * @param visibility boolean Modifie l'affichage
     * @param act        Action à afficher
     */
    public void actionTamagotchiChangeVisibility(boolean visibility, String act) {
        action.setText(act + " en cours");
        action.setX(screenWidth / 2 - action.getMinWidth() / 2);

        // Ajoute la barre et le label à l'affichage
        stage.addActor(waitingBar);
        stage.addActor(action);

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

        whichFood.setVisible(visibility);
        quitBuyEatMenu.setVisible(visibility);
        priceFood.setVisible(visibility);
        priceSuperFood.setVisible(visibility);
        buyEatFoodImage.setVisible(visibility);
        buyEatExtraFoodImage.setVisible(visibility);

        // Rend la barre visible
        waitingBar.setVisible(!visibility);
        action.setVisible(!visibility);

        // Si on remet l'affichage, il faut remettre la bonne table
        if (visibility) {
            switch (screen) {
                case (1):
                    putTable(room1Table);
                    leftArrow.setVisible(false);
                    break;

                case (2):
                    buyOrEatScreen();
                    // Empêche le changement d'écran lord du choix de nourriture
                    leftArrow.setVisible(false);
                    rightArrow.setVisible(false);
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
     * Affichage le message de la mort du tamagotchi
     */
    public void messageMortTamagotchi() {
        stage.clear();
        stage.addActor(deathTamagotchi);
    }

    /**
     * Met à jour l'affichage avec les bonnes valeurs du tamagotchi
     */
    public void updateAttributAffichage() {
        if (tamagotchi instanceof Animal) {
            setAmountProgressBar("life", controller.getDataTamagotchi("life"));
            setAmountProgressBar("food", controller.getDataTamagotchi("food"));
            setAmountProgressBar("sleep", controller.getDataTamagotchi("sleep"));
            setAmountProgressBar("wash", controller.getDataTamagotchi("wash"));
            setAmountProgressBar("happy", controller.getDataTamagotchi("happy"));

            setAmountLabel("apple", (int) controller.getDataTamagotchi("apple"));
            setAmountLabel("goldenApple", (int) controller.getDataTamagotchi("goldenApple"));
        } else {
            setAmountProgressBar("battery", controller.getDataTamagotchi("battery"));
            setAmountProgressBar("tank", controller.getDataTamagotchi("tank"));
            setAmountProgressBar("durability", controller.getDataTamagotchi("durability"));
            setAmountProgressBar("update", controller.getDataTamagotchi("update"));
            setAmountProgressBar("happy", controller.getDataTamagotchi("happy"));

            setAmountLabel("oil", (int) controller.getDataTamagotchi("oil"));
            setAmountLabel("superOil", (int) controller.getDataTamagotchi("superOil"));
        }

        setAmountProgressBar("waiting", controller.getDataTamagotchi("waiting"));
        setAmountLabel("money", (int) controller.getDataTamagotchi("money"));
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

        // Met à jour les barres de progression avec les attributs du Tamagotchi
        updateAttributAffichage();

        elapsed += Gdx.graphics.getDeltaTime();

        // Si passage de pluie à soleil et inversement, on place la bonne table
        if (screen == 1 && previousWeather != flagPluie.get()) {
            previousWeather = flagPluie.get();
            int numberActors = stage.getActors().size;

            // Si l'on se trouve dans les paramètres lors d'un changement d'état, on ne fait aucun changement
            if (numberActors == 3) {
                previousWeather = !flagPluie.get();
            } else if (previousWeather) {
                putGameTable();
            } else {
                putTable(room1Table);
            }
        }

        // Dessine l'image de fond
        batch.begin();
        switch (screen) {
            case 1:
                if (previousWeather) {
                    batch.draw(room1Rain.getKeyFrame(elapsed), 0, 0, screenWidth, screenHeight);
                } else {
                    batch.draw(room1, 0, 0, screenWidth, screenHeight);
                }
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
     * Méthode appelée quand la fenêtre est redimensionnée
     *
     * @param width  largeur
     * @param height hauteur
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        updateAttributScreenSizeProgressBar();

        // Met à jour la position des éléments
        posAndSizeElement();
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