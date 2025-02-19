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

@SuppressWarnings("deprecation")
public class SkinManager {
    private static final String MINESKIN_URL = "https://api.mineskin.org/generate/url?url=%s";
    private static final String MOJANG_NAME_URL = "https://api.minecraftservices.com/minecraft/profile/lookup/name/%s";
    private static final String MOJANG_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    private Map<String, Skin> skins = new HashMap<>();
    private Map<String, UUID> nameToUUID = new HashMap<>();
    
    private JsonObject getObjectFromURL(String urlString) throws IOException {
        URL url = URI.create(urlString).toURL();
        URLConnection request = url.openConnection();
        request.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:135.0) Gecko/20100101 Firefox/135.0");
        request.connect();

        JsonElement root = new JsonParser().parse(new InputStreamReader((InputStream) request.getContent()));
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
        if (this.nameToUUID.containsKey(playerName)) {
            return getFromMojang(this.nameToUUID.get(playerName));
        }
        
        try {
            JsonObject object = getObjectFromURL(String.format(MOJANG_NAME_URL, playerName));

            if (object.has("errorMessage")) {
                throw new IllegalArgumentException("Couldn't find the user " + playerName + ".");
            }

            UUID uuid = StringHelper.toUUID(object.get("id").getAsString());
            this.nameToUUID.put(playerName, uuid);
            return getFromMojang(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Skin getFromMojang(UUID playerId) {
        if (this.skins.containsKey(playerId.toString())) {
            return this.skins.get(playerId.toString());
        }
        
        try {
            JsonObject object = getObjectFromURL(String.format(MOJANG_URL, playerId));

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
