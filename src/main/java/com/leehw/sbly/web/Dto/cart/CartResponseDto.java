package com.leehw.sbly.web.Dto.cart;

import com.leehw.sbly.domain.cart.Cart;
import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.member.Member;
import lombok.Getter;

@Getter
public class CartResponseDto {
    private Long id;
    private Member member;
    private Goods goods;

    public CartResponseDto(Cart cart){
        this.id = cart.getId();
        this.member = cart.getMember();
        this.goods = cart.getGoods();
    }
}
