package com.arnouldcoulon.subway;

import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.ClusteringCoefficient;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.GraphMeasurer;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SubwayPathFinder implements PathFinder {


    /*
    * Class containing all methods that we can perform on our generic graphs.
    * These are available whether for the console or server mode.
    * */

    public double getGraphDiameter(Graph<Station, DefaultEdge> graph) {
        return new GraphMeasurer<Station, DefaultEdge>(graph).getDiameter();
    }

    public Set<Station> getGraphPeriphery(Graph<Station, DefaultEdge> graph) {
        return new GraphMeasurer<Station, DefaultEdge>(graph).getGraphPeriphery();
    }

    public List<Station> findDiameterPathWithBFS(Graph<Station, DefaultEdge> graph, List<Station> stations) {
        Set<Station> peripheryStations = getGraphPeriphery(graph);
        List<Station> startAndEndPeriphery = new ArrayList<>(peripheryStations);
        return this.findShortestPathWithBFS(graph, startAndEndPeriphery.get(0), startAndEndPeriphery.get(1));
    }

    public List<Station> findDiameterPathWithDijkstra(Graph<Station, DefaultEdge> graph, List<Station> stations) {
        Set<Station> peripheryStations = getGraphPeriphery(graph);
        List<Station> startAndEndPeriphery = new ArrayList<>(peripheryStations);
        return this.findShortestPathWithDijkstra(graph, startAndEndPeriphery.get(0), startAndEndPeriphery.get(1));
    }

    public List<Station> findShortestPathWithDijkstra(Graph<Station, DefaultEdge> graph, Station src, Station dest) {
        return DijkstraShortestPath.findPathBetween(graph, src, dest).getVertexList();
    }

    public List<Station> findShortestPathWithBFS(Graph<Station, DefaultEdge> graph, Station src, Station dest) {
        return BFSShortestPath.findPathBetween(graph, src, dest).getVertexList();
    }


    public List<Set<Station>> findGraphClusters(Graph<Station, DefaultEdge> graph) {
        return null;
    }
}
