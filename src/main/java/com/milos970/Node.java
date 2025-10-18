package com.milos970;

public abstract class Node<K,V>
{
    protected  K key;
    protected  V value;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
