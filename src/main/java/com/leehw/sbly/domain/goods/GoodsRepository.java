package com.leehw.sbly.domain.goods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query(value = "select * from Goods d order by RAND() limit 5", nativeQuery = true)
    List<Goods> findAllRAND();

    List<Goods> findByMainCategory(int mainCategory);

    List<Goods> findByMainCategoryAndSubCategory(int mainCategory, int subCategory);
}
