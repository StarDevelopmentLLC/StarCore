package com.stardevllc.starcore.v1_13_2.itembuilder;

import com.stardevllc.starcore.api.itembuilder.ItemBuilder;
import com.stardevllc.starmclib.XMaterial;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

public class MapItemBuilder extends ItemBuilder<MapItemBuilder, MapMeta> {
    
    private Color color;
    private MapView mapView;
    private boolean scaling;
    
    public MapItemBuilder() {
        super(XMaterial.MAP);
    }
    
    public MapItemBuilder(ItemStack itemStack) {
        super(itemStack);
        
        MapMeta itemMeta = (MapMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            this.color = itemMeta.getColor();
            this.mapView = itemMeta.getMapView();
            this.scaling = itemMeta.isScaling();
        }
    }
    
    public MapItemBuilder(MapItemBuilder builder) {
        super(builder);
        this.color = builder.color;
        this.mapView = builder.mapView;
        this.scaling = builder.scaling;
    }
    
    public MapItemBuilder(MapView mapView) {
        mapView(mapView);
    }
    
    public MapItemBuilder color(Color color) {
        this.color = color;
        return this;
    }
    
    public MapItemBuilder mapView(MapView mapView) {
        this.mapView = mapView;
        return this;
    }
    
    public MapItemBuilder scaling(boolean scaling) {
        this.scaling = scaling;
        return this;
    }

    @Override
    protected MapMeta createItemMeta() {
        MapMeta itemMeta = super.createItemMeta();
        itemMeta.setColor(this.color);
        itemMeta.setMapView(this.mapView);
        itemMeta.setScaling(this.scaling);
        return itemMeta;
    }

    @Override
    public MapItemBuilder clone() {
        return new MapItemBuilder(this);
    }
}
