package com.leehw.sbly.web;

import com.leehw.sbly.config.auth.Dto.SessionMember;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.service.member.MemberService;
import com.leehw.sbly.web.Dto.member.MoneyChargeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PutMapping("/api/member/{id}")
    public Long chargeMoney(@PathVariable Long id, @RequestBody MoneyChargeRequestDto moneyChargeRequestDto, HttpServletRequest request){
        memberService.chargeMoney(id, moneyChargeRequestDto);
        HttpSession session = request.getSession();
        Member new_member = memberService.findById(id);
        session.setAttribute("member", new SessionMember(new_member));
        return id;
    }
}
