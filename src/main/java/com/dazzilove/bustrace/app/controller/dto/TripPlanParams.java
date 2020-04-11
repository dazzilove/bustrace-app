package com.dazzilove.bustrace.app.controller.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class TripPlanParams {
    private String id;
    private String routeId;
    private String tripPlanId;
    private String plateNo;
    private String plateType;
    private String weekendOperationYn;
    private String spareYn;
    private String schoolBreakReductionYn;
    private LocalDateTime schoolBreakReductionStartedAt;
    private String tripStopYnl;
    private LocalDateTime tripStopStartedAt;

}
