package tn.esprit.manaiamal4cce11.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.manaiamal4cce11.domain.Vehicule;
import tn.esprit.manaiamal4cce11.services.impls.VehiculeServicesImpl;

@RequestMapping("vehicules")
@RestController
@RequiredArgsConstructor

public class VehiculeRestController {
       private final VehiculeServicesImpl vehiculeServices;

       @PostMapping("add")
       public Vehicule add(@RequestBody Vehicule vehicule) {
           return vehiculeServices.create(vehicule);

       }
       @GetMapping("get/{idVehicule}")
       public Vehicule get(@PathVariable Long idVehicule) {
           return vehiculeServices.findById(idVehicule);
       }


}
