package com.dazzilove.bustrace.app.repository;

import com.dazzilove.bustrace.app.domain.Code;
import com.dazzilove.bustrace.app.domain.DetailCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class CodeRepositoryTest {
    @Autowired
    CodeRepository codeRepository;

    @Test
    public void defaultCodeInsertTest() {
        insertPlateType();
        insertWeekendOperation();
        insertSpareTripYn();
        insertSchoolBreakReductionYn();
        insertTripStopYn();
    }

    private void insertPlateType() {
        List<DetailCode> detailCodes = new ArrayList<>();

        DetailCode detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("1");
        detailCode.setCodeName("소형승합차");
        detailCode.setImg("/img/bus1f_0.png");
        detailCode.setSortNumber(1);
        detailCode.setUseYn("Y");
        detailCode.setDelYn("N");
        detailCode.setCreatedAt(LocalDateTime.now());
        detailCode.setUpdatedAt(LocalDateTime.now());
        detailCodes.add(detailCode);

        detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("2");
        detailCode.setCodeName("중형승합차");
        detailCode.setImg("/img/bus1f_1.png");
        detailCode.setSortNumber(2);
        detailCodes.add(detailCode);

        detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("3");
        detailCode.setCodeName("대형승합차");
        detailCode.setImg("/img/bus1f_2.png");
        detailCode.setSortNumber(3);
        detailCodes.add(detailCode);

        detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("4");
        detailCode.setCodeName("2층버스");
        detailCode.setImg("/img/bus2f_2.png");
        detailCode.setSortNumber(4);
        detailCodes.add(detailCode);

        detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("99");
        detailCode.setCodeName("전체");
        detailCode.setImg("/img/bus.png");
        detailCode.setSortNumber(99);
        detailCodes.add(detailCode);

        Code code = new Code();
        code.setId(UUID.randomUUID());
        code.setCodeId("PLATE_TYPE");
        code.setCodeName("차량타입");
        code.setUseYn("Y");
        code.setDelYn("N");
        code.setCreatedAt(LocalDateTime.now());
        code.setUpdatedAt(LocalDateTime.now());
        code.setDetailCodes(detailCodes);

        codeRepository.save(code);
    }

    private void insertWeekendOperation() {
        List<DetailCode> detailCodes = new ArrayList<>();

        DetailCode detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("Y");
        detailCode.setCodeName("주말운행 O");
        detailCode.setSortNumber(1);
        detailCode.setUseYn("Y");
        detailCode.setDelYn("N");
        detailCode.setCreatedAt(LocalDateTime.now());
        detailCode.setUpdatedAt(LocalDateTime.now());
        detailCodes.add(detailCode);

        detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("N");
        detailCode.setCodeName("주말운행 X");
        detailCode.setSortNumber(2);
        detailCode.setUseYn("Y");
        detailCode.setDelYn("N");
        detailCode.setCreatedAt(LocalDateTime.now());
        detailCode.setUpdatedAt(LocalDateTime.now());
        detailCodes.add(detailCode);

        Code code = new Code();
        code.setId(UUID.randomUUID());
        code.setCodeId("WEEKEND_OPERATION_YN");
        code.setCodeName("주말운행여부");
        code.setUseYn("Y");
        code.setDelYn("N");
        code.setCreatedAt(LocalDateTime.now());
        code.setUpdatedAt(LocalDateTime.now());
        code.setDetailCodes(detailCodes);

        codeRepository.save(code);
    }

    private void insertSpareTripYn() {
        List<DetailCode> detailCodes = new ArrayList<>();

        DetailCode detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("Y");
        detailCode.setCodeName("예비차 O");
        detailCode.setSortNumber(1);
        detailCode.setUseYn("Y");
        detailCode.setDelYn("N");
        detailCode.setCreatedAt(LocalDateTime.now());
        detailCode.setUpdatedAt(LocalDateTime.now());
        detailCodes.add(detailCode);

        detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("N");
        detailCode.setCodeName("예비차 X");
        detailCode.setSortNumber(2);
        detailCode.setUseYn("Y");
        detailCode.setDelYn("N");
        detailCode.setCreatedAt(LocalDateTime.now());
        detailCode.setUpdatedAt(LocalDateTime.now());
        detailCodes.add(detailCode);

        Code code = new Code();
        code.setId(UUID.randomUUID());
        code.setCodeId("SPARE_TRIP_YN");
        code.setCodeName("예비차여부");
        code.setUseYn("Y");
        code.setDelYn("N");
        code.setCreatedAt(LocalDateTime.now());
        code.setUpdatedAt(LocalDateTime.now());
        code.setDetailCodes(detailCodes);

        codeRepository.save(code);
    }

    private void insertSchoolBreakReductionYn() {
        List<DetailCode> detailCodes = new ArrayList<>();

        DetailCode detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("Y");
        detailCode.setCodeName("방학감차 O");
        detailCode.setSortNumber(1);
        detailCode.setUseYn("Y");
        detailCode.setDelYn("N");
        detailCode.setCreatedAt(LocalDateTime.now());
        detailCode.setUpdatedAt(LocalDateTime.now());
        detailCodes.add(detailCode);

        detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("N");
        detailCode.setCodeName("방학감차 X");
        detailCode.setSortNumber(2);
        detailCode.setUseYn("Y");
        detailCode.setDelYn("N");
        detailCode.setCreatedAt(LocalDateTime.now());
        detailCode.setUpdatedAt(LocalDateTime.now());
        detailCodes.add(detailCode);

        Code code = new Code();
        code.setId(UUID.randomUUID());
        code.setCodeId("SCHOOL_BREAK_REDUCTION_YN");
        code.setCodeName("방학감차여부");
        code.setUseYn("Y");
        code.setDelYn("N");
        code.setCreatedAt(LocalDateTime.now());
        code.setUpdatedAt(LocalDateTime.now());
        code.setDetailCodes(detailCodes);

        codeRepository.save(code);
    }

    private void insertTripStopYn() {
        List<DetailCode> detailCodes = new ArrayList<>();

        DetailCode detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("Y");
        detailCode.setCodeName("운행중단 O");
        detailCode.setSortNumber(1);
        detailCode.setUseYn("Y");
        detailCode.setDelYn("N");
        detailCode.setCreatedAt(LocalDateTime.now());
        detailCode.setUpdatedAt(LocalDateTime.now());
        detailCodes.add(detailCode);

        detailCode = new DetailCode();
        detailCode.setId(UUID.randomUUID());
        detailCode.setCodeId("N");
        detailCode.setCodeName("운행중단 X");
        detailCode.setSortNumber(2);
        detailCode.setUseYn("Y");
        detailCode.setDelYn("N");
        detailCode.setCreatedAt(LocalDateTime.now());
        detailCode.setUpdatedAt(LocalDateTime.now());
        detailCodes.add(detailCode);

        Code code = new Code();
        code.setId(UUID.randomUUID());
        code.setCodeId("TRIP_STOP_YN");
        code.setCodeName("운행중단여부");
        code.setUseYn("Y");
        code.setDelYn("N");
        code.setCreatedAt(LocalDateTime.now());
        code.setUpdatedAt(LocalDateTime.now());
        code.setDetailCodes(detailCodes);

        codeRepository.save(code);
    }
}
