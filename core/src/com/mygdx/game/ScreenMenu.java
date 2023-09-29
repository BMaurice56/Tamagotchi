package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class ScreenMenu implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final Texture background = new Texture("images/background.jpg");

    private float screenWidth, screenHeight;

    private final Stage stage = new Stage(new ScreenViewport());

    private final Music musique = Gdx.audio.newMusic(Gdx.files.internal("musics/Allumer-le-feu.mp3"));

    private final Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, new Skin(Gdx.files.internal("skin/uiskin.json")));

    private BoutonImage playButton, settingsButton, quitButton;

    private TextButton newGameButton, saveGameButton, backButton, backButton2;

    private Table homeTable, partyTable, settingsTable;

    private final Modele controllerSettings;


    public ScreenMenu() {
        // Controller pour les paramètres
        controllerSettings = new Modele();

        float son = controllerSettings.getSound();
        volumeSlider.setValue(son);

        musique.setVolume(son);
        musique.setLooping(true);
        musique.play();

        // Création des objets
        createButton();
        createTable();
        addButtonListeners();

        // Ajout du menu d'accueil
        stage.addActor(homeTable);

        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);
    }

    public ScreenMenu(boolean menuGestionGame) {
        this();
        putTable(partyTable);
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

    public void createButton() {
        // Accueil
        playButton = new BoutonImage(new MultiSkin("image"), "images/play.png", 200, 50);
        settingsButton = new BoutonImage(new MultiSkin("image"), "images/settings.png", 200, 50);
        quitButton = new BoutonImage(new MultiSkin("image"), "images/quit.png", 200, 50);

        // Gestion de la partie
        newGameButton = new TextButton("Nouvelle partie", new MultiSkin("text"));
        saveGameButton = new TextButton("partie sauvegardée", new MultiSkin("text"));
        backButton = new TextButton("Retour au centre", new MultiSkin("text"));
        backButton2 = new TextButton("Retour au centre", new MultiSkin("text"));
    }

    public void createTable() {
        // Table du menu
        homeTable = new Table();
        homeTable.setFillParent(true);

        homeTable.add(playButton).row();
        homeTable.add(settingsButton).row();
        homeTable.add(quitButton).row();

        // Table nouvelle parti
        partyTable = new Table();
        partyTable.setFillParent(true);

        partyTable.add(newGameButton).row();
        partyTable.add(saveGameButton).row();
        partyTable.add(backButton).row();

        // Table paramètre
        settingsTable = new Table();
        settingsTable.setFillParent(true);

        settingsTable.add(volumeSlider).row();
        settingsTable.add(backButton2).row();

    }

    public void addButtonListeners() {
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                putTable(partyTable);
                return true;
            }
        });

        settingsButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                putTable(settingsTable);
                return true;
            }
        });

        newGameButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SelectTamagotchi());
                return true;
            }
        });

        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });

        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                putTable(homeTable);
                return true;
            }
        });

        backButton2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float son = volumeSlider.getValue();

                controllerSettings.setSound(son);

                putTable(homeTable);
                return true;
            }
        });

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = volumeSlider.getValue();
                musique.setVolume(volume);
            }
        });
    }


    public void putTable(Table table) {
        stage.clear();
        stage.addActor(table);
    }
}
