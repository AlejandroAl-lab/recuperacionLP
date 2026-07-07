package com.recuperacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "redemptions")
public class Redemption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promo_id", nullable = false)
    private Promo promo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Boolean redeemed;
}
