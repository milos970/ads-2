package com.milos970;

import java.util.NoSuchElementException;

public final class AVLTree<K extends Comparable<K>,V> extends BinarySearchTree<K,V>
{
    private void leftRotation(AvlNode<K,V> node) {
        AvlNode<K,V> rightSon = (AvlNode<K,V>) node.rightSon();
        if (rightSon == null) {
            return;
        }

        AvlNode<K,V> subTree = (AvlNode<K,V>)rightSon.leftSon();
        rightSon.setLeftSon(node);
        node.setRightSon(subTree);

        if (subTree != null) {
            subTree.setParent(node);
        }
        rightSon.setParent(node.parent());
        node.setParent(rightSon);

        if (rightSon.parent() == null) {
            super.root = rightSon;
        }


        node.height = calculateHeight(node);
        rightSon.height = calculateHeight(rightSon);
    }


    private void rightRotation(AvlNode<K,V> node) {
        AvlNode<K,V> leftSon = (AvlNode<K,V>) node.leftSon();
        if (leftSon == null) {
            return;
        }

        AvlNode<K,V> subTree = (AvlNode<K,V>)leftSon.rightSon();
        leftSon.setRightSon(node);
        node.setLeftSon(subTree);

        if (subTree != null) {
            subTree.setParent(node);
        }
        leftSon.setParent(node.parent());
        node.setParent(leftSon);

        if (leftSon.parent() == null) {
            super.root = leftSon;
        }

        node.height = calculateHeight(node);
        leftSon.height = calculateHeight(leftSon);
    }





    public V insert(K key, V value) {
        AvlNode<K,V> current = (AvlNode<K, V>) super.insertNode(new AvlNode<>(key, value));
        AvlNode<K,V> parent = (AvlNode<K, V>)current.parent();

        while(parent != null) {

            parent.height = calculateHeight(parent);
            int balance = calculateBalance(parent);

            AvlNode<K, V> next = (AvlNode<K, V>)parent.parent();
            if (balance < -1)
            {
                if (calculateBalance((AvlNode<K, V>) parent.rightSon()) > 0)
                {
                    this.rightRotation((AvlNode<K, V>) parent.rightSon());
                }
                next = (AvlNode<K, V>) parent.parent();
                this.leftRotation(parent);
            }
            if (balance > 1)
            {
                if (calculateBalance((AvlNode<K, V>) parent.leftSon()) < 0)
                {
                    this.leftRotation((AvlNode<K, V>) parent.leftSon());
                }
                next = (AvlNode<K, V>) parent.parent();
                this.rightRotation(parent);

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
            AvlNode<K, V> next = (AvlNode<K, V>)predecessor.parent();
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
        int leftHeight = node == null ? 0 : (node.hasLeftSon()) ? ((AvlNode<K, V>) node.leftSon()).height : 0;
        int rightHeight = node == null ? 0 : (node.hasRightSon()) ? ((AvlNode<K, V>) node.rightSon()).height : 0;
        return Math.max(leftHeight, rightHeight) + 1;
    }

    private static <K extends Comparable<K>,V> int calculateBalance(AvlNode<K,V> node) {
        return calculateHeight((AvlNode<K, V>)node.leftSon()) - calculateHeight((AvlNode<K, V>)node.rightSon());
    }


    protected static class AvlNode<K extends Comparable<K>,V> extends BstNode<K ,V> {
        protected int height;

        public AvlNode(K key, V value) {
            super(key, value);
        }

        @Override
        public String toString() {
            return "AvlNode{" +
                    "height=" + height +
                    ", key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

}
