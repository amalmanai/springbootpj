package tn.esprit.manaiamal4cce11.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEmploye;
    String nom;
    String prenom;
    @Enumerated(EnumType.STRING)
    RoleEmploye role;
    @ManyToOne(fetch = FetchType.LAZY)
    Agence agence;
}