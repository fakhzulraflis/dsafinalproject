package com.datastruct;

/* 
 * Struktur data Graph dengan bobot pada setiap edge
 * sources: https://www.lavivienpost.net/weighted-graph-as-adjacency-list/  
 * 
 */
import java.util.*;

class Edge<T> {

    private T vertex;
    private T neighbor; //connected vertex
    private int weight; //weight

    //Constructor, Time O(1) Space O(1)
    public Edge(T u, T v, int w) {
        this.vertex = u;
        this.neighbor = v;
        this.weight = w;
    }

    public T getVertex() {
        return vertex;
    }

    public void setNeighbor(T neighbor) {
        this.neighbor = neighbor;
    }

    public T getNeighbor() {
        return neighbor;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    //Time O(1) Space O(1)
    @Override
    public String toString() {
        return "(" + vertex + "," + neighbor + "," + weight + ")";
    }
}

public class Graph<T> {

    //Map<T, LinkedList<Edge<T>>> adj;
    private Map<T, MyLinearList<Edge<T>>> adj;
    boolean directed;

    //Constructor, Time O(1) Space O(1)
    public Graph(boolean type) {
        adj = new LinkedHashMap<>();
        directed = type; // false: undirected, true: directed
    }

    //Add edges including adding nodes, Time O(1) Space O(1)
    public void addEdge(T a, T b, int w) {
        adj.putIfAbsent(a, new MyLinearList<>()); //add node
        adj.putIfAbsent(b, new MyLinearList<>()); //add node
        Edge<T> edge1 = new Edge<>(a, b, w);
        adj.get(a).pushQ(edge1);//add(edge1); //add edge
        if (!directed) { //undirected
            Edge<T> edge2 = new Edge<>(b, a, w);
            adj.get(b).pushQ(edge2);
        }
    }

    public boolean deleteEdge(T a, T b) {
        if (!adj.containsKey(a) || !adj.containsKey(b)) {
            return false;
        }

        boolean deleted = deleteEdgeFromList(adj.get(a), b);

        if (!directed) {
            deleted = deleteEdgeFromList(adj.get(b), a) || deleted;
        }

        return deleted;
    }

    private boolean deleteEdgeFromList(MyLinearList<Edge<T>> list, T neighbor) {
        boolean deleted = false;
        Node<Edge<T>> curr = list.head;

        while (curr != null) {
            Edge<T> edge = curr.getData();
            curr = curr.getNext();
            if (edge.getNeighbor().equals(neighbor)) {
                list.remove(edge);
                deleted = true;
            }
        }

        return deleted;
    }

    //Print graph as hashmap, Time O(V+E), Space O(1)
    public void printGraph() {
        for (T key : adj.keySet()) {
            //System.out.println(key.toString() + " : " + adj.get(key).toString());
            System.out.print(key.toString() + " : ");
            MyLinearList<Edge<T>> thelist = adj.get(key);
            Node<Edge<T>> curr = thelist.head;
            while (curr != null) {
                System.out.print(curr.getData());
                curr = curr.getNext();
            }
            System.out.println();
        }
    }

    public MyLinearList<T> DFSOrder(T start) {
        MyLinearList<T> visited = new MyLinearList<>();
        MyLinearList<T> stack = new MyLinearList<>();

        if (!adj.containsKey(start)) {
            return visited;
        }

        stack.pushS(start);

        while (!stack.isEmpty()) {
            T current = stack.remove();

            if (!contains(visited, current)) {
                visited.pushQ(current);

                MyLinearList<Edge<T>> list = adj.get(current);
                Node<Edge<T>> curr = list.head;
                MyLinearList<T> temp = new MyLinearList<>();

                while (curr != null) {
                    T neighbor = curr.getData().getNeighbor();
                    if (!contains(visited, neighbor)) {
                        temp.pushS(neighbor);
                    }
                    curr = curr.getNext();
                }

                while (!temp.isEmpty()) {
                    stack.pushS(temp.remove());
                }
            }
        }

        return visited;
    }

    public MyLinearList<T> BFSOrder(T start) {
        MyLinearList<T> visited = new MyLinearList<>();
        MyLinearList<T> queue = new MyLinearList<>();

        if (!adj.containsKey(start)) {
            return visited;
        }

        visited.pushQ(start);
        queue.pushQ(start);

        while (!queue.isEmpty()) {
            T current = queue.remove();

            MyLinearList<Edge<T>> list = adj.get(current);
            Node<Edge<T>> curr = list.head;
            while (curr != null) {
                T neighbor = curr.getData().getNeighbor();

                if (!contains(visited, neighbor)) {
                    visited.pushQ(neighbor);
                    queue.pushQ(neighbor);
                }
                curr = curr.getNext();
            }
        }

        return visited;
    }

