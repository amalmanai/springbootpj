package tn.esprit.manaiamal4cce11.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idReservation;
     LocalDate dateDebut;
     LocalDate dateFin;
    @Enumerated(EnumType.STRING)
     StatutReservation statut;


    @ManyToOne(fetch = FetchType.LAZY)
    Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    Vehicule vehicule;

    @OneToOne(
            mappedBy = "reservation",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    Contrat contrat;


}