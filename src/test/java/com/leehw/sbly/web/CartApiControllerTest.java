package com.leehw.sbly.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leehw.sbly.domain.cart.Cart;
import com.leehw.sbly.domain.cart.CartRepository;
import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.member.MemberRepository;
import com.leehw.sbly.domain.order.Orders;
import com.leehw.sbly.domain.order.OrdersRepository;
import com.leehw.sbly.web.Dto.cart.CartSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void cleanup(){
        cartRepository.deleteAll();
        ordersRepository.deleteAll();
        memberRepository.deleteAll();
        goodsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    @Transactional
    public void Cart_등록된다() throws Exception{
        String member_email = "rachel6319@naver.com";
        String member_name = "이혜원";
        Member member = Member.builder()
                .email(member_email)
                .name(member_name).build();
        memberRepository.save(member);
        Long member_id = member.getId();

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
        Long goods_id = goods.getId();

        CartSaveRequestDto cartSaveRequestDto = CartSaveRequestDto.builder().member_id(member_id).goods_id(goods_id).build();

        String url = "http://localhost:" + port + "/api/cart";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(cartSaveRequestDto)))
                .andExpect(status().isOk());

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList.get(0).getMember()).isEqualTo(member);
        assertThat(cartList.get(0).getGoods()).isEqualTo(goods);
    }

    @Test
    @WithMockUser(roles = "USER")
    @Transactional
    public void Cart_삭제하다() throws Exception{
        String member_email = "rachel6319@naver.com";
        String member_name = "이혜원";
        Member member = Member.builder()
                .email(member_email)
                .name(member_name).build();
        memberRepository.save(member);

        List<Long> deleteCart = new ArrayList<>();
        for(int i=1;i<=3;i++) {
            String goods_name = "맨투맨" + i;
            int goods_price = 10000 * i;
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
            Cart cart = Cart.builder().member(member).goods(goods).build();
            cartRepository.save(cart);
            deleteCart.add(cart.getId());
        }

        String goods_name = "맨투맨4";
        int goods_price = 10000 * 4;
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
        Cart cart = Cart.builder().member(member).goods(goods).build();
        cartRepository.save(cart);

        String url = "http://localhost:" + port + "/api/cart";

        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(deleteCart)))
                .andExpect(status().isOk());

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList.get(0)).isEqualTo(cart);
    }

    @Test
    @WithMockUser(roles = "USER")
    @Transactional
    public void Cart_주문하다() throws Exception{
        String member_email = "rachel6319@naver.com";
        String member_name = "이혜원";
        Member member = Member.builder()
                .email(member_email)
                .name(member_name)
                .money(40000).build();
        memberRepository.save(member);
        Long member_id = member.getId();

        List<Long> orderCart = new ArrayList<>();

        Goods goods1 = Goods.builder()
                .name("맨투맨 1")
                .price(10000)
                .mainCategory(1)
                .subCategory(1)
                .deliveryTime(3).build();
        goodsRepository.save(goods1);
        Cart cart1 = Cart.builder().member(member).goods(goods1).build();
        cartRepository.save(cart1);
        orderCart.add(cart1.getId());

        Goods goods2 = Goods.builder()
                .name("맨투맨 2")
                .price(15000)
                .mainCategory(1)
                .subCategory(1)
                .deliveryTime(3).build();
        goodsRepository.save(goods2);
        Cart cart2 = Cart.builder().member(member).goods(goods2).build();
        cartRepository.save(cart2);

        Goods goods3 = Goods.builder()
                .name("맨투맨 3")
                .price(20000)
                .mainCategory(1)
                .subCategory(1)
                .deliveryTime(3).build();
        goodsRepository.save(goods3);
        Cart cart3 = Cart.builder().member(member).goods(goods3).build();
        cartRepository.save(cart3);
        orderCart.add(cart3.getId());

        String url = "http://localhost:" + port + "/api/cart/" + member_id;

        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(orderCart)))
                .andExpect(status().isOk());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList.get(0).getGoods()).isEqualTo(goods1);
        assertThat(ordersList.get(1).getGoods()).isEqualTo(goods3);

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList.get(0)).isEqualTo(cart2);

        assertThat(member.getMoney()).isEqualTo(10000);
    }

    @Test
    @WithMockUser(roles = "USER")
    @Transactional
    public void Cart_주문_실패하다() throws Exception{
        String member_email = "rachel6319@naver.com";
        String member_name = "이혜원";
        Member member = Member.builder()
                .email(member_email)
                .name(member_name)
                .money(20000).build();
        memberRepository.save(member);
        Long member_id = member.getId();

        List<Long> orderCart = new ArrayList<>();

        Goods goods1 = Goods.builder()
                .name("맨투맨 1")
                .price(10000)
                .mainCategory(1)
                .subCategory(1)
                .deliveryTime(3).build();
        goodsRepository.save(goods1);
        Cart cart1 = Cart.builder().member(member).goods(goods1).build();
        cartRepository.save(cart1);
        orderCart.add(cart1.getId());

        Goods goods2 = Goods.builder()
                .name("맨투맨 2")
                .price(15000)
                .mainCategory(1)
                .subCategory(1)
                .deliveryTime(3).build();
        goodsRepository.save(goods2);
        Cart cart2 = Cart.builder().member(member).goods(goods2).build();
        cartRepository.save(cart2);

        Goods goods3 = Goods.builder()
                .name("맨투맨 3")
                .price(20000)
                .mainCategory(1)
                .subCategory(1)
                .deliveryTime(3).build();
        goodsRepository.save(goods3);
        Cart cart3 = Cart.builder().member(member).goods(goods3).build();
        cartRepository.save(cart3);
        orderCart.add(cart3.getId());

        String url = "http://localhost:" + port + "/api/cart/" + member_id;

        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(orderCart)))
                .andExpect(status().isOk());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList.size()).isEqualTo(0);

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList.get(0)).isEqualTo(cart1);
        assertThat(cartList.get(1)).isEqualTo(cart2);
        assertThat(cartList.get(2)).isEqualTo(cart3);

        assertThat(member.getMoney()).isEqualTo(20000);
    }
}