    public void printDFS(T start) {
        System.out.print("DFS dari " + start + " : ");
        DFSOrder(start).cetakList();
    }

    public void printBFS(T start) {
        System.out.print("BFS dari " + start + " : ");
        BFSOrder(start).cetakList();
    }

    // DFS: check if path exists
    public boolean DFS(T src, T dest) {
        if (!adj.containsKey(src) || !adj.containsKey(dest)) {
            return false;
        }

        return contains(DFSOrder(src), dest);
    }

    // BFS: check if path exists
    public boolean BFS(T src, T dest) {
        if (!adj.containsKey(src) || !adj.containsKey(dest)) {
            return false;
        }

        return contains(BFSOrder(src), dest);
    }

    public void primMST(T start) {
        MyLinearList<T> visited = new MyLinearList<>();
        MyLinearList<Edge<T>> result = new MyLinearList<>();
        Heap<Integer, Edge<T>> heap = new Heap<Integer, Edge<T>>(countAllEdges() + 1, true);
        int total = 0;
        int edgeCount = 0;

        if (directed || !adj.containsKey(start)) {
            System.out.println("MST hanya untuk graph undirected dan vertex awal harus ada.");
            return;
        }

        visited.pushQ(start);
        addEdgesToHeap(start, visited, heap);

        while (heap.size() > 0 && edgeCount < adj.size() - 1) {
            Edge<T> edge = heap.removeFirst().getData();

            if (!contains(visited, edge.getNeighbor())) {
                result.pushQ(edge);
                total = total + edge.getWeight();
                edgeCount++;

                visited.pushQ(edge.getNeighbor());
                addEdgesToHeap(edge.getNeighbor(), visited, heap);
            }
        }

        System.out.println("MST dengan Algoritma Prim:");
        printMSTResult(result, total);
    }

    public void kruskalMST() {
        MyLinearList<Edge<T>> result = new MyLinearList<>();
        MyLinearList<Edge<T>> edgeList = new MyLinearList<>();
        MyLinearList<ParentNode> parentList = new MyLinearList<>();
        Heap<Integer, Edge<T>> heap = new Heap<Integer, Edge<T>>(countAllEdges() + 1, true);
        int total = 0;
        int edgeCount = 0;

        if (directed) {
            System.out.println("MST hanya untuk graph undirected.");
            return;
        }

        for (T key : adj.keySet()) {
            parentList.pushQ(new ParentNode(key));

            Node<Edge<T>> curr = adj.get(key).head;
            while (curr != null) {
                Edge<T> edge = curr.getData();
                if (!isEdgeExist(edgeList, edge)) {
                    edgeList.pushQ(edge);
                    heap.insert(edge.getWeight(), edge);
                }
                curr = curr.getNext();
            }
        }

        while (heap.size() > 0 && edgeCount < adj.size() - 1) {
            Edge<T> edge = heap.removeFirst().getData();
            T rootA = findParent(parentList, edge.getVertex());
            T rootB = findParent(parentList, edge.getNeighbor());

            if (!rootA.equals(rootB)) {
                result.pushQ(edge);
                total = total + edge.getWeight();
                edgeCount++;
                unionParent(parentList, rootA, rootB);
            }
        }

        System.out.println("MST dengan Algoritma Kruskal:");
        printMSTResult(result, total);
    }

    private class ParentNode {

        T vertex;
        T parent;

        ParentNode(T vertex) {
            this.vertex = vertex;
            this.parent = vertex;
        }
    }

    private void addEdgesToHeap(T vertex, MyLinearList<T> visited, Heap<Integer, Edge<T>> heap) {
        Node<Edge<T>> curr = adj.get(vertex).head;

        while (curr != null) {
            Edge<T> edge = curr.getData();
            if (!contains(visited, edge.getNeighbor())) {
                heap.insert(edge.getWeight(), edge);
            }
            curr = curr.getNext();
        }
    }

    private boolean isEdgeExist(MyLinearList<Edge<T>> edgeList, Edge<T> edge) {
        Node<Edge<T>> curr = edgeList.head;

        while (curr != null) {
            Edge<T> data = curr.getData();
            boolean sameDirection = data.getVertex().equals(edge.getVertex())
                    && data.getNeighbor().equals(edge.getNeighbor());
            boolean oppositeDirection = data.getVertex().equals(edge.getNeighbor())
                    && data.getNeighbor().equals(edge.getVertex());

            if ((sameDirection || oppositeDirection) && data.getWeight() == edge.getWeight()) {
                return true;
            }
            curr = curr.getNext();
        }

        return false;
    }

