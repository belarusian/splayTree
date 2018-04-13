package jetbrains.interview;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class RandomizedTest {

  @Test
  public void randomMonkeyTestInserts() throws Exception {
    TreeMap<Integer, String> javaTreeMap = new TreeMap<Integer, String>();
    SplayTree<Integer> splayTree = new SplayTree<Integer>();
    buildTree(javaTreeMap, splayTree);
    assertTreesEqual(javaTreeMap, splayTree);
  }

  @Test
  public void randomMonkeyTestDeletes() throws Exception {
    TreeMap<Integer, String> javaTreeMap = new TreeMap<Integer, String>();
    SplayTree<Integer> splayTree = new SplayTree<Integer>();
    buildTree(javaTreeMap, splayTree);
    assertTreesEqual(javaTreeMap, splayTree);
    List<Integer> toDelete = collectDeleteKeys(javaTreeMap);
    for (Integer key : toDelete) {
      assertEquals(javaTreeMap.size(), splayTree.size());
      assertEquals(javaTreeMap.get(key), splayTree.get(key));
      assertEquals(javaTreeMap.remove(key), splayTree.delete(key));
    }
  }

  private void buildTree(TreeMap<Integer, String> javaTreeMap, SplayTree<Integer> splayTree) {
    Random random = new Random();
    for (int i = 0; i < 5000; i++) {
      byte[] space = new byte[32];
      random.nextBytes(space);
      int key = random.nextInt();
      String value = new String(space);
      javaTreeMap.put(key, value);
      splayTree.put(key, value);
    }
  }

  private List<Integer> collectDeleteKeys(TreeMap<Integer, String> javaTreeMap) {
    Random random = new Random();
    List<Integer> toDelete = new ArrayList<Integer>();
    for (Integer key : javaTreeMap.keySet()) {
      if (random.nextBoolean()) {
        toDelete.add(key);
      }
    }
    return toDelete;
  }

  private void assertTreesEqual(TreeMap<Integer, String> javaTreeMap, SplayTree<Integer> splayTree) {
    for (Map.Entry<Integer, String> entry : javaTreeMap.entrySet()) {
      assertEquals(entry.getValue(), splayTree.get(entry.getKey()));
    }
    assertEquals(javaTreeMap.size(), splayTree.size());
  }

}