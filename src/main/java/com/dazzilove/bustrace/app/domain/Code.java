package com.dazzilove.bustrace.app.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Code {

    @Id private String id;
    private String name;
    private String useYn;
    private String delYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private List<DetailCode> detailCodes = new ArrayList<>();

    public List<DetailCode> getDetailCodes() {
        return this.detailCodes;
    }

    public DetailCode getDetailCode(String codeId) {
        for (DetailCode detailCodeTemp: detailCodes) {
            if (detailCodeTemp.getId().equals(codeId)) {
                return detailCodeTemp;
            }
        }
        return new DetailCode();
    }

    public boolean isDeleted() {
        return "Y".equals(delYn);
    }

    public boolean isUse() { return !"N".equals(useYn); }

    public boolean isAddValidate() {
        if(id == null || id.trim().length() == 0) return false;
        if(name == null || name.trim().length() == 0) return false;
        if(useYn == null || useYn.trim().length() == 0) return false;
        return true;
    }

    public boolean isEditValidate() {
        if(id == null || id.trim().length() == 0) return false;
        if(name == null || name.trim().length() == 0) return false;
        if(useYn == null || useYn.trim().length() == 0) return false;
        return true;
    }

    public boolean isDeleteValidate() {
        if(id == null || id.trim().length() == 0) return false;
        return true;
    }
}
