package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ScreenMenu implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final Texture background = new Texture("background.jpg");

    private float screenWidth, screenHeight;

    private final Stage stage = new Stage(new ScreenViewport());

    private final Music musique = Gdx.audio.newMusic(Gdx.files.internal("Allumer-le-feu.mp3"));

    final Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, new Skin(Gdx.files.internal("skin/uiskin.json")));

    private BoutonImage playbutton, settingsbutton, quitbutton;

    private TextButton newgamebutton, savegamebutton, backbutton, backbutton2;

    private Table homeTable, partyTable, settingsTable;

    public ScreenMenu() {

        // Musique de fond
        musique.setLooping(true);
        //musique.setVolume(0.5f);
        musique.setVolume(0);
        musique.play();


        // Création des objets et ajout de l'image de fond
        createButton();
        createTable();
        addButtonListeners();

        volumeSlider.setValue(musique.getVolume());

        // Ajout du menu d'accueil
        stage.addActor(homeTable);

        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);
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

    public void addButtonListeners() {
        playbutton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                putTable(partyTable);
                return true;
            }
        });

        settingsbutton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                putTable(settingsTable);
                return true;
            }
        });

        newgamebutton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SelectTamagotchi());
                return true;
            }
        });

        quitbutton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });

        InputListener listener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                putTable(homeTable);
                return true;
            }
        };
        backbutton.addListener(listener);
        backbutton2.addListener(listener);

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = volumeSlider.getValue();
                musique.setVolume(volume);
            }
        });
    }

    public void createButton() {
        // Accueil
        playbutton = new BoutonImage(new MultiSkin("image"), "play.png", 200, 50);
        settingsbutton = new BoutonImage(new MultiSkin("image"), "settings.png", 200, 50);
        quitbutton = new BoutonImage(new MultiSkin("image"), "quit.png", 200, 50);

        // Gestion de la partie
        newgamebutton = new TextButton("Nouvelle partie", new MultiSkin("text"));
        savegamebutton = new TextButton("partie sauvegardée", new MultiSkin("text"));
        backbutton = new TextButton("Retour au centre", new MultiSkin("text"));
        backbutton2 = new TextButton("Retour au centre", new MultiSkin("text"));
    }

    public void createTable() {
        // Table du menu
        homeTable = new Table();
        homeTable.setFillParent(true);

        homeTable.add(playbutton).row();
        homeTable.add(settingsbutton).row();
        homeTable.add(quitbutton).row();

        // Table nouvelle parti
        partyTable = new Table();
        partyTable.setFillParent(true);

        partyTable.add(newgamebutton).row();
        partyTable.add(savegamebutton).row();
        partyTable.add(backbutton).row();

        // Table paramètre
        settingsTable = new Table();
        settingsTable.setFillParent(true);

        settingsTable.add(volumeSlider).row();
        settingsTable.add(backbutton2).row();

    }

    public void putTable(Table table) {
        stage.clear();
        stage.addActor(table);
    }
}
