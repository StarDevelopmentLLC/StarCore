package com.stardevllc.starcore.skins;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stardevllc.helper.StringHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkinManager {
    private static final String MINESKIN_URL = "https://api.mineskin.org/generate/url?url=%s";
    private static final String MOJANG_NAME_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String MOJANG_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    private Map<String, Skin> skins = new HashMap<>();
    
    private JsonObject getObjectFromURL(String urlString) throws IOException {
        URL url = URI.create(urlString).toURL();
        URLConnection request = url.openConnection();
        request.connect();

        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
        return root.getAsJsonObject();
    }
    
    public Skin getFromImage(String imageUrl) {
        Skin skin = this.skins.get(imageUrl);
        if (skin != null) {
            return skin;
        }

        try {
            JsonObject object = getObjectFromURL(String.format(MINESKIN_URL, imageUrl));

            if (object.has("error")) {
                return null;
            }

            JsonObject skinInfo = object.getAsJsonObject("data").getAsJsonObject("texture");

            skin = new Skin(imageUrl, skinInfo.get("value").getAsString(), skinInfo.get("signature").getAsString());
            this.skins.put(imageUrl, skin);
            return skin;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Skin getFromMojang(String playerName) {
        try {
            JsonObject object = getObjectFromURL(String.format(MOJANG_NAME_URL, playerName));

            if (object.has("errorMessage")) {
                throw new IllegalArgumentException("Couldn't find the user " + playerName + ".");
            }

            return getFromMojang(StringHelper.toUUID(object.get("id").getAsString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Skin getFromMojang(UUID playerId) {
        try {
            JsonObject object = getObjectFromURL(String.format(MOJANG_URL, playerId.toString()));

            if (object.has("errorMessage")) {
                throw new IllegalArgumentException("Couldn't find the user " + playerId + ".");
            }

            JsonObject properties = object.getAsJsonArray("properties").get(0).getAsJsonObject();

            Skin skin = new Skin(playerId.toString(), properties.get("value").getAsString(), properties.get("signature").getAsString());
            this.skins.put(skin.identifier(), skin);
            return skin;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
