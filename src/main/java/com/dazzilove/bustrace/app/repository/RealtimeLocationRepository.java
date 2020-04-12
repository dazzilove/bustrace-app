package com.dazzilove.bustrace.app.repository;

import com.dazzilove.bustrace.app.domain.RealtimeLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RealtimeLocationRepository extends MongoRepository<RealtimeLocation, UUID> {
}
