package com.stardevllc.starcore.api.ui.gui;

import com.stardevllc.starcore.api.ui.element.Element;

public class Slot {
    
    protected final int row, column;
    protected char character;
    protected Element element;
    
    public Slot(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    public Slot(int row, int column, char character) {
        this(row, column);
        this.character = character;
    }
    
    public void setCharacter(char character) {
        this.character = character;
    }
    
    public char getCharacter() {
        return character;
    }
    
    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getColumn() {
        return column;
    }
}
