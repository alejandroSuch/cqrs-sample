package com.veamospues.clientmanagement.domain.command.exception;

import static java.lang.String.format;

public class DuplicateClientNameException extends RuntimeException {
  public DuplicateClientNameException(String clientName) {
    super(format("Duplicate client name: %s", clientName));
  }
}
