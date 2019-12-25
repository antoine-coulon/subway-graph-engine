package com.arnouldcoulon.backend.controllers;

import com.arnouldcoulon.subway.*;
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

@WebServlet(name="stationsEndpoint", urlPatterns = "/stations")
public class StationsServlet extends HttpServlet {


    /** Getting Stations **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        JsonDataReader jdr = new JsonDataReader();
        List<Ligne> stations = jdr.getLignes();

        ObjectMapper mapper = new ObjectMapper();
        String arrayToJson = mapper.writeValueAsString(stations);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(arrayToJson);
    }

}
