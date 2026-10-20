package tn.esprit.manaiamal4cce11.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Agence;
import tn.esprit.manaiamal4cce11.repository.IAgenceRepository;
import tn.esprit.manaiamal4cce11.services.IAgenceServices;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AgenceServicesImpl implements IAgenceServices {
    private final IAgenceRepository agenceRepository;
    @Override
    public Agence create(Agence agence) {
        return agenceRepository.save(agence);
    }
    @Override
    public Agence update(Long idAgence, Agence agence) {
        return agenceRepository.save(agence);
    }
    @Override
    public void delete(Long agence) {
        agenceRepository.deleteById(agence);
    }
    @Override
    public Agence findById(long id) {
        return agenceRepository.findById(id).orElseThrow(() -> new RuntimeException("Agence not found"));
    }
    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public void deleteById(Long idAgence) {
        agenceRepository.deleteById(idAgence);
    }

}
