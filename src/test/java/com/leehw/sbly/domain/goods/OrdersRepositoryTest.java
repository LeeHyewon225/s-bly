package com.leehw.sbly.domain.goods;

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
        String member_email = " rachel6319@naver.com";
        String member_name = "이혜원";
        Member member = Member.builder()
                .email(member_email)
                .name(member_name).build();
        memberRepository.save(member);

        String goods_name = "맨투맨";
        int goods_price = 10000;
        int main_category = 1;
        int sub_category = 1;
        int delivery_time = 3;
        Goods goods = Goods.builder()
                .name(goods_name)
                .price(goods_price)
                .main_category(main_category)
                .sub_category(sub_category)
                .delivery_time(delivery_time).build();
        goodsRepository.save(goods);

        int deliver= 0;
        ordersRepository.save(Orders.builder().orders_member(member).orders_goods(goods).deliver(deliver).build());

        List<Orders> ordersList = ordersRepository.findAll();
        Orders orders = ordersList.get(0);

        assertThat(orders.getOrders_member()).isEqualTo(member);
        assertThat(orders.getOrders_goods()).isEqualTo(goods);
        assertThat(orders.getDeliver()).isEqualTo(deliver);
    }
}
