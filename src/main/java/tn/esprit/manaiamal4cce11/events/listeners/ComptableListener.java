package tn.esprit.manaiamal4cce11.events.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tn.esprit.manaiamal4cce11.events.ContratConfirmeEvent;

/**
 * Observateur en charge de la comptabilité :
 * Dès qu'un contrat est confirmé, le comptable est prévenu et la pièce comptable est initiée.
 */
@Slf4j
@Component
public class ComptableListener {

    @EventListener
    public void onContratConfirme(ContratConfirmeEvent event) {
        log.info("[Listener - Comptabilité] Contrat confirmé reçu (ID: {}). Montant: {} TND. Émission de l'écriture comptable...",
                event.getContrat().getIdContrat(),
                event.getContrat().getMontantTotal());
        // Traitement métier comptable autonome
    }
}
