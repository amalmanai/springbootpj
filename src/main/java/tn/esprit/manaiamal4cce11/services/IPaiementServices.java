package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Paiement;

import java.util.List;

public interface IPaiementServices {
   Paiement create(Paiement  paiement );
    Paiement update(Paiement  paiement );
    void delete(Paiement  paiement );
    Paiement findById(long id);
    List<Paiement> findAll();

}
