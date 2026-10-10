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
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long idEquipement;
    String libelle;
    @ManyToMany(mappedBy = "equipements", fetch = FetchType.LAZY)
    Set<Vehicule> vehicules = new HashSet<>();

}