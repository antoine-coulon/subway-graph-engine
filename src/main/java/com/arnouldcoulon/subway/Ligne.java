package com.arnouldcoulon.subway;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* class used for to serialize JSON into a Java Object */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ligne {

	private String name;
	private String type;
	private String num;
	private List<List<Station>> routes;
	
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

	public List<List<Station>> getRoutes() {
		return routes;
	}

	public void setRoutes(List<List<Station>> routes) {
		this.routes = routes;
	}

	public String toString() {
		StringBuilder res = new StringBuilder(" /////// Ligne = " + "(" + num + ") " + name + " \\\\\\\\" + "\n");
		return res.toString();
	}

	
}
