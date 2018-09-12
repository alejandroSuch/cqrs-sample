package com.veamospues.clientmanagement.domain.command.exception;

import java.util.UUID;

import static java.lang.String.format;

public class ClientNotFoundException extends RuntimeException {
  public ClientNotFoundException(UUID clientId) {
    super(format("Client %s not found", clientId.toString()));
  }
}
