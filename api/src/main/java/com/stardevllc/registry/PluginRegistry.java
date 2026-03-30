package com.stardevllc.registry;

import com.stardevllc.starlib.event.EventDispatcher;
import com.stardevllc.starlib.objects.key.Key;
import com.stardevllc.starlib.registry.*;

import java.util.Map;
import java.util.Set;

public class PluginRegistry<V> extends AbstractRegistry<V> {
    
    public PluginRegistry(Class<V> valueType, Key id, String name, Map<PluginKey, V> backingMap, IRegistry<? super V> parentRegistry, boolean frozen, EventDispatcher dispatcher, Set<Flag> flags) {
        super(valueType, id, name, backingMap, parentRegistry, frozen, dispatcher, flags);
    }
    
    public PluginRegistry(Class<V> valueType, Key id, String name, Map<PluginKey, V> backingMap, Flag[] flags) {
        super(valueType, id, name, backingMap, flags);
    }
    
    public PluginRegistry(Class<V> valueType, Key id, String name, Map<PluginKey, V> backingMap, IRegistry<? super V> parentRegistry, Flag[] flags) {
        super(valueType, id, name, backingMap, parentRegistry, flags);
    }
    
    public PluginRegistry(Class<V> valueType, Map<PluginKey, V> backingMap) {
        super(valueType, backingMap);
    }
    
    public PluginRegistry(Class<V> valueType, Key id, Map<PluginKey, V> backingMap) {
        super(valueType, id, backingMap);
    }
    
    public PluginRegistry(Class<V> valueType, String name, Map<PluginKey, V> backingMap) {
        super(valueType, name, backingMap);
    }
    
    @Override
    public V register(Key key, V value) {
        if (!(key instanceof PluginKey)) {
            throw new IllegalArgumentException("You must use a PluginKey to register objects to this Registry");
        }
        
        return super.register(key, value);
    }
}