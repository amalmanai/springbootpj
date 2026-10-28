package tn.esprit.manaiamal4cce11.events.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tn.esprit.manaiamal4cce11.events.ContratConfirmeEvent;
import tn.esprit.manaiamal4cce11.services.INotificationService;

/**
 * Observateur en charge de la notification au client lors d'un contrat confirmé.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientNotificationListener {

    private final INotificationService notificationService;

    @EventListener
    public void onContratConfirme(ContratConfirmeEvent event) {
        log.info("[Listener - Client] Notification du client pour le contrat ID: {}", event.getContrat().getIdContrat());
        String message = "Contrat " + event.getContrat().getIdContrat() + " confirmé";
        notificationService.notifier(
                event.getReservation().getClient(),
                event.getCanalNotification(),
                message
        );
    }
}
