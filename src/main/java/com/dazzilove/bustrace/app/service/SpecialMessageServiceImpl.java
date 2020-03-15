package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.SpecialMessage;
import com.dazzilove.bustrace.app.repository.SpecialMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialMessageServiceImpl implements SpecialMessageService {

    @Autowired
    private SpecialMessageRepository specialMessageRepository;

    @Override
    public List<SpecialMessage> getSpecialMessageList(String routeId) {
        List<SpecialMessage> specialMessageList = specialMessageRepository.findByRouteId(routeId);
        if (specialMessageList == null) {
            specialMessageList = new ArrayList<>();
        }
        return specialMessageList;
    }
}
