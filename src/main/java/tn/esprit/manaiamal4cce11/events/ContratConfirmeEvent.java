package tn.esprit.manaiamal4cce11.events;

import lombok.Getter;
import tn.esprit.manaiamal4cce11.domain.Contrat;
import tn.esprit.manaiamal4cce11.domain.Reservation;

import java.time.LocalDateTime;

/**
 * Événement métier publié lorsqu'un contrat de location est confirmé.
 * Transporteur de données immuable (State Transfer Object) pour les observateurs.
 */
@Getter
public class ContratConfirmeEvent {

    private final Contrat contrat;
    private final Reservation reservation;
    private final String canalNotification;
    private final LocalDateTime dateConfirmation;

    public ContratConfirmeEvent(Contrat contrat, Reservation reservation, String canalNotification) {
        this.contrat = contrat;
        this.reservation = reservation;
        this.canalNotification = canalNotification;
        this.dateConfirmation = LocalDateTime.now();
    }
}
