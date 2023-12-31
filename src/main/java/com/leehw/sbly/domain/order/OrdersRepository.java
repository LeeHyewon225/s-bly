package com.leehw.sbly.domain.order;

import com.leehw.sbly.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByMemberAndCancelOrderOrderByCreatedDateDesc(Member member, boolean cancelOrder);
    List<Orders> findByMemberAndCancelOrderOrderByModifiedDateDesc(Member member, boolean cancelOrder);
}
