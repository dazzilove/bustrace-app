package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.Route;
import com.dazzilove.bustrace.app.domain.TripPlan;
import com.dazzilove.bustrace.app.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    final static Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    TripPlanService tripPlanService;

    @Override
    public List<Route> getRoutes() {
        List<Route> routeList = routeRepository.findAll().stream()
                .filter(tempRoute -> "N".equals(tempRoute.getDeleteYn())).collect(Collectors.toList());
        routeList.stream()
                .forEach(tempRoute -> {
                    List<TripPlan> tripPlans = null;
                    try {
                        tripPlans = getTripPlans(tempRoute.getRouteId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!tripPlans.isEmpty()) {
                        tempRoute.setTripPlanList(tripPlans);
                        int yesterdayTripRecordCount = 0;
                        int todayTripRecordCount = 0;

                        for(TripPlan tempTripPlan: tripPlans) {
                            if ("Y".equals(tempTripPlan.getYesterdayTripRecordYn())) {
                                yesterdayTripRecordCount++;
                            }
                            if ("Y".equals(tempTripPlan.getTodayTripRecordYn())) {
                                todayTripRecordCount++;
                            }
                        }

                        tempRoute.setTotalTripPlanCount(tripPlans.size());
                        tempRoute.setYesterdayTripRecordCount(yesterdayTripRecordCount);
                        tempRoute.setTodayTripRecordCount(todayTripRecordCount);
                    }
                });
        return routeList;
    }

    private List<TripPlan> getTripPlans(String routeId) {
        return tripPlanService.findByRouteId(routeId).stream()
                .filter((TripPlan tempTripPlan) -> ("N".equals(tempTripPlan.getDeleteYn())))
                .sorted((TripPlan a, TripPlan b) -> ((a.getTurnNumber() > b.getTurnNumber()) ? 1 : -1))
                .collect(Collectors.toList());
    }
}
