package com.leehw.sbly.service.goods;

import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.web.Dto.goods.GoodsListResponseDto;
import com.leehw.sbly.web.Dto.goods.GoodsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;

    @Transactional(readOnly = true)
    public List<GoodsListResponseDto> findByRAND() {
        return goodsRepository.findAllRAND().stream()
                .map(GoodsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GoodsListResponseDto> findByMainCategory(int mainCategory) {
        return goodsRepository.findByMainCategory(mainCategory).stream()
                .map(GoodsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GoodsListResponseDto> findByMainCategoryAndSubCategory(int mainCategory, int subCategory) {
        return goodsRepository.findByMainCategoryAndSubCategory(mainCategory, subCategory).stream()
                .map(GoodsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GoodsResponseDto findById(Long id) {
        Goods goods = goodsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id = " + id));
        return new GoodsResponseDto(goods);
    }

    @Transactional
    public List<GoodsSubCategory> getSubCategory(int mainCategory) {
        List<GoodsSubCategory> subCategoryList = new ArrayList<>();
        if (mainCategory == 1) {
            subCategoryList.add(new GoodsSubCategory(1, "맨투맨"));
            subCategoryList.add(new GoodsSubCategory(2, "후드티"));
            subCategoryList.add(new GoodsSubCategory(3, "니트"));
            subCategoryList.add(new GoodsSubCategory(4, "셔츠"));
        } else if (mainCategory == 2) {
            subCategoryList.add(new GoodsSubCategory(1, "롱팬츠"));
            subCategoryList.add(new GoodsSubCategory(2, "숏팬츠"));
            subCategoryList.add(new GoodsSubCategory(3, "청바지"));
            subCategoryList.add(new GoodsSubCategory(4, "슬랙스"));
        } else if (mainCategory == 3) {
            subCategoryList.add(new GoodsSubCategory(1, "운동화"));
            subCategoryList.add(new GoodsSubCategory(2, "샌들"));
            subCategoryList.add(new GoodsSubCategory(3, "스니커즈"));
            subCategoryList.add(new GoodsSubCategory(4, "슬리퍼"));
            subCategoryList.add(new GoodsSubCategory(5, "워커"));
        } else if (mainCategory == 4) {
            subCategoryList.add(new GoodsSubCategory(1, "백팩"));
            subCategoryList.add(new GoodsSubCategory(2, "크로스백"));
            subCategoryList.add(new GoodsSubCategory(3, "에코백"));
            subCategoryList.add(new GoodsSubCategory(4, "클러치"));
            subCategoryList.add(new GoodsSubCategory(5, "숄더백"));
        } else if (mainCategory == 5) {
            subCategoryList.add(new GoodsSubCategory(1, "모자"));
            subCategoryList.add(new GoodsSubCategory(2, "양말"));
            subCategoryList.add(new GoodsSubCategory(3, "시계"));
            subCategoryList.add(new GoodsSubCategory(4, "장갑"));
        }
        return subCategoryList;
    }

    @Transactional
    public String getMainCategoryName(int mainCategory) {
        if (mainCategory == 1)
            return "상의";
        else if (mainCategory == 2)
            return "하의";
        else if (mainCategory == 3)
            return "신발";
        else if (mainCategory == 4)
            return "가방";
        else if (mainCategory == 5)
            return "패션 소품";
        else
            return "";
    }
}
