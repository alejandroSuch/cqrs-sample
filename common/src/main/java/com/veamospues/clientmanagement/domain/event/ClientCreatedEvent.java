package com.veamospues.clientmanagement.domain.event;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientCreatedEvent extends ClientEvent {
  private String name;

  public ClientCreatedEvent() {
  }

  public ClientCreatedEvent(UUID aggregateId, String name) {
    super(aggregateId);
    this.name = name;
  }
}
