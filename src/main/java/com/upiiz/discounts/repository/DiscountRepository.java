package com.upiiz.discounts.repository;

import com.upiiz.discounts.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}