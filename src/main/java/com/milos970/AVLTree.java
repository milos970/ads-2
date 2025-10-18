package com.milos970;

import java.util.NoSuchElementException;

public final class AVLTree<K extends Comparable<K>,V> extends BinarySearchTree<K,V>
{
    private void leftRotation(AvlNode<K,V> node)
    {
        AvlNode<K,V> rightSon = (AvlNode<K,V>) node.rightSon();
        node.setRightSon(rightSon.leftSon());

        if (rightSon.leftSon() != null) {
            rightSon.leftSon().setParent(node);
        }

        rightSon.setLeftSon(node);

        node.height = calculateHeight(node);
        rightSon.height = calculateHeight(rightSon);

        rightSon.setParent(node.parent());
        node.setParent(rightSon);

        if (node == super.root) {
            this.root = rightSon;
            rightSon.setParent(null);
        }
    }

    private void rightRotation(AvlNode<K,V> node) {
        AvlNode<K,V> leftSon = (AvlNode<K,V>) node.leftSon();

        node.setLeftSon(leftSon.rightSon());
        if (leftSon.rightSon() != null) {
            leftSon.rightSon().setParent(node);
        }


        leftSon.setRightSon(node);


        node.height = calculateHeight(node);
        leftSon.height = calculateHeight(leftSon);


        leftSon.setParent(node.parent());
        node.setParent(leftSon);

        if (node == super.root) {
            super.root = leftSon;
            leftSon.setParent(null);
        }
    }




    public V insert(K key, V value) {
        AvlNode<K,V> current = (AvlNode<K, V>) super.insertNode(new AvlNode<>(key, value));
        AvlNode<K,V> parent = (AvlNode<K, V>)current.parent();

        while(parent != null) {

            parent.height = calculateHeight(parent);
            int balance = calculateBalance(parent);

            AvlNode<K, V> next = null;
            if (balance < -1)
            {
                if (calculateBalance((AvlNode<K, V>) parent.rightSon()) > 0)
                {
                    this.rightRotation((AvlNode<K, V>) parent.rightSon());
                }
                next = (AvlNode<K, V>) parent.parent();
                this.leftRotation(parent);
                break;


            }
            if (balance > 1)
            {

                if (calculateBalance((AvlNode<K, V>) parent.leftSon()) < 0)
                {
                    this.leftRotation((AvlNode<K, V>) parent.leftSon());
                }
                next = (AvlNode<K, V>) parent.parent();
                this.rightRotation(parent);
                break;
            }

            parent = next;
        }

        return current.value;
    }

    public V delete(K key) {
        AvlNode<K,V> node = (AvlNode<K, V>) super.findNode(key).orElseThrow(NoSuchElementException::new);
        AvlNode<K,V> predecessor = null;


        if (node.hasLeftSon() && node.hasRightSon()) {

            AvlNode<K,V> current = (AvlNode<K, V>) node.rightSon();

            while (current.hasLeftSon()) {
                current = (AvlNode<K, V>) current.leftSon();
            }

            node.key = current.key;
            node.value = current.value;

            predecessor = (AvlNode<K, V>) current.parent();

            if (current.hasRightSon()) {
                super.removeOneChild(current);
            } else {
                super.removeLeaf(current);
            }

        } else

        if (node.hasRightSon() || node.hasLeftSon() ) {
            predecessor = (AvlNode<K, V>) node.rightSon();
            super.removeOneChild(node);
        } else {
            predecessor = (AvlNode<K, V>) node.parent();
            super.removeLeaf(node);
        }



        int balance = -4;

        while ( (balance != 1 || balance != -1) && predecessor != null) {

            predecessor.height = calculateHeight(predecessor);
            balance = calculateBalance(predecessor);
            AvlNode<K, V> next = null;
            if (balance < -1)
            {
                if (calculateBalance((AvlNode<K, V>) predecessor.rightSon()) > 0)
                {
                    this.rightRotation((AvlNode<K, V>) predecessor.rightSon());
                }
                next = (AvlNode<K, V>) predecessor.parent();
                this.leftRotation(predecessor);
                break;


            }
            if (balance > 1)
            {

                if (calculateBalance((AvlNode<K, V>) predecessor.leftSon()) < 0)
                {
                    this.leftRotation((AvlNode<K, V>) predecessor.leftSon());
                }
                next = (AvlNode<K, V>) predecessor.parent();
                this.rightRotation(predecessor);
                break;
            }


            predecessor = (AvlNode<K, V>)predecessor.parent();
        }

        super.size--;
        return node.value;

    }


    private static <K extends Comparable<K>,V> int calculateHeight(AvlNode<K,V> node) {
        int leftHeight = node == null ? -1 : (node.hasLeftSon()) ? ((AvlNode<K, V>) node.leftSon()).height : -1;
        int rightHeight = node == null ? -1 : (node.hasRightSon()) ? ((AvlNode<K, V>) node.rightSon()).height : -1;
        return Math.max(leftHeight, rightHeight) + 1;
    }

    private static <K extends Comparable<K>,V> int calculateBalance(AvlNode<K,V> node) {
        return calculateHeight((AvlNode<K, V>)node.leftSon()) - calculateHeight((AvlNode<K, V>)node.rightSon());
    }


    private static class AvlNode<K extends Comparable<K>,V> extends BstNode<K ,V> {
        private int height;

        public AvlNode(K key, V value) {
            super(key, value);
        }
    }

}
