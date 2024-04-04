package com.stardevllc.starcore.gui.gui;

import com.stardevllc.starcore.gui.element.Element;

/**
 * Utility class, not super useful now, but will be later <br>
 * Used to represent a slot
 */
public class Slot {
    
    protected final int index;
    protected Element element;

    public Slot(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}
