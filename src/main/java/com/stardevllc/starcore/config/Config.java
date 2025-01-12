package com.stardevllc.starcore.config;

import com.stardevllc.config.Section;
import com.stardevllc.config.file.yaml.YamlConfig;
import com.stardevllc.config.serialization.ConfigSerializable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Config {

    protected YamlConfig config;
    protected File file;

    public Config(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.file = file;
        this.config = YamlConfig.loadConfiguration(file);
    }
    
    public void addDefault(String path, Object value, String... comments) {
        config.set(path, value);
        config.setComments(path, comments);
    }
    
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Set<String> getKeys() {
        return this.getKeys(false);
    }

    public Set<String> getKeys(boolean deep) {
        return config.getKeys(deep);
    }

    public Map<String, Object> getValues(boolean deep) {
        return config.getValues(deep);
    }

    public boolean contains(String path) {
        return config.contains(path);
    }

    public boolean contains(String path, boolean ignoreDefault) {
        return config.contains(path, ignoreDefault);
    }

    public boolean isSet(String path) {
        return config.isSet(path);
    }

    public String getCurrentPath() {
        return config.getCurrentPath();
    }

    public String getName() {
        return config.getName();
    }

    public com.stardevllc.config.Config getRoot() {
        return config.getRoot();
    }

    public Section getDefaultSection() {
        return config.getDefaultSection();
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public Object get(String path) {
        return config.get(path);
    }

    public Object get(String path, Object def) {
        return config.get(path, def);
    }

    public Section createSection(String path) {
        return config.createSection(path);
    }

    public Section createSection(String path, Map<?, ?> map) {
        return config.createSection(path, map);
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public String getString(String path, String def) {
        return config.getString(path, def);
    }

    public boolean isString(String path) {
        return config.isString(path);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    public boolean isInt(String path) {
        return config.isInt(path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    public boolean isBoolean(String path) {
        return config.isBoolean(path);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public double getDouble(String path, double def) {
        return config.getDouble(path, def);
    }

    public boolean isDouble(String path) {
        return config.isDouble(path);
    }


    public long getLong(String path) {
        return config.getLong(path);
    }

    public long getLong(String path, long def) {
        return config.getLong(path, def);
    }

    public boolean isLong(String path) {
        return config.isLong(path);
    }

    public List<?> getList(String path) {
        return config.getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        return config.getList(path, def);
    }

    public boolean isList(String path) {
        return config.isList(path);
    }

    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public List<Integer> getIntegerList(String path) {
        return config.getIntegerList(path);
    }

    public List<Boolean> getBooleanList(String path) {
        return config.getBooleanList(path);
    }

    public List<Double> getDoubleList(String path) {
        return config.getDoubleList(path);
    }

    public List<Float> getFloatList(String path) {
        return config.getFloatList(path);
    }

    public List<Long> getLongList(String path) {
        return config.getLongList(path);
    }

    public List<Byte> getByteList(String path) {
        return config.getByteList(path);
    }

    public List<Character> getCharacterList(String path) {
        return config.getCharacterList(path);
    }

    public List<Short> getShortList(String path) {
        return config.getShortList(path);
    }

    public List<Map<?, ?>> getMapList(String path) {
        return config.getMapList(path);
    }

    public <T> T getObject(String path, Class<T> clazz) {
        return config.getObject(path, clazz);
    }

    public <T> T getObject(String path, Class<T> clazz, T def) {
        return config.getObject(path, clazz, def);
    }

    public <T extends ConfigSerializable> T getSerializable(String path, Class<T> clazz) {
        return config.getSerializable(path, clazz);
    }

    public <T extends ConfigSerializable> T getSerializable(String path, Class<T> clazz, T def) {
        return config.getSerializable(path, clazz, def);
    }

    public Section getConfigurationSection(String path) {
        return config.getConfigurationSection(path);
    }

    public boolean isConfigurationSection(String path) {
        return config.isConfigurationSection(path);
    }

    public List<String> getComments(String path) {
        return config.getComments(path);
    }

    public List<String> getInlineComments(String path) {
        return config.getInlineComments(path);
    }

    public void setComments(String path, List<String> comments) {
        config.setComments(path, comments);
    }

    public void setComments(String path, String... comments) {
        config.setComments(path, comments);
    }

    public void setInlineComments(String path, List<String> comments) {
        config.setInlineComments(path, comments);
    }

    public void setInlineComments(String path, String... comments) {
        config.setInlineComments(path, comments);
    }

    public String toString() {
        return config.toString();
    }
}
