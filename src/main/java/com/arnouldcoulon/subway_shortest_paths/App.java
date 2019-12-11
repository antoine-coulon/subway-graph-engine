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
        

        Map<String,Station> stations = new HashMap();
        
        try {
        
		//read json file data to String
		byte[] jsonData = Files.readAllBytes(Paths.get("data.json"));
		
		//create ObjectMapper instance
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode rootNode = mapper.readTree(jsonData);
		
		JsonNode stationNode = rootNode.path("stations");
		Iterator<String> itName = stationNode.fieldNames();
		
		
		Iterator<JsonNode> it = stationNode.iterator();
		while(it.hasNext()) {
			JsonNode station = it.next();
			 System.out.println(station);
			stations.put(itName.next(), mapper.treeToValue(station, Station.class));
		}
		
		
        }catch (Exception e) {
        	e.printStackTrace();
        }
        
        stations.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue().toString());  
         });
    }
}
