package com.milos970.structure;

import java.util.*;

public  class BSTree<K extends Comparable<K>,V> implements Tree<K,V>
{
    protected BSTNode<K,V> root;
    protected int size;


    protected BSTNode<K,V> insertNode(BSTNode<K,V> node) {

        if (this.size == 0) {
            this.root = node;
            this.size = 1;
            return this.root;
        }

        BSTNode<K,V> current = root;
        
        while (true)
        {
            int result = current.key.compareTo(node.key);

            if (result == 0) {
                current.value = node.value;
                break;
            }

            if (result < 0) {
                if (current.hasRightSon())
                {
                    current = current.rightSon();
                } else {
                    current.setRightSon(node);
                    this.size++;
                    break;
                }
            } else {
                if (current.hasLeftSon())
                {
                    current = current.leftSon();
                }else {
                    current.setLeftSon(node);
                    this.size++;
                    break;
                }
            }
        }

        return node;
    }

    @Override
    public V insert(K key, V value) {
        BSTNode<K,V> current = this.insertNode(new BSTNode<>(key, value));
        return current.value;
    }


    protected Optional<BSTNode<K,V>> findNode(K key) {
        BSTNode<K,V> current = root;

        while(current != null)
        {
            int result = current.key.compareTo(key);

            if (result == 0)
            {
                return Optional.of(current);
            }

            if (result < 0)
            {
                current = current.hasRightSon() ? current.rightSon() : null;
            } else {
                current = current.hasLeftSon() ? current.leftSon() : null;
            }
        }

        return Optional.empty();
    }


    public Optional<V> find(K key) {
        Optional<BSTNode<K,V>> nodeOpt = findNode(key);
        return nodeOpt.map(node -> node.value);
    }

    protected void removeLeaf(BSTNode<K,V> node) {
        if (!node.hasParent()) {
            this.root = null;
        } else if (node.parent().leftSon() == node) {
            node.parent().removeLeftSon();
        } else {
            node.parent().removeRightSon();
        }
    }

    protected void removeOneChild(BSTNode<K,V> node) {

        BSTNode<K,V> child = node.hasLeftSon() ? node.leftSon() : node.rightSon();
        if (node.hasParent()) {
            if (node.parent().leftSon() == node) {
                node.parent().setLeftSon(child);
                child.setParent(node.parent());
            } else {
                node.parent().setRightSon(child);
            }

        } else {
            this.root = child;
            this.root.setParent(null);
        }




    }

    protected List<BSTNode<K, V>> inOrder() {
        Deque<BSTNode<K,V>> stack = new ArrayDeque<>();
        List<BSTNode<K,V>> nodes = new LinkedList<>();

        BSTNode<K,V> current  = this.root;
        while (current != null || !stack.isEmpty())
        {
            while(current != null) {
                stack.push(current);
                current = current.leftSon();
            }

            current = stack.pop();
            nodes.add(current);
            current = current.rightSon();
        }
        return nodes;
    }


    @Override
    public V delete(K key) {
        BSTNode<K, V> node = this.findNode(key)
                .orElseThrow(NoSuchElementException::new);
        V value = node.value;

        if (node.hasLeftSon() && node.hasRightSon()) {

            BSTNode<K, V> successor = node.rightSon();
            while (successor.hasLeftSon()) {
                successor = successor.leftSon();
            }

            node.key = successor.key;
            node.value = successor.value;


            if (successor.hasRightSon()) {
                this.removeOneChild(successor);
            } else {
                this.removeLeaf(successor);
            }

        } else if (node.hasLeftSon() || node.hasRightSon()) {

            this.removeOneChild(node);
        } else {

            this.removeLeaf(node);

        }

        this.size--;
        return value;
    }


    @Override
    public K min() {
        BSTNode<K,V> node = this.root;
        while (node.hasLeftSon())
        {
            node = node.leftSon();
        }

        return node.key;
    }

    @Override
    public K max() {
        BSTNode<K,V> node = this.root;
        while (node.hasRightSon())
        {
            node = node.rightSon();
        }

        return node.key;
    }


    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }



    public int size() {
        return this.size;
    }


    protected List<BSTNode<K, V>> intervalSearch(K key1, K key2) {

        return null;
    }





    public static class BSTNode<K extends Comparable<K>,V> extends Node<K,V> {

        private BSTNode<K, V> left;
        private BSTNode<K, V> right;
        private BSTNode<K, V> parent;

        public BSTNode(K key, V value) {
            super(key, value);
        }

        public void setParent(BSTNode<K,V> node) {
            this.parent = node;
        }

        public BSTNode<K,V> parent() {
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

        public BSTNode<K,V> leftSon() {
            return this.left;
        }

        public BSTNode<K,V> rightSon() {
            return this.right;
        }

        public void setLeftSon(BSTNode<K,V> node) {
            if (node != null) {
                node.setParent(this);
            }
            this.left = node;
        }

        public void setRightSon(BSTNode<K,V> node) {
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
}



