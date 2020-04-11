package com.dazzilove.bustrace.app.domain;


import com.dazzilove.bustrace.app.utils.CodeUtil;
import com.dazzilove.bustrace.app.utils.DateUtil;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
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
    private LocalDateTime schoolBreakReductionEndedAt;
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

    public String getShortFormatedSchoolBreakReductionStartedAt() {
        return DateUtil.getShortFormatedDate(this.schoolBreakReductionStartedAt);
    }

    public String getShortFormatedSchoolBreakReductionEndedAt() {
        return DateUtil.getShortFormatedDate(this.schoolBreakReductionEndedAt);
    }

    public String getShortFormatedTripStopStartedAt() {
        return DateUtil.getShortFormatedDate(this.tripStopStartedAt);
    }

    public String getPlateTypeIconUrl() {
        return StringUtils.defaultIfEmpty(CodeUtil.getDetailCodeByDetailCodeId("PLATE_TYPE", this.plateType).getImg(), "");
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
        if("".equals(this.tripStopYn))
            return false;

        if ("Y".equals(schoolBreakReductionYn)) {
            if (schoolBreakReductionStartedAt == null || schoolBreakReductionEndedAt == null) {
                return false;
            }
        }
        if ("Y".equals(tripStopYn) && tripStopStartedAt == null) {
            return false;
        }

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
