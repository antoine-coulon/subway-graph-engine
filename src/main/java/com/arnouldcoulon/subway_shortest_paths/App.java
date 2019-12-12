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
			 
			 if(station.getType().equals("metro")) {
				 
				 stations.put(itName.next(), station);
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
				 List<Station> stationsInLigne = new ArrayList<>();
				 //On cherche a integrer toutes les routes deservie par la ligne pour trouver les stations
				 while(it_route.hasNext()) {
					 //Creer un type route Utile ????
					 Iterator<JsonNode> it_station = it_route.next().iterator();
					 while(it_station.hasNext()) {
						 String key_station = it_station.next().asText();
						 Station station = stations.get(key_station);
						 if(station!=null)
						 stationsInLigne.add(station);
					 }
					 
				 }
				 ligne.setStations(stationsInLigne);
				 lignes.add(ligne);
			 }
				 
		}
		
		
        }catch (Exception e) {
        	e.printStackTrace();
        }
        
        System.out.println("////////// LISTE DES STATIONS //////////");
        stations.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue().toString());  
         });
        
        System.out.println("////////// LISTE DES LIGNES ET LEUR STATION ASSOCIEES //////////");
        for(Ligne ligne : lignes) {
        	System.out.println(ligne.toString());
        }
        
        
    }
}
