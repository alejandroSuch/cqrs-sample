package com.veamospues.clientmanagement.domain.command;

import com.veamospues.cqrs.command.Command;

import java.util.UUID;

public abstract class ClientCommand extends Command {
  public ClientCommand(UUID aggregateId, Long aggregateVersion) {
    super(aggregateId, aggregateVersion);
  }
}
