package com.veamospues.clientmanagement.domain.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateClientCommand extends ClientCommand {
  private static final long VERSION = -1L;

  String name;

  public CreateClientCommand(UUID clientId, String name) {
    super(clientId, VERSION);
    this.name = name;
  }
}
