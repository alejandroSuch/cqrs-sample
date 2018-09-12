package com.veamospues.clientmanagement.domain.command.executor;

import com.veamospues.clientmanagement.domain.aggregate.ClientAggregate;
import com.veamospues.clientmanagement.domain.aggregate.ClientAggregateRepository;
import com.veamospues.clientmanagement.domain.command.UpdateClientCommand;
import com.veamospues.clientmanagement.domain.command.exception.ClientNotFoundException;
import com.veamospues.clientmanagement.domain.command.exception.DuplicateClientNameException;
import com.veamospues.clientmanagement.domain.query.Client;
import com.veamospues.clientmanagement.domain.query.ClientQueryCriteria;
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

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.UUID.randomUUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateClientCommandExecutorTest {
  private static final UUID CLIENT_ID = randomUUID();
  private static final UUID ANOTHER_CLIENT_ID = randomUUID();
  private static final long AGGREGATE_VERSION = 0L;
  private static final String CLIENT_NAME = "A name";
  private static final String ANOTHER_CLIENT_NAME = "Another name";
  private static final Client A_CLIENT = Client.builder().id(CLIENT_ID).name(CLIENT_NAME).build();
  private static final Client ANOTHER_CLIENT = Client.builder().id(ANOTHER_CLIENT_ID).name(ANOTHER_CLIENT_NAME).build();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ClientAggregateRepository aggregateRepository;

  @Mock
  private ClientQueryRepository clientQueryRepository;

  UpdateClientCommandExecutor executor;

  @Before
  public void setup() {
    executor = new UpdateClientCommandExecutor(aggregateRepository, clientQueryRepository);
  }

  @Test
  public void fails_when_client_does_not_exist() {
    when(clientQueryRepository.findById(CLIENT_ID)).thenReturn(empty());

    thrown.expect(ClientNotFoundException.class);

    executor.execute(new UpdateClientCommand(CLIENT_ID, AGGREGATE_VERSION, CLIENT_NAME));
  }

  @Test
  public void fails_when_client_exists_but_name_belongs_to_another_client() {
    when(clientQueryRepository.findById(CLIENT_ID)).thenReturn(Optional.of(A_CLIENT));
    when(clientQueryRepository.findAll(ClientQueryCriteria.builder().name(CLIENT_NAME).build())).thenReturn(singletonList(ANOTHER_CLIENT));

    thrown.expect(DuplicateClientNameException.class);

    executor.execute(new UpdateClientCommand(CLIENT_ID, AGGREGATE_VERSION, CLIENT_NAME));
  }

  @Test
  public void ok_when_client_exists_and_name_is_not_duplicated() {
    ClientAggregate clientAggregate = new ClientAggregate(CLIENT_ID, CLIENT_NAME);
    clientAggregate.markChangesAsCommited();

    when(aggregateRepository.getById(ClientAggregate.class, CLIENT_ID)).thenReturn(clientAggregate);
    when(clientQueryRepository.findById(CLIENT_ID)).thenReturn(Optional.of(A_CLIENT));
    doNothing()
      .when(aggregateRepository)
      .save(any(ClientAggregate.class), anyLong());

    executor.execute(new UpdateClientCommand(CLIENT_ID, AGGREGATE_VERSION, ANOTHER_CLIENT_NAME));

    verify(aggregateRepository).save(any(ClientAggregate.class), eq(AGGREGATE_VERSION));
  }
}