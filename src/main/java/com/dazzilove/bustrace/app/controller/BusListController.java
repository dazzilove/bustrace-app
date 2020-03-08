package com.dazzilove.bustrace.app.controller;

import com.dazzilove.bustrace.app.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BusListController {

    @Autowired
    RouteService routeService;

    @RequestMapping("/busList")
    public String getBusList(Model model) throws Exception {
        model.addAttribute("currentMenu", "1");
        model.addAttribute("routes", routeService.getRoutes());
        return "busList/list";
    }
}
