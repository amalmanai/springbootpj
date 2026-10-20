package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Agence;

import java.util.List;

public interface IAgenceServices {
    Agence create (Agence agence);
    Agence update (Long idAgence, Agence agence);
    void delete (Long agence);
    Agence findById (long id);
    List<Agence> findAll ();

    void deleteById(Long idAgence);
}
