package com.arnouldcoulon.subway;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Pour ne pas recuperer les infos inutiles
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ligne {

	private String name;
	private String type;
	private String num;
	private List<Map<String, Station>> routes; // Ligne possede des routes // 1 route est constituté de stations(arrets)
	
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

	public List<Map<String, Station>> getRoutes() {
		return routes;
	}
	public void setRoutes(List<Map<String, Station>> routes) {
		this.routes = routes;
	}


	public String toString() {
		   
				   StringBuilder res = new StringBuilder(" /////// Ligne = " + "(" + num + ") " + name + " \\\\\\\\" + "\n");

			        /* for (Map.Entry station : routes.iterator()) {
			            res.append(" Station = ").append(station.getKey()).append(" ").append(((Station) station.getValue()).getNom());
			        } */
				   return res.toString();
	}

	
}
