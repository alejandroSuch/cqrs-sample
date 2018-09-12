package com.veamospues.clientmanagement.domain.command.executor;

import com.veamospues.clientmanagement.domain.aggregate.ClientAggregate;
import com.veamospues.clientmanagement.domain.aggregate.ClientAggregateRepository;
import com.veamospues.clientmanagement.domain.command.UpdateClientCommand;
import com.veamospues.clientmanagement.domain.command.exception.ClientNotFoundException;
import com.veamospues.clientmanagement.domain.command.exception.DuplicateClientNameException;
import com.veamospues.clientmanagement.domain.query.Client;
import com.veamospues.clientmanagement.domain.query.ClientQueryCriteria;
import com.veamospues.clientmanagement.domain.query.ClientQueryRepository;
import com.veamospues.cqrs.command.CommandExecutor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class UpdateClientCommandExecutor implements CommandExecutor<UpdateClientCommand> {
  ClientAggregateRepository aggregateRepository;
  ClientQueryRepository clientQueryRepository;

  @Override
  public void execute(UpdateClientCommand command) {
    assertThatClientExists(command.getAggregateId());
    assertThatClientIsNotDuplicated(command.getAggregateId(), command.getName());

    updateClient(command);
  }

  private void assertThatClientExists(UUID clientId) {
    if (!clientQueryRepository.findById(clientId).isPresent()) {
      throw new ClientNotFoundException(clientId);
    }
  }

  private void assertThatClientIsNotDuplicated(UUID clientId, String clientName) {
    final ClientQueryCriteria byCriteria = ClientQueryCriteria.builder().name(clientName).build();
    final Predicate<Client> otherThanClientId = it -> !clientId.equals(it.getId());

    final List<Client> clientList = clientQueryRepository
      .findAll(byCriteria)
      .stream()
      .filter(otherThanClientId)
      .collect(toList());

    if (!clientList.isEmpty()) {
      throw new DuplicateClientNameException(clientName);
    }
  }

  private void updateClient(UpdateClientCommand command) {
    ClientAggregate client = aggregateRepository.getById(ClientAggregate.class, command.getAggregateId());
    client.changeName(command.getName());
    aggregateRepository.save(client, command.getAggregateVersion());
  }
}
