package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Maintenance;

import java.util.List;

public interface IMaintenanceServices {
    Maintenance create(Maintenance maintenance);
    Maintenance update(Maintenance maintenance);
    void delete(Maintenance maintenance);
    Maintenance findById(long id);
    List<Maintenance> findAll();


    void deleteById(long id);
}
