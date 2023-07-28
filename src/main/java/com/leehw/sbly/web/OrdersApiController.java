package com.leehw.sbly.web;

import com.leehw.sbly.config.auth.Dto.SessionMember;
import com.leehw.sbly.config.auth.LoginMember;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.order.OrdersRepository;
import com.leehw.sbly.service.member.MemberService;
import com.leehw.sbly.service.orders.OrdersService;
import com.leehw.sbly.web.Dto.orders.OrdersSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class OrdersApiController {
    private final OrdersService ordersService;
    private final MemberService memberService;

    @PostMapping("/api/orders")
    public Long save(@RequestBody OrdersSaveRequestDto ordersSaveRequestDto, HttpServletRequest request){
        Long result = ordersService.save(ordersSaveRequestDto);
        if(result == -1L){
            HttpSession session = request.getSession();
            Member new_member = memberService.findById(ordersSaveRequestDto.getMember_id());
            session.setAttribute("member", new SessionMember(new_member));
        }
        return result;
    }

    @PutMapping("/api/orders/{id}")
    public Long cancel(@PathVariable Long id, HttpServletRequest request, @LoginMember SessionMember member){
        Long result =  ordersService.cancel(id);
        HttpSession session = request.getSession();
        Member new_member = memberService.findById(member.getId());
        session.setAttribute("member", new SessionMember(new_member));
        return result;
    }
}
