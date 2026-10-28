package tn.esprit.manaiamal4cce11.events.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tn.esprit.manaiamal4cce11.events.ContratConfirmeEvent;

/**
 * Observateur pour la Direction :
 * Met à jour les tableaux de bord et statistiques suite à la confirmation du contrat.
 */
@Slf4j
@Component
public class StatistiqueListener {

    @EventListener
    public void onContratConfirme(ContratConfirmeEvent event) {
        log.info("[Listener - Direction/Statistiques] Enregistrement métrique pour le contrat ID: {} (Client: {})",
                event.getContrat().getIdContrat(),
                event.getReservation().getClient() != null ? event.getReservation().getClient().getNom() : "Inconnu");
        // Agrégation de données pour la direction générale
    }
}
