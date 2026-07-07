package com.recuperacion.service.impl;

import com.recuperacion.model.Redemption;
import com.recuperacion.repository.RedemptionRepository;
import com.recuperacion.service.RedemptionService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RedemptionServiceImpl implements RedemptionService {
    private final RedemptionRepository redemptionRepository;

    public RedemptionServiceImpl(RedemptionRepository redemptionRepository) {
        this.redemptionRepository = redemptionRepository;
    }

    @Override
    public List<Redemption> findAll() { return redemptionRepository.findAll(); }

    @Override
    public Redemption findById(Long id) { return redemptionRepository.findById(id).orElseThrow(() -> new RuntimeException("Redención no encontrada")); }

    @Override
    public Redemption save(Redemption entity) { return redemptionRepository.save(entity); }

    @Override
    public void deleteById(Long id) { redemptionRepository.deleteById(id); }
}
