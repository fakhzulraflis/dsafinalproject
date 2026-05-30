package com.datastruct;

/*
 * Generic 2-3 Tree implementation.
 * Key type K must be comparable, and value type V can be any object.
 */

public class Tree23<K extends Comparable<? super K>, V> {
   private static class DataItem<K extends Comparable<? super K>, V> {
      private K key;
      private V data;

      public DataItem(K key, V data) {
         this.key = key;
         this.data = data;
      }

      public void setKey(K key) {
         this.key = key;
      }

      public K getKey() {
         return key;
      }

      public void setData(V data) {
         this.data = data;
      }

      public V getData() {
         return data;
      }

      public void displayItem() {
         System.out.print("/" + key + ":" + data);
      }
   }

   private static class TreeNode<K extends Comparable<? super K>, V> {
      private static final int ORDER = 4; // 2-3 Tree, ORDER = 4
      private int numItems;
      private TreeNode<K,V> parent;
      private TreeNode<K,V> childArray[];
      private DataItem<K,V> itemArray[];

      @SuppressWarnings("unchecked")
      public TreeNode() {
         childArray = (TreeNode<K,V>[]) new TreeNode[ORDER];
         itemArray = (DataItem<K,V>[]) new DataItem[ORDER - 1];
      }

      public void connectChild(int childNum, TreeNode<K,V> child) {
         childArray[childNum] = child;
         if (child != null)
            child.parent = this;
      }

      public TreeNode<K,V> disconnectChild(int childNum) {
         TreeNode<K,V> temp = childArray[childNum];
         childArray[childNum] = null;
         return temp;
      }

      public TreeNode<K,V> getChild(int childNum) {
         return childArray[childNum];
      }

      public TreeNode<K,V> getParent() {
         return parent;
      }

      public boolean isLeaf() {
         return childArray[0] == null;
      }

      public int getNumItems() {
         return numItems;
      }

      public DataItem<K,V> getItem(int index) {
         return itemArray[index];
      }

      public boolean isFull() {
         return numItems == ORDER - 1;
      }

      public int findItem(K key) {
         for (int j = 0; j < ORDER - 1; j++) {
            if (itemArray[j] == null)
               break;
            else if (itemArray[j].getKey().compareTo(key) == 0)
               return j;
         }
         return -1;
      }

      public void deleteItem(K key) {
         for (int j = 0; j < numItems; j++) {
            if (itemArray[j] == null)
               break;
            else if (itemArray[j].getKey().compareTo(key) == 0) {
               for (int k = j; k < numItems - 1; k++) {
                  itemArray[k] = itemArray[k + 1];
               }
               itemArray[numItems - 1] = null;
               --numItems;
               break;
            }
         }
      }

      public int insertItem(DataItem<K,V> newItem) {
         numItems++;
         K newKey = newItem.getKey();

         for (int j = ORDER - 2; j >= 0; j--) {
            if (itemArray[j] == null)
               continue;
            else {
               K itsKey = itemArray[j].getKey();
               if (newKey.compareTo(itsKey) < 0)
                  itemArray[j + 1] = itemArray[j];
               else {
                  itemArray[j + 1] = newItem;
                  return j + 1;
               }
            }
         }
         itemArray[0] = newItem;
         return 0;
      }

      public DataItem<K,V> removeItem() {
         DataItem<K,V> temp = itemArray[numItems - 1];
         itemArray[numItems - 1] = null;
         numItems--;
         return temp;
      }

      public void displayNode() {
         for (int j = 0; j < numItems; j++)
            itemArray[j].displayItem();
         System.out.println("/");
      }
   }

   private TreeNode<K,V> root = new TreeNode<>();

   public V get(K key) {
      TreeNode<K,V> current = root;

      while (true) {
         int childNumber = current.findItem(key);
         if (childNumber != -1)
            return current.getItem(childNumber).getData();
         else if (current.isLeaf())
            return null;
         else
            current = getNextChild(current, key);
      }
   }

   public boolean contains(K key) {
      return get(key) != null;
   }

   public int find(K key) {
      return contains(key) ? 1 : -1;
   }

   public void insert(K key, V data) {
      TreeNode<K,V> current = root;
      DataItem<K,V> tempItem = new DataItem<>(key, data);

      while (!current.isLeaf())
         current = getNextChild(current, key);

      current.insertItem(tempItem);

      while (current.isFull()) {
         split(current);
         current = current.getParent();
         if (current == null)
            break;
      }
   }

   public void delete(K key) {
      TreeNode<K,V> current = root;

      while (true) {
         int itemIndex = current.findItem(key);
         if (itemIndex != -1)
            break;
         else if (current.isLeaf())
            return;
         else
            current = getNextChild(current, key);
      }

      if (current.isLeaf()) {
         if (current.getNumItems() > 1)
            current.deleteItem(key);
         else {
            int index = current.findItem(key);
            root = adoptMerge(current, index);
         }
      }
   }

