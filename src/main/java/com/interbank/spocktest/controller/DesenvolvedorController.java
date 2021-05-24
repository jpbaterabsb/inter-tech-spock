package com.interbank.spocktest.controller;

import com.interbank.spocktest.constants.AppConstants;
import com.interbank.spocktest.model.Desenvolvedor;
import com.interbank.spocktest.service.DesenvolvedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(AppConstants.DESENVOLVEDOR_PATH)
@RequiredArgsConstructor
public class DesenvolvedorController {
    private final DesenvolvedorService desenvolvedorService;


    @PostMapping
    public ResponseEntity<Desenvolvedor> create(@Valid @RequestBody Desenvolvedor desenvolvedor) {
        Desenvolvedor persistedDesenvolvedor = desenvolvedorService.create(desenvolvedor);
        return new ResponseEntity<>(persistedDesenvolvedor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") @NotNull(message = "id nao pode ser nulo") Long id, @Valid @RequestBody Desenvolvedor desenvolvedor) {
        desenvolvedorService.update(id, desenvolvedor);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Desenvolvedor> findById(@PathVariable("id") Long id) {
        Optional<Desenvolvedor> optionalDesenvolvedor = desenvolvedorService.findAll(id);
        if(optionalDesenvolvedor.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(optionalDesenvolvedor.get());
    }


    @GetMapping
    public ResponseEntity<List<Desenvolvedor>> findAll() {
        return ResponseEntity.ok(desenvolvedorService.findAll());
    }
}
