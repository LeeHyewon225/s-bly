package com.leehw.sbly.web;

import com.leehw.sbly.config.auth.Dto.SessionMember;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.service.cart.CartService;
import com.leehw.sbly.service.member.MemberService;
import com.leehw.sbly.web.Dto.cart.CartSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartApiController {
    private final CartService cartService;
    private final MemberService memberService;

    @PostMapping("/api/cart")
    public Long save(@RequestBody CartSaveRequestDto cartSaveRequestDto){
        return cartService.save(cartSaveRequestDto);
    }

    @DeleteMapping("/api/cart")
    public Long delete(@RequestBody List<Long> deleteCart){
        return cartService.delete(deleteCart);
    }

    @DeleteMapping("/api/cart/{id}")
    public Long order(@PathVariable Long id, @RequestBody List<Long> orderCart, HttpServletRequest request){
        Long result = cartService.order(id, orderCart);
        if(result == -1L){
            HttpSession session = request.getSession();
            Member new_member = memberService.findById(id);
            session.setAttribute("member", new SessionMember(new_member));
        }
        return result;
    }
}
