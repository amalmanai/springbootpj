package tn.esprit.manaiamal4cce11.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.manaiamal4cce11.domain.Contrat;
import tn.esprit.manaiamal4cce11.domain.ModePaiement;
import tn.esprit.manaiamal4cce11.domain.Paiement;
import tn.esprit.manaiamal4cce11.domain.Reservation;
import tn.esprit.manaiamal4cce11.repository.IContratRepository;
import tn.esprit.manaiamal4cce11.repository.IReservationRepository;
import tn.esprit.manaiamal4cce11.services.location.legacy.SmsGateway;
import tn.esprit.manaiamal4cce11.services.location.legacy.SmtpClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LocationManager {

    private final IReservationRepository reservationRepository;
    private final IContratRepository contratRepository;

    public Contrat confirmerReservation(Long reservationId, String modePaiement,
                                        String codePromo, String canal) {

        Reservation r = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation introuvable"));

        // 1. Vérification de la disponibilité
        verifierDisponibilite(r);

        // 2 & 3. Calcul du montant avec remises
        double m = calculerMontant(r, codePromo);

        // 4. Préparation et calcul du paiement
        Paiement p = new Paiement();
        p.setDatePaiement(LocalDate.now());
        m = determinerModeEtAppliquerFrais(p, modePaiement, m);
        p.setMontant(BigDecimal.valueOf(m));

        // 5. Création et enregistrement du contrat
        Contrat c = new Contrat();
        c.setDateSignature(LocalDate.now());
        c.setMontantTotal(BigDecimal.valueOf(m));
        c.setValide(true);
        c.getPaiements().add(p);
        p.setContrat(c);
        Contrat res = contratRepository.save(c);

        // 6. Envoi de la notification
        envoyerNotification(canal, r, res.getIdContrat());

        return res;
    }

    private void verifierDisponibilite(Reservation r) {
        if (r.getVehicule() == null) {
            throw new IllegalStateException("Vehicule indisponible");
        }
        for (Reservation x : reservationRepository.findAll()) {
            if (isChevauchement(r, x)) {
                throw new IllegalStateException("Vehicule indisponible");
            }
        }
    }

    private boolean isChevauchement(Reservation r, Reservation x) {
        if (x.getVehicule() == null) {
            return false;
        }
        return x.getVehicule().getIdVehicule().equals(r.getVehicule().getIdVehicule())
                && !x.getIdReservation().equals(r.getIdReservation())
                && !(r.getDateFin().isBefore(x.getDateDebut()) || r.getDateDebut().isAfter(x.getDateFin()));
    }

    private double calculerMontant(Reservation r, String codePromo) {
        long j = ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()) + 1;
        double tarifJournalier = (r.getVehicule() != null && r.getVehicule().getTarifJournalier() != null)
                ? r.getVehicule().getTarifJournalier().doubleValue()
                : 0.0;
        double m = j * tarifJournalier;

        m = appliquerRemises(r, m, j);

        if ("BIENVENUE".equals(codePromo)) {
            m -= 20;
        }
        return Math.max(0, m);
    }

    private double appliquerRemises(Reservation r, double m, long j) {
        String t = (r.getClient() != null && r.getClient().getType() != null)
                ? r.getClient().getType()
                : "PARTICULIER";

        if ("PARTICULIER".equals(t)) {
            if (j >= 7) {
                m *= 0.90;
            }
        } else if ("ENTREPRISE".equals(t)) {
            m *= 0.85;
        } else if ("VIP".equals(t)) {
            m *= 0.80;
        }
        return m;
    }

    private double determinerModeEtAppliquerFrais(Paiement p, String modePaiement, double m) {
        if ("CARTE".equalsIgnoreCase(modePaiement)) {
            System.out.println("Debit de la carte bancaire...");
            p.setModePaiement(ModePaiement.CARTE);
            return m * 1.01; // 1 % de frais
        }
        if ("VIREMENT".equalsIgnoreCase(modePaiement)) {
            System.out.println("Attente du virement...");
            p.setModePaiement(ModePaiement.VIREMENT);
            return m;
        }
        if ("ESPECES".equalsIgnoreCase(modePaiement)) {
            System.out.println("Encaissement en especes a l'agence");
            p.setModePaiement(ModePaiement.ESPECES);
            return m;
        }
        throw new IllegalArgumentException("Mode de paiement inconnu : " + modePaiement);
    }

    private void envoyerNotification(String canal, Reservation r, Long contratId) {
        if ("EMAIL".equalsIgnoreCase(canal)) {
            SmtpClient smtp = new SmtpClient("smtp.autoloc.tn", 25);
            smtp.send(r.getClient().getEmail(), "Contrat " + contratId + " confirme");
        } else if ("SMS".equalsIgnoreCase(canal)) {
            SmsGateway sms = new SmsGateway("https://sms.autoloc.tn");
            sms.send(r.getClient().getTelephone(), "Contrat " + contratId + " confirme");
        }
    }
}
