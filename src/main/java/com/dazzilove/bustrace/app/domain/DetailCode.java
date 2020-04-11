package com.dazzilove.bustrace.app.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
public class DetailCode {
    @Id
    private UUID id;

    private String codeId;
    private String codeName;
    private int sortNumber;
    private String useYn;
    private String delYn;
    private String masterCode;
    private String img;
    private String val1, val2, val3;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public boolean isDeleted() {
        return "Y".equals(delYn);
    }
}
