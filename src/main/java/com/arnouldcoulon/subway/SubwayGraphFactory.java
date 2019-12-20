package com.arnouldcoulon.subway;

import java.util.ArrayList;
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

	public static ArrayList<Station> setStations(Graph<Station, DefaultEdge> graph) {
		return new ArrayList<Station>(graph.vertexSet());
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

        String typeGraph = type == 0 ? "Unweighted" : "Weighted";
        System.out.println( typeGraph + " Graph is ready!" );

		return graph;	
	}
	
	private static double getDistanceBetweenStations(Station src, Station dst) {

		int earthRadiusKm = 6371;

		double dLat = degreesToRadians(dst.getLat()-src.getLat());
		double dLon = degreesToRadians(dst.getLng()-src.getLng());

		double lat1 = degreesToRadians(src.getLat());
		double lat2 = degreesToRadians(dst.getLat());

		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return earthRadiusKm * c;
	}

	private static double degreesToRadians(double degrees) {
		return degrees * Math.PI / 180;
	}

}
