package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Contrat;
import tn.esprit.manaiamal4cce11.domain.ModePaiement;
import tn.esprit.manaiamal4cce11.domain.Reservation;

import java.util.List;

public interface IReservationServices {

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    void delete(Reservation reservation);

    Reservation findById(long id);

    List<Reservation> findAll();

    void deleteById(Long idReservation);

    // Méthodes métier (Atelier 6 - SOLID)
    Contrat confirmer(Long reservationId, ModePaiement mode, String codePromo, String canal);

    void annuler(Long reservationId);

    double calculerMontant(Reservation reservation, String codePromo);

    boolean estDisponible(Reservation reservation);
}
