package com.leehw.sbly.service.goods;

import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.web.Dto.goods.GoodsListResponseDto;
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
public class GoodsServiceTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsService goodsService;

    @After
    public void cleanup(){
        goodsRepository.deleteAll();
    }

    @Test
    public void Goods_조회하다(){
        String name = "테스트 상품";
        int price = 10000;
        int mainCategory = 1;
        int subCategory = 1;
        int deliveryTime = 3;

        goodsRepository.save(Goods.builder()
                .name(name)
                .price(price)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .deliveryTime(deliveryTime)
                .build());

        List<GoodsListResponseDto> goodsList = goodsService.findByRAND();

        GoodsListResponseDto goods = goodsList.get(0);
        assertThat(goods.getName()).isEqualTo(name);
        assertThat(goods.getPrice()).isEqualTo(price);
    }

    @Test
    public void Goods_대분류_조회하다(){
        String name = "테스트 상품";
        int price = 10000;
        int mainCategory = 1;
        int subCategory = 1;
        int deliveryTime = 3;

        goodsRepository.save(Goods.builder()
                .name(name)
                .price(price)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .deliveryTime(deliveryTime)
                .build());

        List<GoodsListResponseDto> goodsList = goodsService.findByMainCategory(mainCategory);

        GoodsListResponseDto goods = goodsList.get(0);
        assertThat(goods.getName()).isEqualTo(name);
        assertThat(goods.getPrice()).isEqualTo(price);
    }

    @Test
    public void Goods_대분류_소분류_조회하다(){
        String name = "테스트 상품";
        int price = 10000;
        int mainCategory = 1;
        int subCategory = 1;
        int deliveryTime = 3;

        goodsRepository.save(Goods.builder()
                .name(name)
                .price(price)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .deliveryTime(deliveryTime)
                .build());

        List<GoodsListResponseDto> goodsList = goodsService.findByMainCategoryAndSubCategory(mainCategory, subCategory);

        GoodsListResponseDto goods = goodsList.get(0);
        assertThat(goods.getName()).isEqualTo(name);
        assertThat(goods.getPrice()).isEqualTo(price);
    }
}
