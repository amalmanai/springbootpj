package tn.esprit.manaiamal4cce11.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Employe;
import tn.esprit.manaiamal4cce11.repository.IEmployeRepository;
import tn.esprit.manaiamal4cce11.services.IEmployeServices;

import java.util.List;

@Service
@RequiredArgsConstructor

public class EmployeServicesImpl implements IEmployeServices {
    private  final IEmployeRepository employeRepository;

    @Override
    public Employe create(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe update(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public void delete(Employe employe) {
        employeRepository.delete(employe);

    }

    @Override
    public Employe findById(long id) {
        return employeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employe not found"));
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public void deleteById(long idEmploye) {
        employeRepository.deleteById(idEmploye);

    }


}
