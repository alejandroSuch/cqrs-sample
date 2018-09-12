package com.veamospues.clientmanagement.domain.event;

import com.veamospues.cqrs.event.Event;

import java.util.UUID;

public abstract class ClientEvent extends Event {
  public ClientEvent() {
    super();
  }

  public ClientEvent(UUID aggregateId) {
    super(aggregateId);
  }
}
