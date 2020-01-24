package com.arnouldcoulon.subway_shortest_paths;

import java.awt.*;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.arnouldcoulon.subway.*;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.alg.shortestpath.GraphMeasurer;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestSimplePaths;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Hello world!
 *
 */
public class App 
{
    private static Object GraphMeasurer;

    public static void main(String[] args )
    {


        /*
        * Instantiating all the needed classes and structures to build a generic Graph filled by our data.
        * The results are printed out in the console.
        * To get these results as JSON, use the beside server API.
        * */

        Graph<Station, DefaultEdge> unweightedGraphSubway = SubwayGraphFactory.createSubwayUnweightedGraph();
        Graph<Station, DefaultEdge> weightedGraphSubway = SubwayGraphFactory.createSubwayWeightedGraph();

    	List<Station> stations = SubwayGraphFactory.setStations(unweightedGraphSubway);
    	List<Station> weightedStations = SubwayGraphFactory.setStations(weightedGraphSubway);

        PathFinder pathFinder = new SubwayPathFinder();
        List<Station> diameterUnweightedStationsPath = pathFinder.findDiameterPathWithBFS(unweightedGraphSubway, stations);
        List<Station> diameterWeightedStationsPath = pathFinder.findDiameterPathWithDijkstra(weightedGraphSubway, stations);

        Station sourceWeighted = diameterWeightedStationsPath.get(12);
        Station destWeighted = diameterWeightedStationsPath.get(25);

        Station sourceUnweighted = diameterUnweightedStationsPath.get(10);
        Station destUnweighted = diameterWeightedStationsPath.get(20);

        System.out.println(sourceUnweighted.toString());
        System.out.println(destUnweighted.toString());
        System.out.println(sourceWeighted.toString());
        System.out.println(destUnweighted.toString());


        System.out.println("Diameter Path for the unweighted Graph");
        SubwayPrinter.printStations(diameterUnweightedStationsPath);
        System.out.println("Diameter Path for the weighted Graph");
        SubwayPrinter.printStations(diameterWeightedStationsPath);

        List<Station> shortestPathWeighted = pathFinder.findShortestPathWithDijkstra(weightedGraphSubway, sourceWeighted, destWeighted);
        List<Station> shortestPathUnweighted = pathFinder.findShortestPathWithBFS(unweightedGraphSubway, sourceUnweighted, destUnweighted);

        System.out.println("Shortest path from " + sourceUnweighted.getNom() + " to " + destUnweighted.getNom());
        SubwayPrinter.printStations(shortestPathUnweighted);

        System.out.println("Shortest path from " + sourceUnweighted.getNom() + " to " + destUnweighted.getNom());
        SubwayPrinter.printStations(shortestPathWeighted);


    }
}
