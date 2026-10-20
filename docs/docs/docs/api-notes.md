# AutoLoc API — Atelier 5
## Spring MVC, API REST, Postman et Swagger

**Module :** Architecture des Systèmes d'Information (ASI)  
**Année universitaire :** 2026–2027  
**Projet :** AutoLoc — Gestion de location de véhicules

---

## A. Endpoints de l'API REST

L'API REST d'AutoLoc permet de gérer les différentes ressources de l'application : agences, employés, véhicules, équipements, clients, réservations, contrats, paiements et maintenances.

Les contrôleurs REST sont placés dans le package `web.controller`. Ils utilisent les interfaces de service pour respecter l'architecture en couches et éviter tout accès direct aux repositories.

Le préfixe commun `/api` est configuré dans `application.properties` à l'aide de la propriété `server.servlet.context-path=/api`.

### A.1. Agence

| Méthode HTTP | Route | Opération | Succès | Erreurs possibles |
|---|---|---|---|---|
| GET | `/api/agences` | Lister les agences | 200 OK | 500 |
| GET | `/api/agences/{id}` | Consulter une agence | 200 OK | 404 |
| POST | `/api/agences` | Créer une agence | 201 Created | 400 |
| PUT | `/api/agences/{id}` | Modifier une agence | 200 OK | 400, 404 |
| DELETE | `/api/agences/{id}` | Supprimer une agence | 204 No Content | 404 |

### A.2. Employé

| Méthode HTTP | Route | Opération | Succès | Erreurs possibles |
|---|---|---|---|---|
| GET | `/api/employes` | Lister les employés | 200 OK | 500 |
| GET | `/api/employes/{id}` | Consulter un employé | 200 OK | 404 |
| POST | `/api/employes` | Créer un employé | 201 Created | 400 |
| PUT | `/api/employes/{id}` | Modifier un employé | 200 OK | 400, 404 |
| DELETE | `/api/employes/{id}` | Supprimer un employé | 204 No Content | 404 |

Pour associer un employé à une agence, le corps JSON peut contenir une référence à l'agence existante, par exemple :

```json
{
  "agence": {
    "id": 1
  }
}
```

Le nom de la propriété identifiant doit correspondre à celui défini dans l'entité `Agence`.

### A.3. Véhicule

| Méthode HTTP | Route | Opération | Succès | Erreurs possibles |
|---|---|---|---|---|
| GET | `/api/vehicules` | Lister les véhicules | 200 OK | 500 |
| GET | `/api/vehicules/{id}` | Consulter un véhicule | 200 OK | 404 |
| POST | `/api/vehicules` | Créer un véhicule | 201 Created | 400 |
| PUT | `/api/vehicules/{id}` | Modifier un véhicule | 200 OK | 400, 404 |
| DELETE | `/api/vehicules/{id}` | Supprimer un véhicule | 204 No Content | 404 |

Les valeurs numériques doivent respecter les contraintes métier du modèle. Une valeur négative, comme un prix ou un kilométrage qui ne peut pas être négatif, doit être refusée si cette règle est implémentée.

### A.4. Équipement

| Méthode HTTP | Route | Opération | Succès | Erreurs possibles |
|---|---|---|---|---|
| GET | `/api/equipements` | Lister les équipements | 200 OK | 500 |
| GET | `/api/equipements/{id}` | Consulter un équipement | 200 OK | 404 |
| POST | `/api/equipements` | Créer un équipement | 201 Created | 400 |
| PUT | `/api/equipements/{id}` | Modifier un équipement | 200 OK | 400, 404 |
| DELETE | `/api/equipements/{id}` | Supprimer un équipement | 204 No Content | 404 |

Le libellé doit être renseigné et la quantité ne doit pas être négative si ces validations sont prévues par le service.

### A.5. Client

