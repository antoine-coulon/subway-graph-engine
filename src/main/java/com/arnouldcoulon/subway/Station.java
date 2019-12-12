package com.arnouldcoulon.subway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Permet de pas recuperer tous les elements du Json
@JsonIgnoreProperties(ignoreUnknown = true)
public class Station {

	private String num;
	private String nom;
	private double lat;
	private double lng;
	private String type;
	private boolean isHue; // inutile ...
	
	public String getNum() { return num; }
	public void setNum() { this.num = num; }
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isHue() {
		return isHue;
	}
	public void setHue(boolean isHue) {
		this.isHue = isHue;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public String toString() {
		   return " Nom = " + nom + " | lat = "+ lat + " | lng = "+ lng + " | type = "+ type + " | id (number) = " + num + " | IsHue = "+ isHue ;
		}
	
}
