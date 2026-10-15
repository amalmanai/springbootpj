# Notes — Couche Service

## 1. IoC et Injection de dépendances

Avec Spring, les classes ne créent pas elles-mêmes leurs dépendances avec `new`. Le conteneur Spring crée les beans et injecte leurs dépendances.

Exemple :

```java
private final IContratRepository contratRepository;
```

Le service dépend de l'interface `IContratRepository`, et Spring fournit automatiquement son implémentation.

---

## 2. Injection par constructeur

Le projet utilise l'injection par constructeur :

```java
@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
}
```

`@RequiredArgsConstructor` génère le constructeur automatiquement.

Cette méthode est privilégiée car elle :

- rend les dépendances explicites ;
- permet d'utiliser `final` ;
- évite les dépendances `null` ;
- facilite les tests ;
- respecte les bonnes pratiques Spring.

L'injection par attribut avec `@Autowired` est moins recommandée et peut être signalée par SonarQube.

---

## 3. Rôle de `@Service`

`@Service` indique que `ContratServiceImpl` est un composant de la couche métier.

Spring détecte automatiquement cette classe et crée son bean. Il n'est donc pas nécessaire d'utiliser `new ContratServiceImpl()`.

---

## 4. Rôle de la couche Service

Le Service se situe entre le Controller et le Repository :

```text
Controller
    ↓
IContratService
    ↓
ContratServiceImpl
    ↓
IContratRepository
    ↓
JPA / Hibernate / MySQL
```

Le Service contient la logique métier et ne dépend pas de HTTP ou du JSON.

Il permet notamment de :

- vérifier l'existence d'un contrat ;
- contrôler les créations et mises à jour ;
- gérer les suppressions ;
- lever `ResourceNotFoundException` lorsqu'une ressource n'existe pas.

---

## 5. Exceptions Spring courantes

### `NoSuchBeanDefinitionException`

Aucun bean correspondant n'est trouvé.

**Cause :** `@Service` oublié ou classe hors du package de scan.

### `NoUniqueBeanDefinitionException`

Plusieurs beans correspondent au même type.

**Solution :** utiliser `@Primary` ou `@Qualifier`.

### `UnsatisfiedDependencyException`

Une dépendance ne peut pas être injectée. Il faut rechercher la cause indiquée par `Caused by`.

### Dépendance circulaire

Deux beans dépendent l'un de l'autre. Il faut revoir la conception.

---

## 6. Choix de conception

| Choix | Justification |
|---|---|
| `@Service` | Déclare le