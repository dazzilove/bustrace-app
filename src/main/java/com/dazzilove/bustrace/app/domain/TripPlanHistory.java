package com.dazzilove.bustrace.app.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
public class TripPlanHistory {
    @Id
    private UUID historyId;
    private LocalDateTime historyCreatedAt;
    private TripPlan tripPlan;

}