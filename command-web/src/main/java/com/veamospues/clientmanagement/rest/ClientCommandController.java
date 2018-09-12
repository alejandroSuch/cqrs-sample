package com.veamospues.clientmanagement.rest;

import com.veamospues.clientmanagement.domain.aggregate.ClientAggregate;
import com.veamospues.clientmanagement.domain.aggregate.ClientAggregateRepository;
import com.veamospues.clientmanagement.domain.command.CreateClientCommand;
import com.veamospues.clientmanagement.domain.command.DeleteClientCommand;
import com.veamospues.clientmanagement.domain.command.UpdateClientCommand;
import com.veamospues.clientmanagement.domain.command.executor.ClientCommandExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;

@Log
@RestController
@AllArgsConstructor
public class ClientCommandController {
  ClientCommandExecutor executor;
  ClientAggregateRepository clientAggregateRepository;

  @PostMapping("clients")
  public ResponseEntity<Void> create(@Valid @RequestBody CreateClientRequest request, UriComponentsBuilder uriComponentsBuilder) {
    final UUID id = randomUUID();

    log.info(format("Creating client %s.", id.toString()));
    executor.execute(new CreateClientCommand(id, request.getName()));

    return created(uriComponentsBuilder.path("/clients/" + id.toString()).build().toUri())
      .build();
  }

  @PutMapping("clients/{id}")
  public ResponseEntity<Void> update(@PathVariable("id") UUID id, @Valid  @RequestBody UpdateClientRequest request, UriComponentsBuilder uriComponentsBuilder) {
    log.info(format("Updating client %s.", id.toString()));

    final ClientAggregate aggregate = clientAggregateRepository.getById(ClientAggregate.class, id);

    executor.execute(new UpdateClientCommand(id, aggregate.getVersion(), request.getName()));

    return noContent().build();
  }

  @DeleteMapping("clients/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
    log.info(format("Deleting client %s.", id.toString()));

    final ClientAggregate aggregate = clientAggregateRepository.getById(ClientAggregate.class, id);

    executor.execute(new DeleteClientCommand(id, aggregate.getVersion()));

    return noContent().build();
  }
}
