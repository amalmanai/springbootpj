Fetch Strategy – AutoLoc
1. Introduction
   Ce document présente les stratégies de chargement (FetchType) et les stratégies de cascade (CascadeType) utilisées dans les associations JPA du projet AutoLoc.
   L'objectif est de choisir une configuration cohérente avec le cycle de vie des entités, tout en évitant de charger inutilement les objets associés.
   Dans AutoLoc, la stratégie LAZY est privilégiée afin de ne charger les entités associées que lorsqu'elles sont réellement utilisées.
2. Rappel sur FetchType
   LAZY
   Avec FetchType.LAZY, l'association n'est pas chargée immédiatement. Les données associées sont chargées uniquement lorsqu'elles sont utilisées.
   Cette stratégie permet de limiter les données chargées et d'améliorer les performances.
   EAGER
   Avec FetchType.EAGER, l'association est chargée automatiquement avec l'entité principale.
   Cette stratégie peut entraîner le chargement d'objets qui ne sont pas nécessaires.
   Dans AutoLoc, LAZY est privilégié pour les associations afin d'éviter de charger inutilement les objets liés.
3. Associations du modèle AutoLoc
   3.1 Agence → Vehicule
   Configuration
- Type : OneToMany / ManyToOne
- Côté propriétaire : Vehicule
- Fetch : LAZY
- Cascade : aucune
  Justification
  Un véhicule survit à la suppression de son agence. Il ne faut donc pas utiliser de cascade de suppression.
  La stratégie LAZY permet de ne charger les véhicules d'une agence que lorsqu'ils sont réellement nécessaires.
  3.2 Agence → Employe
  Configuration
- Type : OneToMany / ManyToOne
- Côté propriétaire : Employe
- Fetch : LAZY
- Cascade : aucune
  Justification
  L'employé possède la clé étrangère vers l'agence.
  La stratégie LAZY permet d'éviter de charger automatiquement tous les employés lorsqu'une agence est récupérée.
  Aucune cascade n'est appliquée, car le cycle de vie de l'employé doit être géré indépendamment.
  3.3 Vehicule ↔ Equipement
  Configuration
- Type : ManyToMany
- Côté propriétaire : Vehicule
- Fetch : LAZY
- Cascade : aucune
- Table de jointure : vehicule_equipement
- Collection : Set
  Justification
  Un véhicule peut posséder plusieurs équipements et un équipement peut être associé à plusieurs véhicules.
  Une table de jointure vehicule_equipement est donc utilisée pour représenter cette association.
  Les équipements sont partagés entre plusieurs véhicules. Il ne faut donc pas appliquer de cascade de suppression.
  La collection utilise un Set afin d'éviter les doublons.
  La stratégie LAZY évite de charger automatiquement les équipements lorsqu'un véhicule est récupéré.
  3.4 Client → Reservation
  Configuration
- Type : OneToMany / ManyToOne
- Côté propriétaire : Reservation
- Fetch : LAZY
- Cascade : PERSIST
  Justification
  La réservation possède la clé étrangère vers le client.
  La cascade PERSIST permet de persister les nouvelles réservations avec le client.
  La stratégie LAZY évite de charger automatiquement toutes les réservations lorsqu'un client est récupéré.
  3.5 Reservation → Vehicule
  Configuration
- Type : ManyToOne
- Côté propriétaire : Reservation
- Fetch : LAZY
- Cascade : aucune
  Justification
  Une réservation référence un véhicule existant.
  Aucune cascade n'est appliquée car le véhicule doit pouvoir exister indépendamment de la réservation.
  La stratégie LAZY permet d'éviter de charger automatiquement le véhicule lorsqu'une réservation est récupérée.
  3.6 Reservation ↔ Contrat
  Configuration
- Type : OneToOne
- Côté propriétaire : Contrat
- Fetch : LAZY
- Cascade : ALL côté Reservation
  Justification
  Une réservation peut être associée à un contrat.
  La stratégie LAZY permet d'éviter de charger systématiquement le contrat lorsqu'une réservation est récupérée.
  La cascade ALL est appliquée côté Reservation conformément au tableau de l'atelier.
  3.7 Vehicule → Maintenance
  Configuration
- Type : OneToMany / ManyToOne
- Côté propriétaire : Maintenance
- Fetch : LAZY
- Cascade : PERSIST
  Justification
  La maintenance possède la clé étrangère vers le véhicule.
  La cascade PERSIST permet de persister les nouvelles maintenances avec le véhicule.
  La stratégie LAZY évite de charger automatiquement toutes les maintenances lorsqu'un véhicule est récupéré.
4. Association Contrat → Paiement
   Configuration
- Type : OneToMany / ManyToOne
- Côté propriétaire : Paiement
- Fetch : LAZY
- Cascade : ALL
- orphanRemoval : true
  Justification
  Un paiement dépend de son contrat et n'a pas de sens sans celui-ci.
  La cascade ALL permet de propager les opérations de persistance, de mise à jour et de suppression du contrat vers ses paiements.
  Avec orphanRemoval = true, lorsqu'un paiement est retiré de la collection paiements du contrat, il est également supprimé de la base de données.
  La stratégie LAZY permet de ne charger les paiements que lorsqu'ils sont réellement utilisés.
5. Tableau récapitulatif
   Association	Type	Côté propriétaire	Fetch	Cascade	OrphanRemoval
   Agence → Vehicule	OneToMany / ManyToOne	Vehicule	LAZY	Aucune	Non
   Agence → Employe	OneToMany / ManyToOne	Employe	LAZY	Aucune	Non
   Vehicule ↔ Equipement	ManyToMany	Vehicule	LAZY	Aucune	Non
   Client → Reservation	OneToMany / ManyToOne	Reservation	LAZY	PERSIST	Non
   Reservation → Vehicule	ManyToOne	Reservation	LAZY	Aucune	Non
   Reservation ↔ Contrat	OneToOne	Contrat	LAZY	ALL côté Reservation	Non
   Vehicule → Maintenance	OneToMany / ManyToOne	Maintenance	LAZY	PERSIST	Non
   Contrat → Paiement	OneToMany / ManyToOne	Paiement	LAZY	ALL	Oui


6. Conclusion
   Dans le projet AutoLoc, la stratégie LAZY est privilégiée afin de limiter le chargement automatique des associations.
   Les cascades sont utilisées uniquement lorsque le cycle de vie des entités liées le justifie.
   L'association Contrat → Paiement constitue une composition : un paiement dépend de son contrat. Elle utilise donc CascadeType.ALL et orphanRemoval = true.
   Pour les équipements, aucune cascade de suppression n'est appliquée car les équipements sont partagés entre plusieurs véhicules.
   L'utilisation de mappedBy permet de définir correctement le côté inverse des associations bidirectionnelles et d'éviter la création de tables de jointure inutiles.