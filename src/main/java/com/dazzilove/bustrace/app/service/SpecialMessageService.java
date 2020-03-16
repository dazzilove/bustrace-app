package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.controller.dto.SpecialMessageParams;
import com.dazzilove.bustrace.app.domain.SpecialMessage;

import java.util.List;

public interface SpecialMessageService {
    List<SpecialMessage> getSpecialMessageList(String routeId);
    List<SpecialMessage> getSpecialMessageList(SpecialMessageParams specialMessageParams);
}
