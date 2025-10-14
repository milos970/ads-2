package com.milos970;

import java.util.Optional;

public final class BinarySearchTree<K extends Comparable<K>,V> extends BinaryTree
{
    private BstNode<K,V> root;
    private long size = 0;


    public void insertRoot(BstNode<K,V> node) {
        this.root = node;
    }


    protected BstNode<K,V> insertNode(BstNode<K,V> node) {

        if (this.size == 0) {
            this.root = node;
            this.size = 1;
            return node;
        }
        BstNode<K,V> current = root;
        
        while (current.hasRightSon() || current.hasLeftSon())
        {
            int result = current.key.compareTo(node.key);

            if (result == 0) {
                current.value = node.value;
                return current;
            }

            if (result < 0) {
                if (current.hasRightSon())
                {
                    current = current.rightSon();
                } else {
                    current.setRightSon(node);
                    ++this.size;
                    return current;
                }
            } else {
                if (current.hasLeftSon())
                {
                    current = current.leftSon();
                }else {
                    current.setLeftSon(node);
                    ++this.size;
                    return current;
                }
            }
        }


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




    public Optional<V> delete(K key)
    {
        BstNode<K,V> node = this.findNode(key).get();

        if (this.root.equals(node)) {
            this.root = null;
            this.size = 0;
            return Optional.of(node.value);
        }


        BstNode parent = node.parent();

        if (node.hasLeftSon() && !node.hasRightSon()) {
            parent.setRightSon(node.leftSon());
        }

        if (!node.hasLeftSon() && node.hasRightSon()) {
            parent.setRightSon(node.rightSon());
        }

        if (node.hasLeftSon() && node.hasRightSon()) {

            BstNode<K,V> current = node.rightSon();

            while (current.hasLeftSon()) {
                current = current.leftSon();
            }

            parent = current.parent();

            if (current.hasRightSon()) {
                parent.setRightSon(current.rightSon());
            } else {
                parent.setLeftSon(null);
            }

            node.parent().setRightSon(current);


        }
        this.size--;
        return Optional.of(node.value);
    }


    @Override
    protected Node findNode(Comparable key) {
        return null;
    }

    @Override
    protected void remove(Comparable key) {

    }

    @Override
    protected void insert(Comparable key, Object value) {

    }
}
