package com.dazzilove.bustrace.app.repository;

import com.dazzilove.bustrace.app.domain.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RouteRepository extends MongoRepository<Route, UUID> {
}
