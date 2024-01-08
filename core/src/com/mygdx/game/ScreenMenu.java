package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
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
import java.util.Arrays;
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
    private Texture background = new Texture("images/background.jpg");

    // Taille de la fenêtre
    private float screenWidth, screenHeight;

    // Stage qui gère les entrées utilisateurs (inputProcessor)
    private final Stage stage = new Stage(new ScreenViewport());

    // Musique du jeu
    private final Music musique;

    // Slider qui gère le niveau du son
    private final Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, new Skin(Gdx.files.internal("skin/uiskin.json")));

    private BoutonImage playButton, settingsButton, quitButton;

    // Table qui gère le placement des objets sur la fenêtre
    private Table homeTable, partyTable, settingsTable, saveGameTable, maxSaveTable, ruleTable;

    // Modèle du jeu
    private final Modele modele;

    // Créez un objet Path
    private static final Path currRelativePath = Paths.get(System.getProperty("user.home") + "/.Tamagotchi/jsonFile/");

    // Label
    private Label noSave, columnTitleName, columnTitleDifficulty, message, rule, listRule, goBackFromRule, badSave, goBackFromBadSave,
            newGameButton, saveGameButton, backButton, backButton2, backButton3;

    // Stock tous les labels de chaque sauvegarde pour l'affichage
    private final ArrayList<Label> yesNoLabels = new ArrayList<>();


    /**
     * Constructeur
     *
     * @param playMusique true → musique, false → pas de musique
     * @param music       Objet musique pour garder le même objet selon l'écran / null pour nouvel objet
     */
    public ScreenMenu(boolean playMusique, Music music) {
        modele = new Modele();

        float son = modele.getSound();
        volumeSlider.setValue(son);

        if (music != null) {
            this.musique = music;
        } else {
            musique = Gdx.audio.newMusic(Gdx.files.internal("musics/Allumer-le-feu.mp3"));
        }

        musique.setVolume(son);
        musique.setLooping(true);
        if (playMusique) {
            musique.play();
        }

        // Création des objets
        createButton();
        createLabel();
        createTable();
        addButtonListeners();

        // Ajout du menu d'accueil
        stage.addActor(homeTable);

        // Définit le stage comme gestionnaire des entrées
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Deuxième constructeur
     *
     * @param music Objet musique pour garder le même objet selon l'écran
     */
    public ScreenMenu(Music music) {
        this(false, music);
        putTable(partyTable);
    }

    /**
     * Constructeur en cas de sauvegarde non valide
     */
    public ScreenMenu() {
        this(true, null);

        stage.clear();

        Table table = new Table();
        table.setFillParent(true);

        table.add(badSave).row();
        table.add(new Label(" ", new MultiSkin("label"))).row();
        table.add(goBackFromBadSave).row();

        stage.addActor(table);
    }

    /**
     * Instancie les boutons
     */
    public void createButton() {
        // Accueil
        playButton = new BoutonImage(new MultiSkin("image"), "images/play.png", 200, 50);
        settingsButton = new BoutonImage(new MultiSkin("image"), "images/settings.png", 200, 50);
        quitButton = new BoutonImage(new MultiSkin("image"), "images/quit.png", 200, 50);
    }

    /**
     * Instancie les labels
     */
    public void createLabel() {
        noSave = new Label("Pas de sauvegarde", new MultiSkin("label"));
        columnTitleName = new Label("Nom du \nTamagotchi", new MultiSkin("label"));
        columnTitleDifficulty = new Label("Difficulte", new MultiSkin("label"));
        message = new Label("", new MultiSkin("label"));
        rule = new Label("Regles du jeu", new MultiSkin("label"));
        listRule = new Label(modele.getRule(), new MultiSkin("label"));
        goBackFromRule = new Label("Retour en arriere", new MultiSkin("label"));
        badSave = new Label("Le fichier de sauvegarde etant non valide\n              (du a une modification)\n               celui-ci a ete supprime", new MultiSkin("label"));
        goBackFromBadSave = new Label("Retour au centre", new MultiSkin("label"));

        // Gestion de la partie
        newGameButton = new Label("Nouvelle partie", new MultiSkin("label"));
        saveGameButton = new Label("Sauvegarde", new MultiSkin("label"));
        backButton = new Label("Retour au centre", new MultiSkin("label"));
        backButton2 = new Label("Retour au centre", new MultiSkin("label"));
        backButton3 = new Label("Retour en arriere", new MultiSkin("label"));
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
        settingsTable.add(rule).row();
        settingsTable.add(backButton2).row();

        ruleTable = new Table();
        ruleTable.setFillParent(true);

        ruleTable.add(listRule).row();
        ruleTable.add(goBackFromRule).row();
    }

    /**
     * Instancie la table de sauvegarde
     */
    public void createSaveTable() {
        // Table d'affichage des sauvegardes
        saveGameTable = new Table();
        saveGameTable.setFillParent(true);
        saveGameTable.center();

        ArrayList<String> sauvegarde = getNamesSave();

        if (sauvegarde.isEmpty()) {
            saveGameTable.add(new Label("", new MultiSkin("label")));
            saveGameTable.add(noSave).row();

        } else {
            saveGameTable.add(columnTitleName);
            saveGameTable.add(columnTitleDifficulty);
            saveGameTable.add(new Label("     ", new MultiSkin("label")));
            saveGameTable.add(new Label("     ", new MultiSkin("label"))).row();
            saveGameTable.add(new Label("", new MultiSkin("label"))).row();

            // Lecteur de json
            JsonReader jsonReader = new JsonReader();

            // Compteur du nombre de sauvegardes valide
            int compteur = 0;

            // Pour toutes les sauvegardes trouvées
            for (String save : sauvegarde) {
                // Permet de lire et d'interagir avec le fichier
                final FileHandle saveFile = new FileHandle(currRelativePath + "/" + save);

                try {
                    // Lecture du fichier de paramètre json
                    final JsonValue saveFileReader = jsonReader.parse(saveFile);

                    // Création d'un label avec le nom du Tamagotchi
                    final Label nomTamagotchi = new Label(saveFileReader.getString("name"), new MultiSkin("label"));
                    Label difficulty = null;

                    // Récupération du numéro de sauvegarde
                    String[] values = save.split("save");
                    final int numberSave = Integer.parseInt(values[1].split(".json")[0]);

                    switch (contains(saveFileReader, "difficulty")) {
                        case (1):
                            difficulty = new Label("Facile", new MultiSkin("label"));
                            break;

                        case (2):
                            difficulty = new Label("Moyen", new MultiSkin("label"));
                            break;

                        case (3):
                            difficulty = new Label("Difficile", new MultiSkin("label"));
                            break;
                    }

                    // Bouton Jouer et supprimer
                    Label jouer = new Label(" Jouer ", new MultiSkin("label"));
                    Label supprimer = new Label("  Supprimer", new MultiSkin("label"));

                    jouer.addListener(new InputListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            musique.dispose();
                            new Controller(contains(saveFileReader, "numberTamagotchi"), "", contains(saveFileReader, "difficulty"), true, numberSave, contains(saveFileReader, "skin"));
                            return true;
                        }
                    });

                    supprimer.addListener(new InputListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            message.setText("Etes vous sur de vouloir supprimer : " + nomTamagotchi.getText());

                            Label oui = new Label("Oui", new MultiSkin("label"));
                            Label non = new Label("Non", new MultiSkin("label"));

                            yesNoLabels.add(oui);
                            yesNoLabels.add(non);

                            // Place les éléments de l'affichage
                            posAndSizeElement();

                            // Affichage du message de confirmation
                            stage.clear();
                            stage.addActor(message);
                            stage.addActor(oui);
                            stage.addActor(non);

                            oui.addListener(new InputListener() {
                                @Override
                                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                    Controller.deleteSave(saveFile);

                                    putSaveTable();
                                    return true;
                                }
                            });

                            non.addListener(new InputListener() {
                                @Override
                                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                    putSaveTable();
                                    return true;
                                }
                            });

                            return true;
                        }
                    });

                    // Ajout des labels à la table
                    saveGameTable.add(nomTamagotchi);
                    saveGameTable.add(difficulty);
                    saveGameTable.add(jouer);
                    saveGameTable.add(supprimer).row();

                    compteur++;

                } catch (Exception e) {
                    Controller.deleteSave(saveFile);
                }
            }

            // Si aucune sauvegarde valide, alors on affiche la table comme s'il n'y avait aucune sauvegarde
            if (compteur == 0) {
                createSaveTable();
            }

        }
        saveGameTable.add(new Label("", new MultiSkin("label"))).row();
        // Ajout d'un espace et d'un bouton retour
        saveGameTable.add(new Label("", new MultiSkin("label")));
        saveGameTable.add(backButton3).row();
    }

    /**
     * Place la table de sauvegarde à l'écran
     * Permet de remettre à jour la table si suppression de sauvegarde
     */
    public void putSaveTable() {
        // Crée la table
        createSaveTable();

        putTable(saveGameTable);

        // Placement les éléments de la table
        posAndSizeElement();
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

        rule.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                background = darkenImage(background);

                putTable(ruleTable);
                return true;
            }
        });

        goBackFromRule.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                lightenImage();
                putTable(settingsTable);
                return true;
            }
        });

        newGameButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int sauvegarde = getAvailableNumber(getNamesSave());

                if (sauvegarde == -1) {
                    maxSaveTable = new Table();
                    maxSaveTable.setFillParent(true);
                    maxSaveTable.center();

                    maxSaveTable.add(new Label("Nombre maximale de sauvegarde atteint", new MultiSkin("label"))).row();
                    maxSaveTable.add(backButton3).row();

                    putTable(maxSaveTable);

                } else {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new SelectTamagotchi(musique));
                }

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

        goBackFromBadSave.addListener(new InputListener() {
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
                putSaveTable();

                return true;
            }
        });

    }

    /**
     * Place et redimensionne les éléments à l'écran
     */
    public void posAndSizeElement() {
        float fontScale = ((float) 1 / 900) * screenHeight;

        // Correction de bug windows
        if (fontScale <= 0) {
            fontScale = 1;
        }

        newGameButton.setFontScale(fontScale);
        saveGameButton.setFontScale(fontScale);
        backButton.setFontScale(fontScale);
        backButton2.setFontScale(fontScale);
        backButton3.setFontScale(fontScale);

        noSave.setFontScale(fontScale);
        columnTitleName.setFontScale(fontScale);
        columnTitleDifficulty.setFontScale(fontScale);

        rule.setFontScale(fontScale);
        listRule.setFontScale(fontScale / Modele.coefficientAffichageRegle);
        goBackFromRule.setFontScale(fontScale);

        message.setFontScale(fontScale);
        message.setPosition(screenWidth / 2 - message.getMinWidth() / 2, screenHeight / 2);

        badSave.setFontScale(fontScale);
        goBackFromBadSave.setFontScale(fontScale);

        // Si la table n'est pas null, alors on redimensionne les polices d'écriture
        if (saveGameTable != null) {
            for (Label label : yesNoLabels) {
                label.setFontScale(fontScale);

                if (label.getText().toString().equals("Oui")) {
                    label.setPosition(screenWidth / 2 - label.getMinWidth(), screenHeight / 2 - message.getMinHeight() * 2);
                } else {
                    label.setPosition(screenWidth / 2 + label.getMinWidth(), screenHeight / 2 - message.getMinHeight() * 2);
                }
            }

            for (Cell<?> cell : saveGameTable.getCells()) {
                Label label = (Label) cell.getActor();
                label.setFontScale(fontScale);
            }
        }
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
     * Renvoie un numéro disponible pour la sauvegarde
     * Renvoie -1 si aucun numéro disponible
     *
     * @param sauvegarde liste des sauvegardes
     * @return int numéro disponible ou -1
     */
    public static int getAvailableNumber(ArrayList<String> sauvegarde) {
        int nombreMaxSave = 10;

        int[] number = new int[nombreMaxSave];

        // Remplie le tableau de -1
        Arrays.fill(number, -1);

        // Récupère tous les numéros de sauvegarde
        for (String save : sauvegarde) {
            String[] values = save.split("save");
            int numero = Integer.parseInt(values[1].split(".json")[0]);

            number[numero - 1] = 1;
        }

        // S'il y a un -1 -> numéro disponible
        for (int j = 0; j < nombreMaxSave; j++) {
            if (number[j] == -1) {
                return j + 1;
            }
        }

        // Sinon pas de numéro disponible
        return -1;
    }

    /**
     * Méthode qui renvoi tous les noms des sauvegardes
     *
     * @return arraylist nom des sauvegarde
     */
    public static ArrayList<String> getNamesSave() {
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

    /**
     * Change la couleur de fond pour mieux voir les règles du jeu
     *
     * @param original Texture de base
     * @return La nouvelle texture assombrie
     */
    public static Texture darkenImage(Texture original) {
        if (!original.getTextureData().isPrepared()) {
            original.getTextureData().prepare();
        }

        Pixmap originalPixmap = original.getTextureData().consumePixmap();

        // Assombrir l'image en réduisant la luminosité de chaque pixel
        for (int y = 0; y < originalPixmap.getHeight(); y++) {
            for (int x = 0; x < originalPixmap.getWidth(); x++) {
                int pixel = originalPixmap.getPixel(x, y);

                // Extraire les composantes RVB
                int r = (pixel & 0x00FF0000) >>> 16;
                int g = (pixel & 0x0000FF00) >>> 8;
                int b = pixel & 0x000000FF;

                // Réduire la luminosité en ajustant les composantes RVB
                r = (int) (r * 0.5f);
                g = (int) (g * 0.5f);
                b = (int) (b * 0.5f);

                // Recréer le pixel avec les nouvelles composantes RVB
                pixel = (r << 16) | (g << 8) | b;

                originalPixmap.drawPixel(x, y, pixel);
            }
        }

        Texture darkenedTexture = new Texture(originalPixmap);
        originalPixmap.dispose();

        return darkenedTexture;
    }

    /**
     * Remet la texture de base
     */
    public void lightenImage() {
        background = new Texture("images/background.jpg");
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

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
