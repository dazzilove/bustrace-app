package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.SpecialMessage;
import com.dazzilove.bustrace.app.repository.SpecialMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialMessageServiceImpl implements SpecialMessageService {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private SpecialMessageRepository specialMessageRepository;

    @Override
    public List<SpecialMessage> getSpecialMessageList(String routeId) {

        List<AggregationOperation> aggOperationlist = new ArrayList<AggregationOperation>();
        if (routeId.length() > 0)
            aggOperationlist.add(Aggregation.match(Criteria.where("routeId").is(routeId)));
        aggOperationlist.add(Aggregation.sort(Sort.Direction.DESC, "createdAt"));

        TypedAggregation<SpecialMessage> agg = Aggregation.newAggregation(SpecialMessage.class, aggOperationlist);

        List<SpecialMessage> specialMessages = mongoOperations.aggregate(agg, SpecialMessage.class, SpecialMessage.class).getMappedResults();
        if (specialMessages == null)
            specialMessages = new ArrayList<>();

        return specialMessages;
    }
}