| Méthode HTTP | Route | Opération | Succès | Erreurs possibles |
|---|---|---|---|---|
| GET | `/api/clients` | Lister les clients | 200 OK | 500 |
| GET | `/api/clients/{id}` | Consulter un client | 200 OK | 404 |
| POST | `/api/clients` | Créer un client | 201 Created | 400 |
| PUT | `/api/clients/{id}` | Modifier un client | 200 OK | 400, 404 |
| DELETE | `/api/clients/{id}` | Supprimer un client | 204 No Content | 404 |

Les attributs obligatoires du client doivent être renseignés avant la création ou la modification.

### A.6. Réservation

| Méthode HTTP | Route | Opération | Succès | Erreurs possibles |
|---|---|---|---|---|
| GET | `/api/reservations` | Lister les réservations | 200 OK | 500 |
| GET | `/api/reservations/{id}` | Consulter une réservation | 200 OK | 404 |
| POST | `/api/reservations` | Créer une réservation | 201 Created | 400 |
| PUT | `/api/reservations/{id}` | Modifier une réservation | 200 OK | 400, 404 |
| DELETE | `/api/reservations/{id}` | Supprimer une réservation | 204 No Content | 404 |

Une réservation référence un client et un véhicule existants. Exemple de corps JSON :

```json
{
  "client": {
    "id": 1
  },
  "vehicule": {
    "id": 1
  }
}
```

Les autres champs obligatoires dépendent de l'entité `Reservation`. La date de fin doit être postérieure ou égale à la date de début, selon la règle métier retenue.

### A.7. Contrat

| Méthode HTTP | Route | Opération | Succès | Erreurs possibles |
|---|---|---|---|---|
| GET | `/api/contrats` | Lister les contrats | 200 OK | 500 |
| GET | `/api/contrats/{id}` | Consulter un contrat | 200 OK | 404 |
| POST | `/api/contrats` | Créer un contrat | 201 Created | 400 |
| PUT | `/api/contrats/{id}` | Modifier un contrat | 200 OK | 400, 404 |
| DELETE | `/api/contrats/{id}` | Supprimer un contrat | 204 No Content | 404 |

La création d'un contrat peut également entraîner la création de ses paiements associés grâce à la cascade JPA configurée sur la relation `Contrat`–`Paiement`.

### A.8. Paiement

| Méthode HTTP | Route | Opération | Succès | Erreurs possibles |
|---|---|---|---|---|
| GET | `/api/paiements` | Lister les paiements | 200 OK | 500 |
| GET | `/api/paiements/{id}` | Consulter un paiement | 200 OK | 404 |

Le paiement est en lecture seule dans cet atelier. La création, la modification et la suppression individuelles ne sont pas exposées par `PaiementController`.

Une requête `POST /api/paiements` doit donc renvoyer `405 Method Not Allowed` si aucune route POST correspondante n'est définie et si aucune autre configuration ne modifie ce comportement.

### A.9. Maintenance

| Méthode HTTP | Route | Opération | Succès | Erreurs possibles |
|---|---|---|---|---|
| GET | `/api/maintenances` | Lister les maintenances | 200 OK | 500 |
| GET | `/api/maintenances/{id}` | Consulter une maintenance | 200 OK | 404 |
| POST | `/api/maintenances` | Créer une maintenance | 201 Created | 400 |
| PUT | `/api/maintenances/{id}` | Modifier une maintenance | 200 OK | 400, 404 |
| DELETE | `/api/maintenances/{id}` | Supprimer une maintenance | 204 No Content | 404 |

La maintenance référence un véhicule. Le coût doit respecter les règles métier et la date de fin ne doit pas précéder la date de début si ces contrôles sont implémentés.

### A.10. Signification des codes HTTP

- **200 OK :** la requête a été traitée avec succès.
- **201 Created :** une nouvelle ressource a été créée.
- **204 No Content :** la suppression a réussi et aucune représentation n'est renvoyée.
- **400 Bad Request :** la requête contient des données invalides ou ne respecte pas une règle métier.
- **404 Not Found :** la ressource demandée n'existe pas.
- **405 Method Not Allowed :** la méthode HTTP demandée n'est pas autorisée pour cette route.
- **500 Internal Server Error :** une erreur interne non traitée correctement est survenue.

