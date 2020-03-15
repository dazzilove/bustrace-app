package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.Route;
import com.dazzilove.bustrace.app.domain.Station;

import java.util.List;

public interface RouteService {
    List<Route> getRoutes();

    Route getOnlyRouteInfo(String id);

    List<Station> getStationsByRouteId(String routeId);
}
