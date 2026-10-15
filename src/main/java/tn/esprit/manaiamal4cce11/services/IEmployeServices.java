package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Employe;

import java.util.List;

public interface IEmployeServices {
    Employe create(Employe employe);
    Employe update(Employe employe);
    void delete(Employe employe);
    Employe findById(long id);
    List<Employe> findAll();
}
