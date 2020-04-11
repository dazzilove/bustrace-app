package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.DataGatherScheduler;
import com.dazzilove.bustrace.app.domain.Route;
import com.dazzilove.bustrace.app.domain.Station;
import com.dazzilove.bustrace.app.domain.TripPlan;
import com.dazzilove.bustrace.app.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    final static Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TripPlanService tripPlanService;

    @Override
    public Route getRouteById(String id) {
        return getRoute(id);
    }

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

    @Override
    public List<Station> getStationsByRouteId(String routeId) {
        List<AggregationOperation> list = new ArrayList<AggregationOperation>();
        if (routeId.length() > 0)
            list.add(Aggregation.match(Criteria.where("routeId").is(routeId)));
        list.add(Aggregation.sort(Sort.Direction.ASC, "stationSeq"));
        TypedAggregation<Station> agg = Aggregation.newAggregation(Station.class, list);

        List<Station> locations = mongoOperations.aggregate(agg, Station.class, Station.class).getMappedResults();
        if (locations == null)
            locations = new ArrayList<>();

        return locations;
    }

    @Override
    public void addRoute(Route route) {
        route.setId(UUID.randomUUID());
        route.setCreatedAt(LocalDateTime.now());
        routeRepository.insert(route);
    }

    @Override
    public void editRoute(Route route) {
        Route updateTarget = getRoute(route.getId().toString());
        updateTarget.setCompanyId(route.getCompanyId());
        updateTarget.setCompanyName(route.getCompanyName());
        updateTarget.setCompanyTel(route.getCompanyTel());
        updateTarget.setDistrictCd(route.getDistrictCd());
        updateTarget.setDownFirstTime(route.getDownFirstTime());
        updateTarget.setDownLastTime(route.getDownLastTime());
        updateTarget.setEndMobileNo(route.getEndMobileNo());
        updateTarget.setEndStationId(route.getEndStationId());
        updateTarget.setEndStationName(route.getEndStationName());
        updateTarget.setPeekAlloc(route.getPeekAlloc());
        updateTarget.setRegionName(route.getRegionName());
        updateTarget.setRouteId(route.getRouteId());
        updateTarget.setRouteName(route.getRouteName());
        updateTarget.setRouteTypeCd(route.getRouteTypeCd());
        updateTarget.setRouteTypeName(route.getRouteTypeName());
        updateTarget.setStartMobileNo(route.getStartMobileNo());
        updateTarget.setStartStationId(route.getStartStationId());
        updateTarget.setStartStationName(route.getStartStationName());
        updateTarget.setUpFirstTime(route.getUpFirstTime());
        updateTarget.setUpLastTime(route.getUpLastTime());
        updateTarget.setNPeekAlloc(route.getNPeekAlloc());
        updateTarget.setUpdatedAt(LocalDateTime.now());
        updateTarget.setDataGatherScheduler(route.getDataGatherScheduler());
        updateTarget.setWeekdayCount(route.getWeekdayCount());
        updateTarget.setWeekendCount(route.getWeekendCount());
        updateTarget.setWeekdayDoubleDeckerCount(route.getWeekdayDoubleDeckerCount());
        updateTarget.setWeekendDoubleDeckerCount(route.getWeekendDoubleDeckerCount());
        routeRepository.save(updateTarget);
    }

    @Override
    public void deleteRoute(Route route) throws Exception {
        Route updateTarget = getRoute(route.getId().toString());
        if("".equals(updateTarget.getRouteId()))
            throw new Exception("정보가 올바르지 않습니다.");
        updateTarget.setDeleteYn("Y");
        updateTarget.setDeletedAt(LocalDateTime.now());
        updateTarget.setUpdatedAt(LocalDateTime.now());
        routeRepository.save(updateTarget);
    }

    private Route getRoute(String id) {
        Route route = routeRepository.findById(UUID.fromString(id)).orElse(new Route());
        if (route.getDataGatherScheduler() == null) {
            route.setDataGatherScheduler(new DataGatherScheduler());
        }
        route.setTripPlanList(getTripPlans(route.getRouteId()));
        return route;
    }

    private List<TripPlan> getTripPlans(String routeId) {
        return tripPlanService.findByRouteId(routeId).stream()
                .filter((TripPlan tempTripPlan) -> ("N".equals(tempTripPlan.getDeleteYn())))
                .sorted((TripPlan a, TripPlan b) -> ((a.getTurnNumber() > b.getTurnNumber()) ? 1 : -1))
                .collect(Collectors.toList());
    }
}