    private T findParent(MyLinearList<ParentNode> parentList, T vertex) {
        ParentNode data = getParentNode(parentList, vertex);

        while (!data.parent.equals(data.vertex)) {
            data = getParentNode(parentList, data.parent);
        }

        return data.vertex;
    }

    private ParentNode getParentNode(MyLinearList<ParentNode> parentList, T vertex) {
        Node<ParentNode> curr = parentList.head;

        while (curr != null) {
            if (curr.getData().vertex.equals(vertex)) {
                return curr.getData();
            }
            curr = curr.getNext();
        }

        return null;
    }

    private void unionParent(MyLinearList<ParentNode> parentList, T rootA, T rootB) {
        ParentNode data = getParentNode(parentList, rootB);
        data.parent = rootA;
    }

    private void printMSTResult(MyLinearList<Edge<T>> result, int total) {
        Node<Edge<T>> curr = result.head;

        System.out.print("[ ");
        while (curr != null) {
            System.out.print(curr.getData());
            if (curr.getNext() != null) {
                System.out.print(" ");
            }
            curr = curr.getNext();
        }
        System.out.println(" ]");
        System.out.println("MST Length = " + total);
    }

    private int countAllEdges() {
        int total = 0;

        for (T key : adj.keySet()) {
            Node<Edge<T>> curr = adj.get(key).head;
            while (curr != null) {
                total++;
                curr = curr.getNext();
            }
        }

        return total;
    }

    public static class Dijkstra<T> {

        private static class DijkstraNode<T> {

            T vertex;
            int distance;
            T previous;

            DijkstraNode(T vertex, int distance, T previous) {
                this.vertex = vertex;
                this.distance = distance;
                this.previous = previous;
            }
        }

        public MyLinearList<String> dijkstra(Graph<T> g, T start) {
            MyLinearList<DijkstraNode<T>> table = createTable(g);
            MyLinearList<String> result = new MyLinearList<>();

            runDijkstra(g, start, table);

            Node<DijkstraNode<T>> curr = table.head;
            while (curr != null) {
                DijkstraNode<T> data = curr.getData();
                if (data.distance == Integer.MAX_VALUE) {
                    result.pushQ(data.vertex + " = Tidak terhubung");
                } else {
                    result.pushQ(data.vertex + " = " + data.distance);
                }
                curr = curr.getNext();
            }

            return result;
        }

        public void printShortestPath(Graph<T> g, T start) {
            MyLinearList<DijkstraNode<T>> table = createTable(g);
            runDijkstra(g, start, table);

            System.out.println("Shortest path dari " + start + " :");
            Node<DijkstraNode<T>> curr = table.head;
            while (curr != null) {
                DijkstraNode<T> data = curr.getData();
                System.out.print(start + " ke " + data.vertex + " = ");
                if (data.distance == Integer.MAX_VALUE) {
                    System.out.println("Tidak terhubung");
                } else {
                    System.out.println(data.distance);
                }
                curr = curr.getNext();
            }
        }

        public void printShortestPath(Graph<T> g, T start, T destination) {
            MyLinearList<DijkstraNode<T>> table = createTable(g);

            if (!g.adj.containsKey(start) || !g.adj.containsKey(destination)) {
                System.out.println("Bandara asal atau tujuan tidak ditemukan.");
                return;
            }

            runDijkstra(g, start, table);
            DijkstraNode<T> destinationData = getData(table, destination);

            if (destinationData.distance == Integer.MAX_VALUE) {
                System.out.println("Flight tidak tersedia.");
                return;
            }

            String path = buildPath(table, start, destination);

            MyLinearList<T> pathList = getPathList(table, start, destination);

            int transitCount = countTransit(path);

            System.out.println("\nRUTE PENERBANGAN TERMURAH");
            System.out.println("Asal\t\t: " + start);
            System.out.println("Tujuan\t\t: " + destination);

            if (transitCount == 0) {
                System.out.println("Tipe\t\t: Direct Flight");
            } else {
                System.out.println("Tipe\t\t: Transit Flight");
            }

            System.out.println("Transit\t\t: " + transitCount);
            System.out.println("Rute\t\t: " + path);

            System.out.println("\nDetail Harga:");

            Node<T> curr = pathList.head;

            while (curr != null && curr.getNext() != null) {

                T from = curr.getData();
                T to = curr.getNext().getData();

                int price = g.getEdgeWeight(from, to);

                System.out.println(
                        from + " -> " + to
                        + "\t: Rp " + price
                );

                curr = curr.getNext();
            }

            System.out.println("\nTotal Cost\t: Rp " + destinationData.distance);
        }

