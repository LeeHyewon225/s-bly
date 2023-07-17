package com.leehw.sbly.web.Dto.goods;

import com.leehw.sbly.domain.goods.Goods;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GoodsResponseDto {
    private String name;
    private int price;
    private String image;
    private int mainCategory;
    private int subCategory;
    private int deliveryTime;

    @Builder
    public GoodsResponseDto(Goods goods){
        this.name = goods.getName();
        this.price = goods.getPrice();
        this.image = goods.getImage();
        this.mainCategory = goods.getMainCategory();
        this.subCategory = goods.getSubCategory();
        this.deliveryTime = goods.getDeliveryTime();
    }
}
