package com.milos970.structure;

public abstract class Node<K,V>
{
    protected  K key;
    protected  V value;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
