package tn.esprit.manaiamal4cce11.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Maintenance;
import tn.esprit.manaiamal4cce11.domain.Vehicule;
import tn.esprit.manaiamal4cce11.repository.IMaintenanceRepository;
import tn.esprit.manaiamal4cce11.repository.IVehiculeRepository;
import tn.esprit.manaiamal4cce11.services.IMaintenanceServices;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServicesImpl implements IMaintenanceServices {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;

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
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Maintenance not found"));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public void deleteById(long idMaintenance) {
        maintenanceRepository.deleteById(idMaintenance);
    }

    // Méthode métier (Atelier 6 - SOLID)
    @Override
    public void planifierMaintenance(Long vehiculeId, LocalDate debut, LocalDate fin) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElse(null);
        if (vehicule != null) {
            Maintenance maintenance = new Maintenance();
            maintenance.setVehicule(vehicule);
            maintenance.setDateDebut(debut);
            maintenance.setDateFin(fin);
            maintenance.setDescription("Maintenance planifiee du " + debut + " au " + fin);
            maintenanceRepository.save(maintenance);
        }
    }
}
