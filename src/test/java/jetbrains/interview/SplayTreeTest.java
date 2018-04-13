package jetbrains.interview;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

public class SplayTreeTest {

  private SplayTree<Integer> splayTree;

  @Before
  public void setUp() throws Exception {
    splayTree = new SplayTree<Integer>();
    splayTree.put(2, "Kostya");
    splayTree.put(1, "Sasha");
    splayTree.put(4, "Timur");
    splayTree.put(3, "Igor");
    splayTree.put(8, "X");
    splayTree.put(7, "Y");
    splayTree.put(5, "Z");
    splayTree.put(9, "X");
    splayTree.put(11, "X");
    splayTree.put(21, "X");
  }

  @Test
  public void testSize() throws Exception {
    assertEquals(10, splayTree.size());
  }

  @Test
  public void testBasicGet() throws Exception {
    assertEquals("Sasha", splayTree.get(1));
    assertEquals("Kostya", splayTree.get(2));
    assertEquals("Igor", splayTree.get(3));
    assertEquals("Timur", splayTree.get(4));
    assertNull("Something", splayTree.get(10));
  }

  @Test
  public void testOverWrite() throws Exception {
    splayTree.put(1, "Kasha");
    assertEquals("Kasha", splayTree.get(1));
    assertEquals(10, splayTree.size());
  }

  @Test
  public void testDelete() throws Exception {
    assertEquals("Sasha", splayTree.delete(1));
    assertEquals(9, splayTree.size());
    assertNull(splayTree.get(1));
    assertEquals("Kostya", splayTree.delete(2));
    assertEquals("Igor", splayTree.delete(3));
    assertEquals("Timur", splayTree.delete(4));
    assertNull(splayTree.delete(0));
    assertEquals(6, splayTree.size());
  }

}
