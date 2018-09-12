package com.veamospues.clientmanagement.stream;

import com.veamospues.clientmanagement.domain.event.ClientEvent;
import com.veamospues.cqrs.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

@Log
@Component
@AllArgsConstructor
public class ClientEventBus<E extends ClientEvent> extends EventBus<E> {
  private static final String CLASS_NAME_HEADER = "X-Class-Name";
  private static final String PARTITION_KEY_HEADER = "X-Partition-Key";

  private Streams streams;

  private final Map<String, List<Function<E, Void>>> callbacks = new HashMap<>();

  @Override
  public void emit(E event) {
    streams.clientEventStreamOut().send(message(event));
  }

  @Override
  public void on(Class<E> event, Function<E, Void> callback) {
    log.severe("Trying to handle an even on command side");
    throw new RuntimeException("Events are not handled in command side");
  }

  private Message<ClientEvent> message(ClientEvent event) {
    return MessageBuilder.withPayload(event)
      .setHeader(CONTENT_TYPE, APPLICATION_JSON)
      .setHeader(CLASS_NAME_HEADER, event.getClass().getName())
      .setHeader(PARTITION_KEY_HEADER, event.getAggregateId())
      .build();
  }
}
