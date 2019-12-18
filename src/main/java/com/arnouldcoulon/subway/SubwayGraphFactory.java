package com.arnouldcoulon.subway;

import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class SubwayGraphFactory {
	
	private static int UNWEIGHTED_GRAPH = 0;
	private static int WEIGHTED_GRAPH = 1;
	
	
	public static Graph<Station, DefaultEdge> createSubwayUnweightedGraph(){
		
		return generateSubwayGraph(UNWEIGHTED_GRAPH);
	}
	
	public static Graph<Station, DefaultEdge> createSubwayWeightedGraph(){

		return generateSubwayGraph(WEIGHTED_GRAPH);
		
	}
	
	private static Graph<Station, DefaultEdge> generateSubwayGraph(int type){
		
		JsonDataReader subwayDatas = new JsonDataReader();
        
        //Generation du graph simple
        
        Graph<Station, DefaultEdge> graph;
        
        if(type == UNWEIGHTED_GRAPH)
        	graph = new SimpleGraph<>(DefaultEdge.class);
        else if( type == WEIGHTED_GRAPH )
        	graph = new SimpleWeightedGraph<Station,DefaultEdge>(DefaultEdge.class);
        else
        	return null; //BofBof
        
        //Creation des vertex
        List<Station> stations = subwayDatas.getListStations();
        for(Station station : stations) {
        	graph.addVertex(station);
        }
        
        //Creation des edges
        
        List<Ligne> lignes = subwayDatas.getLignes();
        for(Ligne ligne : lignes) {
        	List<List<Station>> routes =ligne.getRoutes();
        	for(List<Station> route : routes) {
        		Station lastStation = null;
        		for(Station station :route) {
        			if(lastStation!=null) {
        				graph.addEdge(lastStation, station);
        				if(type == WEIGHTED_GRAPH)
        					graph.setEdgeWeight(lastStation, station, getDistanceBetweenStations(lastStation,station));
        			}
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
            		graph.addEdge(station,correspStation);
            		if(type == WEIGHTED_GRAPH)
            		graph.setEdgeWeight(station, correspStation, getDistanceBetweenStations(station,correspStation)); // On ajoute un poids au correspondance ????
            	}
            }
        	
        }
        
        System.out.println( "Graph pret !" );
		
		
		return graph;	
	}
	
	private static double getDistanceBetweenStations(Station src, Station dst) {
		//Distance euclidienne
		return Math.sqrt((Math.pow((src.getLat() - dst.getLat()),2) + Math.pow((src.getLng() - dst.getLng()),2)));
	}

}
