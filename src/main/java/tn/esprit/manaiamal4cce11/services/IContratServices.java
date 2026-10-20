package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Contrat;

import java.util.List;

public interface IContratServices {
    Contrat create (Contrat contrat);
    Contrat update (Long id, Contrat contrat);
    void delete (Contrat contrat);
    Contrat findById (long id);
    List<Contrat> findAll ();

    void deleteById(Long id);


}
