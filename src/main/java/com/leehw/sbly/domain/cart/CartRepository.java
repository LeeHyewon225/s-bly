package com.leehw.sbly.domain.cart;

import com.leehw.sbly.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMember(Member member);
}
