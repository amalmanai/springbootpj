package tn.esprit.manaiamal4cce11.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Paiement;
import tn.esprit.manaiamal4cce11.repository.IPaiementRepository;
import tn.esprit.manaiamal4cce11.services.IPaiementServices;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServicesImpl implements IPaiementServices {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement create(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement update(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public void delete(Paiement paiement) {
      paiementRepository.delete(paiement);

    }

    @Override
    public Paiement findById(long id) {
        return paiementRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paiement not found"));
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }


    public void deleteById(Long idPaiement) {
     paiementRepository.deleteById(idPaiement);
    }
}

