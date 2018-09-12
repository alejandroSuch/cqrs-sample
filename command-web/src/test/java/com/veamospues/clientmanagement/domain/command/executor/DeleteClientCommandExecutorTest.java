package com.veamospues.clientmanagement.domain.command.executor;

import com.veamospues.clientmanagement.domain.aggregate.ClientAggregate;
import com.veamospues.clientmanagement.domain.aggregate.ClientAggregateRepository;
import com.veamospues.clientmanagement.domain.command.DeleteClientCommand;
import com.veamospues.clientmanagement.domain.command.exception.ClientNotFoundException;
import com.veamospues.clientmanagement.domain.query.Client;
import com.veamospues.clientmanagement.domain.query.ClientQueryRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteClientCommandExecutorTest {
  private static final UUID CLIENT_ID = randomUUID();
  private static final long AGGREGATE_VERSION = 1L;
  private static final DeleteClientCommand COMMAND = new DeleteClientCommand(CLIENT_ID, AGGREGATE_VERSION);
  private static final Client A_CLIENT = Client.builder().build();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ClientAggregateRepository aggregateRepository;

  @Mock
  private ClientQueryRepository clientQueryRepository;

  @Mock
  private ClientAggregate aggregate;

  private DeleteClientCommandExecutor executor;

  @Before
  public void setup() {
    executor = new DeleteClientCommandExecutor(aggregateRepository, clientQueryRepository);
  }

  @Test
  public void throws_exception_when_client_does_not_exist() {
    when(clientQueryRepository.findById(CLIENT_ID)).thenReturn(empty());

    thrown.expect(ClientNotFoundException.class);

    executor.execute(COMMAND);
  }

  @Test
  public void ok_when_client_exists() {
    when(clientQueryRepository.findById(CLIENT_ID)).thenReturn(Optional.of(A_CLIENT));
    when(aggregateRepository.getById(ClientAggregate.class, CLIENT_ID)).thenReturn(aggregate);

    executor.execute(COMMAND);

    verify(aggregate).delete();
    verify(aggregateRepository).save(aggregate, AGGREGATE_VERSION);
  }

}