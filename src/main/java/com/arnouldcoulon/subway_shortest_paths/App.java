package com.arnouldcoulon.subway_shortest_paths;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import com.arnouldcoulon.subway.Correspondance;
import com.arnouldcoulon.subway.JsonDataReader;
import com.arnouldcoulon.subway.Ligne;
import com.arnouldcoulon.subway.Station;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        JsonDataReader subwayDatas = new JsonDataReader();
        
        //Generation du graph simple
        
        Graph<Station, DefaultEdge> graphSubway = new SimpleGraph<>(DefaultEdge.class);
        
        //Creation des vertex
        List<Station> stations = subwayDatas.getListStations();
        for(Station station : stations) {
        	graphSubway.addVertex(station);
        }
        
        //Creation des edges
        
        List<Ligne> lignes = subwayDatas.getLignes();
        for(Ligne ligne : lignes) {
        	List<List<Station>> routes =ligne.getRoutes();
        	for(List<Station> route : routes) {
        		Station lastStation = null;
        		for(Station station :route) {
        			if(lastStation!=null)
        				graphSubway.addEdge(lastStation, station);
        			lastStation = station;
        		}
        		
        	}
        }
        
        
        //Creation des edges : correspondances
        
        for(Station station : stations) {
        	
        	 //Si cette station a des correspondances
            if(Correspondance.hasCorrespondances(station)) {
            	// On récupère une liste de station qui sont les correspondances de la station donnée 
            	List<Station> ls = Correspondance.getStationCorrespondances(station);
            	for(Station correspStation: ls) {
            		// Ajout d'un edge entre cette station et la correspondance par exemple 
            		graphSubway.addEdge(station,correspStation);
            	}
            }
        	
        }
     
        //GraphPath shortestPath = BellmanFordShortestPath.findPathBetween(graphSubway,stations.get(0), stations.get(stations.size()-1));
        //shortestPath.get
       // Iterator<GraphPath<Station,DefaultEdge>> it_path = new EppsteinShortestPathIterator(graphSubway,stations.get(0), stations.get(stations.size()-1));
        
        //System.out.println(subwayDatas.toString());


}
}
