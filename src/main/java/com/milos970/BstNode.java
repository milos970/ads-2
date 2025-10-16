package com.milos970;

public class BstNode<K extends Comparable<K>,V> extends Node<K,V> {

    private BstNode<K, V> left;
    private BstNode<K, V> right;
    private BstNode<K, V> parent;

    public BstNode(K key, V value) {
        super(key, value);
    }

    public void setParent(BstNode<K,V> node) {
        this.parent = node;
    }

    public BstNode<K,V> parent() {
        return this.parent;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public boolean hasLeftSon() {
        return this.left != null;
    }

    public boolean hasRightSon() {
        return this.right != null;
    }

    public BstNode<K,V> leftSon() {
        return this.left;
    }

    public BstNode<K,V> rightSon() {
        return this.right;
    }

    public void setLeftSon(BstNode<K,V> node) {
        if (node != null) {
            node.setParent(this);
        }
        this.left = node;
    }

    public void setRightSon(BstNode<K,V> node) {
        if (node != null) {
            node.setParent(this);
        }
        this.right = node;
    }

    public void removeLeftSon() {
        if (this.left != null) {
            this.left.setParent(null);
        }
        this.left = null;
    }

    public void removeRightSon() {
        if (this.right != null) {
            this.right.setParent(null);
        }
        this.right = null;
    }

    @Override
    public String toString() {
        return "BstNode{" +
                "left=" + left +
                ", right=" + right +
                ", parent=" + parent +
                ", key=" + key +
                ", value=" + value +
                '}';
    }
}
