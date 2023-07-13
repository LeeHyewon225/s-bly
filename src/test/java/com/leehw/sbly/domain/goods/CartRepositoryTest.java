package com.leehw.sbly.domain.goods;

import com.leehw.sbly.domain.cart.Cart;
import com.leehw.sbly.domain.cart.CartRepository;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.member.MemberRepository;
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
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void cleanup(){
        cartRepository.deleteAll();
        goodsRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @Transactional
    public void 장바구니저장_불러오기(){
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

        cartRepository.save(Cart.builder().cart_member(member).cart_goods(goods).build());

        List<Cart> cartsList = cartRepository.findAll();
        Cart cart = cartsList.get(0);

        assertThat(cart.getCart_member()).isEqualTo(member);
        assertThat(cart.getCart_goods()).isEqualTo(goods);
    }
}
