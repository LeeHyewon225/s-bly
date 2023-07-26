package com.leehw.sbly.service.member;

import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.member.MemberRepository;
import com.leehw.sbly.web.Dto.member.MoneyChargeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Long chargeMoney(Long id, MoneyChargeRequestDto moneyChargeRequestDto){
        Member member = memberRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 회원이 없습니다. id = " + id));
        member.chargeMoney(moneyChargeRequestDto.getMoney());
        return id;
    }
}
