package com.leehw.sbly.service.orders;

import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.member.MemberRepository;
import com.leehw.sbly.domain.order.Orders;
import com.leehw.sbly.domain.order.OrdersRepository;
import com.leehw.sbly.web.Dto.orders.OrdersSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;

    @Transactional
    public Long save(OrdersSaveRequestDto ordersSaveRequestDto){
        Long member_id = ordersSaveRequestDto.getMember_id();
        Long goods_id = ordersSaveRequestDto.getGoods_id();

        Member member = memberRepository.findById(member_id)
                .orElseThrow(()->new IllegalArgumentException("해당 회원이 없습니다. id = " + member_id));
        Goods goods = goodsRepository.findById(goods_id)
                .orElseThrow(()->new IllegalArgumentException("해당 상품이 없습니다. id = " + goods_id));

        return ordersRepository.save(Orders.builder()
                .member(member)
                .goods(goods)
                .deliver(0)
                .build()).getId();
    }
}
