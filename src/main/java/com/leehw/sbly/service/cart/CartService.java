package com.leehw.sbly.service.cart;

import com.leehw.sbly.domain.cart.Cart;
import com.leehw.sbly.domain.cart.CartRepository;
import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.member.MemberRepository;
import com.leehw.sbly.web.Dto.cart.CartSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;

    @Transactional
    public Long save(CartSaveRequestDto cartSaveRequestDto){
        Member member = memberRepository.findById(cartSaveRequestDto.getMember_id())
                .orElseThrow(()->new IllegalArgumentException("해당 회원이 없습니다. id = " + cartSaveRequestDto.getMember_id()));
        Goods goods = goodsRepository.findById(cartSaveRequestDto.getGoods_id())
                .orElseThrow(()->new IllegalArgumentException("해당 상품이 없습니다. id = " + cartSaveRequestDto.getGoods_id()));
        return cartRepository.save(Cart.builder()
                .member(member)
                .goods(goods)
                .build()).getId();
    }
}
