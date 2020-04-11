package com.dazzilove.bustrace.app.utils;


import com.dazzilove.bustrace.app.domain.Code;
import com.dazzilove.bustrace.app.domain.DetailCode;
import com.dazzilove.bustrace.app.service.CodeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CodeUtil implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext applicationContext;

    public static Map<String, Code> codes = new HashMap<>();

    private CodeUtil() {
        //
    }

    public void setApplicationContext(ApplicationContext ctx) {
        applicationContext = ctx;
    }

    @Override
    public void afterPropertiesSet() {
        CodeUtil.init();
    }

    public static void init() {
        List<Code> codeList = new ArrayList<>();
        try {
            CodeService codeService = applicationContext.getBean(CodeService.class);
            codeList = codeService.getCodeList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        codes.clear();
        codeList.forEach(code -> codes.put(code.getCodeId(), code));
    }

    public static Code getCode(String codeId) {
        return codes.get(codeId);
    }

    public static DetailCode getDetailCodeByDetailCodeId(String codeId, String detailCodeId) {
        Code code = CodeUtil.getCode(codeId);
        if (code == null) {
            return new DetailCode();
        }
        return code.getDetailCode(detailCodeId);
    }

}
