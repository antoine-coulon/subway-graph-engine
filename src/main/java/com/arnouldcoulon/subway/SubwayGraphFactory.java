package com.arnouldcoulon.subway;import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class SubwayGraphFactory {
	
	private static int SIMPLE_GRAPH = 0;
	private static int WEIGHTED_GRAPH = 1;
	
	
	public static Graph<Station, DefaultEdge> createSubwayUnweightedGraph(){
		
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
        
        System.out.println( "Graph pret !" );
        
		return graphSubway;
		
	}
	
	public static Graph<Station, DefaultEdge> createSubwayWeightedGraph(){
		return null;
		
	}	

}
