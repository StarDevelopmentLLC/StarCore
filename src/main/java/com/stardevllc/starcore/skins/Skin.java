package com.stardevllc.starcore.skins;

import java.util.Objects;

public class Skin {
    private String identifier;
    private String value;
    private String signature;

    public Skin(String identifier, String value, String signature) {
        this.identifier = identifier;
        this.value = value;
        this.signature = signature;
    }

    public String identifier() {
        return identifier;
    }

    public String value() {
        return value;
    }

    public String signature() {
        return signature;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Skin) obj;
        return Objects.equals(this.identifier, that.identifier) &&
                Objects.equals(this.value, that.value) &&
                Objects.equals(this.signature, that.signature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, value, signature);
    }

    @Override
    public String toString() {
        return "Skin[" +
                "identifier=" + identifier + ", " +
                "value=" + value + ", " +
                "signature=" + signature + ']';
    }

}