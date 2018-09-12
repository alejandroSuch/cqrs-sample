package com.veamospues.clientmanagement.domain.command.executor;

import com.veamospues.clientmanagement.domain.aggregate.ClientAggregate;
import com.veamospues.clientmanagement.domain.aggregate.ClientAggregateRepository;
import com.veamospues.clientmanagement.domain.command.CreateClientCommand;
import com.veamospues.clientmanagement.domain.command.exception.ClientAlreadyExistsException;
import com.veamospues.clientmanagement.domain.command.exception.DuplicateClientNameException;
import com.veamospues.clientmanagement.domain.query.ClientQueryCriteria;
import com.veamospues.clientmanagement.domain.query.ClientQueryRepository;
import com.veamospues.cqrs.command.CommandExecutor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class CreateClientCommandExecutor implements CommandExecutor<CreateClientCommand> {
  ClientAggregateRepository aggregateRepository;
  ClientQueryRepository clientQueryRepository;

  @Override
  public void execute(CreateClientCommand command) {
    assertThatClientDoesNotExist(command.getAggregateId());
    assertThatClientNameIsNotDuplicated(command.getName());

    createClient(command);
  }

  private void assertThatClientDoesNotExist(UUID clientId) {
    if (clientQueryRepository.findById(clientId).isPresent()) {
      throw new ClientAlreadyExistsException(clientId);
    }
  }

  private void assertThatClientNameIsNotDuplicated(String clientName) {
    final ClientQueryCriteria byCriteria = ClientQueryCriteria.builder().name(clientName).build();

    if (!clientQueryRepository.findAll(byCriteria).isEmpty()) {
      throw new DuplicateClientNameException(clientName);
    }
  }

  private void createClient(CreateClientCommand command) {
    ClientAggregate client = new ClientAggregate(command.getAggregateId(), command.getName());
    aggregateRepository.save(client, command.getAggregateVersion());
  }
}
