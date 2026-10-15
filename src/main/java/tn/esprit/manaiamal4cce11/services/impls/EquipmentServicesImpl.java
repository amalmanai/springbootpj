package tn.esprit.manaiamal4cce11.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Equipement;
import tn.esprit.manaiamal4cce11.repository.IEquipementRepository;
import tn.esprit.manaiamal4cce11.services.IEquipmentServices;

import java.util.List;

@Service
@RequiredArgsConstructor

public class EquipmentServicesImpl implements IEquipmentServices {
    private final IEquipementRepository equipementRepository;

    @Override
    public Equipement create(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement update(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public void delete(Equipement equipement) {
        equipementRepository.delete(equipement);

    }

    @Override
    public Equipement findById(long id) {
        return equipementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Equipement not found"));
    }

    @Override
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }




}
