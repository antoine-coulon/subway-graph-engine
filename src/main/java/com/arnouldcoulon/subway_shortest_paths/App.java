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
import com.arnouldcoulon.subway.JsonDataReader;
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
        
        JsonDataReader subwayDatas = new JsonDataReader();
        System.out.println(subwayDatas.toString());


}
}
