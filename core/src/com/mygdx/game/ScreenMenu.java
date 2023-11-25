package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe du menu d'accueil du jeu
 */
public class ScreenMenu implements Screen {
    // https://gamedev.stackexchange.com/questions/121316/what-is-the-difference-between-sprite-and-spritebatch-specifically-in-the-conte/121340
    private final SpriteBatch batch = new SpriteBatch();

    // Arrière-plan
    private final Texture background = new Texture("images/background.jpg");

    // Taille de la fenêtre
    private float screenWidth, screenHeight;

    // Stage qui gère les entrées utilisateurs (inputProcessor)
    private final Stage stage = new Stage(new ScreenViewport());

    // Musique du jeu
    private final Music musique = Gdx.audio.newMusic(Gdx.files.internal("musics/Allumer-le-feu.mp3"));

    // Slider qui gère le niveau du son
    private final Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, new Skin(Gdx.files.internal("skin/uiskin.json")));

    private BoutonImage playButton, settingsButton, quitButton;

    private TextButton newGameButton, saveGameButton, backButton, backButton2, backButton3;

    // Table qui gère le placement des objets sur la fenêtre
    private Table homeTable, partyTable, settingsTable, saveGameTable;

    // Modèle du jeu
    private final Modele modele;

    // Créez un objet Path
    private static final Path currRelativePath = Paths.get(System.getProperty("user.dir") + "/core/src/com/mygdx/game/jsonFile");


    /**
     * Constructeur
     */
    public ScreenMenu() {
        modele = new Modele();

        float son = modele.getSound();
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

    // Deuxième constructeur
    public ScreenMenu(boolean ignoredMenuGestionGame) {
        this();
        putTable(partyTable);
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

    /**
     * Instancie les boutons
     */
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
        backButton3 = new TextButton("Retour en arriere", new MultiSkin("text"));
    }

    /**
     * Instancie les tables
     */
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

    /**
     * Ajoutes les écouteurs des boutons
     */
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

                modele.setSound(son);

                putTable(homeTable);
                return true;
            }
        });

        backButton3.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                putTable(partyTable);
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

        saveGameButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int sauvegarde = getLastNumberFromSave(getNameSave());

                // Table d'affichage des sauvegardes
                saveGameTable = new Table();
                saveGameTable.setFillParent(true);
                saveGameTable.center();
                saveGameTable.setPosition(saveGameTable.getX() - 100, saveGameTable.getY());

                if (sauvegarde == -1) {
                    saveGameTable.add(new Label("", new MultiSkin("label")));
                    saveGameTable.add(new Label("Pas de sauvegarde", new MultiSkin("label"))).row();
                    saveGameTable.setPosition(saveGameTable.getX() + 100, saveGameTable.getY());

                } else {
                    saveGameTable.add(new Label("Nom du Tamagotchi", new MultiSkin("label")));
                    saveGameTable.add(new Label("Difficulte", new MultiSkin("label")));
                    saveGameTable.add(new Label("     ", new MultiSkin("label"))).row();
                    saveGameTable.add(new Label("", new MultiSkin("label"))).row();

                    // Lecteur de json
                    JsonReader jsonReader = new JsonReader();

                    // Pour toutes les sauvegardes trouvées
                    for (String save : getNameSave()) {
                        // Permet de lire et d'interagir avec le fichier
                        FileHandle saveFile = new FileHandle(currRelativePath + "/" + save);

                        // Lecture du fichier de paramètre json
                        final JsonValue saveFileReader = jsonReader.parse(saveFile);

                        // Création d'un label avec le nom du Tamagotchi
                        Label nomTamagotchi = new Label(saveFileReader.getString("name"), new MultiSkin("label"));

                        // Récupération du numéro de sauvegarde
                        String[] values = save.split("save");
                        final int numberSave = Integer.parseInt(values[1].split(".json")[0]);

                        // Ajout du label à la table
                        saveGameTable.add(nomTamagotchi);
                        switch (contains(saveFileReader, "difficulty")) {
                            case (1):
                                saveGameTable.add(new Label("Facile", new MultiSkin("label")));
                                break;

                            case (2):
                                saveGameTable.add(new Label("Moyen", new MultiSkin("label")));
                                break;

                            case (3):
                                saveGameTable.add(new Label("Difficile", new MultiSkin("label")));
                                break;
                        }

                        // Bouton Jouer
                        Label jouer = new Label(" Jouer", new MultiSkin("label"));

                        jouer.addListener(new InputListener() {
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                new Controller(contains(saveFileReader, "numberTamagotchi"), "", contains(saveFileReader, "difficulty"), true, numberSave);
                                return true;
                            }
                        });

                        saveGameTable.add(jouer).row();
                    }
                }
                saveGameTable.add(new Label("", new MultiSkin("label"))).row();
                // Ajout d'un espace et d'un bouton retour
                saveGameTable.add(new Label("", new MultiSkin("label")));
                saveGameTable.add(backButton3).row();

                // Ajout de la table à l'écran
                putTable(saveGameTable);

                return true;
            }
        });

    }

    /**
     * Méthode qui vérifie si le fichier json contient une certaine clé
     * Renvoi la valeur de la clé ou 1 si non trouvé
     *
     * @param jsonValue Fichier json
     * @param key       clé
     * @return int la valeur ou 1 si inexistante
     */
    public int contains(JsonValue jsonValue, String key) {
        if (jsonValue.has(key)) {
            return jsonValue.getInt(key);
        }

        return 1;
    }


    /**
     * Méthode qui scan le répèrtoire et renvoi le chiffre correspondant à la dernière sauvegarde
     *
     * @return int numéro de la dernière sauvegarde / -1 si pas de sauvegarde
     */
    public static int getLastNumberFromSave(ArrayList<String> sauvegarde) {
        int save = -1;

        // Pour tous les éléments du répertoire
        for (String str : sauvegarde) {

            String[] values = str.split("save");
            int temp = Integer.parseInt(values[1].split(".json")[0]);

            if (temp > save) {
                save = temp;
            }
        }

        return save;
    }

    /**
     * Méthode qui renvoi tous les noms des sauvegardes
     *
     * @return arraylist nom des sauvegarde
     */
    public static ArrayList<String> getNameSave() {

        // Objet File à partir du chemin
        File repertoire = new File(currRelativePath.toString());

        // Liste des éléments dans le répertoire
        String[] liste = repertoire.list();

        // Liste des noms de sauvegarde
        ArrayList<String> arrayList = new ArrayList<>();

        // Expression régulière
        Pattern pattern = Pattern.compile("save[0-9]+.json");

        assert liste != null;
        // Pour tous les éléments du répertoire
        for (String string : liste) {
            // Créer un matcher à partir de l'élément en cour
            Matcher matcher = pattern.matcher(string);

            // Si match alors c'est une sauvegarde
            if (matcher.matches()) {
                arrayList.add(string);
            }
        }

        return arrayList;
    }

    /**
     * Remplace la table affichée à l'écran
     *
     * @param table la table voulu
     */
    public void putTable(Table table) {
        stage.clear();
        stage.addActor(table);
    }
}
