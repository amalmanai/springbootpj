package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Client;

public interface INotificationService {
    void notifier(Client client, String canal, String message);
}
