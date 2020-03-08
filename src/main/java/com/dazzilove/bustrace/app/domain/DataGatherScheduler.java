package com.dazzilove.bustrace.app.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DataGatherScheduler {

    public static final String SCHEDULE_1_MINUTE = "SCHEDULE_1_MINUTE";
    public static final String SCHEDULE_15_MINUTES = "SCHEDULE_15_MINUTES";
    public static final String SCHEDULE_30_MINUTES = "SCHEDULE_30_MINUTES";
    public static final String SCHEDULE_1_HOUR = "SCHEDULE_1_HOUR";

    private boolean enabled;
    private String schedule;

    public boolean isEnabled() {
        return enabled;
    }
}
