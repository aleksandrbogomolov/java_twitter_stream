package com.aleksandrbogomolov.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import org.apache.log4j.Logger;
import twitter4j.Status;

public class Publisher extends AbstractVerticle {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static final Logger LOGGER = Logger.getLogger(Package.class);

  public static void publish(Status status) {
    try {
      String msg = MAPPER.writeValueAsString(status);
      LOGGER.warn("--- --- --- ---");
      LOGGER.warn(msg);
    } catch (JsonProcessingException e) {
      LOGGER.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
