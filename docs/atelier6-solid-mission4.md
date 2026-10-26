# Atelier 6 — Du code spaghetti au Clean Code : appliquer les principes SOLID

## Mission 4 : Les trois écrans de l'agence

---

### 1. Analyse du Problème Initial (Pourquoi les trois équipes sont affectées)

L'agence utilise une interface unique et volumineuse nommée `IGestionLocationService` pour trois écrans totalement distincts :
- **Écran 1 (Guichet / Réservation)** : confirmation, annulation, calcul de montant, disponibilité.
- **Écran 2 (Direction / Comptabilité)** : génération de rapports mensuels.
- **Écran 3 (Atelier / Parc automobile)** : planification de la maintenance des véhicules.

#### Code initial (problématique) :
```java
public interface IGestionLocationService {
    Contrat confirmer(Long reservationId, ModePaiement mode, String codePromo, String canal);
    void annuler(Long reservationId);
    double calculerMontant(Reservation r, String codePromo);
    boolean estDisponible(Reservation r);
    byte[] genererRapportMensuel(int mois, int annee);
    void planifierMaintenance(Long vehiculeId, LocalDate debut, LocalDate fin);
}
```

#### Causes de l'impact croisé et de l'obligation de tout retester :
1. **Interface monolithique (« Fat Interface »)** : L'interface mélange des contrats de services destinés à des acteurs métiers hétérogènes.
2. **Couplage fort et recompilation en cascade** : En Java, toute modification de signature (ex: ajouter un filtre ou un format dans `genererRapportMensuel`, ou modifier les paramètres de `planifierMaintenance`) altère le fichier `IGestionLocationService.class`. Toutes les classes clientes dépendant de cette interface doivent être recompilées.
3. **Pollution d'interface & tests de régression forcés** : Les 3 équipes dépendent de méthodes dont elles n'ont que faire. Lorsqu'une équipe livre une modification, les deux autres équipes n'ont aucune garantie formelle d'isolation et sont contraintes de revalider et retester intégralité de leurs écrans.
4. **Implémentations et mocks artificiels** : Si un développeur veut tester unitairement l'écran de réservation ou fournir un mock, il est forcé de fournir des implémentations fictives (`return null;` ou `throw new UnsupportedOperationException()`) pour la maintenance et les rapports.

---

### 2. Le Principe de Conception Appliqué

- **Nom du principe** : **ISP — Interface Segregation Principle** (Principe de Ségrégation des Interfaces), le **I** des principes **SOLID**.
- **Définition dans nos propres mots** : 
  > *« Aucun client ne doit être contraint de dépendre d'interfaces ou de méthodes qu'il n'utilise pas. »*  
  Plutôt qu'une interface obèse et généraliste à tout faire, le système doit exposer plusieurs petites interfaces spécialisées (« role interfaces »), taillées sur mesure pour chaque besoin client.

- **Explication & mise en évidence** :
  - **Avant :** `LocationManager` et `IGestionLocationService` concentraient toute l'activité de l'agence. Une modification demandée par l'atelier mécanique (maintenance) pouvait casser l'écran des réservations au comptoir.
  - **Après :** En appliquant ISP, l'interface est scindée en 3 interfaces hautement cohésives : `IReservationService`, `IRapportService` et `IMaintenanceService`. Chaque contrôleur ou écran n'injecte que l'interface qui le concerne directement. Les modifications apportées à la maintenance n'ont aucun impact structurel, binaire ou fonctionnel sur la réservation ou la comptabilité.

---

### 3. Schéma de la Solution

```mermaid
classDiagram
    direction TB

    note "AVANT : Violation d'ISP (Fat Interface)" as N1
    class IGestionLocationService {
        <<interface>>
        +confirmer(...)
        +annuler(...)
        +calculerMontant(...)
        +estDisponible(...)
        +genererRapportMensuel(...)
        +planifierMaintenance(...)
    }
    class EcranReservation
    class EcranRapports
    class EcranMaintenance

    EcranReservation ..> IGestionLocationService : dépend de tout
    EcranRapports ..> IGestionLocationService : dépend de tout
    EcranMaintenance ..> IGestionLocationService : dépend de tout

    note "APRÈS : Respect d'ISP (Interfaces ségréguées)" as N2
    class IReservationService {
        <<interface>>
        +confirmer(...)
        +annuler(...)
        +calculerMontant(...)
        +estDisponible(...)
    }
    class IRapportService {
        <<interface>>
        +genererRapportMensuel(...)
    }
    class IMaintenanceService {
        <<interface>>
        +planifierMaintenance(...)
    }

    class ReservationController
    class RapportController
    class MaintenanceController

    class ReservationServiceImpl {
        +confirmer(...)
        +annuler(...)
        +calculerMontant(...)
        +estDisponible(...)
    }
    class RapportServiceImpl {
        +genererRapportMensuel(...)
    }
    class MaintenanceServiceImpl {
        +planifierMaintenance(...)
    }

    ReservationController ..> IReservationService : injecte
    RapportController ..> IRapportService : injecte
    MaintenanceController ..> IMaintenanceService : injecte

    ReservationServiceImpl ..|> IReservationService : implémente
    RapportServiceImpl ..|> IRapportService : implémente
    MaintenanceServiceImpl ..|> IMaintenanceService : implémente
```

