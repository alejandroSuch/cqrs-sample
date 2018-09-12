package com.veamospues.clientmanagement.domain.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateClientCommand extends ClientCommand {
  String name;

  public UpdateClientCommand(UUID aggregateId, Long aggregateVersion, String name) {
    super(aggregateId, aggregateVersion);
    this.name = name;
  }
}
