package com.leehw.sbly.domain.orders;

import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.member.MemberRepository;
import com.leehw.sbly.domain.order.Orders;
import com.leehw.sbly.domain.order.OrdersRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersRepositoryTest {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void cleanup(){
        ordersRepository.deleteAll();
        goodsRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @Transactional
    public void 주문저장_불러오기(){
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

        int deliver= 0;
        int cancelOrder = 0;
        ordersRepository.save(Orders.builder().member(member).goods(goods).deliver(deliver).cancelOrder(cancelOrder).build());

        List<Orders> ordersList = ordersRepository.findAll();
        Orders orders = ordersList.get(0);

        assertThat(orders.getMember()).isEqualTo(member);
        assertThat(orders.getGoods()).isEqualTo(goods);
        assertThat(orders.getDeliver()).isEqualTo(deliver);
        assertThat(orders.getCancelOrder()).isEqualTo(cancelOrder);
    }
}
