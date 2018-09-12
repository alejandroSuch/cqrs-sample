package com.veamospues.clientmanagement.event.handler;

import com.veamospues.clientmanagement.domain.event.ClientCreatedEvent;
import com.veamospues.clientmanagement.persistence.client.ClientDocument;
import com.veamospues.cqrs.event.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientCreatedEventHandler implements EventHandler<ClientCreatedEvent> {
  MongoOperations operations;

  @Override
  public void handle(ClientCreatedEvent event) {
    operations.save(clientDocumentFrom(event));
  }

  private ClientDocument clientDocumentFrom(ClientCreatedEvent event) {
    return ClientDocument
      .builder()
      .id(event.getAggregateId())
      .name(event.getName())
      .dateCreated(event.getDate())
      .createdBy(event.getUser())
      .lastUpdated(event.getDate())
      .updatedBy(event.getUser())
      .build();
  }
}
