package com.leehw.sbly.service.goods;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoodsSubCategory {
    private int subCategory;
    private String subCategory_name;

    @Builder
    public GoodsSubCategory(int subCategory, String subCategory_name){
        this.subCategory = subCategory;
        this.subCategory_name = subCategory_name;
    }
}
