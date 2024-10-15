package com.stardevllc.starcore.skins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkinManager {
    private static final Gson GSON = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
    
    private static final String MINESKIN_URL = "https://api.mineskin.org/generate/url?url=%s";
    private static final String MOJANG_NAME_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String MOJANG_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";
    
    private Map<String, Skin> skins = new HashMap<>();
    
    public Skin getFromImage(String imageUrl) {
        Skin skin = this.skins.get(imageUrl);
        if (skin != null) {
            return skin;
        }
        
//        try {
//            HttpRequest request = new HttpPostRequest(String.format(MINESKIN_URL, imageUrl));
//            HttpResponse response = request.connect();
//    
//            JsonObject object = GSON.fromJson(response.response(), JsonObject.class);
//            
//            if (object.has("error")) {
//                return null;
//            }
//            
//            JsonObject skinInfo = object.getAsJsonObject("data").getAsJsonObject("texture");
//            
//            skin = new Skin(imageUrl, skinInfo.get("value").getAsString(), skinInfo.get("signature").getAsString());
//            this.skins.put(imageUrl, skin);
//            return skin;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        
        return null;
    }
    
    public Skin getFromMojang(String playerName) {
//        try {
//            HttpRequest request = new HttpGetRequest(String.format(MOJANG_NAME_URL, playerName));
//            HttpResponse response = request.connect();
//            
//            if (response.response() == null || response.response().isEmpty()) {
//                throw new IllegalArgumentException("Couldn't find the user " + playerName + ".");
//            }
//            
//            JsonObject object = GSON.fromJson(response.response(), JsonObject.class);
//            return getFromMojang(StringHelper.toUUID(object.get("id").getAsString()));
//        } catch (IOException e) {
//            return null;
//        }
        return null;
    }
    
    public Skin getFromMojang(UUID playerId) {
//        try {
//            HttpRequest request = new HttpGetRequest(String.format(MOJANG_URL, playerId.toString()));
//            HttpResponse response = request.connect();
//            
//            JsonObject object = GSON.fromJson(response.response(), JsonObject.class);
//            
//            if (object.has("errorMessage")) {
//                throw new IllegalArgumentException("Couldn't find the user " + playerId + ".");
//            }
//            
//            JsonObject properties = object.getAsJsonArray("properties").get(0).getAsJsonObject();
//    
//            Skin skin = new Skin(playerId.toString(), properties.get("value").getAsString(), properties.get("signature").getAsString());
//            this.skins.put(skin.identifier(), skin);
//            return skin;
//        } catch (IOException e) {
//            return null;
//        }
        
        return null;
    }
}