Les codes réellement renvoyés dépendent des contrôleurs, des services et du `GlobalExceptionHandler` configuré dans l'application.

---

## B. Annotations Jackson et associations JPA

Jackson est la bibliothèque utilisée par Spring Boot pour convertir les objets Java en JSON et les requêtes JSON en objets Java.

Une association bidirectionnelle peut entraîner une boucle de sérialisation. Par exemple, un contrat contient une liste de paiements et chaque paiement référence le contrat correspondant. Sans configuration adaptée, Jackson peut essayer de sérialiser indéfiniment le contrat, puis ses paiements, puis le contrat à nouveau.

### B.1. Association Contrat–Paiement

Pour cette association, `@JsonManagedReference` est placée du côté de la collection des paiements et `@JsonBackReference` du côté de la référence vers le contrat.

Exemple dans `Contrat.java` :

```java
@JsonManagedReference("contrat-paiements")
@OneToMany(
    mappedBy = "contrat",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
private List<Paiement> paiements = new ArrayList<>();
```

Exemple dans `Paiement.java` :

```java
@JsonBackReference("contrat-paiements")
@ManyToOne(fetch = FetchType.LAZY)
private Contrat contrat;
```

Cette configuration permet de sérialiser les paiements dans la réponse JSON du contrat sans sérialiser de nouveau le contrat à partir de chaque paiement. Elle permet également à Jackson de reconstruire correctement la relation lors de la lecture du JSON.

La cascade `CascadeType.ALL` permet de propager au paiement les opérations JPA prises en charge par la cascade, notamment la persistance et la suppression, lorsque ces opérations sont effectuées sur le contrat.

### B.2. Associations Agence–Véhicule et autres relations

Pour les autres associations, l'annotation retenue dépend de la façon dont les données doivent être exposées.

| Annotation | Rôle | Utilisation |
|---|---|---|
| `@JsonManagedReference` | Définit le côté principal d'une relation gérée par Jackson | Collection du côté parent |
| `@JsonBackReference` | Ignore la référence de retour pendant la sérialisation | Référence vers le parent |
| `@JsonIgnore` | Exclut une propriété de la sérialisation et de la désérialisation Jackson | Association inverse qui ne doit pas être exposée |

Dans le cas d'une relation `Agence`–`Vehicule`, `@JsonIgnore` peut être placé sur la collection inverse si les véhicules ne doivent pas être inclus dans le JSON de l'agence. Cela évite les références circulaires et simplifie les réponses de l'API.

Il ne faut toutefois pas appliquer automatiquement les mêmes annotations à toutes les associations. Par exemple, ignorer la propriété `Reservation.client` pourrait empêcher Jackson de reconstruire correctement le client lors de la réception d'une réservation.

### B.3. Différence entre `@JsonBackReference` et `@JsonIgnore`

`@JsonBackReference` gère spécifiquement le côté retour d'une relation bidirectionnelle. Elle permet de conserver la relation dans le modèle Java tout en évitant de sérialiser le parent depuis l'enfant.

`@JsonIgnore` exclut complètement la propriété concernée du traitement JSON. Cette annotation est donc plus générale et peut aussi empêcher la lecture de cette propriété dans une requête entrante.

Les DTO constituent une solution plus propre à long terme, car ils permettent de contrôler précisément les données reçues et renvoyées par l'API sans exposer directement toutes les entités JPA.

---

## C. Postman et Swagger

### C.1. Utilisation de Postman

Postman permet d'envoyer des requêtes HTTP à l'application et d'observer les réponses.

Un environnement `local` peut contenir la variable :

```text
baseUrl = http://localhost:8080
```

La requête de consultation de tous les contrats devient alors :

