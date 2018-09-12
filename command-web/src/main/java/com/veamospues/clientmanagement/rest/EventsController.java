package com.veamospues.clientmanagement.rest;

import com.veamospues.clientmanagement.domain.event.ClientEvent;
import com.veamospues.cqrs.event.EventStore;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController("/events")
public class EventsController {
  private EventStore<ClientEvent> eventStore;

  @GetMapping
  public List<ClientEvent> all() {
    return eventStore.getAllEvents();
  }
}
