package com.milos970;

import com.milos970.structure.AVLTree;
import com.milos970.structure.BSTree;
import com.milos970.structure.Tree;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PerformanceTest
{
    @Test
    void runAll() {
        testPerformanceOfAVLandBVS();
        testPerformanceOfAVLandCustom();
    }



    @Test
    void testPerformanceOfAVLandBVS() {
        AVLTree<Integer, String> avl = new AVLTree<>();
        BSTree<Integer, String> bst = new BSTree<>();

        testAscendingInsertPerformance(avl,bst);

    }

    @Test
    void testPerformanceOfAVLandCustom() {
        AVLTree<Integer, String> avl = new AVLTree<>();
        Int2ObjectRBTreeMapWrapper<Integer, String> rbTreeMapWrapper = new Int2ObjectRBTreeMapWrapper<>();

        testInsertPerformance(avl,rbTreeMapWrapper);
        testDeletePerformance(avl,rbTreeMapWrapper);
        testFindPerformance(avl,rbTreeMapWrapper);
        testMinPerformance(avl,rbTreeMapWrapper);
        testMaxPerformance(avl,rbTreeMapWrapper);
    }

    private void testAscendingInsertPerformance(Tree<Integer, String> treeA, Tree<Integer, String> treeB)
    {
        final int NUMBER_OF_ELEMENTS = 100_000;

        treeA.clear();
        treeB.clear();

        List<Integer> randElements = IntStream.range(0, NUMBER_OF_ELEMENTS).boxed().collect(Collectors.toList());

        double sumBVS = 0;
        double sumAVL = 0;
        long start = 0;
        long end = 0;

        for (int key : randElements) {
            start = System.nanoTime();
            treeA.insert(key, "VALUE");
            end = System.nanoTime();

            sumBVS += ((end - start) / 1000_000d);

            start = System.nanoTime();
            treeB.insert(key, "VALUE");
            end = System.nanoTime();

            sumAVL += ((end - start) / 1000_000d);
        }


        System.out.printf("%s insert: %.0f ms%n", treeA.getClass().getSimpleName(), sumBVS );
        System.out.printf("%s  insert: %.0f ms%n", treeB.getClass().getSimpleName(), sumAVL );
        System.out.println("**************************************************************");

    }


    private void testInsertPerformance(Tree<Integer, String> treeA, Tree<Integer, String> treeB)
    {
        final int NUMBER_OF_ELEMENTS = 10_000_000;

        treeA.clear();
        treeB.clear();

        List<Integer> randElements = IntStream.range(0, NUMBER_OF_ELEMENTS).boxed().collect(Collectors.toList());
        Collections.shuffle(randElements);

        double sumBVS = 0;
        double sumAVL = 0;
        long start = 0;
        long end = 0;

        for (int key : randElements) {
            start = System.nanoTime();
            treeA.insert(key, "VALUE");
            end = System.nanoTime();

            sumBVS += ((end - start) / 1000_000d);

            start = System.nanoTime();
            treeB.insert(key, "VALUE");
            end = System.nanoTime();

            sumAVL += ((end - start) / 1000_000d);
        }


        System.out.printf("%s insert: %.0f ms%n", treeA.getClass().getSimpleName(), sumBVS );
        System.out.printf("%s  insert: %.0f ms%n", treeB.getClass().getSimpleName(), sumAVL );
        System.out.println("**************************************************************");

    }

    private void testFindPerformance(Tree<Integer, String> treeA, Tree<Integer, String> treeB)
    {
        final int NUMBER_OF_ELEMENTS = 5_000_000;

        treeA.clear();
        treeB.clear();
        List<Integer> randElements = IntStream.range(0, NUMBER_OF_ELEMENTS).boxed().collect(Collectors.toList());
        Collections.shuffle(randElements);

        for (int key : randElements) {
            treeA.insert(key, "VALUE");
            treeB.insert(key, "VALUE");
        }

        double sumBVS = 0;
        double sumAVL = 0;
        long start = 0;
        long end = 0;

        for (int key : randElements) {
            start = System.nanoTime();
            treeA.find(key);
            end = System.nanoTime();

            sumBVS += ((end - start) / 1000_000d);

            start = System.nanoTime();
            treeB.find(key);
            end = System.nanoTime();

            sumAVL += ((end - start) / 1000_000d);
        }


        System.out.printf("%s find: %.0f ms%n", treeA.getClass().getSimpleName(), sumBVS );
        System.out.printf("%s  find: %.0f ms%n", treeB.getClass().getSimpleName(), sumAVL );
        System.out.println("**************************************************************");

    }

    private void testDeletePerformance(Tree<Integer, String> treeA, Tree<Integer, String> treeB)
    {
        final int NUMBER_OF_ELEMENTS = 2_000_000;
        treeA.clear();
        treeB.clear();

        List<Integer> randElements = IntStream.range(0, NUMBER_OF_ELEMENTS).boxed().collect(Collectors.toList());
        Collections.shuffle(randElements);


        for (int key : randElements) {
            treeA.insert(key, "VALUE");
            treeB.insert(key, "VALUE");
        }

        double sumBVS = 0;
        double sumAVL = 0;
        long start = 0;
        long end = 0;

        for (int key : randElements) {
            start = System.nanoTime();
            treeA.delete(key);
            end = System.nanoTime();

            sumBVS += ((end - start) / 1000_000d);

            start = System.nanoTime();
            treeB.delete(key);
            end = System.nanoTime();

            sumAVL += ((end - start) / 1000_000d);
        }


        System.out.printf("%s delete: %.0f ms%n", treeA.getClass().getSimpleName(), sumBVS );
        System.out.printf("%s  delete: %.0f ms%n", treeB.getClass().getSimpleName(), sumAVL );
        System.out.println("**************************************************************");

    }

    private void testMinPerformance(Tree<Integer, String> treeA, Tree<Integer, String> treeB)
    {
        final int NUMBER_OF_ELEMENTS = 2_000_000;
        treeA.clear();
        treeB.clear();

        List<Integer> randElements = IntStream.range(0, NUMBER_OF_ELEMENTS).boxed().collect(Collectors.toList());
        Collections.shuffle(randElements);

        for (int key : randElements) {
            treeA.insert(key, "VALUE");
            treeB.insert(key, "VALUE");
        }

        double sumBVS = 0;
        double sumAVL = 0;
        long start = 0;
        long end = 0;

        Collections.sort(randElements);
        for (int i = 0; i < NUMBER_OF_ELEMENTS; ++i) {
            int min = randElements.get(i);
            start = System.nanoTime();
            end = System.nanoTime();
            treeA.delete(min);

            sumBVS += ((end - start) / 1000_000d);

            start = System.nanoTime();
            end = System.nanoTime();
            treeB.delete(min);

            sumAVL += ((end - start) / 1000_000d);
        }


        System.out.printf("%s min: %.0f ms%n", treeA.getClass().getSimpleName(), sumBVS );
        System.out.printf("%s  min: %.0f ms%n", treeB.getClass().getSimpleName(), sumAVL );
        System.out.println("**************************************************************");

    }

    private void testMaxPerformance(Tree<Integer, String> treeA, Tree<Integer, String> treeB)
    {
        final int NUMBER_OF_ELEMENTS = 2_000_000;
        treeA.clear();
        treeB.clear();

        List<Integer> randElements = IntStream.range(0, NUMBER_OF_ELEMENTS).boxed().collect(Collectors.toList());
        Collections.shuffle(randElements);

        for (int key : randElements) {
            treeA.insert(key, "VALUE");
            treeB.insert(key, "VALUE");
        }

        double sumBVS = 0;
        double sumAVL = 0;
        long start = 0;
        long end = 0;

        Collections.sort(randElements, Comparator.reverseOrder());
        for (int i = 0; i < NUMBER_OF_ELEMENTS; ++i) {
            int max = randElements.get(i);
            start = System.nanoTime();
            end = System.nanoTime();
            treeA.delete(max);

            sumBVS += ((end - start) / 1000_000d);

            start = System.nanoTime();
            end = System.nanoTime();
            treeB.delete(max);

            sumAVL += ((end - start) / 1000_000d);
        }


        System.out.printf("%s max: %.0f ms%n", treeA.getClass().getSimpleName(), sumBVS );
        System.out.printf("%s  max: %.0f ms%n", treeB.getClass().getSimpleName(), sumAVL );
        System.out.println("**************************************************************");

    }
}
