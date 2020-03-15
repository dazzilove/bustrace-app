package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.TripPlan;
import com.dazzilove.bustrace.app.repository.TripPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripPlanServiceImpl implements TripPlanService {

    @Autowired
    private TripPlanRepository tripPlanRepository;

    @Override
    public List<TripPlan> findByRouteId(String routeId) {
        return tripPlanRepository.findByRouteId(routeId);
    }
}
