package com.veamospues.clientmanagement.config;

import com.veamospues.clientmanagement.stream.Streams;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(Streams.class)
public class StreamsConfiguration {
}
