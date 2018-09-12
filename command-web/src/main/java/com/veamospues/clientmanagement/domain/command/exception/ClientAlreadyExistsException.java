package com.veamospues.clientmanagement.domain.command.exception;

import java.util.UUID;

import static java.lang.String.format;

public class ClientAlreadyExistsException extends RuntimeException {
  public ClientAlreadyExistsException(UUID clientId) {
    super(format("Client %s already exists", clientId.toString()));
  }
}
