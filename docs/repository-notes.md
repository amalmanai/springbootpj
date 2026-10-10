
# Atelier 3 — Spring Data JPA : Repository

## 1. Choix de l'interface Repository

Les neuf interfaces Repository étendent `JpaRepository<Entité, Long>`. Cette interface fournit les opérations CRUD, les listes, le tri, la pagination et les méthodes spécifiques à JPA.

| Interface | Entité | Interface étendue |
|---|---|---|
| IAgenceRepository | Agence | JpaRepository<Agence, Long> |
| IEmployeRepository | Employe | JpaRepository<Employe, Long> |
| IVehiculeRepository | Vehicule | JpaRepository<Vehicule, Long> |
| IEquipementRepository | Equipement | JpaRepository<Equipement, Long> |
| IClientRepository | Client | JpaRepository<Client, Long> |
| IReservationRepository | Reservation | JpaRepository<Reservation, Long> |
| IContratRepository | Contrat | JpaRepository<Contrat, Long> |
| IPaiementRepository | Paiement | JpaRepository<Paiement, Long> |
| IMaintenanceRepository | Maintenance | JpaRepository<Maintenance, Long> |

## 2. Anomalies détectées avec SonarQube for IDE

| Anomalie détectée | Explication | Correction apportée |
|---|---|---|
| Import inutilisé : `java.awt.*` | La bibliothèque AWT n'est pas utilisée dans le fichier Repository concerné. | Suppression de l'import inutile. |
| Import inutilisé dans le package `domain` | Certaines classes importées ne sont pas utilisées dans le fichier concerné. | Suppression des imports inutilisés. |
| Import inutile d'une classe du package `domain` | Une classe importée n'est pas utilisée dans le fichier où l'import a été détecté. | Suppression uniquement si la classe n'est pas utilisée. |

## 3. Vérifications réalisées

- Création des neuf interfaces Repository.
- Utilisation de `JpaRepository` pour les neuf entités.
- Analyse du code avec SonarQube for IDE.
- Correction des imports inutilisés détectés.
- Vérification du démarrage de l'application Spring Boot.