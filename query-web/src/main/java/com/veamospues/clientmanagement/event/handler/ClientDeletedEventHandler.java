package com.veamospues.clientmanagement.event.handler;

import com.veamospues.clientmanagement.domain.event.ClientDeletedEvent;
import com.veamospues.clientmanagement.persistence.client.ClientDocument;
import com.veamospues.cqrs.event.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
@AllArgsConstructor
public class ClientDeletedEventHandler implements EventHandler<ClientDeletedEvent> {
  MongoOperations operations;

  @Override
  public void handle(ClientDeletedEvent event) {
    Query query = new Query().addCriteria(where(ClientDocument.ID_FIELD).is(event.getAggregateId()));
    operations.remove(query, ClientDocument.class);
  }
}
