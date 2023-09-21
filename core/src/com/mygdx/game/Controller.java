package com.mygdx.game;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;


/**
 * Classe controller qui permet l'interaction avec les fichiers json
 */
public class Controller {

    JsonValue base;

    FileHandle file;

    String emplacement = "core/src/com/mygdx/game/";

    /**
     * Constructeur
     */
    public Controller() {

        // Utilisez le chemin complet du fichier
        JsonReader jsonReader = new JsonReader();
        file = new FileHandle(emplacement + "settings.json");

        // Lecture du fichier de paramètre json
        base = jsonReader.parse(file);

    }

    /**
     * Renvoie le niveau de son du jeu
     *
     * @return float son
     */
    public float getSound() {
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
        file.writeString("{\n \"sound\":" + son + "\n}", false);
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