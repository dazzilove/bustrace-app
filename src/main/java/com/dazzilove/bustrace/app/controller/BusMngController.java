package com.dazzilove.bustrace.app.controller;

import com.dazzilove.bustrace.app.controller.dto.RouteParams;
import com.dazzilove.bustrace.app.controller.dto.TripPlanParams;
import com.dazzilove.bustrace.app.domain.DataGatherScheduler;
import com.dazzilove.bustrace.app.domain.Route;
import com.dazzilove.bustrace.app.domain.TripPlan;
import com.dazzilove.bustrace.app.service.RouteService;
import com.dazzilove.bustrace.app.service.TripPlanService;
import com.dazzilove.bustrace.app.utils.CodeUtil;
import com.dazzilove.bustrace.app.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.UUID;

@Controller
public class BusMngController {

    @Autowired
    RouteService routeService;

    @Autowired
    TripPlanService tripPlanService;

    @RequestMapping("/busMng/routeList")
    public String getBusMngList(Model model) throws Exception {
        model.addAttribute("currentMenu", "3");
        model.addAttribute("routes", routeService.getRoutes());
        return "busMng/routeList";
    }

    @RequestMapping("/busMng/routeInfo")
    public String viewRouteInfo(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "3");

        RouteParams routeParams = convertRouteParamsByRequest(request);

        Route route = routeService.getRouteById(routeParams.getId());
        model.addAttribute("routeInfo", route);

