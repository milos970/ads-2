package com.milos970.structure;

import java.util.Optional;

public interface Tree<K extends Comparable<K>, V>
{
    V insert(K key, V Value);
    Optional<V> find(K key);
    V delete(K key);
    K min();
    K max();
    void clear();
    int size();
}
