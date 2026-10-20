
package tn.esprit.manaiamal4cce11.web.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.manaiamal4cce11.domain.Agence;
import tn.esprit.manaiamal4cce11.services.IAgenceServices;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceRestController {

    private final IAgenceServices agenceService;

    @GetMapping
    @Operation(summary = "Lister toutes les agences")
    @ApiResponse(responseCode = "200", description = "Liste des agences récupérée")
    public List<Agence> getAgences() {
        return agenceService.findAll();
    }

    @GetMapping("/{idAgence}")
    @Operation(summary = "Consulter une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence trouvée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    public Agence get(@PathVariable Long idAgence) {
        return agenceService.findById(idAgence);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajouter une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agence créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public Agence add(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @PutMapping("/{idAgence}")
    @Operation(summary = "Modifier une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence modifiée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public Agence update(
            @PathVariable Long idAgence,
            @RequestBody Agence agence) {
        return agenceService.update(idAgence, agence);
    }

    @DeleteMapping("/{idAgence}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une agence")
 @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Agence supprimée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    public void deleteById(@PathVariable Long idAgence) {
        agenceService.deleteById(idAgence);
    }
}
