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
import java.io.IOException;
import java.util.List;

@WebServlet(name="rootEndpoint", urlPatterns = "/actions")
public class RootServlet extends HttpServlet {


    /** Getting Diameter **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Graph<Station, DefaultEdge> unweightedGraphSubway = SubwayGraphFactory.createSubwayUnweightedGraph();
        Graph<Station, DefaultEdge> weightedGraphSubway = SubwayGraphFactory.createSubwayWeightedGraph();

        List<Station> stations = SubwayGraphFactory.setStations(unweightedGraphSubway);
        List<Station> weightedStations = SubwayGraphFactory.setStations(weightedGraphSubway);

        PathFinder pathFinder = new SubwayPathFinder();
        List<Station> diameterUnweightedStationsPath = pathFinder.findDiameterPathWithBFS(unweightedGraphSubway, stations);
        List<Station> diameterWeightedStationsPath = pathFinder.findDiameterPathWithDijkstra(weightedGraphSubway, stations);


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

        String srcName = request.getParameter("src");
        String destName = request.getParameter("dest");


    }

}
