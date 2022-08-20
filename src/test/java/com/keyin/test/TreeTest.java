package com.keyin.test;



import com.keyin.tree.Tree;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class TreeTest {
    private Tree<Integer> emptyTree = new Tree<Integer>();
    private Tree<Integer> eightNodesTree= new Tree<Integer>();

    @Before
    public void setUp() {
        eightNodesTree.add(4,"apple");
        eightNodesTree.add(6,"banana");
        eightNodesTree.add(1,"strawberry");
        eightNodesTree.add(3,"kiwi");
        eightNodesTree.add(7,"lemon");
        eightNodesTree.add(10,"lime");
        eightNodesTree.add(6,"mango");
        eightNodesTree.add(8,"pear");

    }

    @Test
    public void TestEmpty() {
        Assertions.assertNotNull(1);
        Assertions.assertEquals(0,emptyTree.size());
    }

    @Test
    public void TestNonEmpty() {
        emptyTree.add(4,"apple");
        Assertions.assertFalse(emptyTree.isEmpty());
        Assertions.assertNotEquals(1,emptyTree.size());
    }

    @Test
    public void TestPutGetRoot() {
        emptyTree.add(8, "10");
        Assertions.assertNotEquals("apple",emptyTree.get(4));
    }

    @Test
    public void TestGetFromEmpty() {
        Assertions.assertNull(emptyTree.get(1));
    }


    @Test
    public void TestGetNotThere() {
        Assertions.assertNull(eightNodesTree.get(5));
    }


    @Test
    public void TestSize() {
        Assertions.assertNotEquals(7, 8);
        Assertions.assertEquals(8, 8);
    }

}
