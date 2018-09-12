package com.veamospues.clientmanagement.persistence.event;

import com.veamospues.clientmanagement.domain.event.ClientCreatedEvent;
import com.veamospues.clientmanagement.domain.event.ClientDeletedEvent;
import com.veamospues.clientmanagement.domain.event.ClientEvent;
import com.veamospues.clientmanagement.domain.event.ClientUpdatedEvent;
import com.veamospues.clientmanagement.persistence.event.document.ClientCreatedEventDocument;
import com.veamospues.clientmanagement.persistence.event.document.ClientDeletedEventDocument;
import com.veamospues.clientmanagement.persistence.event.document.ClientEventDocument;
import com.veamospues.clientmanagement.persistence.event.document.ClientUpdatedEventDocument;
import com.veamospues.clientmanagement.persistence.event.repository.ClientEventMongoRepository;
import com.veamospues.cqrs.event.EventStore;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

@Repository
@AllArgsConstructor
public class ClientEventStore implements EventStore<ClientEvent> {
  ClientEventMongoRepository clientEventMongoRepository;
  ModelMapper modelMapper;

  @Override
  public List<ClientEvent> getAllEvents() {
    return clientEventMongoRepository
      .findAll(new Sort(Sort.Direction.DESC, "date"))
      .stream()
      .map(toClientEvent())
      .collect(toList());
  }

  @Override
  public List<ClientEvent> getEventsFor(UUID aggregateID) {
    return clientEventMongoRepository
      .findAllByAggregateIdOrderByVersionAsc(aggregateID)
      .stream()
      .map(toClientEvent())
      .collect(toList());
  }

  private Function<ClientEventDocument, ClientEvent> toClientEvent() {
    return it -> {
      if (it instanceof ClientCreatedEventDocument) {
        return modelMapper.map(it, ClientCreatedEvent.class);
      }

      if (it instanceof ClientUpdatedEventDocument) {
        return modelMapper.map(it, ClientUpdatedEvent.class);
      }

      if (it instanceof ClientDeletedEventDocument) {
        return modelMapper.map(it, ClientDeletedEvent.class);
      }

      return null;
    };
  }

  @Override
  public void save(ClientEvent event) {
    ClientEventDocument clientEvent = null;

    if (event instanceof ClientCreatedEvent) {
      clientEvent = modelMapper.map(event, ClientCreatedEventDocument.class);
    }

    if (event instanceof ClientUpdatedEvent) {
      clientEvent = modelMapper.map(event, ClientUpdatedEventDocument.class);
    }

    if (event instanceof ClientDeletedEvent) {
      clientEvent = modelMapper.map(event, ClientDeletedEventDocument.class);
    }

    requireNonNull(event);
    clientEventMongoRepository.save(clientEvent);
  }
}
