package com.dazzilove.bustrace.app.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@ToString
public class Route {

    @Id
    private UUID id;
    private String companyId;
    private String companyName;
    private String companyTel;
    private String districtCd;
    private String downFirstTime;
    private String downLastTime;
    private String endMobileNo;
    private String endStationId;
    private String endStationName;
    private String peekAlloc;
    private String regionName;
    private String routeId;
    private String routeName;
    private String routeTypeCd;
    private String routeTypeName;
    private String startMobileNo;
    private String startStationId;
    private String startStationName;
    private String upFirstTime;
    private String upLastTime;
    private String nPeekAlloc;

    private int weekdayCount;
    private int weekendCount;
    private int weekdayDoubleDeckerCount;
    private int weekendDoubleDeckerCount;

    private DataGatherScheduler dataGatherScheduler;
    private List<TripPlan> tripPlanList; // 운행 계획 정보
    private List<TripPlan> tripPlanListTheDayBefore; // 전일 운행 정보

    private String deleteYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private int totalTripPlanCount;
    private int yesterdayTripRecordCount;
    private int todayTripRecordCount;
    private int todayDoubleDeckerTripRecordCount;

    public String getDeleteYn() {
        return (this.deleteYn == null) ? "N" : this.deleteYn;
    }

    public boolean isAddValidate() {
        if("".equals(this.routeId))
            return false;
        if("".equals(this.routeName))
            return false;
        return true;
    }

    public boolean isEditValidate() {
        if("".equals(this.id.toString()))
            return false;
        if("".equals(this.routeId))
            return false;
        if("".equals(this.routeName))
            return false;
        return true;
    }

    public boolean isDeleteValidate() {
        if("".equals(this.id.toString()))
            return false;
        return true;
    }

    public boolean isDeleted() {
        return "Y".equals(this.deleteYn);
    }
}