```http
GET {{baseUrl}}/api/contrats
```

Pour une création, il faut sélectionner `POST`, ouvrir `Body`, choisir `raw` puis `JSON`, et fournir les champs attendus par l'entité.

Exemple :

```json
{
  "dateSignature": "2026-11-02",
  "montantTotal": 480.00,
  "valide": true
}
```

Cet exemple doit être complété si le modèle impose d'autres attributs ou associations.

Les requêtes de test permettent de vérifier :
- La récupération des ressources avec un code `200`.
- La création d'un contrat avec un code `201`.
- L'absence d'un contrat avec un code `404`.
- La modification d'un contrat avec un code `200`.
- La suppression d'un contrat avec un code `204`.
- Le refus des données invalides avec un code `400`, si les règles correspondantes sont implémentées.
- La création en cascade des paiements associés à un contrat.

Les résultats et les scripts de vérification doivent être conservés dans la collection Postman `AutoLoc`.

### C.2. Utilisation de Swagger UI

SpringDoc OpenAPI génère automatiquement la description de l'API à partir des contrôleurs Spring MVC.

Les URL attendues, avec le préfixe `/api` et le port par défaut, sont :

- Documentation OpenAPI JSON : `http://localhost:8080/api/v3/api-docs`
- Interface Swagger UI : `http://localhost:8080/api/swagger-ui.html`

Swagger UI permet de consulter les routes, les méthodes HTTP, les paramètres, les corps JSON et les réponses documentées. Il permet également d'exécuter les requêtes depuis le navigateur.

La classe `OpenApiConfig` définit les informations générales de l'API :

```java
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI autolocOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("AutoLoc API")
                .description(
                    "API REST de gestion de location de véhicules"
                )
                .version("1.0.0"));
    }
}
```

Les annotations `@Tag`, `@Operation`, `@ApiResponse` et `@Parameter` enrichissent la documentation générée.

### C.3. Différence entre Postman et Swagger

Postman est principalement utilisé pour préparer, organiser, rejouer et vérifier des requêtes HTTP. Par exemple, il permet de tester qu'une consultation d'un contrat inexistant renvoie `404`.

Swagger UI présente une documentation interactive synchronisée avec le code. Il permet de comprendre les routes disponibles et d'exécuter rapidement une requête sans préparer une collection séparée.

Les deux outils sont complémentaires : Postman facilite les scénarios de test répétables, tandis que Swagger facilite la consultation et l'exploration de l'API.

---

## D. Analyse avec SonarQube for IDE

SonarQube for IDE permet d'identifier des problèmes de qualité du code, notamment des erreurs potentielles, des problèmes de maintenabilité et des mauvaises pratiques.

Les contrôleurs doivent rester minces : ils reçoivent les requêtes HTTP, appellent les services et renvoient les résultats. La logique métier doit rester dans la couche service.

Les anomalies suivantes sont des exemples de problèmes à vérifier pendant l'analyse. Elles ne doivent être présentées comme détectées que si l'analyse réelle du projet les confirme.

| Anomalie à vérifier | Explication | Correction envisagée |
|---|---|---|
| Injection de dépendances par attribut | Une injection directe par champ rend les dépendances moins explicites et complique les tests. | Utiliser un constructeur, par exemple avec `@RequiredArgsConstructor` et des champs `final`. |
| Imports inutilisés | Ils encombrent le code et réduisent sa lisibilité. | Supprimer les imports inutiles proposés par l'IDE. |
| Contrôleur contenant de la logique métier | Cela mélange les responsabilités des couches web et service. | Déplacer les règles métier dans le service correspondant. |
| Exposition directe des entités JPA | Elle couple la représentation JSON au modèle de persistance et peut exposer des données internes. | Introduire des DTO pour contrôler les données entrantes et sortantes. |

Après l'analyse, relever au moins deux anomalies réellement signalées par SonarQube for IDE, indiquer leur règle ou leur message, puis noter la correction appliquée.

