package com.veamospues.clientmanagement.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veamospues.clientmanagement.domain.event.ClientEvent;
import com.veamospues.cqrs.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.String.format;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION_ID;
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

  @StreamListener(Streams.CLIENT_EVENTS_IN)
  public void handleClientEvents(
    @Payload String payload, @Header(CLASS_NAME_HEADER) String className, @Header(RECEIVED_PARTITION_ID) int partition
  ) throws ClassNotFoundException, IOException {
    System.out.println(format("Received %s from partition %s", payload, partition));
    Object event = new ObjectMapper().readValue(payload, Class.forName(className));
    final String key = event.getClass().getName();

    if (!callbacks.containsKey(key)) {
      return;
    }

    callbacks.get(key).forEach(callback -> callback.apply((E) event));
  }

  @Override
  public void emit(E event) {
    log.severe("Trying to emit an event on query side");
    throw new RuntimeException("Events are not sent in query side");
  }

  @Override
  public void on(Class<E> event, Function<E, Void> callback) {
    final String key = event.getName();

    if (!callbacks.containsKey(key)) {
      callbacks.put(key, new ArrayList<>());
    }

    callbacks.get(key).add(callback);
  }
}
