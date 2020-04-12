package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.controller.dto.LocationParams;
import com.dazzilove.bustrace.app.domain.Location;

import java.util.List;

public interface RealtimeLocationService {
    List<Location> getLocations(LocationParams locationParams) throws Exception;
}
