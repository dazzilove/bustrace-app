package com.dazzilove.bustrace.app.repository;

import com.dazzilove.bustrace.app.domain.SpecialMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SpecialMessageRepository extends MongoRepository<SpecialMessage, UUID> {

    @Query("{'routeId':?0}")
    List<SpecialMessage> findByRouteId(String routeId);
}
