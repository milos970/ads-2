package com.milos970;

public class AvlNode<K extends Comparable<K>,V> extends BstNode<K,V> {

    private int balanced;

    public AvlNode(K key, V value, int balanced) {
        super(key, value);
        this.balanced = balanced;
    }

    public void changeBalance(int balanced) {
        this.balanced = balanced;
    }

    public int getBalanced() {
        return this.balanced;
    }

}
