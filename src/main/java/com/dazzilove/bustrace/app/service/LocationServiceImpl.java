package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.controller.dto.LocationParams;
import com.dazzilove.bustrace.app.domain.Location;
import com.dazzilove.bustrace.app.repository.LocationRepository;
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
public class LocationServiceImpl implements LocationService {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private LocationRepository locationRepository;

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

        TypedAggregation<Location> agg = Aggregation.newAggregation(Location.class, aggOperationlist);

        List<Location> locations = mongoOperations.aggregate(agg, Location.class, Location.class).getMappedResults();
        if (locations == null)
            locations = new ArrayList<>();

        return locations;
    }
}
