package fr.sparkit.crm.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.sparkit.crm.entities.Opportinity;
import fr.sparkit.crm.services.IOpportinityService;

@RestController()
@CrossOrigin("*")
@RequestMapping("/opportinity-management/v1")
public class Opprotinitycontroller {

    IOpportinityService opportinityService;

    public Opprotinitycontroller(IOpportinityService opportinityService) {
        this.opportinityService = opportinityService;
    }

    @GetMapping("/opportinities")
    public ResponseEntity<List<Opportinity>> getAllOpportinities() {

        return new ResponseEntity<>(opportinityService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/opportinities/{id}")
    public ResponseEntity<Opportinity> getOpportinity(@PathVariable Long id) {
        Optional<Opportinity> opportinity = opportinityService.findOne(id);

        if (opportinity.isPresent()) {
            return new ResponseEntity<>(opportinity.get(), HttpStatus.OK);

        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/opportinities")
    public ResponseEntity<String> saveOpportinity(@RequestBody Opportinity opportinity) {

        opportinityService.saveAndFlush(opportinity);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/opportinities/{id}")
    public ResponseEntity<String> updateOpportinity(@PathVariable Long id,
            @RequestBody Opportinity updatedOpportinity) {

        opportinityService.saveAndFlush(updatedOpportinity);
        return new ResponseEntity<>("Opportinity updated", HttpStatus.OK);

    }

}