        return "busMng/routeInfo";
    }

    @RequestMapping("/busMng/viewAddRoute")
    public String viewAddRoute(Model model) throws Exception {
        model.addAttribute("currentMenu", "3");
        model.addAttribute("pageMode", "ADD");

        Route route = new Route();
        route.setDataGatherScheduler(new DataGatherScheduler());
        model.addAttribute("route", route);
        return "busMng/routeForm";
    }

    @RequestMapping("/busMng/addRoute")
    @ResponseBody
    public String addRoute(ServletRequest request) throws Exception {

        Route route = convertRouteByRequest(request);

        if(!route.isAddValidate()) {
            return "값이 올바르지 않습니다. 필요한 값을 모두 입력했는지 확인하세요.";
        }

        try {
            routeService.addRoute(route);
        } catch(Exception e) {
            e.printStackTrace();
            return "등록 중 에러가 발생 했습니다.";
        }

        return "등록 완료 되었습니다.";
    }

    @RequestMapping("/busMng/viewEditRoute")
    public String viewEditRoute(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "3");
        model.addAttribute("pageMode", "EDIT");

        Route routeParam = convertRouteByRequest(request);

        Route route = routeService.getRouteById(routeParam.getId().toString());
        model.addAttribute("route", route);

        return "busMng/routeForm";
    }

    @RequestMapping("/busMng/editRoute")
    @ResponseBody
    public String editRoute(ServletRequest request) throws Exception {

        Route route = convertRouteByRequest(request);

        if(!route.isEditValidate()) {
            return "값이 올바르지 않습니다. 필요한 값을 모두 입력했는지 확인하세요.";
        }

        try {
            routeService.editRoute(route);
        } catch(Exception e) {
            e.printStackTrace();
            return "수정 중 에러가 발생 했습니다.";
        }

        return "수정 완료 되었습니다.";
    }

    @RequestMapping("/busMng/delRoute")
    @ResponseBody
    public String delRoute(ServletRequest request) throws Exception {

        Route route = convertRouteByRequest(request);

        if(!route.isDeleteValidate()) {
            return "값이 올바르지 않습니다.";
        }

        try {
            routeService.deleteRoute(route);
        } catch(Exception e) {
            e.printStackTrace();
            return "삭제 중 에러가 발생 했습니다.";
        }

        return "삭제 완료 되었습니다.";
    }

    @RequestMapping("/busMng/viewAddTripPlan")
    public String viewAddTripPlan(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "3");
        model.addAttribute("pageMode", "ADD");

        TripPlanParams tripPlanParams = convertTripPlanParamsByRequest(request);

        Route route = routeService.getRouteById(tripPlanParams.getId());
        model.addAttribute("route", route);

        TripPlan tripPlan = new TripPlan();
        tripPlan.setRouteId(route.getRouteId());

        model.addAttribute("tripPlan", tripPlan);

        model.addAttribute("codePlateType", CodeUtil.getCode("PLATE_TYPE").getDetailCodes());

        model.addAttribute("codeWeekendOperationYn", CodeUtil.getCode("WEEKEND_OPERATION_YN").getDetailCodes());

        model.addAttribute("codeSpareTripYn", CodeUtil.getCode("SPARE_TRIP_YN").getDetailCodes());

        model.addAttribute("codeSchoolBreakReductionYn", CodeUtil.getCode("SCHOOL_BREAK_REDUCTION_YN").getDetailCodes());

        model.addAttribute("codeTripStopYn", CodeUtil.getCode("TRIP_STOP_YN").getDetailCodes());

        return "busMng/tripPlanForm";
    }

    @RequestMapping("/busMng/viewEditTripPlan")
    public String viewEditTripPlan(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "3");
        model.addAttribute("pageMode", "EDIT");

        TripPlanParams tripPlanParams = convertTripPlanParamsByRequest(request);

        Route route = routeService.getRouteById(tripPlanParams.getId());
        model.addAttribute("route", route);

        TripPlan tripPlan = tripPlanService.findById(tripPlanParams.getTripPlanId());
        model.addAttribute("tripPlan", tripPlan);

        model.addAttribute("codePlateType", CodeUtil.getCode("PLATE_TYPE").getDetailCodes());

        model.addAttribute("codeWeekendOperationYn", CodeUtil.getCode("WEEKEND_OPERATION_YN").getDetailCodes());

        model.addAttribute("codeSpareTripYn", CodeUtil.getCode("SPARE_TRIP_YN").getDetailCodes());

        model.addAttribute("codeSchoolBreakReductionYn", CodeUtil.getCode("SCHOOL_BREAK_REDUCTION_YN").getDetailCodes());

        model.addAttribute("codeTripStopYn", CodeUtil.getCode("TRIP_STOP_YN").getDetailCodes());

        return "busMng/tripPlanForm";
    }

    @RequestMapping("/busMng/addTripPlan")
    @ResponseBody
    public String addTripPlan(ServletRequest request) throws Exception {
        TripPlan tripPlan = convertTripPlanByRequest(request);

        if(!tripPlan.isAddValidate()) {
            return "값이 올바르지 않습니다. 필요한 값을 모두 입력했는지 확인하세요.";
        }

        try {
            tripPlanService.addTripPlan(tripPlan);
        } catch(Exception e) {
            e.printStackTrace();
            return "등록 중 에러가 발생 했습니다.";
        }

        return "등록 완료 되었습니다.";
    }

    @RequestMapping("/busMng/editTripPlan")
    @ResponseBody
    public String editTripPlan(ServletRequest request) throws Exception {

        TripPlan tripPlan = convertTripPlanByRequest(request);

        if(!tripPlan.isEditValidate()) {
            return "값이 올바르지 않습니다. 필요한 값을 모두 입력했는지 확인하세요.";
        }

        try {
            tripPlanService.editTripPlan(tripPlan);
        } catch(Exception e) {
            e.printStackTrace();
            return "수정 중 에러가 발생 했습니다.";
        }

        return "수정 완료 되었습니다.";
    }

    @RequestMapping("/busMng/delTripPlan")
    @ResponseBody
    public String delTripPlan(ServletRequest request) throws Exception {

        TripPlan tripPlan = convertTripPlanByRequest(request);

        if(!tripPlan.isDeleteValidate()) {
            return "값이 올바르지 않습니다.";
        }

        try {
            tripPlanService.deleteTripPlan(tripPlan);
        } catch(Exception e) {
            e.printStackTrace();
            return "삭제 중 에러가 발생 했습니다.";
        }

        return "삭제 완료 되었습니다.";
    }


    private RouteParams convertRouteParamsByRequest(ServletRequest request) {
        String id = StringUtils.defaultString(request.getParameter("id"), "");
        String routeId = StringUtils.defaultString(request.getParameter("routeId"), "");
        String stationId = StringUtils.defaultString(request.getParameter("stationId"), "");
        String createdAt = StringUtils.defaultString(request.getParameter("createdAt"), "");

        RouteParams routeParams = new RouteParams();
        routeParams.setId(id);
        routeParams.setRouteId(routeId);
        routeParams.setStationId(stationId);
        routeParams.setCreatedAt(createdAt);

        return routeParams;
    }

    private Route convertRouteByRequest(ServletRequest request) throws Exception {
        String id = StringUtils.defaultString(request.getParameter("id"), "");
        String routeId = StringUtils.defaultString(request.getParameter("routeId"), "");
        String routeName = StringUtils.defaultString(request.getParameter("routeName"), "");
        String weekdayCount = StringUtils.defaultString(request.getParameter("weekdayCount"), "0");
        String weekendCount = StringUtils.defaultString(request.getParameter("weekendCount"), "0");
        String weekdayDoubleDeckerCount = StringUtils.defaultString(request.getParameter("weekdayDoubleDeckerCount"), "0");
        String weekendDoubleDeckerCount = StringUtils.defaultString(request.getParameter("weekendDoubleDeckerCount"), "0");
        String companyName = StringUtils.defaultString(request.getParameter("companyName"), "");
        String peekAlloc = StringUtils.defaultString(request.getParameter("peekAlloc"), "");
        String nPeekAlloc = StringUtils.defaultString(request.getParameter("nPeekAlloc"), "");
        String startStationId = StringUtils.defaultString(request.getParameter("startStationId"), "");
        String startStationName = StringUtils.defaultString(request.getParameter("startStationName"), "");
        String upFirstTime = StringUtils.defaultString(request.getParameter("upFirstTime"), "");
        String upLastTime = StringUtils.defaultString(request.getParameter("upLastTime"), "");
        String endStationId = StringUtils.defaultString(request.getParameter("endStationId"), "");
        String endStationName = StringUtils.defaultString(request.getParameter("endStationName"), "");
        String downFirstTime = StringUtils.defaultString(request.getParameter("downFirstTime"), "");
        String downLastTime = StringUtils.defaultString(request.getParameter("downLastTime"), "");
        String dataGatherBatchEnabled = StringUtils.defaultString(request.getParameter("dataGatherBatchEnabled"), "N");
        String dataGatherBatchSchedule = StringUtils.defaultString(request.getParameter("dataGatherBatchSchedule"), "");

        Route route = new Route();
        if(!"".equals(id)) {
            route = routeService.getRouteById(id);
        }
        route.setRouteId(routeId);
        route.setRouteName(routeName);
        route.setCompanyName(companyName);
        route.setPeekAlloc(peekAlloc);
        route.setNPeekAlloc(nPeekAlloc);
        route.setStartStationId(startStationId);
        route.setStartStationName(startStationName);
        route.setUpFirstTime(upFirstTime);
        route.setUpLastTime(upLastTime);
        route.setEndStationId(endStationId);
        route.setEndStationName(endStationName);
        route.setDownFirstTime(downFirstTime);
        route.setDownLastTime(downLastTime);
        route.setWeekdayCount(Integer.valueOf(weekdayCount));
        route.setWeekendCount(Integer.valueOf(weekendCount));
        route.setWeekdayDoubleDeckerCount(Integer.valueOf(weekdayDoubleDeckerCount));
        route.setWeekendDoubleDeckerCount(Integer.valueOf(weekendDoubleDeckerCount));

        DataGatherScheduler dataGatherScheduler = route.getDataGatherScheduler();
        if (dataGatherScheduler == null) {
            dataGatherScheduler = new DataGatherScheduler();
        }
        dataGatherScheduler.setEnabled(("Y".equals(dataGatherBatchEnabled)) ? true : false);
        if(dataGatherScheduler.isEnabled()) {
            dataGatherScheduler.setSchedule(dataGatherBatchSchedule);
        } else {
            dataGatherScheduler.setSchedule("");
        }
        route.setDataGatherScheduler(dataGatherScheduler);

        return route;
    }

    private TripPlanParams convertTripPlanParamsByRequest(ServletRequest request) {
        String id = StringUtils.defaultString(request.getParameter("id"), "");
        String tripPlanId = StringUtils.defaultString(request.getParameter("tripPlanId"), "");

        TripPlanParams tripPlanParams = new TripPlanParams();
        tripPlanParams.setId(id);
        tripPlanParams.setTripPlanId(tripPlanId);
        return tripPlanParams;
    }

    private TripPlan convertTripPlanByRequest(ServletRequest request) {
        String tripPlanId = StringUtils.defaultString(request.getParameter("tripPlanId"), "").trim();
        String routeId = StringUtils.defaultString(request.getParameter("routeId"), "").trim();
        String plateNo = StringUtils.defaultString(request.getParameter("plateNo"), "").trim();
        String turnNumber = StringUtils.defaultString(request.getParameter("turnNumber"), "0").trim();
        String plateType = StringUtils.defaultString(request.getParameter("plateType"), "").trim();
        String weekendOperationYn = StringUtils.defaultString(request.getParameter("weekendOperationYn"), "N").trim();
        String spareYn = StringUtils.defaultString(request.getParameter("spareYn"), "N").trim();
        String schoolBreakReductionYn = StringUtils.defaultString(request.getParameter("schoolBreakReductionYn"), "N").trim();
        String schoolBreakReductionStartedAt = StringUtils.defaultString(request.getParameter("schoolBreakReductionStartedAt"), "").trim();
        String schoolBreakReductionEndedAt = StringUtils.defaultString(request.getParameter("schoolBreakReductionEndedAt"), "").trim();
        String tripStopYn = StringUtils.defaultString(request.getParameter("tripStopYn"), "N").trim();
        String tripStopStartedAt = StringUtils.defaultString(request.getParameter("tripStopStartedAt"), "").trim();

        TripPlan tripPlan = new TripPlan();
        if (!"".equals(tripPlanId)) {
            tripPlan.setId(UUID.fromString(tripPlanId));
        }
        tripPlan.setRouteId(routeId);
        tripPlan.setPlateNo(plateNo);
        tripPlan.setTurnNumber(Integer.parseInt(turnNumber));
        tripPlan.setPlateType(plateType);
        tripPlan.setWeekendOperationYn(weekendOperationYn);
        tripPlan.setSpareYn(spareYn);
        tripPlan.setSchoolBreakReductionYn(schoolBreakReductionYn);
        tripPlan.setTripStopYn(tripStopYn);

        if ("Y".equals(schoolBreakReductionYn)) {
            tripPlan.setSchoolBreakReductionStartedAt(DateUtil.getStartCreatedAt(schoolBreakReductionStartedAt));
            tripPlan.setSchoolBreakReductionEndedAt(DateUtil.getEndCreatedAt(schoolBreakReductionEndedAt));
        }
        if ("Y".equals(tripStopYn)) {
            tripPlan.setTripStopStartedAt(DateUtil.getStartCreatedAt(tripStopStartedAt));
        }

        return tripPlan;
    }

}
