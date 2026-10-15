package tn.esprit.manaiamal4cce11.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Vehicule;
import tn.esprit.manaiamal4cce11.repository.IVehiculeRepository;
import tn.esprit.manaiamal4cce11.services.IVehiculeServices;

import java.util.List;
@Service
@RequiredArgsConstructor

public class VehiculeServicesImpl implements IVehiculeServices {

    private final IVehiculeRepository vehiculeRepository;
    @Override
    public Vehicule create(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);

    }

    @Override
    public void delete(Vehicule vehicule) {
     vehiculeRepository.delete(vehicule);
    }

    @Override
    public Vehicule findById(long id) {
        return vehiculeRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(" vehicule not found "));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }
}
