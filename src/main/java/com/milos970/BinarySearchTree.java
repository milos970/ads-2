package com.milos970;

import java.util.*;

public  class BinarySearchTree<K extends Comparable<K>,V>
{
    protected BstNode<K,V> root;
    protected int size = 0;


    protected BstNode<K,V> insertNode(BstNode<K,V> node) {

        if (this.size == 0) {
            this.root = node;
            this.size = 1;
            return this.root;
        }

        BstNode<K,V> current = root;
        
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

    public V insert(K key, V value) {
        BstNode<K,V> current = this.insertNode(new BstNode<>(key, value));
        return current.value;
    }


    protected Optional<BstNode<K,V>> findNode(K key) {
        BstNode<K,V> current = root;

        while(current != null) //nie stale current.key.compareToKey lebo by to porovnanie mohlo byt zlozite
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
        Optional<BstNode<K,V>> nodeOpt = findNode(key);
        return nodeOpt.map(node -> node.value);
    }

    protected void removeLeaf(BstNode<K,V> node) {
        if (!node.hasParent()) {
            this.root = null;
        } else if (node.parent().leftSon() == node) {
            node.parent().removeLeftSon();
        } else {
            node.parent().removeRightSon();
        }
    }

    protected void removeOneChild(BstNode<K,V> node) {

        BstNode<K,V> child = node.hasLeftSon() ? node.leftSon() : node.rightSon();
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

    protected List<BstNode<K,V>> inOrder() {
        Deque<BstNode<K,V>> stack = new ArrayDeque<>();
        List<BstNode<K,V>> nodes = new LinkedList<>();

        BstNode<K,V> current  = this.root;
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

    public void preOrder() {
        Deque<BstNode<K,V>> stack = new ArrayDeque<>();
        BstNode<K,V> current  = this.root;
        stack.push(current);

        while (!stack.isEmpty()) {

            current = stack.pop();


            if (current.hasLeftSon()) {
                stack.push(current.leftSon());
            }

            if (current.hasRightSon()) {
                stack.push(current.rightSon());
            }
        }
    }





    public V delete(K key) {
        BstNode<K, V> node = this.findNode(key)
                .orElseThrow(NoSuchElementException::new);
        V value = node.value;

        if (node.hasLeftSon() && node.hasRightSon()) {

            BstNode<K, V> successor = node.rightSon();
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


    public K min() {
        BstNode<K,V> node = this.root;
        while (node.hasLeftSon())
        {
            node = node.leftSon();
        }

        return node.key;
    }

    public K max() {
        BstNode<K,V> node = this.root;
        while (node.hasRightSon())
        {
            node = node.rightSon();
        }

        return node.key;
    }

    public void clear() {
        this.root = null;
        this.size = 0;
    }



    public int size() {
        return this.size;
    }
}
