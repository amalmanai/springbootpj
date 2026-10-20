package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Equipement;

import java.util.List;

public interface IEquipmentServices {
    Equipement create(Equipement equipement);
    Equipement update(Equipement equipement);
    void delete(Equipement equipement);
    Equipement findById(long id);
    List<Equipement> findAll();

    void deleteById(long id);
}
