package com.veamospues.clientmanagement.domain.command;

import java.util.UUID;

public class DeleteClientCommand extends ClientCommand {
  public DeleteClientCommand(UUID aggregateId, Long aggregateVersion) {
    super(aggregateId, aggregateVersion);
  }
}
