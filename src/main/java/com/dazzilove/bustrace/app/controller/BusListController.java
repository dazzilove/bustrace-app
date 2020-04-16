package com.dazzilove.bustrace.app.controller;

import com.dazzilove.bustrace.app.controller.dto.LocationParams;
import com.dazzilove.bustrace.app.controller.dto.RouteParams;
import com.dazzilove.bustrace.app.controller.dto.StationParams;
import com.dazzilove.bustrace.app.domain.Location;
import com.dazzilove.bustrace.app.domain.Route;
import com.dazzilove.bustrace.app.domain.Station;
import com.dazzilove.bustrace.app.service.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class BusListController {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    @Autowired
    RouteService routeService;

    @Autowired
    LocationService locationService;

    @Autowired
    RealtimeLocationService realtimeLocationService;

    @Autowired
    BusLocationService busLocationService;

    @Autowired
    private SpecialMessageService specialMessageService;

    @RequestMapping("/busList")
    public String getBusList(Model model) throws Exception {
        model.addAttribute("currentMenu", "1");
        model.addAttribute("routes", routeService.getRoutes());
        return "busList/list";
    }

    @RequestMapping("/route/info")
    public String getRouteInfo(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "1");

        RouteParams routeParams = convertRouteParams(request);

        String id = routeParams.getId();
        String createdAt = routeParams.getCreatedAt();

        if ("".equals(createdAt)) {
            LocalDateTime localDateTime = LocalDateTime.now();
            createdAt = DateTimeFormatter.ofPattern(DATE_PATTERN).format(localDateTime);
        }
        model.addAttribute("createdAt", createdAt);

        Route route = routeService.getRouteById(id);
        model.addAttribute("route", route);

        return "busList/routeInfo";
    }

    @RequestMapping("/route/info/default")
    public String getRouteInfoDefault(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "1");

        RouteParams routeParams = convertRouteParams(request);

        String id = routeParams.getId();
        String createdAt = routeParams.getCreatedAt();

        if ("".equals(createdAt)) {
            LocalDateTime localDateTime = LocalDateTime.now();
            createdAt = DateTimeFormatter.ofPattern(DATE_PATTERN).format(localDateTime);
        }
        model.addAttribute("createdAt", createdAt);

        Route route = routeService.getRouteById(id);
        model.addAttribute("route", route);

        return "busList/routeInfoDefault";
    }

    @RequestMapping("/route/info/station")
    public String getRouteInfoStation(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "1");

        RouteParams routeParams = convertRouteParams(request);

        String id = routeParams.getId();
        String createdAt = routeParams.getCreatedAt();

        if ("".equals(createdAt)) {
            LocalDateTime localDateTime = LocalDateTime.now();
            createdAt = DateTimeFormatter.ofPattern(DATE_PATTERN).format(localDateTime);
        }
        model.addAttribute("createdAt", createdAt);

        Route route = routeService.getRouteById(id);
        model.addAttribute("route", route);

        return "busList/routeInfoStation";
    }

    @RequestMapping("/route/info/specialMessage")
    public String getRouteInfoSpecialMessage(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "1");

        RouteParams routeParams = convertRouteParams(request);

        String id = routeParams.getId();
        String createdAt = routeParams.getCreatedAt();

        if ("".equals(createdAt)) {
            LocalDateTime localDateTime = LocalDateTime.now();
            createdAt = DateTimeFormatter.ofPattern(DATE_PATTERN).format(localDateTime);
        }
        model.addAttribute("createdAt", createdAt);

        Route route = routeService.getRouteById(id);
        model.addAttribute("route", route);

        return "busList/routeInfoSpecialMessage";
    }

    @RequestMapping("/route/info/rushHour")
    public String getRouteInfoRushHour(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "1");

        RouteParams routeParams = convertRouteParams(request);

        String id = routeParams.getId();
        String createdAt = routeParams.getCreatedAt();

        if ("".equals(createdAt)) {
            LocalDateTime localDateTime = LocalDateTime.now();
            createdAt = DateTimeFormatter.ofPattern(DATE_PATTERN).format(localDateTime);
        }
        model.addAttribute("createdAt", createdAt);

        Route route = routeService.getRouteById(id);
        model.addAttribute("route", route);

        return "busList/routeInfoRushHour";
    }

    @RequestMapping("/route/stations")
    @ResponseBody
    public String getStations(ServletRequest request) throws Exception {
        StationParams stationParams = convertStationParams(request);
        String routeId = stationParams.getRouteId();

        JsonArray list = new JsonArray();

        List<Station> stations = routeService.getStationsByRouteId(routeId);
        for (Station station: stations) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("routeId", station.getRouteId());
            jsonObject.addProperty("stationId", station.getStationId());
            jsonObject.addProperty("stationSeq", station.getStationSeq());
            jsonObject.addProperty("stationName", station.getStationName());
            list.add(jsonObject);
        }

        return list.toString();
    }

    @RequestMapping("/route/locations")
    @ResponseBody
    public String getLocations(ServletRequest request) throws Exception {
        JsonArray list = new JsonArray();

        LocationParams locationParams = convertLocationParams(request);

        List<Location> locations;
        if (isToday(locationParams.getCreatedAt())) {
            locations = realtimeLocationService.getLocations(locationParams);
        } else {
            locations = locationService.getLocations(locationParams);
        }

        for(Location location: locations) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("routeId", location.getRouteId());
            jsonObject.addProperty("stationId", location.getStationId());
            jsonObject.addProperty("stationSeq", location.getStationSeq());
            jsonObject.addProperty("plateNo", location.getPlateNo());
            jsonObject.addProperty("plateType", location.getPlateType());
            jsonObject.addProperty("plateTypeIconUrl", location.getPlateTypeIconUrl());
            jsonObject.addProperty("remainSeatCnt", location.getRemainSeatCnt());
            jsonObject.addProperty("createdAt", location.getFormatedCreatedAt());
            list.add(jsonObject);
        }

        return list.toString();
    }

    private boolean isToday(String createdAt) {
        String year = createdAt.substring(0, 4);
        String month = createdAt.substring(4, 6);
        String day = createdAt.substring(6, 8);

        month = ("0".equals(month.substring(0, 1))) ? month.substring(1, 2) : month;
        day = ("0".equals(day.substring(0, 1))) ? day.substring(1, 2) : day;

        LocalDateTime now = LocalDateTime.now();

        if (now.getYear() != Integer.parseInt(year)) {
            return false;
        }
        if (now.getMonthValue() != Integer.parseInt(month)) {
            int a = now.getMonthValue();
            return false;
        }
        if (now.getDayOfMonth() != Integer.parseInt(day)) {
            int a = now.getDayOfMonth();
            return false;
        }

        return true;
    }

    private RouteParams convertRouteParams(ServletRequest request) {
        String id = StringUtils.defaultIfBlank(request.getParameter("id"), "").trim();
        String routeId = StringUtils.defaultIfBlank(request.getParameter("routeId"), "").trim();
        String stationId = StringUtils.defaultIfBlank(request.getParameter("stationId"), "").trim();
        String createdAt = StringUtils.defaultIfBlank(request.getParameter("createdAt"), "").trim().replace("-", "");

        RouteParams routeParams = new RouteParams();
        routeParams.setId(id);
        routeParams.setRouteId(routeId);
        routeParams.setStationId(stationId);
        routeParams.setCreatedAt(createdAt);

        return routeParams;
    }

    private StationParams convertStationParams(ServletRequest request) {
        String routeId = StringUtils.defaultIfBlank(request.getParameter("routeId"), "").trim();
        String stationId = StringUtils.defaultIfBlank(request.getParameter("stationId"), "").trim();
        String createdAt = StringUtils.defaultIfBlank(request.getParameter("createdAt"), "").trim().replace("-", "");

        StationParams stationParams = new StationParams();
        stationParams.setRouteId(routeId);
        stationParams.setStationId(stationId);
        stationParams.setCreatedAt(createdAt);

        return stationParams;
    }

    private LocationParams convertLocationParams(ServletRequest request) {
        String routeId = StringUtils.defaultIfBlank(request.getParameter("routeId"), "").trim();
        String stationId = StringUtils.defaultIfBlank(request.getParameter("stationId"), "").trim();
        String stationSeq = StringUtils.defaultIfBlank(request.getParameter("stationSeq"), "").trim();
        String createdAt = StringUtils.defaultIfBlank(request.getParameter("createdAt"), "").trim().replace("-", "");

        LocationParams locationParams = new LocationParams();
        locationParams.setRouteId(routeId);
        locationParams.setStationId(stationId);
        locationParams.setStationSeq(stationSeq);
        locationParams.setCreatedAt(createdAt);

        return locationParams;
    }
}
