package com.milos970;

public class AVLNode<K extends Comparable<K>,V> extends BstNode<K,V> {

    private int height;

    public AVLNode(K key, V value, int height) {
        super(key, value);
        this.height = height;
    }

    public void changeHeight(int heigh) {
        this.height = height;
    }

    public int height() {
        return this.height;
    }

}
