package tn.esprit.manaiamal4cce11.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Contrat;
import tn.esprit.manaiamal4cce11.repository.IContratRepository;
import tn.esprit.manaiamal4cce11.services.IContratServices;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServicesImpl implements IContratServices {
    private final IContratRepository contratRepository;
    @Override
    public Contrat create(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public void delete(Contrat contrat) {
        contratRepository.delete(contrat);
    }

    @Override
    public Contrat findById(long id) {
        return contratRepository.findById(id).orElseThrow(() -> new RuntimeException("Contrat not found"));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    public void deleteById(Long idContrat) {
       contratRepository.deleteById(idContrat);
    }
}