---

### 4. Proposition de Code

#### 4.1. Interfaces ségréguées (`services`)

##### `IReservationService.java`
```java
package tn.esprit.manaiamal4cce11.services;

import tn.esprit.manaiamal4cce11.domain.Contrat;
import tn.esprit.manaiamal4cce11.domain.ModePaiement;
import tn.esprit.manaiamal4cce11.domain.Reservation;

public interface IReservationService {

    Contrat confirmer(Long reservationId,
                      ModePaiement mode,
                      String codePromo,
                      String canal);

    void annuler(Long reservationId);

    double calculerMontant(Reservation reservation,
                           String codePromo);

    boolean estDisponible(Reservation reservation);
}
```

##### `IRapportService.java`
```java
package tn.esprit.manaiamal4cce11.services;

public interface IRapportService {

    byte[] genererRapportMensuel(int mois, int annee);
}
```

##### `IMaintenanceService.java`
```java
package tn.esprit.manaiamal4cce11.services;

import java.time.LocalDate;

public interface IMaintenanceService {

    void planifierMaintenance(Long vehiculeId,
                              LocalDate debut,
                              LocalDate fin);
}
```

---

#### 4.2. Implémentations découplées (`services.impls`)

##### `ReservationServiceImpl.java`
```java
package tn.esprit.manaiamal4cce11.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Contrat;
import tn.esprit.manaiamal4cce11.domain.ModePaiement;
import tn.esprit.manaiamal4cce11.domain.Reservation;
import tn.esprit.manaiamal4cce11.repository.IReservationRepository;
import tn.esprit.manaiamal4cce11.services.IReservationService;
import tn.esprit.manaiamal4cce11.services.LocationManager;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final LocationManager locationManager;

    @Override
    public Contrat confirmer(Long reservationId, ModePaiement mode, String codePromo, String canal) {
        return locationManager.confirmerReservation(reservationId, mode.name(), codePromo, canal);
    }

    @Override
    public void annuler(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public double calculerMontant(Reservation reservation, String codePromo) {
        long j = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin()) + 1;
        double tarif = (reservation.getVehicule() != null && reservation.getVehicule().getTarifJournalier() != null)
                ? reservation.getVehicule().getTarifJournalier().doubleValue()
                : 0.0;
        double m = j * tarif;
        String t = (reservation.getClient() != null && reservation.getClient().getType() != null)
                ? reservation.getClient().getType()
                : "PARTICULIER";
        if ("PARTICULIER".equals(t)) {
            if (j >= 7) m *= 0.90;
        } else if ("ENTREPRISE".equals(t)) {
            m *= 0.85;
        } else if ("VIP".equals(t)) {
            m *= 0.80;
        }
        if ("BIENVENUE".equals(codePromo)) {
            m -= 20;
        }
        return Math.max(0, m);
    }

    @Override
    public boolean estDisponible(Reservation reservation) {
        if (reservation.getVehicule() == null) return false;
        for (Reservation x : reservationRepository.findAll()) {
            if (x.getVehicule().getIdVehicule().equals(reservation.getVehicule().getIdVehicule())
                    && !x.getIdReservation().equals(reservation.getIdReservation())
                    && !(reservation.getDateFin().isBefore(x.getDateDebut())
                    || reservation.getDateDebut().isAfter(x.getDateFin()))) {
                return false;
            }
        }
        return true;
    }
}
```

##### `RapportServiceImpl.java`
```java
package tn.esprit.manaiamal4cce11.services.impls;

import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.services.IRapportService;

import java.nio.charset.StandardCharsets;

@Service
public class RapportServiceImpl implements IRapportService {

    @Override
    public byte[] genererRapportMensuel(int mois, int annee) {
        String rapport = "Rapport mensuel des locations - " + mois + "/" + annee;
        return rapport.getBytes(StandardCharsets.UTF_8);
    }
}
```

##### `MaintenanceServiceImpl.java`
```java
package tn.esprit.manaiamal4cce11.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.manaiamal4cce11.domain.Maintenance;
import tn.esprit.manaiamal4cce11.domain.Vehicule;
import tn.esprit.manaiamal4cce11.repository.IMaintenanceRepository;
import tn.esprit.manaiamal4cce11.repository.IVehiculeRepository;
import tn.esprit.manaiamal4cce11.services.IMaintenanceService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public void planifierMaintenance(Long vehiculeId, LocalDate debut, LocalDate fin) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElse(null);
        if (vehicule != null) {
            Maintenance maintenance = new Maintenance();
            maintenance.setVehicule(vehicule);
            maintenance.setDateDebut(debut);
            maintenance.setDateFin(fin);
            maintenance.setDescription("Maintenance planifiee du " + debut + " au " + fin);
            maintenanceRepository.save(maintenance);
        }
    }
}
```

