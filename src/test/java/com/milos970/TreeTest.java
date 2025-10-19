package com.milos970;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TreeTest
{
    private static final int NUMBER_OF_ELEMENTS = 10_000_000;

    private  BinarySearchTree<Integer, String> bst;




    @Test
    public void validateAVL() {
        BinarySearchTree<Integer, String> bst = new AVLTree<>();

        Random random = new Random(14);

        List<Integer> randElements = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            randElements.add(i);
        }

        Collections.shuffle(randElements,random);
        for (int i = 0; i < 5; i++) {
            bst.insert(randElements.get(i), String.valueOf(randElements.get(i)));


            List<AVLTree.AvlNode<Integer, String>> inorder = (List<AVLTree.AvlNode<Integer, String>>) (List<?>) bst.inOrder();

            for (AVLTree.AvlNode<Integer, String> node : inorder) {
                int leftHeight = node.hasLeftSon() ? ((AVLTree.AvlNode<Integer, String>) node.leftSon()).height : 0;
                int rightHeight = node.hasRightSon() ? ((AVLTree.AvlNode<Integer, String>) node.rightSon()).height : 0;

                assertTrue(Math.abs(leftHeight - rightHeight) <= 1,
                        "Uzol " + node.key + " nie je vyvážený (LH=" + leftHeight + ", RH=" + rightHeight + ")");
            }
        }


        /*for (Integer key : randElements) {
            bst.delete(key);


            List<AVLTree.AvlNode<Integer, String>> inorder = (List<AVLTree.AvlNode<Integer, String>>) (List<?>) bst.inOrder();

            for (AVLTree.AvlNode<Integer, String> node : inorder) {
                int leftHeight = node.hasLeftSon()
                        ? ((AVLTree.AvlNode<Integer, String>) node.leftSon()).height
                        : 0;

                int rightHeight = node.hasRightSon()
                        ? ((AVLTree.AvlNode<Integer, String>) node.rightSon()).height
                        : 0;

                assertTrue(
                        Math.abs(leftHeight - rightHeight) <= 1,
                        "Uzol " + node.key + " nie je vyvážený (LH=" + leftHeight + ", RH=" + rightHeight + ")"
                );
            }
        }*/

    }

    @Test
    public void validateBST()
    {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        Set<Integer> keys = new TreeSet<>();
        bst.clear();

        Random random = new Random(8);

        List<Integer> randElements = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            randElements.add(i);
        }

        Collections.shuffle(randElements,random);
        System.out.println(randElements);

        for (int i = 0; i < 1000; i++) {

            bst.insert(randElements.get(i), String.valueOf(randElements.get(i)));
            List<BstNode<Integer,String>> inorder = bst.inOrder();
            for (int j = 1; j < bst.size; ++j)
            {
                assertTrue(inorder.get(j - 1).key < inorder.get(j).key);
            }
        }

        List<BstNode<Integer,String>> inorder = bst.inOrder();


        for (Integer key: randElements) {
            bst.delete(key);
            for (int j = 1; j < bst.size; ++j)
            {
                assertTrue(inorder.get(j - 1).key < inorder.get(j).key);
            }
        }

    }

    @Test
    public void test() {
        List<Integer> insertedKeys = new ArrayList<>();
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        double avgTime = 0;

        for (int i = 0; i < 10; ++i) {
            Random random = new Random(4561 + i);
            bst.clear();
            insertedKeys.clear();

            long start = System.nanoTime();

            for (int j = 0; j < 1000000; j++) {
                int randKey = random.nextInt();
                double rand = random.nextDouble();

                if (rand < 0.3 && !insertedKeys.isEmpty()) {
                    int index = random.nextInt(insertedKeys.size());
                    randKey = insertedKeys.get(index);
                    bst.delete(randKey);
                    insertedKeys.remove(index);
                } else if (rand < 0.6) {
                    bst.insert(randKey, "VALUE");

                    insertedKeys.add(randKey);
                } else {
                    bst.find(randKey);
                }
            }

            long end = System.nanoTime();
            avgTime += (end - start) / 1_000_000.0;
        }

        avgTime /= 10;

        System.out.printf("Priemerný čas operácií (%s): %.2f ms%n",
                bst.getClass().getSimpleName(), avgTime);
    }


    @Test
    void testInorder()
    {
        final int NUMBER_OF_ELEMENTS = 10;
        final int REPLICATIONS = 1;

        List<Integer> randElements = new ArrayList<>(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            randElements.add(i);
        }

        for (int i = 0; i < REPLICATIONS; ++i) {
            Random random = new Random(12345 + i);
            Collections.shuffle(randElements, random);

            this.bst.clear();

            for (int key : randElements) {
                this.bst.insert(key, "VALUE");
            }


            List<Integer> bstValues = null;
            for (int j = 1; j < bstValues.size(); ++j) {

            }

        }

        System.out.println("Test presiel");

    }

    @Test
    void testInsertPerformance() {
        final int NUMBER_OF_ELEMENTS = 10_000_000;
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        AVLTree<Integer, String> avl = new AVLTree<>();

        List<Integer> randElements = IntStream.range(0, NUMBER_OF_ELEMENTS).boxed().collect(Collectors.toList());
        Collections.shuffle(randElements, new Random());


        long start = System.nanoTime();

        long end = System.nanoTime();
        double avgBstMs = (end - start) / 1_000_000.0;
        System.out.printf("Priemerný čas BST insert: %.3f ms%n", avgBstMs);

        start = System.nanoTime();
        for (int key : randElements) avl.insert(key, "VALUE");
        end = System.nanoTime();
        double avgAvlMs = (end - start) /  1_000_000.0;
        System.out.printf("Priemerný čas AVL insert: %.3f ms%n", avgAvlMs);

        System.out.println("**************************************************************");

        start = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_ELEMENTS; ++i) {
            bst.delete(randElements.get(i));
        }
        end = System.nanoTime();


        avgBstMs = (end - start) / 1_000_000.0;
        System.out.printf("Priemerný čas BST delete: %.3f ms%n", avgBstMs);

        start = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_ELEMENTS; ++i) {
            avl.delete(randElements.get(i));
        }
        end = System.nanoTime();


        avgAvlMs = (end - start) / 1_000_000.0;
        System.out.printf("Priemerný čas AVL delete: %.3f ms%n", avgAvlMs);

        start = System.nanoTime();
        System.out.println("**************************************************************");

        start = System.nanoTime();
        for (int i = 2000_000; i < 7000_000; ++i) {
            bst.find(randElements.get(i));
        }
        end = System.nanoTime();

        avgBstMs = (end - start) / 1_000_000.0;
        System.out.printf("Priemerný čas BST find: %.3f ms%n", avgBstMs);

        start = System.nanoTime();
        for (int i = 2000_000; i < 7000_000; ++i) {
            avl.find(randElements.get(i));
        }
        end = System.nanoTime();

        avgAvlMs = (end - start) /  1_000_000.0;
        System.out.printf("Priemerný čas AVL find: %.3f ms%n", avgAvlMs);

    }


    @Test
    void testDelete()
    {
        final int NUMBER_OF_ELEMENTS = 2_000_000;
        final int REPLICATIONS = 10;
        double avgTime = 0;

        List<Integer> randElements = new ArrayList<>(NUMBER_OF_ELEMENTS);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            randElements.add(i);
        }

        for (int i = 0; i < REPLICATIONS; ++i) {
            Random random = new Random(12345 + i);
            Collections.shuffle(randElements, random);

            long start = System.nanoTime();
            for (int key : randElements) {
                this.bst.delete(key);
            }
            long end = System.nanoTime();

            double durationMs = (end - start) / 1_000_000.0;
            avgTime += durationMs;
        }

        avgTime /= REPLICATIONS;
        System.out.printf("Priemerný čas %s delete pre %,d prvkov: %.2f ms%n",
                this.bst.getClass().getSimpleName(),
                NUMBER_OF_ELEMENTS, avgTime);
    }


    @Test
    void testFind()
    {
        for (int i = 0; i < NUMBER_OF_ELEMENTS; ++i) {

        }

        double time = 0;

        for (int i = 0; i < NUMBER_OF_ELEMENTS; ++i)
        {
            long start = System.nanoTime();
            try {

            } catch (NoSuchElementException e) {

            }

            long finish = System.nanoTime();
            time += ((finish - start) / 1000_000);
        }

        System.out.println(time / NUMBER_OF_ELEMENTS);
    }


}
