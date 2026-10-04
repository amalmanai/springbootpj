package tn.esprit.manaiamal4cce11.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    @Column(nullable = false, length = 100)
    private String nom;
    @Column(nullable = false, length = 100)
    private String prenom;
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    @Column(length = 20)
    private String telephone;
    @Column(nullable = false, unique = true, length = 30)
    private String numPermis;
    @Column(nullable = false)
    private LocalDate dateInscription;
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations = new ArrayList<>();
}