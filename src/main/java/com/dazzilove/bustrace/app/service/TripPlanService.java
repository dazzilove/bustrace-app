package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.TripPlan;

import java.util.List;

public interface TripPlanService {
    List<TripPlan> findByRouteId(String routeId);
}
