package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Reservation;

import java.util.List;

public interface IReservationServices {

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    void delete(Reservation reservation);

    Reservation findById(long id);

    List<Reservation> findAll();

}
