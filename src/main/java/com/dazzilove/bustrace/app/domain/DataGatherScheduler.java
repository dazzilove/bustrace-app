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

    public String getScheduleName() {
        String scheduleName = "";
        if (schedule == null) {
            return scheduleName;
        }
        switch (this.schedule) {
            case "1":
                scheduleName = "1분";
                break;
            case "2":
                scheduleName = "5분";
                break;
            case "3":
                scheduleName = "15분";
                break;
            case "4":
                scheduleName = "1시간";
                break;
        }
        return scheduleName;
    }
}
