package com.dazzilove.bustrace.app.controller;

import com.dazzilove.bustrace.app.domain.Route;
import com.dazzilove.bustrace.app.service.RouteService;
import com.dazzilove.bustrace.app.service.SpecialMessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class BusListController {

    @Autowired
    RouteService routeService;

    @Autowired
    private SpecialMessageService specialMessageService;

    @RequestMapping("/busList")
    public String getBusList(Model model) throws Exception {
        model.addAttribute("currentMenu", "1");
        model.addAttribute("routes", routeService.getRoutes());
        return "busList/list";
    }

    @RequestMapping("/routeInfo")
    public String getRouteInfo(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "1");

        String id = request.getParameter("id");
        String createdAt = StringUtils.defaultString(request.getParameter("createdAt"), "").trim().replace("-", "");

        LocalDateTime startCreatedAtLdt = LocalDateTime.now();
        LocalDateTime endCreatedAtLdt = LocalDateTime.now();
        if ("".equals(createdAt)) {
            LocalDate localDate = LocalDate.now();
            startCreatedAtLdt = LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), 0, 0, 0);
            endCreatedAtLdt = LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), 23, 59, 59);
        } else {
            String year = createdAt.substring(0, 4);
            String month = createdAt.substring(4, 6);
            String day = createdAt.substring(6, 8);
            month = (month.indexOf("0") == 0) ? month.substring(1,2) : month;
            day = (day.indexOf("0") == 0) ? day.substring(1,2) : day;
            startCreatedAtLdt = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0, 0);
            endCreatedAtLdt = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 23, 59, 59);
        }

        Route route = routeService.getOnlyRouteInfo(id);
        model.addAttribute("route", route);

        model.addAttribute("specialMessages", getSpecialMessageList(route.getRouteId()));

        return "busList/routeInfo";
    }

    private Object getSpecialMessageList(String routeId) {
        return specialMessageService.getSpecialMessageList(routeId);
    }
}
