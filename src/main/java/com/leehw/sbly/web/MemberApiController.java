package com.leehw.sbly.web;

import com.leehw.sbly.service.member.MemberService;
import com.leehw.sbly.web.Dto.member.MoneyChargeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PutMapping("/api/member/{id}")
    public Long chargeMoney(@PathVariable Long id, @RequestBody MoneyChargeRequestDto moneyChargeRequestDto){
        System.out.println("-----111111---");
        System.out.println(moneyChargeRequestDto.getMoney());
        return memberService.chargeMoney(id, moneyChargeRequestDto);
    }
}
