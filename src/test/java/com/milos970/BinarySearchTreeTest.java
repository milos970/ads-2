package com.milos970;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class BinarySearchTreeTest {
    private static final int NUMBER_OF_RANDOM_KEYS = 2_000_000;
    private List<Integer> randomKeys;
    BinarySearchTree<Integer, String> bst;

    @Test
    public void test() {
        BinarySearchTree<Integer, String> bst = new AVLTree<>();


        List<Integer> keys = new ArrayList<>(10_000_000);

        for (int i = 0; i < 10_000_000; i++) {
            keys.add(i);
        }

        Collections.shuffle(keys);

        int c = 0;
        for (int i = 0; i < 10; ++i) {
            Random random = new Random(i);
            ++c;

            for (int j = 0; j < 1_000_000; j++) {

                double rand = random.nextDouble();
                if (rand < 0.3) {

                    bst.insertNode(new BstNode<>(keys.get(j), "MESTO"));
                } else if (rand < 0.6) {

                    bst.find(keys.get(j));
                } else {
                    if (bst.find(keys.get(j)).isPresent()) {

                    } else {
                        int finalJ = j;
                        Optional<String> value = bst.find(keys.get(j));
                        if (value.isPresent()) {
                            assertEquals(value.get(), bst.delete(keys.get(j)));
                        } else {
                            assertThrows(NoSuchElementException.class, () -> bst.delete(keys.get(finalJ)));
                        }

                    }
                }
            }
        }

    }

    @Test
    public void testInsertDelete() {
        this.testInsert();
        this.testDelete();
    }






    @Test
    public void testInsert() {
        this.randomKeys = new ArrayList<>(NUMBER_OF_RANDOM_KEYS);

        Random random = new Random();

        for (int i = 0; i < NUMBER_OF_RANDOM_KEYS; ++i) {
            int key = random.nextInt(NUMBER_OF_RANDOM_KEYS);
            randomKeys.add(key);
        }


        this.bst =  new AVLTree<>();

        for (int i = 0; i < NUMBER_OF_RANDOM_KEYS; ++i) {
            int randomValue = this.randomKeys.get(i);
            bst.insert(i, "Value");
        }

        assertEquals(NUMBER_OF_RANDOM_KEYS, this.bst.size);

    }

    @Test
    public void testDelete() {
        for (int i = 0; i < 2_000_000; ++i) {
            int key = this.randomKeys.get(i);
            if (this.bst.find(key).isPresent()) {
                this.bst.delete(key);
            } else {
                assertThrows(NoSuchElementException.class, () -> this.bst.delete(key));
            }

        }
    }

    @Test
    public void testFind() {
        List<Integer> keys = new ArrayList<>(10_000_000);

        for (int i = 0; i < 10_000_000; i++) {
            keys.add(i);
        }

        Collections.shuffle(keys);

        BinarySearchTree<Integer, String> bst = new AVLTree<>();

        for (int i = 0; i < keys.size(); i++) {
            bst.insert(keys.get(i), "Value");
        }

        for (int i = 0; i < keys.size(); i++) {
            assertTrue(bst.find(keys.get(i)).isPresent());
        }
    }

    @Test
    public void testMin() {

        List<Integer> keys = new ArrayList<>(1000);
        for (int j = 0; j < 1000; ++j)
        {

            keys.clear();
            for (int i = 0; i < 1000; i++) {
                keys.add(i);
            }

            Collections.shuffle(keys);
            int min = Collections.min(keys);
            this.bst = new AVLTree<>();

            for (int i = 0; i < 1000; i++) {
                this.bst.insert(keys.get(i), "VALUE");
            }

            assertEquals(min, this.bst.min());


        }
    }

    @Test
    public void testMax() {

        List<Integer> keys = new ArrayList<>(1000);
        for (int j = 0; j < 1000; ++j)
        {

            keys.clear();
            for (int i = 0; i < 1000; i++) {
                keys.add(i);
            }

            Collections.shuffle(keys);
            int max = Collections.max(keys);
            this.bst = new AVLTree<>();

            for (int i = 0; i < 1000; i++) {
                this.bst.insert(keys.get(i), "VALUE");
            }

            assertEquals(max, this.bst.max());



        }
    }




}
