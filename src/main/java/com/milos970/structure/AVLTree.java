package com.milos970.structure;

import java.util.NoSuchElementException;

public final class AVLTree<K extends Comparable<K>,V> extends BSTree<K,V>
{
    private void leftRotation(AvlNode<K, V> node) {
        AvlNode<K, V> rightSon = (AvlNode<K, V>) node.rightSon();
        if (rightSon == null) {
            return;
        }

        AvlNode<K, V> nodeParent = (AvlNode<K, V>) node.parent();
        AvlNode<K, V> subTree = (AvlNode<K, V>) rightSon.leftSon();

        rightSon.setLeftSon(node);
        node.setRightSon(subTree);

        if (nodeParent != null) {
            if (nodeParent.leftSon() == node) {
                nodeParent.setLeftSon(rightSon);
            } else {
                nodeParent.setRightSon(rightSon);
            }
        } else {
            super.root = rightSon;
            super.root.setParent(null);
        }

        node.height = calculateHeight(node);
        rightSon.height = calculateHeight(rightSon);
    }



    private void rightRotation(AvlNode<K,V> node) {
        AvlNode<K, V> leftSon = (AvlNode<K, V>) node.leftSon();
        if (leftSon == null) {
            return;
        }

        AvlNode<K, V> nodeParent = (AvlNode<K, V>) node.parent();
        AvlNode<K, V> subTree = (AvlNode<K, V>) leftSon.rightSon();

        leftSon.setRightSon(node);
        node.setLeftSon(subTree);

        if (nodeParent != null) {
            if (nodeParent.rightSon() == node) {
                nodeParent.setRightSon(leftSon);
            } else {
                nodeParent.setLeftSon(leftSon);
            }
        } else {
            super.root = leftSon;
            super.root.setParent(null);
        }

        node.height = calculateHeight(node);
        leftSon.height = calculateHeight(leftSon);
    }




    @Override
    public V insert(K key, V value) {
        AvlNode<K,V> current = (AvlNode<K, V>) super.insertNode(new AvlNode<>(key, value));
        AvlNode<K,V> parent = (AvlNode<K, V>)current.parent();

        while(parent != null) {

            parent.height = calculateHeight(parent);
            int balance = calculateBalance(parent);

            AvlNode<K, V> next = (AvlNode<K, V>)parent.parent();
            if (balance == 0) {
                break;
            }
            if (balance < -1)
            {
                if (calculateBalance((AvlNode<K, V>) parent.rightSon()) > 0)
                {
                    this.rightRotation((AvlNode<K, V>) parent.rightSon());
                }

                this.leftRotation(parent);
                next = (AvlNode<K, V>) parent.parent();

            }
            if (balance > 1) {
                if (calculateBalance((AvlNode<K, V>) parent.leftSon()) < 0) {
                    this.leftRotation((AvlNode<K, V>) parent.leftSon());
                }
                this.rightRotation(parent);
                next = (AvlNode<K, V>) parent.parent();

            }

            parent = next;
        }
        return current.value();
    }

    @Override
    public V delete(K key) {
        AvlNode<K,V> node = (AvlNode<K, V>) super.findNode(key).orElseThrow(NoSuchElementException::new);
        AvlNode<K,V> predecessor = null;

        V value = node.value();

        if (node.hasLeftSon() && node.hasRightSon()) {

            AvlNode<K,V> current = (AvlNode<K, V>) node.rightSon();

            while (current.hasLeftSon()) {
                current = (AvlNode<K, V>) current.leftSon();
            }

            node.setKey(current.key());
            node.setValue(current.value());

            predecessor = (AvlNode<K, V>) current.parent();

            if (current.hasRightSon()) {
                super.removeOneChild(current);
            } else {
                super.removeLeaf(current);
            }

        } else

        if (node.hasRightSon() || node.hasLeftSon() ) {
            predecessor = (AvlNode<K, V>) node.parent();
            super.removeOneChild(node);
        } else {
            predecessor = (AvlNode<K, V>) node.parent();
            super.removeLeaf(node);
        }


        while (predecessor != null) {

            predecessor.height = calculateHeight(predecessor);
            int balance = calculateBalance(predecessor);

            if (predecessor.equals(root)) {
                System.out.println(656);
            }

            if (balance == -1 || balance == 1) {
                break;
            }

            AvlNode<K, V> next = (AvlNode<K, V>)predecessor.parent();
            if (balance < -1)
            {
                if (calculateBalance((AvlNode<K, V>) predecessor.rightSon()) > 0)
                {
                    this.rightRotation((AvlNode<K, V>) predecessor.rightSon());
                }

                this.leftRotation(predecessor);
                next = (AvlNode<K, V>) predecessor.parent();
            }
            if (balance > 1) {
                if (calculateBalance((AvlNode<K, V>) predecessor.leftSon()) < 0) {
                    this.leftRotation((AvlNode<K, V>) predecessor.leftSon());
                }
                this.rightRotation(predecessor);
                next = (AvlNode<K, V>) predecessor.parent();
            }


            predecessor = next;
        }

        super.size--;
        return value;

    }


    private static <K extends Comparable<K>, V> int calculateHeight(AvlNode<K, V> node) {
        if (node == null) {
            return -1;
        }
        int leftHeight = node.hasLeftSon() ? ((AvlNode<K, V>) node.leftSon()).height : -1;
        int rightHeight = node.hasRightSon() ? ((AvlNode<K, V>) node.rightSon()).height : -1;
        return Math.max(leftHeight, rightHeight) + 1;
    }


    private static <K extends Comparable<K>, V> int calculateBalance(AvlNode<K, V> node) {
        int left = calculateHeight((AvlNode<K, V>) node.leftSon());
        int right = calculateHeight((AvlNode<K, V>) node.rightSon());
        return left - right;
    }



    static class AvlNode<K extends Comparable<K>,V> extends BSTNode<K ,V> {
        private int height;

        public AvlNode(K key, V value) {
            super(key, value);
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int height() {
            return this.height;
        }
    }

}
