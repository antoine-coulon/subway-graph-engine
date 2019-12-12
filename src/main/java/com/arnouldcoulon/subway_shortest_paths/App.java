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
        

        Map<String,Station> stations = new HashMap<String, Station>();
        List<Ligne> lignes = new ArrayList<>();
        
        
        try {
        
		//read json file data to String
		byte[] jsonData = Files.readAllBytes(Paths.get("data.json"));
		
		//create ObjectMapper instance
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(jsonData);
		
		//Recuperation des stations //
		
		JsonNode stationsNode = rootNode.path("stations");
		Iterator<String> itName = stationsNode.fieldNames();
		
		Iterator<JsonNode> it_stations = stationsNode.iterator();
		while(it_stations.hasNext()) {
			JsonNode stationItem = it_stations.next();
			 Station station = mapper.treeToValue(stationItem, Station.class);
			 String key_station = itName.next();
			 if(station.getType().equals("metro")) {
				 
				 stations.put(key_station, station);
			 }
				 
		}
		
		//Recuperation des lignes //
		
		JsonNode lignesNode = rootNode.path("lignes");
		
		Iterator<JsonNode> it_lignes = lignesNode.iterator();
		while(it_lignes.hasNext()) {
			JsonNode ligneItem = it_lignes.next();
			 Ligne ligne = mapper.treeToValue(ligneItem, Ligne.class);
			 
			 if(ligne.getType().equals("metro")) {
				 JsonNode arretsNode = ligneItem.path("arrets");
				 Iterator<JsonNode> it_route = arretsNode.iterator();
				 
				
				 //On cherche a integrer toutes les routes deservie par la ligne pour trouver les stations
				 List<Map<Integer,Station>> routes = new ArrayList<>();
				 while(it_route.hasNext()) {
					 
					 JsonNode routeNode = it_route.next();
					 Map<Integer,Station> route = new HashMap<>();
					 Iterator<JsonNode> it_station = routeNode.iterator();
					 int stationInterator=0;
					 while(it_station.hasNext()) {
						 
						 //FOnctionne pas
						 String key_station = it_station.next().asText();
						 Station station = stations.get(key_station);
						 if(station!=null)
							 route.put(stationInterator, station);
						 stationInterator++;
					 }
					 routes.add(route);
					 
					 
				 }
				 ligne.setRoutes(routes);
				 lignes.add(ligne);
			 }
				 
		}
		
		//Recuperation des correspondances //
		//Cas particulier des stations differentes mais en liaison par sous terrain pieton
		
		JsonNode correspNode = rootNode.path("corresp");
		
		
		
		
		
		
		
        }catch (Exception e) {
        	e.printStackTrace();
        }
        
        System.out.println("////////// LISTE DES STATIONS //////////");
        stations.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue().toString());  
         });
        
        System.out.println("////////// LISTE DES LIGNES ET LEUR STATION ASSOCIEES //////////");
        for(Ligne ligne : lignes) {
        	//System.out.println(ligne.toString());
        }
        
        
    }
}
