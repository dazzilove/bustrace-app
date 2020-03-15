package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.controller.dto.LocationParams;
import com.dazzilove.bustrace.app.domain.BusLocation;
import com.dazzilove.bustrace.app.domain.Location;
import com.dazzilove.bustrace.app.repository.BusLocationRepository;
import com.dazzilove.bustrace.app.utils.DateUtil;
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

@Service
public class BusLocationServiceImpl implements BusLocationService {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private BusLocationRepository busLocationRepository;

    @Override
    public List<Location> getLocations(LocationParams locationParams) {
        String routeId = locationParams.getRouteId();
        String stationId = locationParams.getStationId();
        String stationSeq = locationParams.getStationSeq();
        String createdAt = locationParams.getCreatedAt();

        LocalDateTime startCreatedAt = DateUtil.getStartCreatedAt(createdAt);
        LocalDateTime endCreatedAt = DateUtil.getEndCreatedAt(createdAt);

        List<AggregationOperation> aggOperationlist = new ArrayList<AggregationOperation>();
        if (routeId.length() > 0)
            aggOperationlist.add(Aggregation.match(Criteria.where("routeId").is(routeId)));
        if (stationId.length() > 0)
            aggOperationlist.add(Aggregation.match(Criteria.where("stationId").is(stationId)));
        if (stationSeq.length() > 0)
            aggOperationlist.add(Aggregation.match(Criteria.where("stationSeq").is(stationSeq)));
        if (startCreatedAt != null)
            aggOperationlist.add(Aggregation.match(Criteria.where("createdAt").gte(startCreatedAt)));
        if (endCreatedAt != null)
            aggOperationlist.add(Aggregation.match(Criteria.where("createdAt").lte(endCreatedAt)));
        aggOperationlist.add(Aggregation.sort(Sort.Direction.ASC, "createdAt"));

        TypedAggregation<BusLocation> agg = Aggregation.newAggregation(BusLocation.class, aggOperationlist);

        List<BusLocation> busLocations = mongoOperations.aggregate(agg, BusLocation.class, BusLocation.class).getMappedResults();
        if (busLocations == null)
            busLocations = new ArrayList<>();

        List<Location> locations = new ArrayList<>();
        for(BusLocation busLocation: busLocations) {
            Location location = new Location();
            location.setRouteId(busLocation.getRouteId());
            location.setStationId(busLocation.getStationId());
            location.setStationSeq(busLocation.getStationSeq());
            location.setCreatedAt(busLocation.getCreatedAt());
            location.setPlateNo(busLocation.getPlateNo());
            location.setPlateType(busLocation.getPlateType());
            location.setRemainSeatCnt(busLocation.getRemainSeatCnt());
            locations.add(location);
        }

        return locations;
    }
}
