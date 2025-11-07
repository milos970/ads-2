package com.milos970;

import com.milos970.structure.Tree;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;

import java.util.Optional;

public class Int2ObjectRBTreeMapWrapper<K extends Comparable<K>, V> implements Tree<Integer,V> {

    private final Int2ObjectRBTreeMap map;

    public Int2ObjectRBTreeMapWrapper()
    {
        this.map = new Int2ObjectRBTreeMap<>();
    }

    @Override
    public V insert(Integer key, V value) {
        return (V) this.map.put(key, value);
    }

    @Override
    public Optional<V> find(Integer key) {
        V value = (V) this.map.get(key);
        return Optional.ofNullable(value);
    }

    @Override
    public V delete(Integer key) {
        return (V) this.map.remove(key);
    }

    @Override
    public Integer min() {
        return this.map.firstIntKey();
    }

    @Override
    public Integer max() {
        return this.map.lastIntKey();
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public int size() {
        return this.map.size();
    }
}
