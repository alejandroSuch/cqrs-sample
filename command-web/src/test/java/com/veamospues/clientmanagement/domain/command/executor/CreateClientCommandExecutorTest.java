package com.veamospues.clientmanagement.domain.command.executor;

import com.veamospues.clientmanagement.domain.aggregate.ClientAggregate;
import com.veamospues.clientmanagement.domain.aggregate.ClientAggregateRepository;
import com.veamospues.clientmanagement.domain.command.CreateClientCommand;
import com.veamospues.clientmanagement.domain.command.exception.ClientAlreadyExistsException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateClientCommandExecutorTest {
  private static final Client A_CLIENT = new Client();
  private static final String CLIENT_NAME = "A name";
  private static final CreateClientCommand CREATE_CLIENT_COMMAND = new CreateClientCommand(UUID.randomUUID(), CLIENT_NAME);
  private static final long VERSION_ZERO = 0L;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private ClientAggregateRepository aggregateRepository;

  @Mock
  private ClientQueryRepository clientQueryRepository;

  private CreateClientCommandExecutor executor;

  @Before
  public void setup() {
    executor = new CreateClientCommandExecutor(aggregateRepository, clientQueryRepository);

    doNothing()
      .when(aggregateRepository)
      .save(any(ClientAggregate.class), anyLong());
  }

  @Test
  public void throws_exception_on_existing_client() {
    when(clientQueryRepository.findById(any(UUID.class))).thenReturn(Optional.of(A_CLIENT));

    thrown.expect(ClientAlreadyExistsException.class);

    executor.execute(CREATE_CLIENT_COMMAND);
  }

  @Test
  public void throws_exception_on_duplicate_name() {
    when(clientQueryRepository.findById(any(UUID.class))).thenReturn(empty());
    when(clientQueryRepository.findAll(any(ClientQueryCriteria.class))).thenReturn(singletonList(A_CLIENT));

    thrown.expect(DuplicateClientNameException.class);

    executor.execute(CREATE_CLIENT_COMMAND);
  }

  @Test
  public void ok_when_existing_client_and_no_duplicate_name() {
    when(clientQueryRepository.findById(any(UUID.class))).thenReturn(empty());

    executor.execute(CREATE_CLIENT_COMMAND);

    verify(aggregateRepository).save(any(ClientAggregate.class), eq(VERSION_ZERO));
  }


}