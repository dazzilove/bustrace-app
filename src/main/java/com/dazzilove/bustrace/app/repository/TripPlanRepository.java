package com.dazzilove.bustrace.app.repository;

import com.dazzilove.bustrace.app.domain.TripPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface TripPlanRepository extends MongoRepository<TripPlan, UUID> {
    List<TripPlan> findByRouteId(String routeId);
}