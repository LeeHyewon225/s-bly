package com.leehw.sbly.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leehw.sbly.config.auth.Dto.SessionMember;
import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.member.MemberRepository;
import com.leehw.sbly.domain.order.Orders;
import com.leehw.sbly.domain.order.OrdersRepository;
import com.leehw.sbly.web.Dto.orders.OrdersSaveRequestDto;
import org.aspectj.weaver.ast.Or;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrdersApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private MockHttpSession session = new MockHttpSession();

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception{
        ordersRepository.deleteAll();
        memberRepository.deleteAll();
        goodsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    @Transactional
    public void Orders_등록된다() throws Exception{
        String member_email = "rachel6319@naver.com";
        String member_name = "이혜원";
        int money = 20000;
        Member member = Member.builder()
                .email(member_email)
                .name(member_name)
                .money(money).build();
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

        OrdersSaveRequestDto ordersSaveRequestDto = OrdersSaveRequestDto.builder()
                .member_id(member_id)
                .goods_id(goods_id)
                .build();

        String url = "http://localhost:" + port + "/api/orders";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(ordersSaveRequestDto)))
                .andExpect(status().isOk());

        List<Orders> all = ordersRepository.findAll();
        assertThat(all.get(0).getMember()).isEqualTo(member);
        assertThat(all.get(0).getGoods()).isEqualTo(goods);

        assertThat(all.get(0).getMember().getMoney()).isEqualTo(10000);
    }

    @Test
    @WithMockUser(roles = "USER")
    @Transactional
    public void Orders_등록_실패한다() throws Exception{
        String member_email = "rachel6319@naver.com";
        String member_name = "이혜원";
        int money = 5000;
        Member member = Member.builder()
                .email(member_email)
                .name(member_name)
                .money(money).build();
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

        OrdersSaveRequestDto ordersSaveRequestDto = OrdersSaveRequestDto.builder()
                .member_id(member_id)
                .goods_id(goods_id)
                .build();

        String url = "http://localhost:" + port + "/api/orders";

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(ordersSaveRequestDto)))
                .andExpect(status().isOk());

        List<Orders> all = ordersRepository.findAll();
        assertThat(all.size()).isEqualTo(0);

        assertThat(member.getMoney()).isEqualTo(5000);
    }

    @Test
    @WithMockUser(roles = "USER")
    @Transactional
    public void Orders_취소하다() throws Exception{
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
        Long id = orders.getId();

        String url = "http://localhost:" + port + "/api/orders/" + id;
        session.setAttribute("member", new SessionMember(member));
        mvc.perform(put(url).session(session))
                .andExpect(status().isOk());

        assertThat(orders.isCancelOrder()).isEqualTo(true);
    }
}
