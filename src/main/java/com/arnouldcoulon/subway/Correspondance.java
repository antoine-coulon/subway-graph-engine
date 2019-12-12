package com.arnouldcoulon.subway;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Correspondance {

    /* Simple liste de toutes les correspondances */
    public static List<Station> correspondances = new ArrayList<Station>();

    /* Map qui associe les correspondances entre-elles */
    public static Map<String, List<Station>> linkedCorrespondances = new HashMap<>();

    public static List<Station> getAllIndependentCorrespondances() {
        return correspondances;
    }

    public static Map<String, List<Station>> getLinksBetweenCorrespondances() {
        return linkedCorrespondances;
    }

    public static List<Station> getStationCorrespondances(Station s) {
        if(linkedCorrespondances.containsKey(s.getNum())) {
            return linkedCorrespondances.get(s.getNum());
        }
        return null;
    }

    public static boolean hasCorrespondances(Station s) {
        return linkedCorrespondances.containsKey((s.getNum()));
    }
}