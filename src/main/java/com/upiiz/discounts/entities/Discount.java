package com.upiiz.discounts.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_descuento")
    private String discountCode;

    @Column(name = "monto_descuento")
    private Double discountAmount;

    @Column(name = "valido_hasta")
    private LocalDate validUntil;

    public void setDiscountId(Long id) {
        this.id = id;
    }
}