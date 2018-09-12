package com.veamospues.clientmanagement.domain.command.executor;

import com.veamospues.clientmanagement.domain.command.ClientCommand;
import com.veamospues.clientmanagement.domain.command.CreateClientCommand;
import com.veamospues.clientmanagement.domain.command.DeleteClientCommand;
import com.veamospues.clientmanagement.domain.command.UpdateClientCommand;
import com.veamospues.cqrs.command.exception.UnknownCommandException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ClientCommandExecutorTest {
  private static final long AGGREGATE_VERSION = 1L;
  private static final UUID CLIENT_ID = randomUUID();
  private static final String CLIENT_NAME = "A NAME";

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  CreateClientCommandExecutor createClientCommandExecutor;

  @Mock
  UpdateClientCommandExecutor updateClientCommandExecutor;

  @Mock
  DeleteClientCommandExecutor deleteClientCommandExecutor;

  ClientCommandExecutor clientCommandExecutor;

  @Before
  public void setup() {
    clientCommandExecutor = new ClientCommandExecutor(
      createClientCommandExecutor, updateClientCommandExecutor, deleteClientCommandExecutor
    );
  }

  @Test
  public void throws_null_pointer_exception_on_null_command() {
    ClientCommand command = null;

    thrown.expect(NullPointerException.class);

    clientCommandExecutor.execute(command);
  }

  @Test
  public void throws_unknown_command_exception_on_unknown_command() {
    ClientCommand command = new UnknownClientCommand();

    thrown.expect(UnknownCommandException.class);

    clientCommandExecutor.execute(command);
  }

  @Test
  public void calls_create_client_command_executor() {
    final CreateClientCommand command = new CreateClientCommand(CLIENT_ID, CLIENT_NAME);

    clientCommandExecutor.execute(command);

    verify(createClientCommandExecutor).execute(command);
  }

  @Test
  public void calls_update_client_command_executor() {
    final UpdateClientCommand command = new UpdateClientCommand(CLIENT_ID, AGGREGATE_VERSION, CLIENT_NAME);

    clientCommandExecutor.execute(command);

    verify(updateClientCommandExecutor).execute(command);
  }

  @Test
  public void calls_delete_client_command_executor() {
    final DeleteClientCommand command = new DeleteClientCommand(CLIENT_ID, AGGREGATE_VERSION);

    clientCommandExecutor.execute(command);

    verify(deleteClientCommandExecutor).execute(command);
  }

  private class UnknownClientCommand extends ClientCommand {

    UnknownClientCommand() {
      super(CLIENT_ID, AGGREGATE_VERSION);
    }
  }

}