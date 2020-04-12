package com.dazzilove.bustrace.app.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@ToString
public class DetailCode {
    @Id private String id;
    private String masterId;
    private String name;
    private int sortNumber;
    private String useYn;
    private String delYn;
    private String img;
    private String val1, val2, val3;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public boolean isDeleted() {
        return "Y".equals(delYn);
    }

    public boolean isAddValidate() {
        if(masterId == null || masterId.trim().length() == 0) return false;
        if(id == null || id.trim().length() == 0) return false;
        if(name == null || name.trim().length() == 0) return false;
        if(useYn == null || useYn.trim().length() == 0) return false;
        return true;
    }

    public boolean isEditValidate() {
        if(masterId == null || masterId.trim().length() == 0) return false;
        if(id == null || id.trim().length() == 0) return false;
        if(name == null || name.trim().length() == 0) return false;
        if(useYn == null || useYn.trim().length() == 0) return false;
        return true;
    }

    public boolean isDelValidate() {
        if(masterId == null || masterId.trim().length() == 0) return false;
        if(id == null || id.trim().length() == 0) return false;
        return true;
    }
}
