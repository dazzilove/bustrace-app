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
public class Location {
    @Id
    private UUID id;

    /** 노선ID */
    private String routeId;
    /** 정류소ID */
    private String stationId;
    /** 정류소 순서 */
    private String stationSeq;
    /** 막차여부 (0:해당사항없음, 1:막차) */
    private String endBus;
    /** 저상버스여부 (0:일반버스, 1:저상버스) */
    private String lowPlate;
    /** 차량번호 */
    private String plateNo;
    /** 차종 (0:정보없음, 1:소형승합차, 2:중형승합차, 3:대형승합차, 4:2층버스) */
    private String plateType;
    /** 차량 빈자리수 (-1:정보없음, 0~:빈자리 수) */
    private String remainSeatCnt;
    /** 생성시간 */
    private LocalDateTime createdAt;

    public String getShortPlateNo() {
        int length = plateNo.length();
        return plateNo.substring(length -4, length);
    }

    public String getPlateTypeIconUrl() {
        return StringUtils.defaultIfEmpty(CodeUtil.getDetailCodeByDetailCodeId("PLATE_TYPE", this.plateType).getImg(), "");
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