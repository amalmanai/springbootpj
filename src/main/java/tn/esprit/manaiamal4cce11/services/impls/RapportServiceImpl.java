package tn.esprit.manaiamal4cce11.services.impls;

import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.services.IRapportService;

import java.nio.charset.StandardCharsets;
@Service
public class RapportServiceImpl implements IRapportService {

    @Override
    public byte[] genererRapportMensuel(int mois, int annee) {
        String rapport = "Rapport mensuel des locations - " + mois + "/" + annee;
        return rapport.getBytes(StandardCharsets.UTF_8);
    }
}
