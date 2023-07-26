package com.leehw.sbly.web.Dto.goods;

import com.leehw.sbly.domain.goods.Goods;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GoodsResponseDto {
    private Long id;
    private String name;
    private long price;
    private String image;
    private int mainCategory;
    private int subCategory;
    private int deliveryTime;

    @Builder
    public GoodsResponseDto(Goods goods){
        this.id = goods.getId();
        this.name = goods.getName();
        this.price = goods.getPrice();
        this.image = goods.getImage();
        this.mainCategory = goods.getMainCategory();
        this.subCategory = goods.getSubCategory();
        this.deliveryTime = goods.getDeliveryTime();
    }
}
