package com.milos970;

import com.milos970.structure.AVLTree;
import com.milos970.structure.BSTree;
import com.milos970.structure.Tree;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest
{

    @Test
    void runAll() {
        validateBST();
        validateAVL();
    }



    @Test
    void validateBST() {
        BSTree<Integer, String> bst = new BSTree<>();

        testInsert(bst);
        testFind(bst);
        testDelete(bst);
        testMin(bst);
        testMax(bst);
        stressTest(bst);

        isValidBST();
    }

    @Test
    void validateAVL() {
        AVLTree<Integer, String> avl = new AVLTree<>();
        testInsert(avl);
        testFind(avl);
        testDelete(avl);
        testMin(avl);
        testMax(avl);
        stressTest(avl);

        isValidAVL();
    }



    @Test
    public void isValidAVL() {
        AVLTree<Integer, String> bst = new AVLTree<>();

        List<Integer> randElements = IntStream.range(0, 100_000).boxed().collect(Collectors.toList());

            Collections.shuffle(randElements);

            for (int key : randElements) {
                bst.insert(key, "VALUE");
            }
            validateAVLInvariant(bst);

            for (int key : randElements) {
                bst.delete(key);
            }
            validateAVLInvariant(bst);

            bst.clear();

    }

    private void validateAVLInvariant(AVLTree<Integer,String> bst) {
        List<AVLTree.AvlNode<Integer, String>> inorder = (List<AVLTree.AvlNode<Integer, String>>) (List<?>) bst.inOrder();
        for (AVLTree.AvlNode<Integer,String> node : inorder) {
            int leftHeight = node.hasLeftSon() ? ((AVLTree.AvlNode<Integer, String>) node.leftSon()).height() : -1;
            int rightHeight = node.hasRightSon() ? ((AVLTree.AvlNode<Integer, String>) node.rightSon()).height() : -1;
            assertTrue(Math.abs(leftHeight - rightHeight) <= 1);
            int expectedHeight = Math.max(leftHeight, rightHeight) + 1;
            assertEquals(expectedHeight, node.height());
        }
        for (int j = 1; j < inorder.size(); j++) {
            assertTrue(inorder.get(j-1).key < inorder.get(j).key);
        }
    }



    @Test
    public void isValidBST() {
        BSTree<Integer, String> bst = new BSTree<>();

        List<Integer> keys = IntStream.range(0, 1_000_000).boxed().collect(Collectors.toList());
        Collections.shuffle(keys);

        for (int key : keys) {
            bst.insert(key, "VALUE");
        }

        for (int i = 0; i < 1_000_000; i++) {
            assertEquals(i, bst.min());
            bst.delete(i);
        }
        assertEquals(0, bst.size());

        Random random = new Random();
        List<Integer> randElements = IntStream.range(0, 10_000).boxed().collect(Collectors.toList());
        Collections.shuffle(randElements, random);

        for (int i = 0; i < 10_000; i++) {
            bst.insert(randElements.get(i), String.valueOf(randElements.get(i)));
        }

        List<BSTree.BSTNode<Integer, String>> inorder = bst.inOrder();
        for (int j = 1; j < inorder.size(); ++j)
            assertTrue(inorder.get(j - 1).key < inorder.get(j).key);

        for (Integer key : randElements) {
            bst.delete(key);
        }

        assertEquals(0, bst.size());
    }


    public void stressTest(Tree<Integer, String> tree)
    {
        List<Integer> insertedKeys = new ArrayList<>();

        tree.clear();
            Random random = new Random();

            List<Integer> randElements = IntStream.range(0, 10_000_000).boxed().collect(Collectors.toList());
            Collections.shuffle(randElements, random);

            for (int j = 0; j < 10_000_000; j++) {
                int randKey = randElements.get(j);
                double rand = random.nextDouble();

                if (rand < 0.3 && !insertedKeys.isEmpty()) {
                    int index = random.nextInt(insertedKeys.size());
                    randKey = insertedKeys.get(index);
                    tree.delete(randKey);
                    insertedKeys.remove(index);
                } else if (rand < 0.6) {
                    tree.insert(randKey, "VALUE");

                    insertedKeys.add(randKey);
                } else {
                    tree.find(randKey);
                }
            }
    }







    private void testMin(Tree<Integer, String> tree)
    {
        List<Integer> keys = IntStream.range(0, 100_000).boxed().collect(Collectors.toList());
        Collections.shuffle(keys);
        tree.clear();
        for (int key : keys)
        {
            tree.insert(key, "VALUE");
        }

        for (int i = 0; i < 100_000; ++i)
        {
            int min = Collections.min(keys);
            assertEquals(min, tree.min());
            tree.delete(min);
            keys.remove((Integer) min);
        }
    }


    private void testMax(Tree<Integer, String> tree)
    {
        List<Integer> keys = IntStream.range(0, 100_000).boxed().collect(Collectors.toList());
        Collections.shuffle(keys);
        tree.clear();
        for (int key : keys)
        {
            tree.insert(key, "VALUE");
        }

        for (int i = 0; i < 100_000; ++i)
        {
            int max = Collections.max(keys);
            assertEquals(max, tree.max());
            tree.delete(max);
            keys.remove((Integer) max);
        }
    }

    private void testInsert(Tree<Integer, String> tree) {
        List<Integer> keys = IntStream.range(0, 1000_000).boxed().collect(Collectors.toList());
        Collections.shuffle(keys);

tree.clear();
        for (Integer key : keys) {
            tree.insert(key, "VALUE");
        }

        assertEquals(keys.size(), tree.size());

        for (Integer key : keys) {
            assertTrue(tree.find(key).isPresent());
        }

    }

    private void testFind(Tree<Integer, String> tree)
    {
        List<Integer> keys = IntStream.range(0, 1000_000).boxed().collect(Collectors.toList());
        Collections.shuffle(keys);

        tree.clear();
        for (Integer key : keys) {
            tree.insert(key, "VALUE");
        }

        for (Integer key : keys) {
            assertTrue(tree.find(key).isPresent());
        }

        List<Integer> otherKeys = IntStream.range(1000_000, 2000_000).boxed().collect(Collectors.toList());

        for (Integer key : otherKeys) {
            assertTrue(tree.find(key).isEmpty());
        }

    }

    private void testDelete(Tree<Integer, String> tree)
    {
        List<Integer> keys = IntStream.range(0, 1000_000).boxed().collect(Collectors.toList());
        Collections.shuffle(keys);

        tree.clear();
        for (Integer key : keys) {
            tree.insert(key, "VALUE");
        }

        Collections.shuffle(keys);

        for (Integer key : keys) {
            tree.delete(key);
            assertTrue(tree.find(key).isEmpty());
        }

        assertEquals(0,tree.size());

    }

}
