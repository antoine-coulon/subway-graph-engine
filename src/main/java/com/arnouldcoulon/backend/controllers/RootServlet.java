package com.arnouldcoulon.backend.controllers;

import com.arnouldcoulon.subway.PathFinder;
import com.arnouldcoulon.subway.Station;
import com.arnouldcoulon.subway.SubwayGraphFactory;
import com.arnouldcoulon.subway.SubwayPathFinder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@WebServlet(name="rootEndpoint", urlPatterns = "/actions")
public class RootServlet extends HttpServlet {


    /** Getting the Diameter **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        /* Creating the Graph using a Factory class */
        Graph<Station, DefaultEdge> unweightedGraphSubway = SubwayGraphFactory.createSubwayUnweightedGraph();
        /* Setting our stations data structure */
        List<Station> stations = SubwayGraphFactory.setStations(unweightedGraphSubway);

        /* Instantiating a template class to perform algorithms */
        PathFinder pathFinder = new SubwayPathFinder();
        List<Station> diameterUnweightedStationsPath = pathFinder.findDiameterPathWithBFS(unweightedGraphSubway, stations);

        /* Serializing the List of Vertices into JSON */
        ObjectMapper mapper = new ObjectMapper();
        String arrayToJson = mapper.writeValueAsString(diameterUnweightedStationsPath);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(arrayToJson);
    }

    /** Getting Shortest path **/
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      String srcId = request.getParameter("src");
      String destId = request.getParameter("dest");
      String graphMode = request.getParameter("mode");

      PathFinder pathFinder = new SubwayPathFinder();
      ObjectMapper mapper = new ObjectMapper();

      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setContentType("application/json;charset=UTF-8");

      if(graphMode.equals("weighted")) {
          Graph<Station, DefaultEdge> weightedGraphSubway = SubwayGraphFactory.createSubwayWeightedGraph();
          List<Station> weightedStations = SubwayGraphFactory.setStations(weightedGraphSubway);
          List<Station> shortestPath = pathFinder.findShortestPathWithDijkstra(weightedGraphSubway, SubwayGraphFactory.getStationById(srcId), SubwayGraphFactory.getStationById(destId));
          String arrayToJson = mapper.writeValueAsString(shortestPath);
          response.getWriter().write(arrayToJson);
      } else {
          Graph<Station, DefaultEdge> unweightedGraphSubway = SubwayGraphFactory.createSubwayUnweightedGraph();
          List<Station> unWeightedStations = SubwayGraphFactory.setStations(unweightedGraphSubway);
          List<Station> shortestPath = pathFinder.findShortestPathWithDijkstra(unweightedGraphSubway, SubwayGraphFactory.getStationById(srcId), SubwayGraphFactory.getStationById(destId));
          String arrayToJson = mapper.writeValueAsString(shortestPath);
          response.getWriter().write(arrayToJson);
      }



    }

}
