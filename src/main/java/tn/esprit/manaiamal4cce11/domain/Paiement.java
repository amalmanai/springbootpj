package tn.esprit.manaiamal4cce11.domain;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPaiement;
    BigDecimal montant;
     LocalDate datePaiement;
    @Enumerated(EnumType.STRING)
     ModePaiement modePaiement;
    @ManyToOne(fetch = FetchType.LAZY)
    Contrat contrat;
}