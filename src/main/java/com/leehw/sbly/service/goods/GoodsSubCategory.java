package com.leehw.sbly.service.goods;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoodsSubCategory {
    private int subCategory_id;
    private String subCategory_name;

    @Builder
    public GoodsSubCategory(int subCategory_id, String subCategory_name){
        this.subCategory_id = subCategory_id;
        this.subCategory_name = subCategory_name;
    }
}
