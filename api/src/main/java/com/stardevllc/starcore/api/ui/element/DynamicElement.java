package com.stardevllc.starcore.api.ui.element;

public class DynamicElement extends Element {
    
    private Element element;
    
    public DynamicElement() {
        iconCreator(player -> {
            if (element == null) {
                return null;
            }
            
            return element.getIconCreator().apply(player);
        });
    }
    
    public void setElement(Element element) {
        this.element = element;
    }
    
    public Element getElement() {
        return element;
    }
}