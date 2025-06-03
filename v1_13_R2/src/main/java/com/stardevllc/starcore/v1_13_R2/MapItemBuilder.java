package com.stardevllc.starcore.v1_13_R2;

import com.stardevllc.starcore.api.XMaterial;
import com.stardevllc.starcore.base.itembuilder.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

public class MapItemBuilder extends ItemBuilder {
    
    static {
        ItemBuilder.META_TO_BUILDERS.put(MapMeta.class, MapItemBuilder.class);
    }
    
    private Color color;
    private MapView mapView;
    private boolean scaling;
    
    public MapItemBuilder() {
        super(XMaterial.MAP);
    }

    public MapItemBuilder(MapView mapView) {
        mapView(mapView);
    }
    
    protected static MapItemBuilder createFromItemStack(ItemStack itemStack) {
        MapItemBuilder builder = new MapItemBuilder();
        MapMeta meta = (MapMeta) itemStack.getItemMeta();
        builder.color(meta.getColor()).mapView(meta.getMapView()).scaling(meta.isScaling());
        return builder;
    }

    protected static MapItemBuilder createFromConfig(ConfigurationSection section) {
        return new MapItemBuilder();
    }

    @Override
    public void saveToConfig(ConfigurationSection section) {
        super.saveToConfig(section);
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
        MapMeta itemMeta = (MapMeta) super.createItemMeta();
        itemMeta.setColor(this.color);
        itemMeta.setMapView(this.mapView);
        itemMeta.setScaling(this.scaling);
        return itemMeta;
    }

    @Override
    public MapItemBuilder clone() {
        MapItemBuilder clone = (MapItemBuilder) super.clone();
        clone.color = this.color;
        clone.mapView = this.mapView;
        clone.scaling = this.scaling;
        return clone;
    }
}
