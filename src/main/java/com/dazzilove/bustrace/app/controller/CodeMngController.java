package com.dazzilove.bustrace.app.controller;

import com.dazzilove.bustrace.app.domain.Code;
import com.dazzilove.bustrace.app.domain.DetailCode;
import com.dazzilove.bustrace.app.exception.code.HadCodeException;
import com.dazzilove.bustrace.app.service.CodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;

@Controller
public class CodeMngController {

    @Autowired
    CodeService codeService;

    @RequestMapping("/codeMng/codeList")
    public String getCodeList(Model model) throws Exception {
        model.addAttribute("currentMenu", "7");
        model.addAttribute("codes", codeService.getCodeList());
        return "codeMng/codeList";
    }

    @RequestMapping("/codeMng/codeInfo")
    public String getCodeInfo(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "7");

        Code code = convertCodeByRequest(request);

        model.addAttribute("codeInfo", codeService.getCode(code.getId()));
        return "codeMng/codeInfo";
    }

    @RequestMapping("/codeMng/viewAddCode")
    public String viewAddCode(Model model) throws Exception {
        model.addAttribute("currentMenu", "7");
        model.addAttribute("pageMode", "ADD");

        Code code = new Code();
        model.addAttribute("code", code);
        return "codeMng/codeForm";
    }

    @RequestMapping("/codeMng/addCode")
    @ResponseBody
    public String addCode(ServletRequest request) throws Exception {

        Code code = convertCodeByRequest(request);

        if(!code.isAddValidate()) {
            return "값이 올바르지 않습니다. 필요한 값을 모두 입력했는지 확인하세요.";
        }

        try {
            codeService.addCode(code);
        } catch (HadCodeException e) {
            return "[ERROR-1] 등록된 코드ID가 존재합니다.";
        } catch(Exception e) {
            e.printStackTrace();
            return "[ERROR-99] 등록 중 에러가 발생 했습니다.";
        }

        return "등록 완료 되었습니다.";
    }

    @RequestMapping("/codeMng/viewEditCode")
    public String viewAddRoute(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "7");
        model.addAttribute("pageMode", "EDIT");

        Code codeParams = convertCodeByRequest(request);

        Code code = codeService.getCode(codeParams.getId());
        model.addAttribute("code", code);
        return "codeMng/codeForm";
    }

    @RequestMapping("/codeMng/editCode")
    @ResponseBody
    public String eidtCode(ServletRequest request) throws Exception {

        Code code = convertCodeByRequest(request);

        if(!code.isEditValidate()) {
            return "값이 올바르지 않습니다. 필요한 값을 모두 입력했는지 확인하세요.";
        }

        try {
            codeService.editCode(code);
        } catch(Exception e) {
            e.printStackTrace();
            return "수정 중 에러가 발생 했습니다.";
        }

        return "수정 완료 되었습니다.";
    }

    @RequestMapping("/codeMng/delCode")
    @ResponseBody
    public String delCode(ServletRequest request) throws Exception {

        Code code = convertCodeByRequest(request);

        if(!code.isDeleteValidate()) {
            return "값이 올바르지 않습니다.";
        }

        try {
            codeService.deleteCode(code);
        } catch(Exception e) {
            e.printStackTrace();
            return "삭제 중 에러가 발생 했습니다.";
        }

        return "삭제 완료 되었습니다.";
    }

    @RequestMapping("/codeMng/viewAddDetailCode")
    public String viewAddDetailCode(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "7");
        model.addAttribute("pageMode", "ADD");

        DetailCode detailCodeParam = convertDetailCodeByRequest(request);

        DetailCode detailCode = new DetailCode();
        detailCode.setMasterId(detailCodeParam.getMasterId());
        model.addAttribute("detailCode", detailCode);
        return "codeMng/detailCodeForm";
    }

    @RequestMapping("/codeMng/viewEditDetailCode")
    public String viewEditDetailCode(Model model, ServletRequest request) throws Exception {
        model.addAttribute("currentMenu", "7");
        model.addAttribute("pageMode", "EDIT");

        DetailCode detailCodeParam = convertDetailCodeByRequest(request);

        DetailCode detailCode = codeService.getDetailCode(detailCodeParam);
        if (detailCode == null || detailCode.getId() == null || detailCode.getId().trim().length() == 0) {
            throw new Exception("정보가 올바르지 않습니다.");
        }
        model.addAttribute("detailCode", detailCode);
        return "codeMng/detailCodeForm";
    }

    @RequestMapping("/codeMng/addDetailCode")
    @ResponseBody
    public String addDetailCode(ServletRequest request) throws Exception {

        DetailCode detailCode = convertDetailCodeByRequest(request);

        if(!detailCode.isAddValidate()) {
            return "값이 올바르지 않습니다. 필요한 값을 모두 입력했는지 확인하세요.";
        }

        try {
            codeService.addDetailCode(detailCode);
        } catch (HadCodeException e) {
            return "[ERROR-1] 등록된 코드ID가 존재합니다.";
        } catch(Exception e) {
            e.printStackTrace();
            return "[ERROR-99] 등록 중 에러가 발생 했습니다.";
        }

        return "등록 완료 되었습니다.";
    }

    @RequestMapping("/codeMng/editDetailCode")
    @ResponseBody
    public String editDetailCode(ServletRequest request) throws Exception {

        DetailCode detailCode = convertDetailCodeByRequest(request);

        if(!detailCode.isEditValidate()) {
            return "값이 올바르지 않습니다. 필요한 값을 모두 입력했는지 확인하세요.";
        }

        try {
            codeService.editDetailCode(detailCode);
        } catch(Exception e) {
            e.printStackTrace();
            return "[ERROR-99] 수정 중 에러가 발생 했습니다.";
        }

        return "수정 완료 되었습니다.";
    }

    @RequestMapping("/codeMng/delDetailCode")
    @ResponseBody
    public String delDetailCode(ServletRequest request) throws Exception {

        DetailCode detailCode = convertDetailCodeByRequest(request);

        if(!detailCode.isDelValidate()) {
            return "값이 올바르지 않습니다. 필요한 값을 모두 입력했는지 확인하세요.";
        }

        try {
            codeService.deleteDetailCode(detailCode);
        } catch(Exception e) {
            e.printStackTrace();
            return "[ERROR-99] 삭제 중 에러가 발생 했습니다.";
        }

        return "삭제 완료 되었습니다.";
    }

    private Code convertCodeByRequest(ServletRequest request) {
        String id = StringUtils.defaultString(request.getParameter("id"), "").trim();
        String name = StringUtils.defaultString(request.getParameter("name"), "").trim();
        String useYn = StringUtils.defaultString(request.getParameter("useYn"), "").trim();
        String delYn = StringUtils.defaultString(request.getParameter("delYn"), "").trim();

        Code code = new Code();
        code.setId(id);
        code.setName(name);
        code.setUseYn(useYn);
        code.setDelYn(delYn);

        return code;
    }

    private DetailCode convertDetailCodeByRequest(ServletRequest request) {
        String masterId = StringUtils.defaultString(request.getParameter("masterId"), "").trim();
        String id = StringUtils.defaultString(request.getParameter("id"), "").trim();
        String name = StringUtils.defaultString(request.getParameter("name"), "").trim();
        String sortNumber = StringUtils.defaultString(request.getParameter("sortNumber"), "99").trim();
        String useYn = StringUtils.defaultString(request.getParameter("useYn"), "").trim();
        String delYn = StringUtils.defaultString(request.getParameter("delYn"), "").trim();
        String img = StringUtils.defaultString(request.getParameter("img"), "").trim();
        String val1 = StringUtils.defaultString(request.getParameter("val1"), "").trim();
        String val2 = StringUtils.defaultString(request.getParameter("val2"), "").trim();
        String val3 = StringUtils.defaultString(request.getParameter("val3"), "").trim();

        DetailCode detailCode = new DetailCode();
        detailCode.setMasterId(masterId);
        detailCode.setId(id);
        detailCode.setName(name);
        detailCode.setSortNumber(Integer.parseInt(sortNumber));
        detailCode.setUseYn(useYn);
        detailCode.setDelYn(delYn);
        detailCode.setImg(img);
        detailCode.setVal1(val1);
        detailCode.setVal2(val2);
        detailCode.setVal3(val3);

        return detailCode;
    }

}
