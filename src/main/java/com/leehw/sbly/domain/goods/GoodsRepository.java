package com.leehw.sbly.domain.goods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query(value = "select * from goods d order by RAND() limit 8", nativeQuery = true)
    List<Goods> findAllRAND();

    List<Goods> findByMainCategory(int mainCategory);

    List<Goods> findByMainCategoryAndSubCategory(int mainCategory, int subCategory);
}
