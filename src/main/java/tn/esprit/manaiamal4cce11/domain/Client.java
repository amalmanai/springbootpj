package tn.esprit.manaiamal4cce11.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idClient;
    String nom;
    String prenom;
    String email;
    String telephone;
    String numPermis;
    LocalDate dateInscription;
    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    Set<Reservation> reservations = new HashSet<>();

}