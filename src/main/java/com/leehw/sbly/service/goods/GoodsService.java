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
            subCategoryList.add(new GoodsSubCategory(4, "가디건"));
        } else if (mainCategory == 2) {
            subCategoryList.add(new GoodsSubCategory(1, "반바지"));
            subCategoryList.add(new GoodsSubCategory(2, "청바지"));
            subCategoryList.add(new GoodsSubCategory(3, "카고바지"));
        } else if (mainCategory == 3) {
            subCategoryList.add(new GoodsSubCategory(1, "운동화"));
            subCategoryList.add(new GoodsSubCategory(2, "구두"));
        } else if (mainCategory == 4) {
            subCategoryList.add(new GoodsSubCategory(1, "백팩"));
            subCategoryList.add(new GoodsSubCategory(2, "크로스백"));
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
        else
            return "";
    }
}