        private void runDijkstra(Graph<T> g, T start, MyLinearList<DijkstraNode<T>> table) {
            Heap<Integer, T> pq = new Heap<Integer, T>(countEdges(g) + 1, true);
            DijkstraNode<T> startData = getData(table, start);

            if (startData == null) {
                return;
            }

            startData.distance = 0;
            pq.insert(0, start);

            while (pq.size() > 0) {
                BTNode<Integer, T> heapNode = pq.removeFirst();
                T current = heapNode.getData();
                int currentDistance = heapNode.getKey();
                DijkstraNode<T> currentData = getData(table, current);

                if (currentDistance > currentData.distance) {
                    continue;
                }

                MyLinearList<Edge<T>> list = g.adj.get(current);
                Node<Edge<T>> curr = list.head;
                while (curr != null) {
                    Edge<T> edge = curr.getData();
                    T neighbor = edge.getNeighbor();
                    DijkstraNode<T> neighborData = getData(table, neighbor);
                    int newDistance = currentData.distance + edge.getWeight();

                    if (newDistance < neighborData.distance) {
                        neighborData.distance = newDistance;
                        neighborData.previous = current;
                        pq.insert(newDistance, neighbor);
                    }

                    curr = curr.getNext();
                }
            }
        }

        private MyLinearList<DijkstraNode<T>> createTable(Graph<T> g) {
            MyLinearList<DijkstraNode<T>> table = new MyLinearList<>();

            for (T key : g.adj.keySet()) {
                table.pushQ(new DijkstraNode<T>(key, Integer.MAX_VALUE, null));
            }

            return table;
        }

        private DijkstraNode<T> getData(MyLinearList<DijkstraNode<T>> table, T vertex) {
            Node<DijkstraNode<T>> curr = table.head;

            while (curr != null) {
                if (curr.getData().vertex.equals(vertex)) {
                    return curr.getData();
                }
                curr = curr.getNext();
            }

            return null;
        }

        private int countEdges(Graph<T> g) {
            int total = 0;

            for (T key : g.adj.keySet()) {
                Node<Edge<T>> curr = g.adj.get(key).head;
                while (curr != null) {
                    total++;
                    curr = curr.getNext();
                }
            }

            return total;
        }

        private String buildPath(MyLinearList<DijkstraNode<T>> table, T start, T destination) {
            MyLinearList<T> path = new MyLinearList<>();
            T current = destination;

            while (current != null) {
                path.pushS(current);
                if (current.equals(start)) {
                    break;
                }
                current = getData(table, current).previous;
            }

            StringBuilder text = new StringBuilder();
            while (!path.isEmpty()) {
                text.append(path.remove());
                if (!path.isEmpty()) {
                    text.append(" -> ");
                }
            }

            return text.toString();
        }

        private MyLinearList<T> getPathList(MyLinearList<DijkstraNode<T>> table, T start, T destination) {

            MyLinearList<T> path = new MyLinearList<>();

            T current = destination;

            while (current != null) {
                path.pushS(current);

                if (current.equals(start)) {
                    break;
                }

                current = getData(table, current).previous;
            }

            return path;
        }
    }

    private static int countTransit(String path) {
        int airportCount = 1;

        for (int i = 0; i < path.length() - 1; i++) {
            if (path.charAt(i) == '-' && path.charAt(i + 1) == '>') {
                airportCount++;
            }
        }

        return airportCount - 2;
    }

    private boolean contains(MyLinearList<T> list, T data) {
        Node<T> curr = list.head;

        while (curr != null) {
            if (curr.getData().equals(data)) {
                return true;
            }
            curr = curr.getNext();
        }

        return false;
    }

    public int getEdgeWeight(T from, T to) {
        if (!adj.containsKey(from)) {
            return -1;
        }

        Node<Edge<T>> curr = adj.get(from).head;

        while (curr != null) {
            Edge<T> edge = curr.getData();

            if (edge.getNeighbor().equals(to)) {
                return edge.getWeight();
            }

            curr = curr.getNext();
        }

        return -1;
    }
}
