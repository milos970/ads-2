package com.milos970;

public abstract class BinaryTree<K extends Comparable<K>, V> extends Tree {
    private Node<K,V> root;


    protected abstract Node<K,V> findNode(K key);
    protected abstract void remove(K key);
    protected abstract void insert(K key, V value);

    protected Node<K,V> root() {
        return this.root;
    }


    protected static class BinaryNode<K extends Comparable<K>,V> {
        private K key;
        private V value;
        private BinaryNode<K,V> leftSon;
        private BinaryNode<K,V> rightSon;
        private BinaryNode<K,V> parent;

        protected BinaryNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        protected void setLeftSon(BinaryNode<K,V> node) {
            this.leftSon = node;
        }

        protected void setRightSon(BinaryNode<K,V> node) {
            this.rightSon = node;
        }

        protected BinaryNode<K,V> leftSon() {
            return this.leftSon;
        }

        protected BinaryNode<K,V> rightSon() {
            return this.rightSon;
        }

        protected K key() {
            return this.key;
        }

        protected V value() {
            return this.value;
        }


    }


}
