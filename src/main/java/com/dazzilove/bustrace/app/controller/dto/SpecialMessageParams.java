package com.dazzilove.bustrace.app.controller.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class SpecialMessageParams {

    public static String CREATED_AT_CLF_CD_THIS_WEEK = "1";
    public static String CREATED_AT_CLF_CD_LAST_WEEK = "2";
    public static String CREATED_AT_CLF_CD_THIS_MONTH = "3";
    public static String CREATED_AT_CLF_CD_LAST_MONTH = "4";

    private String routeId;
    private String createdAtClfCd;
    private LocalDateTime startCreatedAt;
    private LocalDateTime endCreatedAt;

}
