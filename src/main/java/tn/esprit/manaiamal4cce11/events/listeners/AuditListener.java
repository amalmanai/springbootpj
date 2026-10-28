package tn.esprit.manaiamal4cce11.events.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tn.esprit.manaiamal4cce11.events.ContratConfirmeEvent;

/**
 * Observateur pour l'Audit et la Conformité :
 * Trace immuable de chaque confirmation de contrat.
 */
@Slf4j
@Component
public class AuditListener {

    @EventListener
    public void onContratConfirme(ContratConfirmeEvent event) {
        log.info("[Listener - Audit & Conformité] Trace audit horodatée [{}] : Contrat ID {} validé.",
                event.getDateConfirmation(),
                event.getContrat().getIdContrat());
        // Enregistrement d'un log de conformité ou audit trail en base
    }
}
