package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.TripPlan;

import java.util.List;

public interface TripPlanService {
    List<TripPlan> findByRouteId(String routeId);

    void addTripPlan(TripPlan tripPlan);

    TripPlan findById(String id);

    void editTripPlan(TripPlan tripPlan);

    void deleteTripPlan(TripPlan tripPlan) throws Exception;
}
