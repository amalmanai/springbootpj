package tn.esprit.manaiamal4cce11.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Contrat;
import tn.esprit.manaiamal4cce11.domain.ModePaiement;
import tn.esprit.manaiamal4cce11.domain.Reservation;
import tn.esprit.manaiamal4cce11.repository.IReservationRepository;
import tn.esprit.manaiamal4cce11.services.IReservationServices;
import tn.esprit.manaiamal4cce11.services.LocationManager;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServicesImpl implements IReservationServices {

    private final IReservationRepository reservationRepository;
    private final LocationManager locationManager;

    @Override
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation update(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    @Override
    public Reservation findById(long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteById(Long idReservation) {
        reservationRepository.deleteById(idReservation);
    }

    // Méthodes métier (Atelier 6 - SOLID)
    @Override
    public Contrat confirmer(Long reservationId, ModePaiement mode, String codePromo, String canal) {
        return locationManager.confirmerReservation(reservationId, mode.name(), codePromo, canal);
    }

    @Override
    public void annuler(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public double calculerMontant(Reservation reservation, String codePromo) {
        long j = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin()) + 1;
        double tarif = (reservation.getVehicule() != null && reservation.getVehicule().getTarifJournalier() != null)
                ? reservation.getVehicule().getTarifJournalier().doubleValue()
                : 0.0;
        double m = j * tarif;
        String t = (reservation.getClient() != null && reservation.getClient().getType() != null)
                ? reservation.getClient().getType()
                : "PARTICULIER";
        if ("PARTICULIER".equals(t)) {
            if (j >= 7) m *= 0.90;
        } else if ("ENTREPRISE".equals(t)) {
            m *= 0.85;
        } else if ("VIP".equals(t)) {
            m *= 0.80;
        }
        if ("BIENVENUE".equals(codePromo)) {
            m -= 20;
        }
        return Math.max(0, m);
    }

    @Override
    public boolean estDisponible(Reservation reservation) {
        if (reservation.getVehicule() == null) return false;
        for (Reservation x : reservationRepository.findAll()) {
            if (x.getVehicule().getIdVehicule().equals(reservation.getVehicule().getIdVehicule())
                    && !x.getIdReservation().equals(reservation.getIdReservation())
                    && !(reservation.getDateFin().isBefore(x.getDateDebut())
                    || reservation.getDateDebut().isAfter(x.getDateFin()))) {
                return false;
            }
        }
        return true;
    }
}
