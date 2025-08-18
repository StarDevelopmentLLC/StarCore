package com.stardevllc.starcore.config;

import com.electronwill.nightconfig.core.*;
import com.electronwill.nightconfig.core.Config.Entry;
import com.electronwill.nightconfig.core.UnmodifiableCommentedConfig.CommentNode;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.stardevllc.starlib.helper.StringHelper;

import java.util.*;
import java.util.function.*;

public class Configuration {
    protected FileConfig fileConfig;
    protected ConfigSpec configSpec;
    
    public Configuration(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
        this.configSpec = new ConfigSpec();
    }
    
    public void load() {
        this.fileConfig.load();
    }
    
    public void save() {
        fileConfig.save();
    }
    
    public void addDefault(String path, Object value, String... commentLines) {
        String comment = StringHelper.join(commentLines, "\n");
        set(path, value);
        setComment(path, comment);
    }
    
    public String setComment(String path, String comment) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.setComment(path, comment);
        }
        
        return "";
    }
    
    public String setComment(List<String> path, String comment) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.setComment(path, comment);
        }
        
        return "";
    }
    
    public String removeComment(String path) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.removeComment(path);
        }
        
        return "";
    }
    
    public String removeComment(List<String> path) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.removeComment(path);
        }
        
        return "";
    }
    
    public void clearComments() {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            commentedConfig.clearComments();
        }
    }
    
    public void putAllComments(Map<String, CommentNode> comments) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            commentedConfig.putAllComments(comments);
        }
    }
    
    public void putAllComments(UnmodifiableCommentedConfig commentedConfig) {
        if (fileConfig instanceof CommentedConfig cc) {
            cc.putAllComments(commentedConfig);
        }
    }
    
    public UnmodifiableConfig unmodifiable() {
        return fileConfig.unmodifiable();
    }
    
    public Config checked() {
        return fileConfig.checked();
    }
    
    public String getComment(String path) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.getComment(path);
        }
        
        return "";
    }
    
    public String getComment(List<String> path) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.getComment(path);
        }
        
        return "";
    }
    
    public Optional<String> getOptionalComment(String path) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.getOptionalComment(path);
        }
        return Optional.empty();
    }
    
    public Optional<String> getOptionalComment(List<String> path) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.getOptionalComment(path);
        }
        return Optional.empty();
    }
    
    public boolean containsComment(String path) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.containsComment(path);
        }
        return false;
    }
    
    public boolean containsComment(List<String> path) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.containsComment(path);            
        }
        return false;
    }
    
    public Map<String, CommentNode> getComments() {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            return commentedConfig.getComments();
        }
        return Map.of();
    }
    
    public void getComments(Map<String, CommentNode> destination) {
        if (fileConfig instanceof CommentedConfig commentedConfig) {
            commentedConfig.getComments(destination);
        }
    }
    
    public <T> T set(String path, Object value) {
        return fileConfig.set(path, value);
    }
    
    public <T> T set(List<String> path, Object value) {
        return fileConfig.set(path, value);
    }
    
    public boolean add(List<String> path, Object value) {
        return fileConfig.add(path, value);
    }
    
    public boolean add(String path, Object value) {
        return fileConfig.add(path, value);
    }
    
    public void addAll(UnmodifiableConfig config) {
        fileConfig.addAll(config);
    }
    
    public void putAll(UnmodifiableConfig config) {
        fileConfig.addAll(config);
    }
    
    public <T> T remove(String path) {
        return fileConfig.remove(path);
    }
    
    public <T> T remove(List<String> path) {
        return fileConfig.remove(path);
    }
    
    public void removeAll(UnmodifiableConfig config) {
        fileConfig.removeAll(config);
    }
    
    public void clear() {
        fileConfig.clear();
    }
    
    public <T> T get(String path) {
        return fileConfig.get(path);
    }
    
    public <T> T get(List<String> path) {
        return fileConfig.get(path);
    }
    
    public <T> T getRaw(String path) {
        return fileConfig.getRaw(path);
    }
    
    public <T> T getRaw(List<String> path) {
        return fileConfig.getRaw(path);
    }
    
    public <T> Optional<T> getOptional(String path) {
        return fileConfig.getOptional(path);
    }
    
    public <T> Optional<T> getOptional(List<String> path) {
        return fileConfig.getOptional(path);
    }
    
    public <T> T getOrElse(String path, T defaultValue) {
        return fileConfig.getOrElse(path, defaultValue);
    }
    
    public <T> T getOrElse(List<String> path, T defaultValue) {
        return fileConfig.getOrElse(path, defaultValue);
    }
    
    public <T> T getOrElse(List<String> path, Supplier<T> defaultValueSupplier) {
        return fileConfig.getOrElse(path, defaultValueSupplier);
    }
    
    public <T> T getOrElse(String path, Supplier<T> defaultValueSupplier) {
        return fileConfig.getOrElse(path, defaultValueSupplier);
    }
    
    public <T extends Enum<T>> T getEnum(String path, Class<T> enumType, EnumGetMethod method) {
        return fileConfig.getEnum(path, enumType, method);
    }
    
    public <T extends Enum<T>> T getEnum(String path, Class<T> enumType) {
        return fileConfig.getEnum(path, enumType);
    }
    
    public <T extends Enum<T>> T getEnum(List<String> path, Class<T> enumType, EnumGetMethod method) {
        return fileConfig.getEnum(path, enumType, method);
    }
    
    public <T extends Enum<T>> T getEnum(List<String> path, Class<T> enumType) {
        return fileConfig.getEnum(path, enumType);
    }
    
    public <T extends Enum<T>> Optional<T> getOptionalEnum(String path, Class<T> enumType, EnumGetMethod method) {
        return fileConfig.getOptionalEnum(path, enumType, method);
    }
    
    public <T extends Enum<T>> Optional<T> getOptionalEnum(String path, Class<T> enumType) {
        return fileConfig.getOptionalEnum(path, enumType);
    }
    
    public <T extends Enum<T>> Optional<T> getOptionalEnum(List<String> path, Class<T> enumType, EnumGetMethod method) {
        return fileConfig.getOptionalEnum(path, enumType, method);
    }
    
    public <T extends Enum<T>> Optional<T> getOptionalEnum(List<String> path, Class<T> enumType) {
        return fileConfig.getOptionalEnum(path, enumType);
    }
    
    public <T extends Enum<T>> T getEnumOrElse(String path, T defaultValue, EnumGetMethod method) {
        return fileConfig.getEnumOrElse(path, defaultValue, method);
    }
    
    public <T extends Enum<T>> T getEnumOrElse(String path, T defaultValue) {
        return fileConfig.getEnumOrElse(path, defaultValue);
    }
    
    public <T extends Enum<T>> T getEnumOrElse(List<String> path, T defaultValue, EnumGetMethod method) {
        return fileConfig.getEnumOrElse(path, defaultValue, method);
    }
    
    public <T extends Enum<T>> T getEnumOrElse(List<String> path, T defaultValue) {
        return fileConfig.getEnumOrElse(path, defaultValue);
    }
    
    public <T extends Enum<T>> T getEnumOrElse(String path, Class<T> enumType, EnumGetMethod method, Supplier<T> defaultValueSupplier) {
        return fileConfig.getEnumOrElse(path, enumType, method, defaultValueSupplier);
    }
    
    public <T extends Enum<T>> T getEnumOrElse(String path, Class<T> enumType, Supplier<T> defaultValueSupplier) {
        return fileConfig.getEnumOrElse(path, enumType, defaultValueSupplier);
    }
    
    public <T extends Enum<T>> T getEnumOrElse(List<String> path, Class<T> enumType, EnumGetMethod method, Supplier<T> defaultValueSupplier) {
        return fileConfig.getEnumOrElse(path, enumType, method, defaultValueSupplier);
    }
    
    public <T extends Enum<T>> T getEnumOrElse(List<String> path, Class<T> enumType, Supplier<T> defaultValueSupplier) {
        return fileConfig.getEnumOrElse(path, enumType, defaultValueSupplier);
    }
    
    public int getInt(String path) {
        return fileConfig.getInt(path);
    }
    
    public int getInt(List<String> path) {
        return fileConfig.getInt(path);
    }
    
    public OptionalInt getOptionalInt(String path) {
        return fileConfig.getOptionalInt(path);
    }
    
    public OptionalInt getOptionalInt(List<String> path) {
        return fileConfig.getOptionalInt(path);
    }
    
    public int getIntOrElse(String path, int defaultValue) {
        return fileConfig.getIntOrElse(path, defaultValue);
    }
    
    public int getIntOrElse(List<String> path, int defaultValue) {
        return fileConfig.getIntOrElse(path, defaultValue);
    }
    
    public int getIntOrElse(String path, IntSupplier defaultValueSupplier) {
        return fileConfig.getIntOrElse(path, defaultValueSupplier);
    }
    
    public int getIntOrElse(List<String> path, IntSupplier defaultValueSupplier) {
        return fileConfig.getIntOrElse(path, defaultValueSupplier);
    }
    
    public long getLong(String path) {
        return fileConfig.getLong(path);
    }
    
    public long getLong(List<String> path) {
        return fileConfig.getLong(path);
    }
    
    public OptionalLong getOptionalLong(String path) {
        return fileConfig.getOptionalLong(path);
    }
    
    public OptionalLong getOptionalLong(List<String> path) {
        return fileConfig.getOptionalLong(path);
    }
    
    public long getLongOrElse(String path, long defaultValue) {
        return fileConfig.getLongOrElse(path, defaultValue);
    }
    
    public long getLongOrElse(List<String> path, long defaultValue) {
        return fileConfig.getLongOrElse(path, defaultValue);
    }
    
    public long getLongOrElse(String path, LongSupplier defaultValueSupplier) {
        return fileConfig.getLongOrElse(path, defaultValueSupplier);
    }
    
    public long getLongOrElse(List<String> path, LongSupplier defaultValueSupplier) {
        return fileConfig.getLongOrElse(path, defaultValueSupplier);
    }
    
    public byte getByte(String path) {
        return fileConfig.getByte(path);
    }
    
    public byte getByte(List<String> path) {
        return fileConfig.getByte(path);
    }
    
    public byte getByteOrElse(String path, byte defaultValue) {
        return fileConfig.getByteOrElse(path, defaultValue);
    }
    
    public byte getByteOrElse(List<String> path, byte defaultValue) {
        return fileConfig.getByteOrElse(path, defaultValue);
    }
    
    public short getShort(String path) {
        return fileConfig.getShort(path);
    }
    
    public short getShort(List<String> path) {
        return fileConfig.getShort(path);
    }
    
    public short getShortOrElse(String path, short defaultValue) {
        return fileConfig.getShortOrElse(path, defaultValue);
    }
    
    public short getShortOrElse(List<String> path, short defaultValue) {
        return fileConfig.getShortOrElse(path, defaultValue);
    }
    
    public char getChar(String path) {
        return fileConfig.getChar(path);
    }
    
    public char getChar(List<String> path) {
        return fileConfig.getChar(path);
    }
    
    public char getCharOrElse(String path, char defaultValue) {
        return fileConfig.getCharOrElse(path, defaultValue);
    }
    
    public char getCharOrElse(List<String> path, char defaultValue) {
        return fileConfig.getCharOrElse(path, defaultValue);
    }
    
    public boolean contains(String path) {
        return fileConfig.contains(path);
    }
    
    public boolean contains(List<String> path) {
        return fileConfig.contains(path);
    }
    
    public boolean isNull(String path) {
        return fileConfig.isNull(path);
    }
    
    public boolean isNull(List<String> path) {
        return fileConfig.isNull(path);
    }
    
    public int size() {
        return fileConfig.size();
    }
    
    public boolean isEmpty() {
        return fileConfig.isEmpty();
    }
    
    public Set<? extends Entry> entrySet() {
        return fileConfig.entrySet();
    }
    
    public ConfigFormat<?> configFormat() {
        return fileConfig.configFormat();
    }
    
    public <T> T apply(String path) {
        return fileConfig.apply(path);
    }
    
    public <T> T apply(List<String> path) {
        return fileConfig.apply(path);
    }
    
    public Config createSubConfig() {
        return fileConfig.createSubConfig();
    }
    
    public void update(String path, Object value) {
        fileConfig.update(path, value);
    }
    
    public void update(List<String> path, Object value) {
        fileConfig.update(path, value);
    }
}