package tn.esprit.manaiamal4cce11.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.manaiamal4cce11.domain.Equipement;
import tn.esprit.manaiamal4cce11.services.IEquipmentServices;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipements", description = "Gestion des équipements")
 public class EquipementRestController {
    private final IEquipmentServices equipementServices;

    @GetMapping
    @Operation(summary = "Lister tous les équipements")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    public List<Equipement> findAll() {
        return equipementServices.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement trouvé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    public Equipement findById(
            @Parameter(description = "Identifiant de l'équipement")
            @PathVariable long id) {
        return equipementServices.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un équipement")
    @ApiResponse(responseCode = "201", description = "Équipement créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public Equipement create(@RequestBody Equipement equipement) {
        return equipementServices.create(equipement);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un équipement")
    @ApiResponse(responseCode = "200", description = "Équipement modifié")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public Equipement update(
            @PathVariable long id,
            @RequestBody Equipement equipement) {
        equipement.setIdEquipement(id);
        return equipementServices.update(equipement);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un équipement")
    @ApiResponse(responseCode = "204", description = "Équipement supprimé")
    @ApiResponse(responseCode = "404", description = "Équipement introuvable")
    public void deleteById(@PathVariable long id) {
        equipementServices.deleteById(id);
    }
}
