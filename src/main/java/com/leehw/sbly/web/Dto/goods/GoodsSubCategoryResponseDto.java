package com.leehw.sbly.web.Dto.goods;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoodsSubCategoryResponseDto {
    private int subCategory_id;
    private String subCategory_name;

    @Builder
    public GoodsSubCategoryResponseDto(int subCategory_id, String subCategory_name){
        this.subCategory_id = subCategory_id;
        this.subCategory_name = subCategory_name;
    }
}
