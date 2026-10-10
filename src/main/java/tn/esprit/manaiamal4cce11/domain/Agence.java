package tn.esprit.manaiamal4cce11.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idAgence;
    String nom;
    String ville;
    String adresse;
    String telephone;

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    Set <Vehicule> vehicules = new HashSet<>();

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    Set <Employe> employes = new HashSet<>();

}