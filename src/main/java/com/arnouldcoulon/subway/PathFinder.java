package com.arnouldcoulon.subway;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.Set;

public interface PathFinder{

    double getGraphDiameter(Graph<Station, DefaultEdge> graph);
    Set<Station> getGraphPeriphery(Graph<Station, DefaultEdge> graph);
    List<Station> findDiameterPathWithBFS(Graph<Station, DefaultEdge> graph, List<Station> stations);
    List<Station> findDiameterPathWithDijkstra(Graph<Station, DefaultEdge> graph, List<Station> stations);
    List<Station> findShortestPathWithDijkstra(Graph<Station, DefaultEdge> graph, Station src, Station dest);
    List<Station> findShortestPathWithBFS(Graph<Station, DefaultEdge> graph, Station src, Station dest);
    List<Set<Station>> findGraphClusters(Graph<Station, DefaultEdge> graph);

}
