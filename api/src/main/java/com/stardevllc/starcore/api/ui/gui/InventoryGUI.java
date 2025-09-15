package com.stardevllc.starcore.api.ui.gui;

import com.stardevllc.starcore.api.ui.element.DynamicElement;
import com.stardevllc.starcore.api.ui.element.Element;
import com.stardevllc.starcore.api.ui.element.button.Button;
import com.stardevllc.starcore.api.ui.handler.InventoryHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class InventoryGUI implements InventoryHandler {
    
    protected String title;
    protected Inventory inventory;
    protected final Slot[] slots = new Slot[54]; //6 rows of 9
    protected int rows;
    protected final UUID playerUUID;
    
    protected final String[] slotPattern = new String[6];
    
    protected final Map<Character, Element> patternMap = new HashMap<>();
    
    protected char dynamicChar;
    
    protected List<Element> dynamicElements = new LinkedList<>();
    
    protected int page;
    private int dynamicSlots;
    
    public InventoryGUI(String title, UUID player, String[] slotPattern) {
        this.title = title;
        this.playerUUID = player;
        System.arraycopy(slotPattern, 0, this.slotPattern, 0, slotPattern.length);
        
        for (String row : this.slotPattern) {
            if (row != null && !row.isEmpty()) {
                rows++;
            }
        }
        
        calcuateSlots();
        
        this.inventory = Bukkit.createInventory(null, rows * 9, ChatColor.translateAlternateColorCodes('&', title));
    }
    
    public boolean setSlotPattern(String[] slotPattern) {
        System.arraycopy(new String[6], 0, this.slotPattern, 0, 6);
        System.arraycopy(slotPattern, 0, this.slotPattern, 0, slotPattern.length);
        int oldRows = this.rows;
        rows = 0;
        for (String row : this.slotPattern) {
            if (row != null && !row.isEmpty()) {
                rows++;
            }
        }
        
        calcuateSlots();
        setDynamicChar(this.dynamicChar);
        
        if (rows != oldRows) {
            this.inventory = Bukkit.createInventory(null, rows * 9, ChatColor.translateAlternateColorCodes('&', title));
            return true;
        }
        
        update();
        return false;
    }
    
    private void calcuateSlots() {
        Arrays.fill(this.slots, null);
        
        for (int row = 0; row < this.slotPattern.length; row++) {
            String rowPattern = this.slotPattern[row];
            if (rowPattern == null) {
                continue;
            }
            
            for (int column = 0; column < rowPattern.length(); column++) {
                char columnChar = rowPattern.charAt(column);
                int slotIndex = row * 9 + column;
                if (slotIndex < this.slots.length) {
                    this.slots[slotIndex] = new Slot(row, column, columnChar);
                }
            }
        }
    }
    
    public void createItems() {
        
    }
    
    public void nextPage() {
        int currentPageIndex = (this.page + 1) * this.dynamicSlots;
        
        if (this.dynamicElements.size() > currentPageIndex) {
            this.page++;
        }
    }
    
    public void previousPage() {
        this.page = Math.max(0, this.page - 1);
    }
    
    public void setPage(int page) {
        this.page = Math.max(0, page);
    }
    
    public void update() {
        if (this.playerUUID == null) {
            return;
        }
        
        Player player = Bukkit.getPlayer(this.playerUUID);
        if (player == null) {
            return;
        }
        
        if (!inventory.getViewers().contains(player)) {
            return;
        }
        
        for (Slot slot : this.slots) {
            if (slot == null) {
                continue;
            }
            
            if (slot.getElement() != null) {
                if (!slot.getElement().isDeleteOnUpdate()) {
                    continue;
                }
            }
            slot.setElement(null);
        }
        
        createItems();
        decorate(player);
    }
    
    public void setElement(char c, Element element) {
        this.patternMap.put(c, element);
    }
    
    public void setDynamicChar(char dynamicChar) {
        this.dynamicChar = dynamicChar;
        
        this.dynamicSlots = 0;
        for (Slot slot : this.slots) {
            if (slot != null) {
                if (slot.getCharacter() == this.dynamicChar) {
                    this.dynamicSlots++;
                }   
            }
        }
        
        this.page = 0;
    }
    
    public void addElement(Element element) {
        this.dynamicElements.add(element);
    }
    
    public void decorate(Player player) {
        int dynamicPageIndex = 0;
        for (int index = 0; index < this.slots.length; index++) {
            Slot slot = slots[index];
            if (slot == null) {
                continue;
            }
            
            String rowPattern = slotPattern[slot.getRow()];
            
            if (rowPattern == null) {
                continue;
            }
            
            char correctChar = rowPattern.charAt(slot.getColumn());
            if (slot.getCharacter() != correctChar) {
                slot.setCharacter(correctChar);
                slot.setElement(null);
            }
            
            if (slot.getCharacter() == this.dynamicChar) {
                DynamicElement element = slot.getElement() == null ? new DynamicElement() : slot.getElement() instanceof DynamicElement ? (DynamicElement) slot.getElement() : new DynamicElement();
                
                int dynamicIndex = this.page * this.dynamicSlots + dynamicPageIndex;
                
                if (dynamicIndex < this.dynamicElements.size()) {
                    element.setElement(this.dynamicElements.get(dynamicIndex));
                    dynamicPageIndex++;
                }
                slot.setElement(element);
            } else {
                slot.setElement(patternMap.get(slot.getCharacter()));
            }
            
            Element element = slot.getElement();
            if (element != null) {
                this.inventory.setItem(index, element.getIconCreator().apply(player));
            } else {
                this.inventory.setItem(index, null);
            }
        }
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    @Override
    public void onClick(InventoryClickEvent e) {
        if (e.getRawSlot() < 0) {
            return;
        }
        
        if (e.getSlot() != e.getRawSlot()) {
            return;
        }
        
        if (this.slots.length < e.getRawSlot()) {
            return;
        }
        
        Slot slot = this.slots[e.getRawSlot()];
        if (slot == null) {
            return;
        }
        
        Element element = slot.getElement();
        if (element == null) {
            return;
        }
        
        if (element instanceof DynamicElement dynamicElement && dynamicElement.getElement() != null) {
            element = dynamicElement.getElement();
        }
        
        e.setCancelled(!element.isAllowInsert());
        
        if (element instanceof Button button) {
            if (button.getEventConsumer() != null) {
                button.getEventConsumer().accept(e);
                button.playSound((Player) e.getWhoClicked());
            }
        }
    }
    
    @Override
    public void onDrag(InventoryDragEvent e) {
        
    }
    
    @Override
    public void onOpen(InventoryOpenEvent e) {
        decorate((Player) e.getPlayer());
    }
    
    @Override
    public void onClose(InventoryCloseEvent e) {
        
    }
}
