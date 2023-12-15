package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe qui permet de sélectionner le Tamagotchi
 */
public class SelectTamagotchi implements Screen {
    // https://gamedev.stackexchange.com/questions/121316/what-is-the-difference-between-sprite-and-spritebatch-specifically-in-the-conte/121340
    private final SpriteBatch batch = new SpriteBatch();

    // Arrière-plan
    private final Texture background = new Texture("images/background.jpg");

    // Taille de la fenêtre
    private float screenWidth, screenHeight;

    // Stage qui gère les entrées utilisateurs (inputProcessor)
    private final Stage stage = new Stage(new ScreenViewport());

    private BoutonImage pixelCat, pixelDog, pixelDinosaur, pixelRobot, tamagotchiSelected, leftArrow, rightArrow;

    private TextButton playButton, backButton, retour;

    // Zone de texte
    private final TextField nomTamagotchi = new TextField("Nom", new MultiSkin("textfield"));

    private Label labelLevelDifficult, labelTamagotchiSelection, labelNomTamagotchi, labelDifficulty, message;

    private int tamagotchiSelection = 1, difficultyLevel = 1, skin = 1;

    /**
     * Constructeur
     */
    public SelectTamagotchi() {

        createLabel();
        createButton();
        addButtonListeners();


        nomTamagotchi.setText("Garfield");
        nomTamagotchi.setMaxLength(10);
        nomTamagotchi.setWidth(300);

        posAndSizeElement();

        addActorStage();

        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Change l'image du tamagotchi sélectionné
     *
     * @param boutonImage Tamagotchi sélectionné
     */
    public void imageSelected(BoutonImage boutonImage) {
        // Copie la texture de boutonImage dans selectedImage
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(boutonImage.getImageTexture()));
        tamagotchiSelected.getStyle().imageUp = tamagotchiSelected.getStyle().imageDown = drawable;
    }

