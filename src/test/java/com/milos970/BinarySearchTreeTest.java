package com.milos970;

import java.util.*;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {

    private List<Integer> keys;

    @org.junit.Test
    public void test() {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();


        List<Integer> keys = new ArrayList<>(10_000_000);

        for (int i = 0; i < 10_000_000; i++) {
            keys.add(i);
        }

        Collections.shuffle(keys);

        int c = 0;
        for (int i = 0; i < 10; ++i) {
            Random random = new Random(i);
            ++c;

            for (int j = 0; j < 10_000_000; j++) {

                double value = random.nextDouble();
                if (value < 0.3) {

                    bst.insertNode(new BstNode<>(keys.get(j), "MESTO"));
                } else if (value < 0.6) {

                    bst.find(keys.get(j));
                } else {
                    if (bst.find(keys.get(j)).isPresent()) {

                    } else {
                        int finalJ = j;
                        assertThrows(NoSuchElementException.class, () -> bst.delete(keys.get(finalJ)));
                    }
                }
            }
        }

    }


    @org.junit.Test
    public void testInsert() {
        List<Integer> keys = new ArrayList<>(10_000_000);

        Random random = new Random();

        for (int i = 0; i < 10_000_000; i++) {
            int key = random.nextInt(10_000_000);
            keys.add(key);
        }


        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();

        for (int i = 0; i < keys.size(); i++) {
            bst.insert(keys.get(i), "Value");
        }

        for (int i = 0; i < 10000; i++) {
            bst.delete(keys.get(i));
        }


    }

    @org.junit.Test
    public void testFind() {
        List<Integer> keys = new ArrayList<>(10_000_000);

        for (int i = 0; i < 10_000_000; i++) {
            keys.add(i);
        }

        Collections.shuffle(keys);
        System.out.println(5);

        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();

        for (int i = 0; i < keys.size(); i++) {
            bst.insert(keys.get(i), "Value");
        }

        for (int i = 0; i < keys.size(); i++) {
            assertTrue(bst.find(keys.get(i)).isPresent());
        }
    }

    @org.junit.Test
    public void testDelete() {
        List<Integer> keys = new ArrayList<>(10_000_000);

        for (int i = 0; i < 10_000_000; i++) {
            keys.add(i);
        }

        Collections.shuffle(keys);

        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();

        for (int i = 0; i < keys.size(); i++) {
            bst.insert(keys.get(i), "Value");
        }

        for (int i = 0; i < keys.size(); i++) {
            assertTrue(bst.find(keys.get(i)).isPresent());
            bst.delete(keys.get(i));
        }
    }


}
