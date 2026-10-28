package tn.esprit.manaiamal4cce11.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.manaiamal4cce11.domain.Contrat;
import tn.esprit.manaiamal4cce11.domain.ModePaiement;
import tn.esprit.manaiamal4cce11.domain.Reservation;
import tn.esprit.manaiamal4cce11.events.ContratConfirmeEvent;
import tn.esprit.manaiamal4cce11.repository.IReservationRepository;
import tn.esprit.manaiamal4cce11.services.IConfirmationReservationService;
import tn.esprit.manaiamal4cce11.services.IContratServices;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmationReservationServiceImpl implements IConfirmationReservationService {

    private final IReservationRepository reservationRepository;
    private final IContratServices contratService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Contrat confirmer(Long reservationId, ModePaiement mode, String codePromo, String canal) {
        Reservation r = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation introuvable : " + reservationId));

        // 1. Logique métier de validation & création de contrat
        Contrat c = new Contrat();
        c.setDateSignature(LocalDate.now());
        c.setValide(true);
        c.setReservation(r);
        c.setMontantTotal(BigDecimal.valueOf(150.0));

        Contrat cree = contratService.create(c);

        // 2. Publication de l'événement (Patron Observer)
        // La méthode n'a plus à connaître aucun de ses destinataires (client, comptable, audit, stats...) !
        eventPublisher.publishEvent(new ContratConfirmeEvent(cree, r, canal));
        log.info("Événement ContratConfirmeEvent publié avec succès pour le contrat ID {}", cree.getIdContrat());

        return cree;
    }
}
