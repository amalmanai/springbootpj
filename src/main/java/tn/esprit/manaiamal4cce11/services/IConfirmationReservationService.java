package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Contrat;
import tn.esprit.manaiamal4cce11.domain.ModePaiement;

public interface IConfirmationReservationService {
    Contrat confirmer(Long reservationId, ModePaiement mode, String codePromo, String canal);
}
