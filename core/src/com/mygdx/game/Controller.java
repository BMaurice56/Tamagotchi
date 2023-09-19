package com.mygdx.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Classe controller qui permet l'interaction avec les fichiers json
 */
public class Controller {

    String nomFichier;

    JsonValue base;

    FileHandle file;

    String emplacement = "core/src/com/mygdx/game/";

    /**
     * Constructeur
     *
     * @param nomFichier String nom du fichier
     */
    public Controller(String nomFichier) {
        this.nomFichier = nomFichier;

        if (!nomFichier.equals("settings.json") && !nomFichier.equals("save.json")) {
            throw new IllegalArgumentException("Fichier json inexistant");
        }

        // Utilisez le chemin complet du fichier
        JsonReader jsonReader = new JsonReader();
        file = new FileHandle(emplacement + nomFichier);

        // Lecture du fichier de paramètre json
        base = jsonReader.parse(file);
    }

    /**
     * Renvoie le niveau de son du jeu
     *
     * @return float son
     */
    public float getSound() {
        settingsFileCheck();
        if (base != null && contains("sound")) {
            return base.getFloat("sound");
        }
        return 0.5f;
    }

    /**
     * Enregistre le niveau de son du jeu
     *
     * @param son float son
     */
    public void setSound(float son) {
        settingsFileCheck();
        file.writeString("{\n \"sound\":" + son + "\n}", false);
    }

    /**
     * Vérifie que les méthodes appelées soient pour le bon fichier
     */
    public void settingsFileCheck() {
        if (!nomFichier.equals("settings.json")) {
            throw new IllegalArgumentException("Contrôleur utilisé pour le mauvais fichier");
        }
    }

    /**
     * Vérifie si le nom est présent dans le fichier json
     *
     * @param name nom
     * @return boolean true si contient le nom
     */
    public boolean contains(String name) {
        return base.has(name);
    }
}
