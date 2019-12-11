package com.arnouldcoulon.subway;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Pour ne pas recuperer les infos inutiles
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ligne {

	private String name;
	private String type;
	private String num;
	private List<Station> stations;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public List<Station> getStations() {
		return stations;
	}
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	
	public String toString() {
		   
				   String res =" /////// Ligne = " + name + "\\\\\\\\"+ "\n" ;
				   for(Station station: stations) {
					   if(station.getNom().equals("Gambetta"))
						return res;
					   res+= " Station = " +station.getNom()+ " \n";
					   }
				   
				   return res;
		}
	
	
}
