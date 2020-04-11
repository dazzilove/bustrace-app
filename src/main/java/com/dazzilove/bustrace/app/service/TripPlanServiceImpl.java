package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.TripPlan;
import com.dazzilove.bustrace.app.domain.TripPlanHistory;
import com.dazzilove.bustrace.app.repository.TripPlanHistoryRepository;
import com.dazzilove.bustrace.app.repository.TripPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TripPlanServiceImpl implements TripPlanService {

    @Autowired
    private TripPlanRepository tripPlanRepository;

    @Autowired
    TripPlanHistoryRepository tripPlanHistoryRepository;

    @Override
    public List<TripPlan> findByRouteId(String routeId) {
        return tripPlanRepository.findByRouteId(routeId);
    }

    @Override
    public TripPlan findById(String id) {
        return tripPlanRepository.findById(UUID.fromString(id)).orElse(new TripPlan());
    }

    @Override
    public void addTripPlan(TripPlan tripPlan) {
        tripPlan.setId(UUID.randomUUID());
        tripPlan.setCreatedAt(LocalDateTime.now());
        tripPlanRepository.insert(tripPlan);
        addTripPlanHistory(tripPlan);
    }

    @Override
    public void editTripPlan(TripPlan tripPlan) {
        TripPlan updateTarget = findById(tripPlan.getTripPlanId());
        updateTarget.setRouteId(tripPlan.getRouteId());
        updateTarget.setPlateNo(tripPlan.getPlateNo());
        updateTarget.setTurnNumber(tripPlan.getTurnNumber());
        updateTarget.setPlateType(tripPlan.getPlateType());
        updateTarget.setWeekendOperationYn(tripPlan.getWeekendOperationYn());
        updateTarget.setSpareYn(tripPlan.getSpareYn());
        updateTarget.setSchoolBreakReductionYn(tripPlan.getSchoolBreakReductionYn());
        updateTarget.setSchoolBreakReductionStartedAt(tripPlan.getSchoolBreakReductionStartedAt());
        updateTarget.setSchoolBreakReductionEndedAt(tripPlan.getSchoolBreakReductionEndedAt());
        updateTarget.setTripStopYn(tripPlan.getTripStopYn());
        updateTarget.setTripStopStartedAt(tripPlan.getTripStopStartedAt());
        updateTarget.setUpdatedAt(LocalDateTime.now());
        tripPlanRepository.save(updateTarget);
        addTripPlanHistory(tripPlan);
    }

    @Override
    public void deleteTripPlan(TripPlan tripPlan) throws Exception {
        TripPlan updateTarget = findById(tripPlan.getTripPlanId());
        if("".equals(updateTarget.getTripPlanId()))
            throw new Exception("정보가 올바르지 않습니다.");
        updateTarget.setDeleteYn("Y");
        updateTarget.setDeletedAt(LocalDateTime.now());
        updateTarget.setUpdatedAt(LocalDateTime.now());
        tripPlanRepository.save(updateTarget);
        addTripPlanHistory(tripPlan);
    }

    private void addTripPlanHistory(TripPlan tripPlan) {
        TripPlanHistory tripPlanHistory = new TripPlanHistory();
        tripPlanHistory.setHistoryId(UUID.randomUUID());
        tripPlanHistory.setHistoryCreatedAt(LocalDateTime.now());
        tripPlanHistory.setTripPlan(tripPlan);
        tripPlanHistoryRepository.save(tripPlanHistory);
    }
}
