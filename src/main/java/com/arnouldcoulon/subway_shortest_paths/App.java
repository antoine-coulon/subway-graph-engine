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

import com.arnouldcoulon.subway.Correspondance;
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
					 List<Map<String,Station>> routes = new ArrayList<>();
					 while(it_route.hasNext()) {

						 JsonNode routeNode = it_route.next();
						 Map<String, Station> route = new HashMap<>();
						 Iterator<JsonNode> it_station = routeNode.iterator();

						 while(it_station.hasNext()) {
							 //FOnctionne pas
							 String key_station = it_station.next().textValue();
							 Station station = stations.get(key_station);
							 if(station!=null)
								 route.put(key_station, station);
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
			Iterator<JsonNode> it_correspondances = correspNode.iterator();

			while(it_correspondances.hasNext()) {
				int numberCorrespondances = 0;
				JsonNode corresp = it_correspondances.next();
				List<Station> temporaryStationsAsCorrespondances = new ArrayList<Station>();
				for(JsonNode node: corresp) {
					String currentCorrespNumber = node.textValue();
					Station s = stations.get(currentCorrespNumber);
					if(s != null) {
						numberCorrespondances++;
						temporaryStationsAsCorrespondances.add(s);
					}
				}
				/* Il y'a au moins une correspondance entre deux stations, de type 'Métro', donc on ajoute cette correspondance */
				if(numberCorrespondances >= 2) {

					/* Pour chaque station associée à la correspondance, on va créer une Map
					qui relie à une station les autres stations de correspondances associées
					 */
					Iterator it_newCorrespondances = temporaryStationsAsCorrespondances.iterator();
					while(it_newCorrespondances.hasNext()) {
						/*Station newStationCorrespondance = (Station) it_newCorrespondances.next();
						if(!Correspondance.correspondances.contains(newStationCorrespondance)) {
							Correspondance.correspondances.add(newStationCorrespondance);
						}*/
						Station newStationCorrespondance = (Station) it_newCorrespondances.next();

						/* Si la station n'est pas déjà présente dans la liste des stations ayant des correspondances,
						on l'ajoute
						 */
						if(!Correspondance.correspondances.contains(newStationCorrespondance)) {
							Correspondance.correspondances.add(newStationCorrespondance);

							/* Une fois ajoutée, on va pour chaque station de la correspondance l'associer avec ses
							semblables dans une Map
							Par exemple on a une correspondance avec trois stations
							En prenant la Station 1 on aura "Station 1" => ["Station 2", "Station 3"]
							De sorte à ce quand on veut savoir si une station à des correspondances,
							on récupère dans les récupère en même temps
							 */
							Iterator it_newSubCorrespondances = temporaryStationsAsCorrespondances.iterator();

							/* Map qui vise à combiner une station avec les autres stations d'une correspondance */
							List<Station> associatedStations = new ArrayList<>();
							for(Station s : temporaryStationsAsCorrespondances) {
								/* Ici, on exclut la station dont on veut déterminer les stations associées car
								on ne veut pas qu'une station figure parmi ses propres correspondances
								 */
								if(!(newStationCorrespondance.equals(s))) {
									associatedStations.add(s);
									List<Station> copyList = new ArrayList<>(associatedStations);
									Correspondance.linkedCorrespondances.put(newStationCorrespondance.getNum(), copyList);
								}
							}
							associatedStations.clear();
						}
					}
					temporaryStationsAsCorrespondances.clear();

				}
			}

        } catch (Exception e) {
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

        System.out.println("//////// LISTE DES STATIONS QUI ONT DES CORRESPONDANCES //////////");
        for(Station s: Correspondance.correspondances) {
        	System.out.println(s.getNom());
		}

        System.out.println("/////// MAP QUI CONTIENT LA OU LES STATIONS ASSOCIEES A LA STATION DE CORRESPONDANCE ////////");
		for (Map.Entry<String, List<Station>> entry : Correspondance.linkedCorrespondances.entrySet()) {
			System.out.println("ID de la station = " + entry.getKey() + " - Nombre de correspondances (" + entry.getValue().size() + ")");
			for(Station s: entry.getValue()) {
				System.out.println("Station de correspondance => " + s.toString());
			}
			System.out.println("----------------");
		}

		/* Exemple où on récupère les correspondances d'une station donnée */
		Station s = stations.get("1694");
		/* Si cette station a des correspondances */
		if(Correspondance.hasCorrespondances(s)) {
			/* On récupère une liste de station qui sont les correspondances de la station donnée */
			List<Station> ls = Correspondance.getStationCorrespondances(s);
			for(Station correspStation: ls) {
				/* Ajout d'un edge entre cette station et la correspondance par exemple */
				// s.addEdge(correspStation);
			}
		}





}
}
