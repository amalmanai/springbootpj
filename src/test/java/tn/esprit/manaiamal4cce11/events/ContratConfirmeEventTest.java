package tn.esprit.manaiamal4cce11.events;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tn.esprit.manaiamal4cce11.domain.Client;
import tn.esprit.manaiamal4cce11.domain.Contrat;
import tn.esprit.manaiamal4cce11.domain.Reservation;
import tn.esprit.manaiamal4cce11.events.listeners.ClientNotificationListener;
import tn.esprit.manaiamal4cce11.services.INotificationService;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

class ContratConfirmeEventTest {

    @Test
    void testListenerReactsToEvent() {
        AtomicBoolean notified = new AtomicBoolean(false);

        // Simulation de INotificationService
        INotificationService testNotificationService = (client, canal, message) -> {
            if ("EMAIL".equals(canal) && message.contains("100")) {
                notified.set(true);
            }
        };

        ClientNotificationListener listener = new ClientNotificationListener(testNotificationService);

        Client client = new Client();
        client.setIdClient(1L);
        client.setNom("Ben Salah");
        client.setEmail("bensalah@example.tn");

        Reservation reservation = new Reservation();
        reservation.setIdReservation(10L);
        reservation.setClient(client);

        Contrat contrat = new Contrat();
        contrat.setIdContrat(100L);
        contrat.setMontantTotal(BigDecimal.valueOf(250.0));

        ContratConfirmeEvent event = new ContratConfirmeEvent(contrat, reservation, "EMAIL");

        // Déclenchement de l'écouteur
        listener.onContratConfirme(event);

        Assertions.assertTrue(notified.get(), "Le listener doit avoir appelé le service de notification");
        Assertions.assertEquals(100L, event.getContrat().getIdContrat());
        Assertions.assertNotNull(event.getDateConfirmation());
    }
}
