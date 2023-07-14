package com.leehw.sbly.domain.goods;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {

    @Autowired
    GoodsRepository goodsRepository;

    @After
    public void cleanup(){
        goodsRepository.deleteAll();
    }

    @Test
    public void 상품저장_불러오기(){
        String name = "테스트 상품";
        int price = 10000;
        int maincategory = 1;
        int subcategory = 1;
        int deliverytime = 3;

        goodsRepository.save(Goods.builder()
                .name(name)
                .price(price)
                .maincategory(maincategory)
                .subcategory(subcategory)
                .deliverytime(deliverytime)
                .build());

        List<Goods> goodsList = goodsRepository.findAll();

        Goods goods = goodsList.get(0);
        assertThat(goods.getName()).isEqualTo(name);
        assertThat(goods.getPrice()).isEqualTo(price);
        assertThat(goods.getMaincategory()).isEqualTo(maincategory);
        assertThat(goods.getSubcategory()).isEqualTo(subcategory);
        assertThat(goods.getDeliverytime()).isEqualTo(deliverytime);
    }
}
