package com.dazzilove.bustrace.app.repository;

import com.dazzilove.bustrace.app.domain.BusLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface BusLocationRepository extends MongoRepository<BusLocation, UUID> {
}
