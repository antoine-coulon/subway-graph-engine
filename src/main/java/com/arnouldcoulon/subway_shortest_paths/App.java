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


        Graph<Station, DefaultEdge> unweightedGraphSubway = SubwayGraphFactory.createSubwayUnweightedGraph();
        Graph<Station, DefaultEdge> weightedGraphSubway = SubwayGraphFactory.createSubwayWeightedGraph();

        //AllDirectedPaths inspectorPath = new AllDirectedPaths(graphSubway);
        //List<GraphPath> paths = inspectorPath.getAllPaths(stations.get(0), stations.get(stations.size()-1), false, Integer.MAX_VALUE);
       //KShortestPaths<Station, DefaultEdge> pathInspector = new KShortestPaths<Station, DefaultEdge>(graphSubway,Integer.MAX_VALUE, Integer.MAX_VALUE);
        //GraphPath shortestPath = BellmanFordShortestPath.findPathBetween(graphSubway,stations.get(0), stations.get(stations.size()-1));
        //shortestPath.get
       // Iterator<GraphPath<Station,DefaultEdge>> it_path = new EppsteinShortestPathIterator(graphSubway,stations.get(0), stations.get(stations.size()-1));
        
        //System.out.println(subwayDatas.toString());

    	List<Station> stations = SubwayGraphFactory.setStations(unweightedGraphSubway);
    	List<Station> weightedStations = SubwayGraphFactory.setStations(weightedGraphSubway);


        KShortestSimplePaths shortestPathsFinder = new KShortestSimplePaths(unweightedGraphSubway);
        List<GraphPath<Station,DefaultEdge>> shortestPaths = shortestPathsFinder.getPaths(stations.get(0), stations.get(stations.size()-1),300);
        
        List<Station>  shortPath = shortestPaths.get(0).getVertexList();
        GraphPath longestShortPath = shortestPaths.get(shortestPaths.size()-1);
        List<Station> pathFound = longestShortPath.getVertexList();
        
        System.out.println(" TRAJET PLUS COURT DE "+ stations.get(0).getNom() +" A "+ stations.get(stations.size()-1).getNom()+"\n");
        for(Station station : shortPath) {
        	//System.out.println(station.getNom());
        }
        
        
        System.out.println(" TRAJET DE "+ stations.get(0).getNom() +" A "+ stations.get(stations.size()-1).getNom()+"\n");
        for(Station station : pathFound) {
        	//System.out.println(station.getNom());
        	
        }

        PathFinder pathFinder = new SubwayPathFinder();
        List<Station> diameterUnweightedStationsPath = pathFinder.findDiameterPathWithBFS(unweightedGraphSubway, stations);
        List<Station> diameterWeightedStationsPath = pathFinder.findDiameterPathWithDijkstra(weightedGraphSubway, stations);

        System.out.println("Diameter Path for the unweighted Graph");
        SubwayPrinter.printStations(diameterUnweightedStationsPath);
        System.out.println("Diameter Path for the weighted Graph");
        SubwayPrinter.printStations(diameterWeightedStationsPath);

    }
}
