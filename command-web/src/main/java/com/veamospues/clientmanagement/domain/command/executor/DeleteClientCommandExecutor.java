package com.veamospues.clientmanagement.domain.command.executor;

import com.veamospues.clientmanagement.domain.aggregate.ClientAggregate;
import com.veamospues.clientmanagement.domain.aggregate.ClientAggregateRepository;
import com.veamospues.clientmanagement.domain.command.DeleteClientCommand;
import com.veamospues.clientmanagement.domain.command.exception.ClientNotFoundException;
import com.veamospues.clientmanagement.domain.query.ClientQueryRepository;
import com.veamospues.cqrs.command.CommandExecutor;
import lombok.AllArgsConstructor;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
public class DeleteClientCommandExecutor implements CommandExecutor<DeleteClientCommand> {
  private ClientAggregateRepository aggregateRepository;
  private ClientQueryRepository clientQueryRepository;

  @Override
  public void execute(DeleteClientCommand command) {
    assertThatClientExists(command.getAggregateId());

    deleteClient(command);
  }

  private void assertThatClientExists(UUID clientId) {
    if (!clientQueryRepository.findById(clientId).isPresent()) {
      throw new ClientNotFoundException(clientId);
    }
  }

  private void deleteClient(DeleteClientCommand command) {
    ClientAggregate client = aggregateRepository.getById(ClientAggregate.class, command.getAggregateId());
    client.delete();
    aggregateRepository.save(client, command.getAggregateVersion());
  }
}
