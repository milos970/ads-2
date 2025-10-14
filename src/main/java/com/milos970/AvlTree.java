package com.milos970;

public class AvlTree<K extends Comparable<K>,V> extends BinaryTree<K,V>
{
    public void leftRotation(AvlNode<K,V> node) {
        if (!node.hasRightSon())
        {
            return;
        }

        if (!node.hasParent())
        {
            AvlNode<K,V> rightSon = (AvlNode<K, V>) node.rightSon();
            this.insertRoot(rightSon);
            rightSon.setParent(null);

            if (rightSon.hasLeftSon()) {
                node.setRightSon(rightSon.leftSon());
                node.rightSon().setParent(node);
            }

            node.setParent(rightSon);
            rightSon.setLeftSon(node);
            return;
        }

        if (node.hasParent())
        {
            AvlNode<K,V> parent = (AvlNode<K, V>) node.parent();
            AvlNode<K,V> rightSon = (AvlNode<K, V>)node.rightSon();

            if (parent.rightSon().equals(node)) {
                parent.setRightSon(rightSon);
            } else {
                parent.setLeftSon(rightSon);
            }
            rightSon.setParent(parent);

            node.setRightSon(rightSon.leftSon());
            rightSon.leftSon().setParent(node);
            rightSon.setLeftSon(node);


            node.setParent(rightSon);
        }


    }

    public void rightRotation(AvlNode<K,V> node) {

        if (!node.hasLeftSon()) {
            return;
        }


        if (!node.hasParent()) {
            AvlNode<K,V> leftSon = (AvlNode<K,V>) node.leftSon();
            this.insertRoot(leftSon);
            leftSon.setParent(null);

            if (leftSon.hasRightSon()) {
                node.setLeftSon(leftSon.rightSon());
                node.leftSon().setParent(node);
            } else {
                node.setLeftSon(null);
            }

            node.setParent(leftSon);
            leftSon.setRightSon(node);
            return;
        }

        AvlNode<K,V> parent = (AvlNode<K,V>) node.parent();
        AvlNode<K,V> leftSon = (AvlNode<K,V>) node.leftSon();


        if (parent.leftSon().equals(node)) {
            parent.setLeftSon(leftSon);
        } else {
            parent.setRightSon(leftSon);
        }
        leftSon.setParent(parent);

        node.setLeftSon(leftSon.rightSon());
        if (leftSon.hasRightSon()) {
            leftSon.rightSon().setParent(node);
        }

        leftSon.setRightSon(node);
        node.setParent(leftSon);
    }

    public void insert(K key, V value) {
        AvlNode<K,V> current = (AvlNode<K, V>) this.insertNode(new AvlNode<K,V>(key, value,0));

        while(current.hasParent()) {
            AvlNode<K,V> parent = (AvlNode<K, V>) current.parent();
            AvlNode<K,V> leftSon = null;
            AvlNode<K,V> rightSon = null;

            int leftSonHeight = -1;
            int rightSonHeight = -1;

            if (parent.hasLeftSon()) {
                leftSon = (AvlNode<K, V>) parent.leftSon();
                leftSonHeight = leftSon.getBalanced();
            }

            if (parent.hasRightSon()) {
                rightSon = (AvlNode<K, V>) parent.rightSon();
                rightSonHeight = rightSon.getBalanced();
            }

            int parentHeight = Math.max(leftSonHeight, rightSonHeight) + 1;
            int balance = leftSonHeight - rightSonHeight;

            if (balance < - 1) {
                this.leftRotation(parent);
                return;
            } else if (balance > 1) {
                this.rightRotation(parent);
                return;
            }
        }

    }



    public static class AvlNode<K extends Comparable<K>,V> extends BinaryNode {
        private int balance;

        public AvlNode(K key, V value, int balance) {
            super(key, value);
            this.balance = balance;
        }

        public void setBalance(int value) {
            this.balance = value;
        }

        public int balance() {
            return this.balance;
        }


    }

}
