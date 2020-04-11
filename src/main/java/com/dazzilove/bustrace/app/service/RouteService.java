package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.Route;
import com.dazzilove.bustrace.app.domain.Station;

import java.util.List;

public interface RouteService {
    List<Route> getRoutes();

    List<Station> getStationsByRouteId(String routeId);

    void addRoute(Route route);

    void editRoute(Route route);

    void deleteRoute(Route route) throws Exception;

    Route getRouteById(String id);
}