    public void setSkin(String image) {
        Texture img = new Texture(Gdx.files.internal("images/" + image));
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(img));
        tamagotchiSelected.getStyle().imageUp = tamagotchiSelected.getStyle().imageDown = drawable;
    }

    /**
     * Ajout les éléments à l'interface
     */
    public void addActorStage() {
        stage.clear();
        stage.addActor(pixelCat);
        stage.addActor(pixelDog);
        stage.addActor(pixelDinosaur);
        stage.addActor(pixelRobot);
        stage.addActor(labelTamagotchiSelection);
        stage.addActor(tamagotchiSelected);
        stage.addActor(labelNomTamagotchi);
        stage.addActor(nomTamagotchi);
        stage.addActor(labelDifficulty);
        stage.addActor(labelLevelDifficult);
        stage.addActor(leftArrow);
        stage.addActor(rightArrow);
        stage.addActor(backButton);
        stage.addActor(playButton);
    }

    /**
     * Instancie les boutons
     */
    public void createButton() {
        // Boutons de selection du Tamagotchi
        pixelCat = new BoutonImage(new MultiSkin("image"), "images/pixelCat1.png", 500, 500);
        pixelDog = new BoutonImage(new MultiSkin("image"), "images/pixelDog1.png", 500, 500);
        pixelDinosaur = new BoutonImage(new MultiSkin("image"), "images/pixelDinosaur1.png", 500, 500);
        pixelRobot = new BoutonImage(new MultiSkin("image"), "images/pixelRobot1.png", 500, 500);

        tamagotchiSelected = new BoutonImage(new MultiSkin("image"), "images/pixelCat1.png", 500, 500);

        backButton = new TextButton("Retour en arriere", new MultiSkin("text"));
        playButton = new TextButton("Jouer", new MultiSkin("text"));

        leftArrow = new BoutonImage(new MultiSkin("image"), "images/leftArrowWhite.png", 100, 75);
        rightArrow = new BoutonImage(new MultiSkin("image"), "images/rightArrowWhite.png", 100, 75);

        retour = new TextButton("Retour", new MultiSkin("text"));
    }

    /**
     * Instancie les labels
     */
    public void createLabel() {
        labelTamagotchiSelection = new Label("Tamagotchi \nselectionner : ", new MultiSkin("label"));
        labelNomTamagotchi = new Label("Nom du Tamagotchi : ", new MultiSkin("label"));
        labelDifficulty = new Label("Niveau de difficulter : ", new MultiSkin("label"));
        labelLevelDifficult = new Label("Facile", new MultiSkin("label"));

        message = new Label("", new MultiSkin("label"));
    }

    /**
     * Définie la taille et place les éléments
     */
    public void posAndSizeElement() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        float tailleImage = screenHeight * (150f / 900f);
        float xArrow = screenHeight * (100f / 900f);
        float yArrow = screenHeight * (75f / 900f);
        float shift = screenHeight * (30f / 900f);
        float ajustementXElement = screenHeight * (35f / 900f);
        float ajustementYElement = screenHeight * (200f / 900f);
        float fontScale = screenHeight * (1f / 900f);
        float middleX = screenWidth / 2f;
        float middleY = screenHeight / 2f;

        if (fontScale > 1) {
            fontScale = 1;
        }

        pixelCat.setSize(tailleImage, tailleImage);
        pixelDog.setSize(tailleImage, tailleImage);
        pixelDinosaur.setSize(tailleImage, tailleImage);
        pixelRobot.setSize(tailleImage, tailleImage);
        tamagotchiSelected.setSize(tailleImage - shift, tailleImage - shift);

        leftArrow.setSize(xArrow, yArrow);
        rightArrow.setSize(xArrow, yArrow);

        labelTamagotchiSelection.setFontScale(fontScale);
        labelDifficulty.setFontScale(fontScale);
        labelLevelDifficult.setFontScale(fontScale);
        labelNomTamagotchi.setFontScale(fontScale);
        nomTamagotchi.getStyle().font.getData().setScale(fontScale);
        backButton.getLabel().setFontScale(fontScale);
        playButton.getLabel().setFontScale(fontScale);

        pixelCat.setPosition(middleX - tailleImage - shift - ajustementXElement, middleY + shift + ajustementYElement);
        pixelDog.setPosition(middleX + shift + ajustementXElement, middleY + shift + ajustementYElement);
        pixelDinosaur.setPosition(middleX - tailleImage - shift - ajustementXElement, middleY - tailleImage - shift + ajustementYElement);
        pixelRobot.setPosition(middleX + shift + ajustementXElement, middleY - tailleImage - shift + ajustementYElement);

        labelTamagotchiSelection.setPosition(middleX - labelTamagotchiSelection.getMinWidth() - ajustementXElement, middleY - (tailleImage / 1.2f));
        tamagotchiSelected.setPosition(middleX + shift + ajustementXElement, middleY - (tailleImage / 1.2f));

        labelNomTamagotchi.setPosition(middleX - labelNomTamagotchi.getMinWidth() - ajustementXElement, middleY - tailleImage - (shift * 2.2f));
        nomTamagotchi.setPosition(middleX + (shift * 3f), middleY - tailleImage - (shift * 2.2f));

        labelDifficulty.setPosition(middleX - labelDifficulty.getMinWidth() - ajustementXElement, middleY - tailleImage - (shift * 5f));
        leftArrow.setPosition(middleX, middleY - tailleImage - shift * 4 - labelLevelDifficult.getMinHeight());
        labelLevelDifficult.setPosition(middleX + labelLevelDifficult.getMinWidth() + (ajustementXElement / 2f), middleY - tailleImage - (shift * 5f));
        rightArrow.setPosition(middleX + labelLevelDifficult.getMinWidth() + (ajustementXElement * 4.2f), middleY - tailleImage - (shift * 4f) - labelLevelDifficult.getMinHeight());

        if (backButton.getMinWidth() <= 220) {
            backButton.setPosition(middleX - 220 - ajustementXElement, 20);
        } else {
            backButton.setPosition(middleX - backButton.getMinWidth() - ajustementXElement, 20);
        }

        playButton.setPosition(middleX + playButton.getMinWidth(), 20);

        message.setPosition(screenWidth / 2f - message.getMinWidth() / 2f, screenHeight / 2f);
        retour.setPosition(screenWidth / 2f - retour.getMinWidth() / 2f, screenHeight / 2f - message.getMinHeight() - 20);
    }

    /**
     * Ajoute les écouteurs sur les boutons
     */
    public void addButtonListeners() {
        pixelCat.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageSelected(pixelCat); // Appel de la méthode avec le bouton sélectionné
                tamagotchiSelection = 1;
                nomTamagotchi.setText("Garfield");
                nomTamagotchi.setCursorPosition(8);
                return true;
            }
        });

        pixelDog.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageSelected(pixelDog); // Appel de la méthode avec le bouton sélectionné
                tamagotchiSelection = 2;
                nomTamagotchi.setText("Scooby");
                nomTamagotchi.setCursorPosition(6);
                return true;
            }
        });

        pixelDinosaur.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageSelected(pixelDinosaur); // Appel de la méthode avec le bouton sélectionné
                tamagotchiSelection = 3;
                nomTamagotchi.setText("Blue");
                nomTamagotchi.setCursorPosition(4);
                return true;
            }
        });

        pixelRobot.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                imageSelected(pixelRobot); // Appel de la méthode avec le bouton sélectionné
                tamagotchiSelection = 4;
                nomTamagotchi.setText("Wall-E");
                nomTamagotchi.setCursorPosition(6);
                return true;
            }
        });

        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ScreenMenu(true));
                return true;
            }
        });

        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Pattern p = Pattern.compile("[a-zA-Z0-9-_]+");
                Matcher m = p.matcher(nomTamagotchi.getText());
                boolean b = m.matches();

                if (b) {
                    int numberSave = ScreenMenu.getAvailableNumber(ScreenMenu.getNamesSave());

                    // Si vaut -1 → pas de save
                    if (numberSave == -1) {
                        numberSave = 1;
                    }

                    new Controller(tamagotchiSelection, nomTamagotchi.getText(), difficultyLevel, false, numberSave, skin);
                } else {
                    if (nomTamagotchi.getText().isEmpty()) {
                        message.setText("Entrer un nom pour le Tamagotchi.");
                    } else {
                        message.setText("       Nom incorrect. \n\nN'utilisez que des lettres \n    ou chiffres ou tirets");
                    }

                    posAndSizeElement();

                    stage.clear();
                    stage.addActor(message);
                    stage.addActor(retour);
                }

                return true;
            }
        });

        leftArrow.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (difficultyLevel) {
                    case (1):
                        labelLevelDifficult.setText("Difficile");
                        difficultyLevel = 3;
                        break;
                    case (2):
                        labelLevelDifficult.setText("Facile");
                        difficultyLevel = 1;
                        break;
                    case (3):
                        labelLevelDifficult.setText("Moyen");
                        difficultyLevel = 2;
                        break;
                }

                return true;
            }
        });

        rightArrow.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (difficultyLevel) {
                    case 1:
                        labelLevelDifficult.setText("Moyen");
                        difficultyLevel = 2;
                        break;
                    case 2:
                        labelLevelDifficult.setText("Difficile");
                        difficultyLevel = 3;
                        break;
                    case 3:
                        labelLevelDifficult.setText("Facile");
                        difficultyLevel = 1;
                        break;
                }

                return true;
            }
        });

        retour.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                addActorStage();
                return true;
            }
        });

        tamagotchiSelected.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                switch (skin) {
                    case (1):
                        skin = 2;
                        break;

                    case (2):
                        skin = 1;
                        break;
                }
                switch (tamagotchiSelection) {
                    case (1):
                        setSkin("pixelCat" + skin + ".png");
                        break;

                    case (2):
                        setSkin("pixelDog" + skin + ".png");
                        break;

                    case (3):
                        setSkin("pixelDinosaur" + skin + ".png");
                        break;

                    case (4):
                        setSkin("pixelRobot" + skin + ".png");
                        break;
                }

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

        // Dessine l'image de fond
        batch.begin();
        batch.draw(background, 0, 0, screenWidth, screenHeight);
        batch.end();

        // Dessine le stage
        stage.draw();
    }

    /**
     * Méthode appelée quand la fenêtre est redimensionnée
     *
     * @param width  largeur de l'écran
     * @param height hauteur de l'écran
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        // Met à jour les nouvelles dimensions de la fenêtre
        screenWidth = width;
        screenHeight = height;

        posAndSizeElement();

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
        batch.dispose();
        stage.dispose();
    }
}

