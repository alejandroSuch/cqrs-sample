package com.veamospues.clientmanagement.domain.aggregate;

import com.veamospues.clientmanagement.domain.event.ClientEvent;
import com.veamospues.cqrs.aggregate.AggregateRepository;
import com.veamospues.cqrs.event.EventBus;
import com.veamospues.cqrs.event.EventStore;

public class ClientAggregateRepository extends AggregateRepository<ClientAggregate, ClientEvent> {
  public ClientAggregateRepository(EventStore<ClientEvent> eventStore, EventBus<ClientEvent> eventBus) {
    super(eventStore, eventBus);
  }
}
