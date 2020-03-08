package com.dazzilove.bustrace.app.controller;

import com.dazzilove.bustrace.app.domain.sample.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

    @GetMapping("/sample")
    public String getUser(Model model) {
        User user = new User("kkaok", "테스트", "web") ;
        model.addAttribute("user", user);
        model.addAttribute("currentMenu", "1");
        return "sample";
    }
}
