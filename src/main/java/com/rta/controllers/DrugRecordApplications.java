package com.rta.controllers;

import com.rta.db.DrugRecordApplicationRepository;
import com.rta.fda.FdaService;
import com.rta.model.DrugRecordApplication;
import com.rta.model.DrugRecordApplicationDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class DrugRecordApplications {

    private final FdaService fdaService;
    private final DrugRecordApplicationRepository repository;

    public DrugRecordApplications(FdaService fdaService,
                                  DrugRecordApplicationRepository repository) {
        this.fdaService = fdaService;
        this.repository = repository;
    }

    @GetMapping("fda")
    @ApiOperation("Search drug application record in FDA by manufactureName and brandName. Result presented in pages")
    public ResponseEntity<List<DrugRecordApplicationDTO>> search(@RequestParam String manufactureName,
                                                                 @RequestParam(required = false) String brandName,
                                                                 @RequestParam(defaultValue = "0") int page) {

        try {
            return new ResponseEntity<>(fdaService.search(manufactureName, brandName, page).stream()
                    .map(DrugRecordApplicationDTO::fromModel)
                    .collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("applications")
    @ApiOperation("Stores specific drug record applications details")
    public ResponseEntity<DrugRecordApplicationDTO> saveApplication(@RequestBody DrugRecordApplicationDTO drugRecordApplicationDTO) {
        try {
            DrugRecordApplication drugRecordApplication = repository.save(drugRecordApplicationDTO.toModel());
            return new ResponseEntity<>(DrugRecordApplicationDTO.fromModel(drugRecordApplication), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("applications")
    @ApiOperation("Get all stored drug record application details")
    public ResponseEntity<List<DrugRecordApplicationDTO>> loadApplications() {
        try {
            return new ResponseEntity<>(repository.findAll().stream()
                    .map(DrugRecordApplicationDTO::fromModel)
                    .collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("applications/{applicationId}")
    @ApiOperation("Get stored drug record application details by application id")
    public ResponseEntity<DrugRecordApplicationDTO> loadByApplicationId(@PathVariable String applicationId) {
        return repository.findById(applicationId)
                .map(DrugRecordApplicationDTO::fromModel)
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
