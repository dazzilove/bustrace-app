package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.Code;
import com.dazzilove.bustrace.app.domain.DetailCode;

import java.util.List;
import java.util.UUID;

public interface CodeService {
    Code getCode(UUID id) throws Exception;
    List<Code> getCodeList() throws Exception;
    Code getCode(String id) throws Exception;
    void addCode(Code code) throws Exception;
    void editCode(Code code) throws Exception;
    void deleteCode(Code code) throws Exception;
    void editDetailCode(String codeId, DetailCode detailCode) throws Exception;
    void deleteDetailCode(String codeId, DetailCode editedDetailCode) throws Exception;
}
