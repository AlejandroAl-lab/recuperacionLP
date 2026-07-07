package com.recuperacion.controller;

import com.recuperacion.model.Redemption;
import com.recuperacion.service.RedemptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/redemptions")
public class RedemptionController {
    private final RedemptionService redemptionService;

    public RedemptionController(RedemptionService redemptionService) { this.redemptionService = redemptionService; }

    @GetMapping
    public List<Redemption> getAll() { return redemptionService.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Redemption> getById(@PathVariable Long id) { return ResponseEntity.ok(redemptionService.findById(id)); }

    @PostMapping
    public Redemption create(@RequestBody Redemption redemption) { return redemptionService.save(redemption); }

    @PutMapping("/{id}")
    public Redemption update(@PathVariable Long id, @RequestBody Redemption redemption) {
        redemption.setId(id);
        return redemptionService.save(redemption);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        redemptionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
