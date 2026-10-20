package tn.esprit.manaiamal4cce11.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.manaiamal4cce11.domain.Maintenance;
import tn.esprit.manaiamal4cce11.services.IMaintenanceServices;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceRestController {

    private final IMaintenanceServices maintenanceServices;

    @GetMapping
    @Operation(summary = "Lister toutes les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances récupérée")
    public List<Maintenance> findAll() {
        return maintenanceServices.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une maintenance")
    @ApiResponse(responseCode = "200", description = "Maintenance trouvée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    public Maintenance findById(
            @Parameter(description = "Identifiant de la maintenance")
            @PathVariable long id) {
        return maintenanceServices.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une maintenance")
    @ApiResponse(responseCode = "201", description = "Maintenance créée")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return maintenanceServices.create(maintenance);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une maintenance")
    @ApiResponse(responseCode = "200", description = "Maintenance modifiée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public Maintenance update(
            @Parameter(description = "Identifiant de la maintenance")
            @PathVariable long id,
            @RequestBody Maintenance maintenance) {
        maintenance.setIdMaintenance(id);
        return maintenanceServices.update(maintenance);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une maintenance")
    @ApiResponse(responseCode = "204", description = "Maintenance supprimée")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    public void deleteById(
            @Parameter(description = "Identifiant de la maintenance")
            @PathVariable long id) {
        maintenanceServices.deleteById(id);
    }
}
