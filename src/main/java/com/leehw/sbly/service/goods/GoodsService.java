package com.leehw.sbly.service.goods;

import com.leehw.sbly.domain.goods.GoodsRepository;
import com.leehw.sbly.web.Dto.goods.GoodsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GoodsService {

    private GoodsRepository goodsRepository;

    @Transactional
    public List<GoodsListResponseDto> findByRAND(){
        return goodsRepository.findAllRAND().stream()
                .map(GoodsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GoodsListResponseDto> findByMainCategory(int mainCategory){
        return goodsRepository.findByMainCategory(mainCategory).stream()
                .map(GoodsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GoodsListResponseDto> findByMainCategoryAndSubCategory(int mainCategory, int subCategory){
        return goodsRepository.findByMainCategoryAndSubCategory(mainCategory, subCategory).stream()
                .map(GoodsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
