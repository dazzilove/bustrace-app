package com.dazzilove.bustrace.app.service;

import com.dazzilove.bustrace.app.domain.Code;
import com.dazzilove.bustrace.app.domain.DetailCode;
import com.dazzilove.bustrace.app.domain.Location;
import com.dazzilove.bustrace.app.exception.code.HadCodeException;
import com.dazzilove.bustrace.app.exception.code.HadDetailCodeException;
import com.dazzilove.bustrace.app.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        Code existCode = getCode(code.getId());
        if (existCode.getId() != null) {
            if (existCode.getId().trim().length() > 0) {
                throw new HadCodeException();
            }
        }

        code.setCreatedAt(LocalDateTime.now());
        code.setUpdatedAt(LocalDateTime.now());
        codeRepository.insert(code);
    }

    @Override
    public void editCode(Code code) throws Exception {
        Code existCode = getCode(code.getId());
        if (existCode == null)
            throw new Exception("정보가 올바르지 않습니다.");
        code.setCreatedAt(existCode.getCreatedAt());
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
    public void addDetailCode(DetailCode detailCode) throws Exception {
        Code updateTarget = getCode(detailCode.getMasterId());
        if (updateTarget == null)
            throw new Exception("정보가 올바르지 않습니다.");

        List<DetailCode> detailCodes = updateTarget.getDetailCodes();
        if (detailCodes == null)
            detailCodes = new ArrayList<>();

        String id = detailCode.getId();
        for(DetailCode tempDetailCode: detailCodes) {
            if (id.equals(tempDetailCode.getId())) {
                throw new HadDetailCodeException();
            }
        }

        detailCode.setCreatedAt(LocalDateTime.now());
        detailCodes.add(detailCode);

        editCode(updateTarget);
    }

    @Override
    public void editDetailCode(DetailCode editedDetailCode) throws Exception {
        Code code = getCode(editedDetailCode.getMasterId());

        DetailCode orgDetailCode = code.getDetailCode(editedDetailCode.getId());
        editedDetailCode.setCreatedAt(orgDetailCode.getCreatedAt());
        editedDetailCode.setUpdatedAt(LocalDateTime.now());

        code.getDetailCodes().remove(orgDetailCode);
        code.getDetailCodes().add(editedDetailCode);

        editCode(code);
    }

    @Override
    public void deleteDetailCode(DetailCode editedDetailCode) throws Exception {
        DetailCode updateTarget = getDetailCode(editedDetailCode);
        updateTarget.setDelYn("Y");
        updateTarget.setDeletedAt(LocalDateTime.now());
        editDetailCode(updateTarget);
    }

    @Override
    public List<Code> getCodeList() throws Exception {
        return codeRepository.findAll().stream()
                .filter(tempCode -> !tempCode.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public Code getCode(String id) throws Exception {
        Code code = new Code();

        List<AggregationOperation> aggOperationlist = new ArrayList<AggregationOperation>();
        aggOperationlist.add(Aggregation.match(Criteria.where("id").is(id)));

        TypedAggregation<Code> agg = Aggregation.newAggregation(Code.class, aggOperationlist);

        List<Code> codes = mongoOperations.aggregate(agg, Code.class, Code.class).getMappedResults();
        if (codes != null && codes.size() > 0) {
            code = codes.get(0);
        }

        List<DetailCode> detailCodes = code.getDetailCodes();
        if (detailCodes != null && detailCodes.size() > 0) {
            List<DetailCode> tempDetailCodes = detailCodes.stream()
                    .filter(tempDetailCode -> !tempDetailCode.isDeleted()).collect(Collectors.toList());
            code.setDetailCodes(tempDetailCodes);
        }

        return code;
    }

    @Override
    public DetailCode getDetailCode(DetailCode detailCodeParam) throws Exception {
        Code code = getCode(detailCodeParam.getMasterId());
        if (code == null || code.getId() == null || code.getId().trim().length() == 0) {
            throw new Exception("코드 정보가 올바르지 않습니다.");
        }

        List<DetailCode> detailCodes = code.getDetailCodes();
        if (detailCodes == null || detailCodes.size() == 0) {
            throw new Exception("상세코드 정보가 올바르지 않습니다.");
        }

        for(DetailCode detailCode: detailCodes) {
            if (detailCode.getId().equals(detailCodeParam.getId())) {
                return detailCode;
            }
        }

        return null;
    }
}
