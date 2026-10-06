package tn.esprit.manaiamal4cce11.domain;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMaintenance;
    LocalDate dateDebut;
    LocalDate dateFin;
    String description;
    @ManyToOne
    Vehicule vehicule;

}