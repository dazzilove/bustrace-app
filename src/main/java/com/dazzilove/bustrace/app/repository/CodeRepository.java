package com.dazzilove.bustrace.app.repository;

import com.dazzilove.bustrace.app.domain.Code;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CodeRepository extends MongoRepository<Code, UUID> {
}
