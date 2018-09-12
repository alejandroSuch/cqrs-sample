package com.veamospues.clientmanagement.domain.event;

import java.util.UUID;

public class ClientDeletedEvent extends ClientEvent {
  public ClientDeletedEvent() {
  }

  public ClientDeletedEvent(UUID aggregateId) {
    super(aggregateId);
  }
}
