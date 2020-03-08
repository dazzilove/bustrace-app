package com.dazzilove.bustrace.app.domain;


import com.dazzilove.bustrace.app.utils.DateUtil;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
public class TripPlan {

    @Id
    private UUID id;
    private String routeName;
    private String routeId;
    private String plateNo;
    private Integer turnNumber;
    private String plateType;
    private String plateTypeName;
    private String weekendOperationYn;
    private String spareYn;
    private String schoolBreakReductionYn;
    private LocalDateTime schoolBreakReductionStartedAt;
    private String tripStopYn;
    private LocalDateTime tripStopStartedAt;
    private String yesterdayTripRecordYn;
    private String todayTripRecordYn;
    private String deleteYn;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getTripPlanId() {
        return this.id.toString();
    }

    public Integer getTurnNumber() {
        return (this.turnNumber == null) ? 0 : this.turnNumber;
    }

    public String getDeleteYn() {
        return (this.deleteYn == null) ? "N" : this.deleteYn;
    }

    public String getShortPlateNo() {
        String shortPlateNo = this.plateNo;
        shortPlateNo = shortPlateNo.substring(shortPlateNo.length() - 4, shortPlateNo.length());
        return shortPlateNo;
    }

    public String getFormatedSchoolBreakReductionStartedAt() {
        if (this.schoolBreakReductionStartedAt == null) {
            return "";
        }
        return String.format("%s/%s/%s"
                , DateUtil.formatTwoLength(String.valueOf(schoolBreakReductionStartedAt.getYear()))
                , DateUtil.formatTwoLength(String.valueOf(schoolBreakReductionStartedAt.getMonthValue()))
                , DateUtil.formatTwoLength(String.valueOf(schoolBreakReductionStartedAt.getDayOfMonth())));
    }

    public String getFormatedTripStopStartedAt() {
        if (this.tripStopStartedAt == null) {
            return "";
        }
        return String.format("%s/%s/%s"
                , DateUtil.formatTwoLength(String.valueOf(tripStopStartedAt.getYear()))
                , DateUtil.formatTwoLength(String.valueOf(tripStopStartedAt.getMonthValue()))
                , DateUtil.formatTwoLength(String.valueOf(tripStopStartedAt.getDayOfMonth())));
    }

    public boolean isAddValidate() {
        if("".equals(this.routeId))
            return false;
        if("".equals(this.plateNo))
            return false;
        if("".equals(this.plateType))
            return false;
        if("".equals(this.weekendOperationYn))
            return false;
        if("".equals(this.spareYn))
            return false;
        if("".equals(this.schoolBreakReductionYn))
            return false;

        return true;
    }

    public boolean isEditValidate() {
        if("".equals(this.id))
            return false;
        if(!isAddValidate())
            return false;

        return true;
    }

    public boolean isDeleteValidate() {
        if("".equals(this.id))
            return false;

        return true;
    }

    public boolean isDeleted() {
        return "Y".equals(this.deleteYn);
    }

}
