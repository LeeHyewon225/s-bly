package com.leehw.sbly.service.orders;

import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.member.MemberRepository;
import com.leehw.sbly.domain.order.Orders;
import com.leehw.sbly.domain.order.OrdersRepository;
import com.leehw.sbly.web.Dto.orders.OrderResponseDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersServiceTest {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private OrdersService ordersService;

    @After
    public void cleanup(){
        ordersRepository.deleteAll();
        memberRepository.deleteAll();
        goodsRepository.deleteAll();
    }

    @Test
    @Transactional
    public void Orders_회원아이디로_조회하다_and_배송_여부_계산하다(){
        String member_email = "rachel6319@naver.com";
        String member_name = "이혜원";
        Member member = Member.builder()
                .email(member_email)
                .name(member_name).build();
        memberRepository.save(member);

        String goods_name = "맨투맨";
        int goods_price = 10000;
        int mainCategory = 1;
        int subCategory = 1;
        int deliveryTime = 3;
        Goods goods = Goods.builder()
                .name(goods_name)
                .price(goods_price)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .deliveryTime(deliveryTime).build();
        goodsRepository.save(goods);

        boolean deliver= false;
        boolean cancelOrder = false;
        Orders orders = Orders.builder()
                .member(member)
                .goods(goods)
                .deliver(deliver)
                .cancelOrder(cancelOrder)
                .build();
        ordersRepository.save(orders);
        orders.setCreatedDate(2023, 7,11);

        OrderResponseDto responseDto = ordersService.findByMemberIdAndCancelOrderOrder(member.getId(), false).get(0);

        assertThat(responseDto.getMember()).isEqualTo(member);
        assertThat(responseDto.isDeliver()).isEqualTo(true);
    }
}
