package jetbrains.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * the class is implemented from the steps as described in http://en.wikipedia.org/wiki/Splay_tree#Operations
 * @author Sasha Malahov
 */

public class SplayTree<Key extends Comparable<Key>> {

  private Node<Key> myRoot;
  private int myItemCount;

  private static class Node<T> {
    T key;
    Object value;
    Node<T> leftChild;
    Node<T> rightChild;

    public Node(T key, Object value) {
      this.key = key;
      this.value = value;
    }
  }

  public Object get(Key key) {
    List<Node<Key>> traversalPath = startPath(myRoot);
    Node<Key> currentNode = myRoot;
    while (currentNode != null) {
      int cmp = currentNode.key.compareTo(key);
      if (cmp < 0) {
        currentNode = currentNode.rightChild;
      } else if (cmp > 0) {
        currentNode = currentNode.leftChild;
      } else {
        splay(traversalPath);
        return currentNode.value;
      }
      traversalPath.add(currentNode);
    }
    return null;
  }

  public Object delete(Key key) {
    Object o = get(key);
    if (o == null) {
      return o;
    }
    assert myRoot.value == o;
    join(myRoot.leftChild, myRoot.rightChild);
    myItemCount--;
    return o;
  }

  private void join(Node<Key> s, Node<Key> t) {
    if (s == null) {
      myRoot = t;
      return;
    }
    if (t == null) {
      myRoot = s;
      return;
    }
    List<Node<Key>> traversalPath = startPath(s);
    Node<Key> nextNode = s.rightChild;
    while (nextNode != null) {
      traversalPath.add(nextNode);
      nextNode = nextNode.rightChild;
    }
    splay(traversalPath);
    myRoot.rightChild = t;
  }

  public void put(Key key, Object value) {
    Node<Key> newNode = new Node<Key>(key, value);
    List<Node<Key>> traversalPath = startPath(myRoot);
    if (myRoot == null) {
      myRoot = newNode;
    } else {
      Node<Key> prevNode = myRoot;
      Node<Key> currentNode = moveDown(myRoot, key);
      while (currentNode != null) {
        if (currentNode != myRoot) {
          traversalPath.add(currentNode);
        }
        int cmp = currentNode.key.compareTo(key);
        if (cmp == 0) {
          splay(traversalPath);
          currentNode.value = value;
          return;
        }
        prevNode = currentNode;
        currentNode = moveDown(currentNode, key);
      }
      int cmp = prevNode.key.compareTo(key);
      if (cmp < 0) {
        prevNode.rightChild = newNode;
      } else if (cmp > 0) {
        prevNode.leftChild = newNode;
      }
      traversalPath.add(newNode);
      splay(traversalPath);
    }
    myItemCount++;
  }

  public int size() {
    return myItemCount;
  }

  private Node<Key> moveDown(Node<Key> fromNode, Key newNode) {
    int cmp = fromNode.key.compareTo(newNode);
    if (cmp < 0) {
      return fromNode.rightChild;
    } else if (cmp > 0){
      return fromNode.leftChild;
    }
    return fromNode;
  }

  private void splay(List<Node<Key>> pathX) {
    while (pathX.size() >= 4) {
      int listSize = pathX.size();
      Node<Key> gg = pathX.get(listSize - 4);
      Node<Key> g  = pathX.get(listSize - 3);
      Node<Key> p  = pathX.get(listSize - 2);
      Node<Key> x  = pathX.get(listSize - 1);
      zigIt(g, p, x);
      if (gg.leftChild == g) {
        gg.leftChild = x;
      } else {
        gg.rightChild = x;
      }
      pathX.set(listSize - 3, x);
      pathX.remove(listSize - 1);
      pathX.remove(listSize - 2);
    }
    if (pathX.size() == 3) {
      Node<Key> g  = pathX.get(0);
      Node<Key> p  = pathX.get(1);
      Node<Key> x  = pathX.get(2);
      zigIt(g, p, x);
      myRoot = x;
    } else if (pathX.size() == 2) {
      Node<Key> p = pathX.get(0);
      Node<Key> x = pathX.get(1);
      zigStep(p, x);
      myRoot = x;
    } else if (pathX.size() == 1) {
      myRoot = pathX.get(0);
    }
  }

  private void zigIt(Node<Key> g, Node<Key> p, Node<Key> x) {
    if (g.leftChild == p && p.leftChild == x) {
      zigZigLeft(g, p, x);
    } else if (g.rightChild == p && p.rightChild == x) {
      zigZigRight(g, p, x);
    } else if (g.leftChild == p && p.rightChild == x) {
      zigZagLeft(g, p, x);
    } else if (g.rightChild == p && p.leftChild == x) {
      zigZagRight(g, p, x);
    }
  }

  private void zigStep(Node<Key> p, Node<Key> x) {
    if (p.leftChild == x) {
      moveFromLeftToRight(p, x);
    }
    if (p.rightChild == x) {
      moveFromRightToLeft(p, x);
    }
  }

  private void zigZigLeft(Node<Key> g, Node<Key> p, Node<Key> x) {
    moveFromLeftToRight(g, p);
    moveFromLeftToRight(p, x);
  }

  private void zigZigRight(Node<Key> g, Node<Key> p, Node<Key> x) {
    moveFromRightToLeft(p, x);
    moveFromRightToLeft(g, p);
  }

  private void zigZagLeft(Node<Key> g, Node<Key> p, Node<Key> x) {
    moveFromRightToLeft(p, x);
    moveFromLeftToRight(g, x);
  }

  private void zigZagRight(Node<Key> g, Node<Key> p, Node<Key> x) {
    moveFromRightToLeft(g, x);
    moveFromLeftToRight(p, x);
  }

  private void moveFromLeftToRight(Node<Key> p, Node<Key> x) {
    p.leftChild = x.rightChild;
    x.rightChild = p;
  }

  private void moveFromRightToLeft(Node<Key> g, Node<Key> x) {
    g.rightChild = x.leftChild;
    x.leftChild = g;
  }

  private List<Node<Key>> startPath(Node<Key> startingNode) {
    List<Node<Key>> pathList = new ArrayList<Node<Key>>();
    if (startingNode != null) {
      pathList.add(startingNode);
    }
    return pathList;
  }

}
