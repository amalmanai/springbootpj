package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Client;

import java.util.List;

public interface IClientServices {
    Client create (Client client);
    Client update (Client client);
    void delete (Client client);
    Client findById (long id);
     List<Client> findAll ();
}
