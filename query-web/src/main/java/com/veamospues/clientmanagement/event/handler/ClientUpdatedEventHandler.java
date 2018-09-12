package com.veamospues.clientmanagement.event.handler;

import com.veamospues.clientmanagement.domain.event.ClientUpdatedEvent;
import com.veamospues.clientmanagement.persistence.client.ClientDocument;
import com.veamospues.cqrs.event.EventHandler;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import static com.veamospues.clientmanagement.persistence.client.ClientDocument.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
@AllArgsConstructor
public class ClientUpdatedEventHandler implements EventHandler<ClientUpdatedEvent> {
  MongoOperations operations;

  @Override
  public void handle(ClientUpdatedEvent event) {
    Query query = new Query().addCriteria(
      where(ClientDocument.ID_FIELD).is(event.getAggregateId())
    );

    Update update = new Update()
      .set(ClientDocument.NAME_FIELD, event.getName())
      .set(ClientDocument.LAST_UPDATED_FIELD, event.getDate())
      .set(ClientDocument.LAST_UPDATED_BY_FIELD, event.getUser());

    operations.updateFirst(query, update, ClientDocument.class);

  }
}
