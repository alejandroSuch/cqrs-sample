package com.veamospues.clientmanagement.domain.aggregate;

import com.veamospues.clientmanagement.domain.event.ClientCreatedEvent;
import com.veamospues.clientmanagement.domain.event.ClientDeletedEvent;
import com.veamospues.clientmanagement.domain.event.ClientEvent;
import com.veamospues.clientmanagement.domain.event.ClientUpdatedEvent;
import com.veamospues.cqrs.aggregate.AggregateRoot;
import lombok.Data;

import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Data
public class ClientAggregate extends AggregateRoot<ClientEvent> {
  private String name;
  private Boolean deleted;

  public ClientAggregate() {
  }

  public ClientAggregate(UUID id, String name) {
    applyChange(new ClientCreatedEvent(id, name));
  }

  public void changeName(String name) {
    if (!name.equals(this.getName())) {
      applyChange(new ClientUpdatedEvent(getId(), name));
    }
  }

  public void delete() {
    applyChange(new ClientDeletedEvent());
  }

  protected void handle(ClientEvent event) {
    if (handleClientCreatedEvent(event)) return;

    if (handleClientUpdatedEvent(event)) return;

    if (handleClientDeletedEvent(event)) return;
  }

  private boolean handleClientCreatedEvent(ClientEvent event) {
    if (event instanceof ClientCreatedEvent) {
      setId(event.getAggregateId());
      setName(((ClientCreatedEvent) event).getName());
      setDeleted(FALSE);
      return true;
    }
    return false;
  }

  private boolean handleClientUpdatedEvent(ClientEvent event) {
    if (event instanceof ClientUpdatedEvent) {
      setName(((ClientUpdatedEvent) event).getName());
      return true;
    }
    return false;
  }

  private boolean handleClientDeletedEvent(ClientEvent event) {
    if (event instanceof ClientDeletedEvent) {
      setDeleted(TRUE);
      return true;
    }
    return false;
  }
}
