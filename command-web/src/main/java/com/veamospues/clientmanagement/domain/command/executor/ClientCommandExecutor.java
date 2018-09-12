package com.veamospues.clientmanagement.domain.command.executor;

import com.veamospues.clientmanagement.domain.command.ClientCommand;
import com.veamospues.clientmanagement.domain.command.CreateClientCommand;
import com.veamospues.clientmanagement.domain.command.DeleteClientCommand;
import com.veamospues.clientmanagement.domain.command.UpdateClientCommand;
import com.veamospues.cqrs.command.CommandExecutor;
import com.veamospues.cqrs.command.exception.UnknownCommandException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Log
@AllArgsConstructor
public class ClientCommandExecutor implements CommandExecutor<ClientCommand> {
  CreateClientCommandExecutor createClientCommandExecutor;
  UpdateClientCommandExecutor updateClientCommandExecutor;
  DeleteClientCommandExecutor deleteClientCommandExecutor;

  @Override
  public void execute(ClientCommand command) {
    requireNonNull(command);

    log.info(format("Executing command of type %s", command.getClass().getSimpleName()));

    if (executeCreateCommand(command)) return;

    if (executeUpdateCommand(command)) return;

    if (executeDeleteCommand(command)) return;

    throw new UnknownCommandException(command);
  }

  private boolean executeCreateCommand(ClientCommand command) {
    if (command instanceof CreateClientCommand) {
      createClientCommandExecutor.execute((CreateClientCommand) command);
      return true;
    }
    return false;
  }

  private boolean executeUpdateCommand(ClientCommand command) {
    if (command instanceof UpdateClientCommand) {
      updateClientCommandExecutor.execute((UpdateClientCommand) command);
      return true;
    }
    return false;
  }

  private boolean executeDeleteCommand(ClientCommand command) {
    if (command instanceof DeleteClientCommand) {
      deleteClientCommandExecutor.execute((DeleteClientCommand) command);
      return true;
    }
    return false;
  }
}
