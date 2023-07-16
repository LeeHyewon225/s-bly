package com.leehw.sbly.service.cart;

import com.leehw.sbly.domain.cart.Cart;
import com.leehw.sbly.domain.cart.CartRepository;
import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.member.MemberRepository;
import com.leehw.sbly.domain.order.OrdersRepository;
import com.leehw.sbly.web.CartApiController;
import com.leehw.sbly.web.Dto.cart.CartResponseDto;
import com.leehw.sbly.web.Dto.cart.CartSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;
    private final OrdersRepository ordersRepository;

    @Transactional
    public Long save(CartSaveRequestDto cartSaveRequestDto) {
        Member member = memberRepository.findById(cartSaveRequestDto.getMember_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id = " + cartSaveRequestDto.getMember_id()));
        Goods goods = goodsRepository.findById(cartSaveRequestDto.getGoods_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id = " + cartSaveRequestDto.getGoods_id()));
        return cartRepository.save(Cart.builder()
                .member(member)
                .goods(goods)
                .build()).getId();
    }

    @Transactional
    public Long delete(List<Long> deleteCart) {
        for (Long i : deleteCart) {
            Cart cart = cartRepository.findById(i)
                    .orElseThrow(() -> new IllegalArgumentException("해당 장바구니 상품이 없습니다. id +" + i));
            cartRepository.delete(cart);
        }
        return 1L;
    }

    @Transactional
    public Long order(Long id, List<Long> orderCart) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id = " + id));
        int price_sum = 0;
        for (Long i : orderCart) {
            Cart cart = cartRepository.findById(i)
                    .orElseThrow(() -> new IllegalArgumentException("해당 장바구니 상품이 없습니다. id +" + i));
            price_sum += cart.getGoods().getPrice();
        }
        if (price_sum > member.getMoney())
            return -1L;
        member.pricecalculate(price_sum);
        for (Long i : orderCart) {
            Cart cart = cartRepository.findById(i)
                    .orElseThrow(() -> new IllegalArgumentException("해당 장바구니 상품이 없습니다. id +" + i));
            cartRepository.delete(cart);
            ordersRepository.save(cart.toOrders());
        }
        return 1L;
    }

    @Transactional
    public List<CartResponseDto> findByMemberId(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id = " + id));
        return cartRepository.findByMember(member).stream()
                .map(CartResponseDto::new)
                .collect(Collectors.toList());
    }
}
