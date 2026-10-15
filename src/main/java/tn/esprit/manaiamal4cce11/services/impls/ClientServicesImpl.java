package tn.esprit.manaiamal4cce11.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Client;
import tn.esprit.manaiamal4cce11.repository.IClientRepository;
import tn.esprit.manaiamal4cce11.services.IClientServices;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServicesImpl implements IClientServices {
    private final IClientRepository clientRepository;
    @Override
    public Client create(Client client) {
        return clientRepository.save(client);
    }
    @Override
    public Client update(Client client) {
        return clientRepository.save(client);
    }
    @Override
    public void delete(Client client) {
        clientRepository.delete(client);

    }
    @Override
    public Client findById(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }
    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

}
