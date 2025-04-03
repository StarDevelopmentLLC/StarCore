package com.stardevllc.starcore.skins;

import com.google.gson.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;

public class SkinManager {
    private static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

    private static final String MINESKIN_URL = "https://api.mineskin.org/generate/url?url=%s";
    private static final String MOJANG_NAME_URL = "https://api.minecraftservices.com/minecraft/profile/lookup/name/%s";
    private static final String MOJANG_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";

    private static Map<String, UUID> uuidCache = new HashMap<>();
    private static Map<String, UUID> nameToUUID = new HashMap<>();
    
    private Map<String, Skin> skins = new HashMap<>();
    
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

            skin = new Skin(null, null, imageUrl, skinInfo.get("value").getAsString(), skinInfo.get("signature").getAsString());
            this.skins.put(imageUrl, skin);
            return skin;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Skin getFromMojang(String playerName) {
        if (nameToUUID.containsKey(playerName)) {
            return getFromMojang(nameToUUID.get(playerName));
        }
        
        try {
            JsonObject object = getObjectFromURL(String.format(MOJANG_NAME_URL, playerName));

            if (object.has("errorMessage")) {
                throw new IllegalArgumentException("Couldn't find the user " + playerName + ".");
            }

            UUID uuid = toUUID(object.get("id").getAsString());
            nameToUUID.put(playerName, uuid);
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
            
            String name = object.get("name").getAsString();

            JsonObject properties = object.getAsJsonArray("properties").get(0).getAsJsonObject();

            Skin skin = new Skin(playerId, name, playerId.toString(), properties.get("value").getAsString(), properties.get("signature").getAsString());
            this.skins.put(skin.getIdentifier(), skin);
            return skin;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static UUID toUUID(String id) {
        if (uuidCache.containsKey(id)) {
            return uuidCache.get(id);
        }

        if (UUID_PATTERN.matcher(id).matches()) {
            UUID uuid = UUID.fromString(id);
            uuidCache.put(id, uuid);
            return uuid;
        }
        
        String oldId = id;
        id = id.substring(0, 8) + "-" +
                id.substring(8, 12) + "-" +
                id.substring(12, 16) + "-" +
                id.substring(16, 20) + "-" +
                id.substring(20, 32);
        UUID uuid = UUID.fromString(id);
        uuidCache.put(oldId, uuid);
        return uuid;
    }
}
