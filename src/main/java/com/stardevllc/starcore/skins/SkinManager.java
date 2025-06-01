package com.stardevllc.starcore.skins;

import com.stardevllc.helper.StringHelper;
import com.stardevllc.mojang.MojangAPI;
import com.stardevllc.mojang.MojangProfile;
import com.stardevllc.starcore.base.model.Skin;

import java.util.*;

public class SkinManager {
//    private static final String MINESKIN_URL = "https://api.mineskin.org/generate/url?url=%s";
    
    private Map<String, Skin> skins = new HashMap<>();
    
//    public Skin getFromImage(String imageUrl) {
//        Skin skin = this.skins.get(imageUrl);
//        if (skin != null) {
//            return skin;
//        }
//        
//        try {
//            JsonObject object = getObjectFromURL(String.format(MINESKIN_URL, imageUrl));
//            
//            if (object.has("error")) {
//                return null;
//            }
//            
//            JsonObject skinInfo = object.getAsJsonObject("data").getAsJsonObject("texture");
//            
//            skin = new Skin(null, null, imageUrl, skinInfo.get("value").getAsString(), skinInfo.get("signature").getAsString());
//            this.skins.put(imageUrl, skin);
//            return skin;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        return null;
//    }
    
    public Skin getFromMojang(Object object) {
        if (object instanceof String str) {
            try {
                UUID uuid = StringHelper.toUUID(str);
                return getByUUID(uuid);
            } catch (Exception e) {
                return getByPlayerName(str);
            }
        } else if (object instanceof UUID uuid) {
            return getByUUID(uuid);
        } else {
            return null;
        }
    }
    
    public Skin getByPlayerName(String playerName) {
        if (this.skins.containsKey(playerName)) {
            return this.skins.get(playerName);
        }
        
        MojangProfile profile = MojangAPI.getProfile(playerName);
        
        if (profile == null) {
            return null;
        }
        
        Skin skin = new Skin(profile.getUniqueId(), profile.getName(), playerName, profile.getSkinValue(), profile.getSkinSignature());
        this.skins.put(profile.getUniqueId().toString(), skin);
        this.skins.put(profile.getName(), skin);
        return skin;
    }
    
    public Skin getByUUID(UUID playerId) {
        if (this.skins.containsKey(playerId.toString())) {
            return this.skins.get(playerId.toString());
        }
        
        MojangProfile profile = MojangAPI.getProfile(playerId);
        
        if (profile == null) {
            return null;
        }
        
        Skin skin = new Skin(playerId, profile.getName(), playerId.toString(), profile.getSkinValue(), profile.getSkinSignature());
        this.skins.put(profile.getUniqueId().toString(), skin);
        this.skins.put(profile.getName(), skin);
        return skin;
    }
}