   private TreeNode<K,V> adoptMerge(TreeNode<K,V> curNode, int index) {
      boolean left = true;
      TreeNode<K,V> parentNode = curNode.getParent();
      int numItems = parentNode.getNumItems();
      int j = 0;
      TreeNode<K,V> siblingNode = null;
      int curPos = 0;

      while (j < numItems && parentNode.getChild(j) != curNode) {
         ++j;
      }
      curPos = j;

      if (j == 0) {
         siblingNode = parentNode.getChild(j + 1);
         left = false;
      } else {
         siblingNode = parentNode.getChild(j - 1);
         left = true;
      }

      if (j < numItems && parentNode.getChild(j + 1) != null && parentNode.getChild(j + 1).getNumItems() > 1) {
         siblingNode = parentNode.getChild(j + 1);
         left = false;
      }

      if (siblingNode.getNumItems() > 1) {
         if (left) {
            DataItem<K,V> parentItem = parentNode.getItem(j - 1);
            DataItem<K,V> siblingItem = siblingNode.removeItem();
            DataItem<K,V> curItem = curNode.getItem(index);

            curItem.setKey(parentItem.getKey());
            curItem.setData(parentItem.getData());
            parentItem.setKey(siblingItem.getKey());
            parentItem.setData(siblingItem.getData());

            if (!curNode.isLeaf()) {
               TreeNode<K,V> lastNode = siblingNode.disconnectChild(siblingNode.getNumItems() + 1);
               TreeNode<K,V> tempNode = curNode.disconnectChild(0);
               curNode.connectChild(1, tempNode);
               curNode.connectChild(0, lastNode);
            }
         } else {
            DataItem<K,V> parentItem = parentNode.getItem(j);
            DataItem<K,V> siblingItem = siblingNode.getItem(0);
            DataItem<K,V> curItem = curNode.getItem(index);

            curItem.setKey(parentItem.getKey());
            curItem.setData(parentItem.getData());
            parentItem.setKey(siblingItem.getKey());
            parentItem.setData(siblingItem.getData());

            if (!curNode.isLeaf()) {
               TreeNode<K,V> firstNode = siblingNode.disconnectChild(0);
               TreeNode<K,V> tempNode = curNode.disconnectChild(curNode.getNumItems());
               curNode.connectChild(0, tempNode);
               curNode.connectChild(curNode.getNumItems(), firstNode);
            }
            siblingNode.deleteItem(siblingItem.getKey());
         }
      } else {
         if (parentNode.getNumItems() == 1) {
            DataItem<K,V> tempItem = parentNode.removeItem();
            siblingNode.insertItem(tempItem);
            if (parentNode == root)
               return root;
            else {
               curNode = parentNode;
               return adoptMerge(curNode, 0);
            }
         } else {
            DataItem<K,V> tempItem;
            if (curPos > 0)
               tempItem = new DataItem<>(parentNode.getItem(curPos - 1).getKey(), parentNode.getItem(curPos - 1).getData());
            else
               tempItem = new DataItem<>(parentNode.getItem(curPos).getKey(), parentNode.getItem(curPos).getData());

            siblingNode.insertItem(tempItem);

            if (left) {
               for (j = curPos; j < parentNode.getNumItems(); ++j) {
                  TreeNode<K,V> tempNode = parentNode.disconnectChild(j + 1);
                  parentNode.connectChild(j, tempNode);
               }
               parentNode.deleteItem(tempItem.getKey());
            } else {
               for (j = 0; j < parentNode.getNumItems(); ++j) {
                  TreeNode<K,V> tempNode = parentNode.disconnectChild(j + 1);
                  parentNode.connectChild(j, tempNode);
               }
               parentNode.deleteItem(tempItem.getKey());
            }
         }
      }
      return root;
   }

   public void split(TreeNode<K,V> thisNode) {
      DataItem<K,V> itemB, itemC;
      TreeNode<K,V> parent, child2, child3;
      int itemIndex;

      itemC = thisNode.removeItem();
      itemB = thisNode.removeItem();
      child2 = thisNode.disconnectChild(2);
      child3 = thisNode.disconnectChild(3);

      TreeNode<K,V> newRight = new TreeNode<>();

      if (thisNode == root) {
         root = new TreeNode<>();
         parent = root;
         root.connectChild(0, thisNode);
      } else
         parent = thisNode.getParent();

      itemIndex = parent.insertItem(itemB);
      int n = parent.getNumItems();

      for (int j = n - 1; j > itemIndex; j--) {
         TreeNode<K,V> temp = parent.disconnectChild(j);
         parent.connectChild(j + 1, temp);
      }
      parent.connectChild(itemIndex + 1, newRight);
      newRight.insertItem(itemC);
      newRight.connectChild(0, child2);
      newRight.connectChild(1, child3);
   }

   public TreeNode<K,V> getNextChild(TreeNode<K,V> thisNode, K key) {
      int numItems = thisNode.getNumItems();
      for (int j = 0; j < numItems; j++) {
         if (key.compareTo(thisNode.getItem(j).getKey()) < 0)
            return thisNode.getChild(j);
      }
      return thisNode.getChild(numItems);
   }

   public void displayTree() {
      recDisplayTree(root, 0, 0);
   }

   private void recDisplayTree(TreeNode<K,V> thisNode, int level, int childNumber) {
      System.out.print("level=" + level + " child=" + childNumber + " ");
      thisNode.displayNode();

      int numItems = thisNode.getNumItems();
      for (int j = 0; j < numItems + 1; j++) {
         TreeNode<K,V> nextNode = thisNode.getChild(j);
         if (nextNode != null)
            recDisplayTree(nextNode, level + 1, j);
         else
            return;
      }
   }
}
