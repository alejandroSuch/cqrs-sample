package com.veamospues.clientmanagement.config;

import com.veamospues.clientmanagement.domain.aggregate.ClientAggregateRepository;
import com.veamospues.clientmanagement.domain.command.executor.ClientCommandExecutor;
import com.veamospues.clientmanagement.domain.command.executor.CreateClientCommandExecutor;
import com.veamospues.clientmanagement.domain.command.executor.DeleteClientCommandExecutor;
import com.veamospues.clientmanagement.domain.command.executor.UpdateClientCommandExecutor;
import com.veamospues.clientmanagement.domain.event.ClientEvent;
import com.veamospues.clientmanagement.domain.query.ClientQueryRepository;
import com.veamospues.clientmanagement.persistence.event.ClientEventStore;
import com.veamospues.cqrs.event.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorConfiguration {
  @Bean
  public ClientCommandExecutor clientCommandExecutor(
    CreateClientCommandExecutor createClientCommandExecutor,
    UpdateClientCommandExecutor updateClientCommandExecutor,
    DeleteClientCommandExecutor deleteClientCommandExecutor
  ) {
    return new ClientCommandExecutor(
      createClientCommandExecutor, updateClientCommandExecutor, deleteClientCommandExecutor
    );
  }

  @Bean
  public CreateClientCommandExecutor createClientCommandExecutor(
    ClientAggregateRepository clientAggregateRepository, ClientQueryRepository clientQueryRepository
  ) {
    return new CreateClientCommandExecutor(clientAggregateRepository, clientQueryRepository);
  }

  @Bean
  public UpdateClientCommandExecutor updateClientCommandExecutor(
    ClientAggregateRepository clientAggregateRepository, ClientQueryRepository clientQueryRepository
  ) {
    return new UpdateClientCommandExecutor(clientAggregateRepository, clientQueryRepository);
  }

  @Bean
  public DeleteClientCommandExecutor deleteClientCommandExecutor(
    ClientAggregateRepository clientAggregateRepository, ClientQueryRepository clientQueryRepository
  ) {
    return new DeleteClientCommandExecutor(clientAggregateRepository, clientQueryRepository);
  }

  @Bean
  public ClientAggregateRepository clientAggregateRepository(ClientEventStore clientEventStore, EventBus<ClientEvent> clientEventEventBus) {
    return new ClientAggregateRepository(clientEventStore, clientEventEventBus);
  }

  /* @Bean
  public ClientQueryRepository clientQueryRepository(ClientQueryFeignClient clientQueryFeignRepository) {
    return clientQueryFeignRepository;
  }*/
}
