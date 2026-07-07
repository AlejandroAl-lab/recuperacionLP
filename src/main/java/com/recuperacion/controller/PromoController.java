package com.recuperacion.controller;

import com.recuperacion.model.Promo;
import com.recuperacion.service.PromoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/promos")
public class PromoController {
    private final PromoService promoService;

    public PromoController(PromoService promoService) { this.promoService = promoService; }

    @GetMapping
    public List<Promo> getAll() { return promoService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Promo> getById(@PathVariable Long id) { return ResponseEntity.ok(promoService.findById(id)); }

    @PostMapping
    public Promo create(@RequestBody Promo promo) { return promoService.save(promo); }

    @PutMapping("/{id}")
    public Promo update(@PathVariable Long id, @RequestBody Promo promo) {
        promo.setId(id);
        return promoService.save(promo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        promoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}