package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.Code;
import com.dazzilove.bustrace.app.domain.DetailCode;

import java.util.List;
import java.util.UUID;

public interface CodeService {
    List<Code> getCodeList() throws Exception;
    Code getCode(String id) throws Exception;
    DetailCode getDetailCode(DetailCode detailCodeParam) throws Exception;
    void addCode(Code code) throws Exception;
    void editCode(Code code) throws Exception;
    void deleteCode(Code code) throws Exception;
    void addDetailCode(DetailCode detailCode) throws Exception;
    void editDetailCode(DetailCode detailCode) throws Exception;
    void deleteDetailCode(DetailCode editedDetailCode) throws Exception;
}
