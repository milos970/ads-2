package com.milos970.structure;

import java.util.List;
import java.util.Optional;

public interface Tree<K extends Comparable< ? super K>, V>
{
    V insert(K key, V Value);
    Optional<V> find(K key);
    Optional<V> delete(K key);
    K min();
    K max();
    void clear();
    int size();

    List<V> intervalSearch(K keyA, K keyB);


    interface Node<K,V> {
        void setValue(V value);
        void setKey(K key);
        K key();
        V value();
    }
}
