package tn.esprit.manaiamal4cce11.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;

import java.util.Set;

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    public class Vehicule {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long idVehicule;
        String immatriculation;
        String marque;
        String modele;
        @Enumerated(EnumType.STRING)
        CategorieVehicule categorie;
        BigDecimal tarifJournalier;
        @Enumerated(EnumType.STRING)
        StatutVehicule statut;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonBackReference("agence-vehicules")
        Agence agence;
        //@JsonIgnore

        @ManyToMany(fetch = FetchType.LAZY)
        Set<Equipement> equipements = new HashSet<>();

        @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY)
       Set<Reservation> reservations = new HashSet<>();

        @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY)
       Set<Maintenance> maintenances = new HashSet<>();
    }






