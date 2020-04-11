package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.Code;
import com.dazzilove.bustrace.app.domain.DetailCode;
import com.dazzilove.bustrace.app.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public void addCode(Code code) throws Exception {
        code.setId(UUID.randomUUID());
        code.setCreatedAt(LocalDateTime.now());
        code.setUpdatedAt(LocalDateTime.now());
        codeRepository.insert(code);
    }

    @Override
    public void editCode(Code code) throws Exception {
        code.setUpdatedAt(LocalDateTime.now());
        codeRepository.save(code);
    }

    @Override
    public void deleteCode(Code code) throws Exception {
        Code updateTarget = getCode(code.getId());
        if (updateTarget == null)
            throw new Exception("정보가 올바르지 않습니다.");
        updateTarget.setDelYn("Y");
        updateTarget.setDeletedAt(LocalDateTime.now());
        updateTarget.setUpdatedAt(LocalDateTime.now());
        codeRepository.save(updateTarget);
    }

    @Override
    public void editDetailCode(String codeId, DetailCode editedDetailCode) throws Exception {
        Code code = getCode(UUID.fromString(codeId));

        DetailCode orgDetailCode = code.getDetailCode(editedDetailCode.getId().toString());
        editedDetailCode.setCreatedAt(orgDetailCode.getCreatedAt());
        editedDetailCode.setUpdatedAt(LocalDateTime.now());

        code.getDetailCodes().remove(orgDetailCode);
        code.getDetailCodes().add(editedDetailCode);

        editCode(code);
    }

    @Override
    public void deleteDetailCode(String codeId, DetailCode editedDetailCode) throws Exception {
        editedDetailCode.setDelYn("Y");
        editedDetailCode.setDeletedAt(LocalDateTime.now());
        editDetailCode(codeId, editedDetailCode);
    }

    @Override
    public Code getCode(UUID id) throws Exception {
        Optional<Code> code = codeRepository.findById(id);
        return code.orElse(new Code());
    }

    @Override
    public List<Code> getCodeList() throws Exception {
        return codeRepository.findAll().stream()
                .filter(tempCode -> !tempCode.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public Code getCode(String id) throws Exception {
        Code code = codeRepository.findById(UUID.fromString(id)).orElse(new Code());
        List<DetailCode> deletedDetailCodes =
                code.getDetailCodes().stream()
                        .filter(codeDetail -> codeDetail.isDeleted()).collect(Collectors.toList());
        code.getDetailCodes().removeAll(deletedDetailCodes);
        return code;
    }
}
