package com.leehw.sbly.web;

import com.leehw.sbly.config.auth.Dto.SessionMember;
import com.leehw.sbly.config.auth.LoginMember;
import com.leehw.sbly.service.orders.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final OrdersService ordersService;

    @GetMapping("/")
    public String index(Model model, @LoginMember SessionMember member){

        if(member != null) {
            model.addAttribute("memberName", member.getName());
            model.addAttribute("id", member.getId());
            model.addAttribute("money", member.getMoney());
        }
        return "index";
    }

    @GetMapping("/myPage/{id}")
    public String myPage(@PathVariable Long id, Model model, @LoginMember SessionMember member){
        if(member != null) {
            model.addAttribute("memberName", member.getName());
            model.addAttribute("email", member.getEmail());
            model.addAttribute("id", member.getId());
            model.addAttribute("money", member.getMoney());
        }
        model.addAttribute("orders", ordersService.findByMemberId(id));
        return "myPage";
    }

    @GetMapping("/goods")
    public String goods(){
        return "goods";
    }
}
