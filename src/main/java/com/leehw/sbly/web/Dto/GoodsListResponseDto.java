package com.leehw.sbly.web.Dto;

import com.leehw.sbly.domain.goods.Goods;
import lombok.Getter;

@Getter
public class GoodsListResponseDto {
    private long id;
    private String name;
    private int price;
    private String image;

    public GoodsListResponseDto(Goods entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.image = entity.getImage();
    }
}
