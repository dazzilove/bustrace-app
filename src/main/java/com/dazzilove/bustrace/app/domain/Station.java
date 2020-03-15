package com.dazzilove.bustrace.app.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ToString
public class Station {
    @Id private UUID id;
    private String routeId;
    private String stationId;
    private String stationSeq;
    private String stationName;
    private String centerYn;
    private String districtCd;
    private String mobileNo;
    private String regionName;
    private String turnYn;
    private int remainSeatCntZeroCnt;

    private List<Location> locations = new ArrayList<>();
}
