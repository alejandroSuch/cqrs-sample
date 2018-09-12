package com.veamospues.clientmanagement.rest;

import com.veamospues.clientmanagement.domain.query.Client;
import com.veamospues.clientmanagement.domain.query.ClientQueryCriteria;
import com.veamospues.clientmanagement.domain.query.ClientQueryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.ResponseEntity.notFound;

@Log
@RestController
@AllArgsConstructor
public class ClientQueryController {
  ClientQueryRepository clientQueryRepository;

  @GetMapping("clients")
  public List<Client> all(ClientQueryCriteria criteria) {
    log.info(format("Searching. Param name is %s", criteria.getName()));
    return clientQueryRepository.findAll(criteria);
  }

  @GetMapping("clients/{id}")
  public ResponseEntity<Client> get(@PathVariable("id") UUID id) {
    log.info(format("Getting by id %s", id.toString()));
    final Optional<Client> client = clientQueryRepository.findById(id);

    return client.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
  }
}
