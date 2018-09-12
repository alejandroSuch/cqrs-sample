package com.veamospues.clientmanagement.domain.event;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientUpdatedEvent extends ClientEvent {
  private String name;
  
  public ClientUpdatedEvent() {
  }

  public ClientUpdatedEvent(UUID aggregateId, String name) {
    super(aggregateId);
    this.name = name;
  }
}
