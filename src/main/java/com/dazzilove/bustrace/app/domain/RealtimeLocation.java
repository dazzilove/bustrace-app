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
public class RealtimeLocation {
    @Id
    private UUID id;
    private String routeId;
    private String stationId;
    private String stationSeq;
    private String endBus;
    private String lowPlate;
    private String plateNo;
    private String plateType;
    private String remainSeatCnt;
    private LocalDateTime createdAt;

    public String getShortPlateNo() {
        int length = plateNo.length();
        return plateNo.substring(length -4, length);
    }

    public String getPlateTypeIconUrl() {
        DetailCode detailCode = new DetailCode();
        detailCode.setMasterId(CodeEnum.PLATE_TYPE.name());
        detailCode.setId(this.plateType);
        return StringUtils.defaultIfEmpty(CodeUtil.getDetailCode(detailCode).getImg(), "");
    }

    public String getFormatedCreatedAt() {
        return String.format("%s/%s/%s %s:%s:%s"
                , DateUtil.formatTwoLength(String.valueOf(createdAt.getYear()))
                , DateUtil.formatTwoLength(String.valueOf(createdAt.getMonthValue()))
                , DateUtil.formatTwoLength(String.valueOf(createdAt.getDayOfMonth()))
                , DateUtil.formatTwoLength(String.valueOf(createdAt.getHour()))
                , DateUtil.formatTwoLength(String.valueOf(createdAt.getMinute()))
                , DateUtil.formatTwoLength(String.valueOf(createdAt.getSecond())));
    }
}
