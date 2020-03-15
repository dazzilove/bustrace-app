package com.dazzilove.bustrace.app.controller.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StationParams {

    private String routeId;
    private String stationId;
    private String createdAt;

}
