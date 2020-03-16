package com.dazzilove.bustrace.app.controller;

import com.dazzilove.bustrace.app.controller.dto.SpecialMessageParams;
import com.dazzilove.bustrace.app.domain.SpecialMessage;
import com.dazzilove.bustrace.app.service.SpecialMessageService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Controller
public class SpecialMessageController {

    @Autowired
    private SpecialMessageService specialMessageService;

    @RequestMapping("/specialMessage/messages")
    @ResponseBody
    public String getSpecialMessages(ServletRequest request) throws Exception {
        List<SpecialMessage> specialMessages = specialMessageService.getSpecialMessageList(convertSpecialMessageParams(request));

        JsonArray list = new JsonArray();
        for(SpecialMessage specialMessage: specialMessages) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", specialMessage.getRouteId());
            jsonObject.addProperty("divCd", specialMessage.getDivCd());
            jsonObject.addProperty("routeId", specialMessage.getRouteId());
            jsonObject.addProperty("message", specialMessage.getMessage());
            jsonObject.addProperty("createdAt", specialMessage.getFormatedCreatedAt());
            list.add(jsonObject);
        }

        return list.toString();
    }

    private SpecialMessageParams convertSpecialMessageParams(ServletRequest request) {
        String routeId = StringUtils.defaultIfBlank(request.getParameter("routedId"), "").trim();
        String createdAtClfCd = StringUtils.defaultIfBlank(request.getParameter("createdAtClfCd"), "").trim();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startCreatedAt = LocalDateTime.now();
        LocalDateTime endCreatedAt = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 23, 59, 59);
        if (SpecialMessageParams.CREATED_AT_CLF_CD_THIS_WEEK.equals(createdAtClfCd)) {
            LocalDate date = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            startCreatedAt = LocalDateTime.of(now.getYear(), now.getMonthValue(), date.getDayOfMonth(), 0, 0, 0);
            startCreatedAt = startCreatedAt.minusDays(7);
        } else if (SpecialMessageParams.CREATED_AT_CLF_CD_LAST_WEEK.equals(createdAtClfCd)) {
            startCreatedAt = LocalDateTime.now().minusDays(7);
            startCreatedAt = LocalDateTime.of(
                    startCreatedAt.getYear(),
                    startCreatedAt.getMonthValue(),
                    startCreatedAt.getDayOfMonth(),
                    0,
                    0,
                    0
            );
        } else if (SpecialMessageParams.CREATED_AT_CLF_CD_THIS_MONTH.equals(createdAtClfCd)) {
            startCreatedAt = LocalDateTime.of(now.getYear(), now.getMonthValue(), 1, 0, 0, 0);
        } else if (SpecialMessageParams.CREATED_AT_CLF_CD_LAST_MONTH.equals(createdAtClfCd)) {
            startCreatedAt = LocalDateTime.now().minusDays(31);
            startCreatedAt = LocalDateTime.of(
                    startCreatedAt.getYear(),
                    startCreatedAt.getMonthValue(),
                    startCreatedAt.getDayOfMonth(),
                    0,
                    0,
                    0
            );
        }

        SpecialMessageParams specialMessageParams = new SpecialMessageParams();
        specialMessageParams.setRouteId(routeId);
        specialMessageParams.setCreatedAtClfCd(createdAtClfCd);
        specialMessageParams.setStartCreatedAt(startCreatedAt);
        specialMessageParams.setEndCreatedAt(endCreatedAt);

        return specialMessageParams;
    }

}
