package com.leehw.sbly.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leehw.sbly.domain.cart.Cart;
import com.leehw.sbly.domain.cart.CartRepository;
import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.domain.member.Member;
import com.leehw.sbly.domain.member.MemberRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GoodsRepository goodsRepository;

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
}