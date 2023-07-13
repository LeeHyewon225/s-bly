package com.leehw.sbly.web;

import com.leehw.sbly.config.auth.Dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){
        SessionMember member = (SessionMember) httpSession.getAttribute("member");

        if(member != null)
            model.addAttribute("memberName", member.getName());
        return "index";
    }
}