---

## E. Questions de compréhension

### 1. Quel composant reçoit toutes les requêtes, et comment retrouve-t-il la méthode du contrôleur à appeler ?

Le `DispatcherServlet` reçoit les requêtes HTTP entrantes dans Spring MVC. Il s'appuie sur les mappings enregistrés par Spring, notamment à partir des annotations `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping` et `@DeleteMapping`, pour identifier le contrôleur et la méthode correspondant au chemin et au verbe HTTP. Le contrôleur appelle ensuite le service, qui interagit avec le repository si nécessaire. Le résultat est converti en JSON par les mécanismes de sérialisation de Spring et Jackson.

### 2. Pourquoi `ContratController` dépend-il de `IContratService` et non de `ContratRepository` ni de `ContratServiceImpl` ?

Le contrôleur dépend de l'interface `IContratService` pour respecter la séparation des responsabilités et réduire le couplage. Le service contient la logique métier et utilise le repository pour accéder aux données. Dépendre de l'interface permet de remplacer l'implémentation du service plus facilement et facilite les tests et la maintenance.

### 3. Pourquoi un POST renvoie-t-il 201 et un DELETE 204 ? Pourquoi une ressource absente donne-t-elle 404 et non 500 ?

Un POST qui crée une ressource renvoie `201 Created` pour indiquer que la création a réussi. Un DELETE renvoie `204 No Content` lorsque la suppression réussit et qu'aucun corps de réponse n'est nécessaire. Une ressource inexistante renvoie `404 Not Found` parce que cette situation est identifiée par l'application, par exemple grâce à `ResourceNotFoundException` et au `GlobalExceptionHandler`. Un code `500` est réservé aux erreurs internes inattendues.

### 4. Pourquoi `PaiementController` n'expose-t-il que des GET ? Quelle réponse reçoit un POST `/api/paiements` ?

Dans cet atelier, le paiement est géré par l'intermédiaire du contrat auquel il appartient. La création et la suppression des paiements sont donc prises en charge par les opérations sur le contrat et la cascade JPA, au lieu d'être exposées comme des opérations individuelles. Un POST sur `/api/paiements` renvoie normalement `405 Method Not Allowed` si aucun mapping POST n'existe et qu'aucune autre configuration ne modifie le comportement.

### 5. Quels risques présente l'exposition directe des entités JPA ? Pourquoi `Contrat.paiements` utilise-t-il `@JsonManagedReference` alors que `Agence.vehicules` utilise `@JsonIgnore` ?

Exposer directement les entités JPA couple le contrat JSON à la base de données, peut révéler des champs internes et provoquer des problèmes de sérialisation ou de chargement des associations. Les DTO permettent de mieux maîtriser les données exposées.

`Contrat.paiements` utilise `@JsonManagedReference` pour inclure les paiements dans la représentation JSON du contrat, tout en empêchant une boucle avec la référence retour `Paiement.contrat`, annotée `@JsonBackReference`. `Agence.vehicules` peut utiliser `@JsonIgnore` si la collection inverse ne doit pas être exposée dans le JSON. Le choix dépend du comportement attendu pour chaque relation.

---

## Conclusion

Cet atelier a pour objectif d'exposer les services AutoLoc au moyen d'une API REST organisée en couches, de vérifier les endpoints avec Postman et de générer une documentation interactive avec SpringDoc OpenAPI et Swagger UI.

Les contrôleurs doivent respecter les conventions REST, utiliser l'injection par constructeur et déléguer la logique métier aux services. La gestion des associations JSON doit éviter les boucles de sérialisation, tandis que les tests doivent couvrir les cas nominaux et les erreurs.

La dernière étape consiste à vérifier que l'application démarre correctement, à finaliser la collection Postman, à compléter les observations SonarQube, puis à enregistrer et pousser les modifications sur le dépôt Git AutoLoc.
