package com.recuperacion.service.impl;

import com.recuperacion.model.Promo;
import com.recuperacion.repository.PromoRepository;
import com.recuperacion.service.PromoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PromoServiceImpl implements PromoService {
    private final PromoRepository promoRepository;

    public PromoServiceImpl(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    @Override
    public List<Promo> findAll() { return promoRepository.findAll(); }

    @Override
    public Promo findById(Long id) { return promoRepository.findById(id).orElseThrow(() -> new RuntimeException("Promoción no encontrada")); }

    @Override
    public Promo save(Promo entity) { return promoRepository.save(entity); }

    @Override
    public void deleteById(Long id) { promoRepository.deleteById(id); }
}