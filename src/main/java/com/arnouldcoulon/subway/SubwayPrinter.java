package com.arnouldcoulon.subway;


import java.util.List;

public class SubwayPrinter {

    /* Utility class used to print over Lists */

    public static void printStations(List<Station> stations) {
        for(Station s : stations) {
            System.out.println(s);
        }
        System.out.println("*********** END PRINTING ***********");
    }

    public static int getStationIndex(Station station, List<Station> stations) {
        int index = 0;
        for(Station s : stations) {
            if(s.equals(station)) {
                break;
            }
            index++;
        }
        return index;
    }
}
