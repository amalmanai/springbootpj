package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Vehicule;

import java.util.List;

public interface IVehiculeServices {

   Vehicule create(Vehicule vehicule);
   Vehicule update(Vehicule vehicule);
   void delete(Vehicule vehicule);
   Vehicule findById(long id);
   List<Vehicule> findAll();

    void deleteById(Long idVehicule);

    Vehicule update(Long idVehicule, Vehicule vehicule);
}
