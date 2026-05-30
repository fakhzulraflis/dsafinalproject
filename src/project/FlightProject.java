package project;

import com.datastruct.Graph;
import data.FlightData;
import java.util.Scanner;
import model.Airport;

public class FlightProject {
    public static void main(String[] args) {

        Graph<Airport> graph = new Graph<>(true);

        // load seluruh data flight
        FlightData.load(graph);

        Scanner input = new Scanner(System.in);

        System.out.print("Bandara asal   : ");
        String asal = input.nextLine().toUpperCase();

        System.out.print("Bandara tujuan : ");
        String tujuan = input.nextLine().toUpperCase();

        Airport start = FlightData.airports.get(asal);
        Airport destination = FlightData.airports.get(tujuan);

        if (start == null || destination == null) {
            System.out.println("Kode bandara tidak ditemukan.");
            return;
        }

        Graph.Dijkstra<Airport> dijkstra = new Graph.Dijkstra<>();

        dijkstra.printShortestPath(graph, start, destination);
    }
}