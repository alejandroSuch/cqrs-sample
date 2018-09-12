package com.veamospues.clientmanagement.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Streams {
  String CLIENT_EVENTS_OUT = "clientEventStreamOut";

  @Output
  MessageChannel clientEventStreamOut();
}
