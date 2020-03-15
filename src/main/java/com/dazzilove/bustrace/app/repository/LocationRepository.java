package com.dazzilove.bustrace.app.repository;

import com.dazzilove.bustrace.app.domain.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface LocationRepository extends MongoRepository<Location, UUID> {
}
