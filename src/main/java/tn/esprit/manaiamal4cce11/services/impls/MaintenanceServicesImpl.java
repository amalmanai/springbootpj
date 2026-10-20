package tn.esprit.manaiamal4cce11.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Maintenance;
import tn.esprit.manaiamal4cce11.repository.IMaintenanceRepository;
import tn.esprit.manaiamal4cce11.services.IMaintenanceServices;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MaintenanceServicesImpl implements IMaintenanceServices {
    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance update(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public void delete(Maintenance maintenance) {
        maintenanceRepository.delete(maintenance);
    }

    @Override
    public Maintenance findById(long id) {
        return maintenanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Maintenance not found"));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public void deleteById(long idMaintenance) {
        maintenanceRepository.deleteById(idMaintenance);

    }

}