---

#### 4.3. Contrôleurs / Écrans clients démontrant l'isolation

##### `ReservationScreenController.java`
```java
@RestController
@RequestMapping("/api/ecran-reservation")
@RequiredArgsConstructor
public class ReservationScreenController {

    // N'injecte QUE l'interface de réservation
    private final IReservationService reservationService;

    @PostMapping("/confirmer/{id}")
    public ResponseEntity<Contrat> confirmer(@PathVariable Long id,
                                             @RequestParam ModePaiement mode,
                                             @RequestParam(required = false) String codePromo,
                                             @RequestParam String canal) {
        return ResponseEntity.ok(reservationService.confirmer(id, mode, codePromo, canal));
    }
}
```

##### `RapportScreenController.java`
```java
@RestController
@RequestMapping("/api/ecran-rapports")
@RequiredArgsConstructor
public class RapportScreenController {

    // N'injecte QUE l'interface des rapports
    private final IRapportService rapportService;

    @GetMapping("/mensuel")
    public ResponseEntity<byte[]> getRapportMensuel(@RequestParam int mois, @RequestParam int annee) {
        return ResponseEntity.ok(rapportService.genererRapportMensuel(mois, annee));
    }
}
```

##### `MaintenanceScreenController.java`
```java
@RestController
@RequestMapping("/api/ecran-maintenance")
@RequiredArgsConstructor
public class MaintenanceScreenController {

    // N'injecte QUE l'interface de maintenance
    private final IMaintenanceService maintenanceService;

    @PostMapping("/planifier")
    public ResponseEntity<Void> planifier(@RequestParam Long vehiculeId,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        maintenanceService.planifierMaintenance(vehiculeId, debut, fin);
        return ResponseEntity.ok().build();
    }
}
```

---

### 5. Tableau des Prompts de la Phase de Découverte

| Prompt (tel quel) | Ce que nous en avons retenu | Ce que nous avons corrigé ou écarté, et pourquoi |
| :--- | :--- | :--- |
| **Prompt 1 :** *« Dans une application Spring Boot, trois écrans (réservation, rapports, maintenance) injectent la même interface IGestionLocationService avec 6 méthodes. Pourquoi toute modification sur une méthode force les 3 équipes à retester leurs écrans ? »* | L'explication du couplage fort et de la rupture de contrat d'interface : toute altération binaire oblige à recompiler les consommateurs, et l'absence d'isolation fonctionnelle impose des tests de non-régression sur tout le périmètre. | L'IA proposait initialement de fusionner les équipes ou d'automatiser les tests de régression globaux. Nous avons écarté cela car le problème est d'ordre architectural et conceptuel, pas organisationnel. |
| **Prompt 2 :** *« Quel principe SOLID est violé quand une interface contient des méthodes pour des fonctionnalités non utilisées par certains de ses clients ? »* | Identification claire du principe **ISP (Interface Segregation Principle)** : les clients ne doivent pas dépendre de méthodes dont ils n'ont pas besoin. | L'IA mentionnait aussi SRP (Single Responsibility). Bien que connexe au niveau des classes, le problème précis ici concerne le contrat d'interface et ses clients multiples, ce qui caractérise exactement ISP. |
| **Prompt 3 :** *« Comment résoudre ce problème sans casser le code existant ? Est-ce qu'on doit utiliser des méthodes `default` en Java 8 ou faire hériter une grosse interface de plusieurs sous-interfaces ? »* | La compréhension des techniques alternatives : les méthodes `default` masquent le problème sans le résoudre (l'interface reste polluée), tandis que l'héritage multiple d'interfaces (`IGestionLocationService extends IReservation, IRapport, IMaintenance`) maintient le couplage chez les clients existants. | **Proposition écartée :** Nous avons rejeté l'usage de méthodes `default` et l'interface chapeau monolithique, car les clients continueraient d'avoir accès à des méthodes hors de leur domaine. Nous avons opté pour une ségrégation nette en 3 interfaces de rôles distinctes injectées indépendamment. |
| **Prompt 4 :** *« Peux-tu me donner le découpage des interfaces pour l'écran de réservation, l'écran des rapports et l'écran de maintenance, avec l'injection de dépendances Spring appropriée ? »* | Les 3 interfaces précises (`IReservationService`, `IRapportService`, `IMaintenanceService`) et l'usage de l'injection par constructeur (`@RequiredArgsConstructor` avec `final`) pour chaque contrôleur d'écran. | L'IA avait initialement créé une seule classe de service géante implémentant les 3 interfaces. Pour garantir une indépendance totale et respecter également SRP, nous avons séparé également les implémentations (`ReservationServiceImpl`, `RapportServiceImpl`, `MaintenanceServiceImpl`). |
