package com.datastruct;

/*
 * Graph berbobot yang disimpan dalam bentuk adjacency list.
 * Setiap vertex memiliki daftar edge yang menghubungkannya ke vertex lain.
 * Sumber referensi:
 * https://www.lavivienpost.net/weighted-graph-as-adjacency-list/
 */
import java.util.*;

// Menyimpan hubungan dari satu vertex ke vertex tetangga beserta bobotnya.
class Edge<T> {

    private T vertex;
    private T neighbor;
    private int weight;

    // Membuat edge dari vertex u ke vertex v dengan bobot w.
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

    // Mengembalikan bobot edge.
    public int getWeight() {
        return weight;
    }

    // Menampilkan edge dalam format (vertex, tetangga, bobot).
    @Override
    public String toString() {
        return "(" + vertex + "," + neighbor + "," + weight + ")";
    }
}

public class Graph<T> {

    // Memetakan setiap vertex ke daftar edge yang keluar dari vertex tersebut.
    private Map<T, MyLinearList<Edge<T>>> adj;
    // Bernilai true untuk directed graph dan false untuk SOQundirected graph.
    boolean directed;

    // Membuat graph sesuai jenis yang diberikan.
    public Graph(boolean type) {
        adj = new LinkedHashMap<>();
        directed = type;
    }

    // Menambahkan dua vertex jika belum ada, lalu menghubungkannya dengan edge.
    public void addEdge(T a, T b, int w) {
        adj.putIfAbsent(a, new MyLinearList<>());
        adj.putIfAbsent(b, new MyLinearList<>());
        Edge<T> edge1 = new Edge<>(a, b, w);
        adj.get(a).pushQ(edge1);

        // Undirected graph membutuhkan edge dengan arah sebaliknya.
        if (!directed) {
            Edge<T> edge2 = new Edge<>(b, a, w);
            adj.get(b).pushQ(edge2);
        }
    }

    // Menghapus hubungan dari a ke b, termasuk arah sebaliknya pada undirected graph.
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

    // Mencari dan menghapus semua edge yang menuju neighbor dari sebuah daftar.
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

    // Menampilkan setiap vertex beserta seluruh edge yang dimilikinya.
    public void printGraph() {
        for (T key : adj.keySet()) {
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

    // Menghasilkan urutan kunjungan Depth First Search (DFS) menggunakan stack.
    public MyLinearList<T> DFSOrder(T start) {
        MyLinearList<T> visited = new MyLinearList<>();
        MyLinearList<T> stack = new MyLinearList<>();

        // Graph kosong dikembalikan jika vertex awal tidak ditemukan.
        if (!adj.containsKey(start)) {
            return visited;
        }

        stack.pushS(start);

        while (!stack.isEmpty()) {
            T current = stack.remove();

            if (!contains(visited, current)) {
                visited.pushQ(current);

                // Tetangga ditampung sementara agar urutan masuk stack tetap sesuai.
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

    // Menghasilkan urutan kunjungan Breadth First Search (BFS) menggunakan queue.
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

    // Mencetak hasil penelusuran DFS mulai dari vertex start.
    public void printDFS(T start) {
        System.out.print("DFS dari " + start + " : ");
        DFSOrder(start).cetakList();
    }

    // Mencetak hasil penelusuran BFS mulai dari vertex start.
    public void printBFS(T start) {
        System.out.print("BFS dari " + start + " : ");
        BFSOrder(start).cetakList();
    }

    // Memeriksa apakah dest dapat dicapai dari src menggunakan DFS.
    public boolean DFS(T src, T dest) {
        if (!adj.containsKey(src) || !adj.containsKey(dest)) {
            return false;
        }

        return contains(DFSOrder(src), dest);
    }

    // Memeriksa apakah dest dapat dicapai dari src menggunakan BFS.
    public boolean BFS(T src, T dest) {
        if (!adj.containsKey(src) || !adj.containsKey(dest)) {
            return false;
        }

        return contains(BFSOrder(src), dest);
    }

    // Menghitung jarak terpendek dari satu vertex dengan algoritma Dijkstra.
    public static class Dijkstra<T> {

        // Menyimpan hasil sementara Dijkstra untuk sebuah vertex.
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

        // Mengembalikan jarak terpendek dari start ke seluruh vertex.
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

        // Mencetak jarak terpendek dari start ke seluruh vertex.
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

        // Mencetak rute dan rincian biaya termurah dari start ke destination.
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

            // Membaca setiap pasangan vertex pada rute untuk menampilkan harga per edge.
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

        // Menjalankan Dijkstra dengan min-heap sebagai priority queue.
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

                // Abaikan data heap lama jika jarak yang lebih pendek sudah ditemukan.
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

                    // Perbarui jarak dan asal sebelumnya jika rute baru lebih murah.
                    if (newDistance < neighborData.distance) {
                        neighborData.distance = newDistance;
                        neighborData.previous = current;
                        pq.insert(newDistance, neighbor);
                    }

                    curr = curr.getNext();
                }
            }
        }

        // Membuat tabel awal dengan jarak tak terhingga untuk setiap vertex.
        private MyLinearList<DijkstraNode<T>> createTable(Graph<T> g) {
            MyLinearList<DijkstraNode<T>> table = new MyLinearList<>();

            for (T key : g.adj.keySet()) {
                table.pushQ(new DijkstraNode<T>(key, Integer.MAX_VALUE, null));
            }

            return table;
        }

        // Mengambil data Dijkstra milik vertex tertentu dari tabel.
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

        // Menghitung jumlah edge untuk menentukan kapasitas awal heap.
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

        // Menyusun teks rute dengan mengikuti previous dari tujuan kembali ke awal.
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

        // Menyusun rute sebagai list agar setiap edge dapat diproses secara terpisah.
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

    // Menghitung jumlah bandara transit dari teks rute.
    private static int countTransit(String path) {
        int airportCount = 1;

        for (int i = 0; i < path.length() - 1; i++) {
            if (path.charAt(i) == '-' && path.charAt(i + 1) == '>') {
                airportCount++;
            }
        }

        return airportCount - 2;
    }

    // Memeriksa apakah sebuah data sudah ada di dalam list.
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

    // Mengambil bobot edge dari vertex from ke vertex to.
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
