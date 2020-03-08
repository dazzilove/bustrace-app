package com.dazzilove.bustrace.app.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
public class SpecialMessage {
    @Id
    UUID id;
    String divCd;
    String routeId;
    String message;
    LocalDateTime createdAt;

    private String formatTwoLength(String string) {
        String returnValue = string;
        returnValue = (returnValue == null) ? "" : returnValue;
        returnValue = returnValue.trim();
        returnValue = (returnValue.length() == 1) ? "0" + returnValue : returnValue;
        return returnValue;
    }

    public String getFormatedCreatedAt() {
        return String.format("%s/%s/%s %s:%s"
                , formatTwoLength(String.valueOf(createdAt.getYear()))
                , formatTwoLength(String.valueOf(createdAt.getMonthValue()))
                , formatTwoLength(String.valueOf(createdAt.getDayOfMonth()))
                , formatTwoLength(String.valueOf(createdAt.getHour()))
                , formatTwoLength(String.valueOf(createdAt.getMinute())));
    }
}
