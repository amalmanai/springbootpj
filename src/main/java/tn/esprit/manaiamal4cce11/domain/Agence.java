package tn.esprit.manaiamal4cce11.domain;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    //@JsonIgnore

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @JsonManagedReference("agence-vehicules")
    Set<Vehicule> vehicules = new HashSet<>();
  //@JsonIgnore

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    Set <Employe> employes = new HashSet<>();

}