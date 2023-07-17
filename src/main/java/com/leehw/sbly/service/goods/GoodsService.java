package com.leehw.sbly.service.goods;

import com.leehw.sbly.domain.goods.Goods;
import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.web.Dto.goods.GoodsListResponseDto;
import com.leehw.sbly.web.Dto.goods.GoodsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;

    @Transactional(readOnly = true)
    public List<GoodsListResponseDto> findByRAND(){
        return goodsRepository.findAllRAND().stream()
                .map(GoodsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GoodsListResponseDto> findByMainCategory(int mainCategory){
        return goodsRepository.findByMainCategory(mainCategory).stream()
                .map(GoodsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GoodsListResponseDto> findByMainCategoryAndSubCategory(int mainCategory, int subCategory){
        return goodsRepository.findByMainCategoryAndSubCategory(mainCategory, subCategory).stream()
                .map(GoodsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GoodsResponseDto findById(Long id){
        Goods goods = goodsRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 상품이 없습니다. id = " + id));
        return new GoodsResponseDto(goods);
    }
}
