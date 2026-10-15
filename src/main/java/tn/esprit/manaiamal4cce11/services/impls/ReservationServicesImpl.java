package tn.esprit.manaiamal4cce11.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Reservation;
import tn.esprit.manaiamal4cce11.repository.IReservationRepository;
import java.util.List;
import tn.esprit.manaiamal4cce11.services.IReservationServices;

@Service
@RequiredArgsConstructor
public class ReservationServicesImpl implements IReservationServices {
    private final IReservationRepository reservationRepository;

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
        return reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

}