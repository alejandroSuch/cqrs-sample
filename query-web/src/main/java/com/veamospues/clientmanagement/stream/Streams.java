package com.veamospues.clientmanagement.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Streams {
  String CLIENT_EVENTS_IN = "clientEventStreamIn";

  @Input
  SubscribableChannel clientEventStreamIn();
}
