package com.veamospues.clientmanagement.event.handler;

import com.veamospues.clientmanagement.domain.event.ClientCreatedEvent;
import com.veamospues.clientmanagement.domain.event.ClientDeletedEvent;
import com.veamospues.clientmanagement.domain.event.ClientUpdatedEvent;
import com.veamospues.clientmanagement.stream.ClientEventBus;
import com.veamospues.cqrs.event.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.asList;

@Log
@Component
@AllArgsConstructor
public class ClientEventHandler implements CommandLineRunner {
  private ClientEventBus eventBus;

  private ClientCreatedEventHandler clientCreatedEventHandler;
  private ClientUpdatedEventHandler clientUpdatedEventHandler;
  private ClientDeletedEventHandler clientDeletedEventHandler;

  private ApplicationContext context;

  @Override
  public void run(String... args) throws Exception {

    final Map<String, EventHandler> eventHandlers = context.getBeansOfType(EventHandler.class);

    /* eventHandlers.forEach((s, eventHandler) -> {
      final Type[] genericInterfaces = eventHandler.getClass().getGenericInterfaces();

      asList(genericInterfaces)
        .stream()
        .flatMap(type -> asList(((ParameterizedType) type).getActualTypeArguments()).stream())
        .findFirst();
      ((ParameterizedType) genericInterfaces[0]).getActualTypeArguments();
    }); */

    //eventHandlers.get("clientCreatedEventHandler").getClass().getGenericInfo().getSuperInterfaces()[0].getTypeName()


    eventBus.on(ClientCreatedEvent.class, event -> {
      log.info(String.format("Got event of type %s", event.getClass().getSimpleName()));
      clientCreatedEventHandler.handle((ClientCreatedEvent) event);
      return null;
    });

    eventBus.on(ClientUpdatedEvent.class, event -> {
      log.info(String.format("Got event of type %s", event.getClass().getSimpleName()));
      clientUpdatedEventHandler.handle((ClientUpdatedEvent) event);
      return null;
    });

    eventBus.on(ClientDeletedEvent.class, event -> {
      log.info(String.format("Got event of type %s", event.getClass().getSimpleName()));
      clientDeletedEventHandler.handle((ClientDeletedEvent) event);
      return null;
    });

  }
}
