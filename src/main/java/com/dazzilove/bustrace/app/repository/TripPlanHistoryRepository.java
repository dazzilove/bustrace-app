package com.dazzilove.bustrace.app.repository;

import com.dazzilove.bustrace.app.domain.TripPlanHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface TripPlanHistoryRepository extends MongoRepository<TripPlanHistory, UUID> {
}
