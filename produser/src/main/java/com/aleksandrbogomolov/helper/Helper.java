package com.aleksandrbogomolov.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import org.apache.log4j.Logger;
import twitter4j.Status;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Optional;

/**
 * A helper class used to publish data to a {@link io.vertx.core.eventbus.EventBus} and process
 * {@link URI) to obtain keywords for filtering a stream.
 */
public class Helper extends AbstractVerticle {

  private static final Logger LOGGER = Logger.getLogger(Helper.class);

  /**
   * @param status a data interface representing one single status of a user.
   */
  public void publish(Status status) {
    final ObjectMapper mapper = new ObjectMapper();
    try {
      String msg = mapper.writeValueAsString(status);
      vertx.eventBus().publish("stream", msg);
    } catch (JsonProcessingException e) {
      LOGGER.error("Couldn't create JSON from Twitter status.");
      throw new RuntimeException(e.getCause() + "\n" + e.getMessage());
    }
  }

  /**
   * Gets the {@link URI}, processes the data of the {@code filter} parameter, and returns it as an
   * array of strings, if the parameter is empty, it returns the default array of strings.
   *
   * @param rawUri {@link URI} corresponding to the the HTTP request
   * @return array of keywords.
   */
  public static String[] prepareFilters(String rawUri) {
    final String[] DEFAULT_FILTERS = {"#java", "#scala", "#kotlin", "#groovy", "#haskell"};
    String filtersParam;
    String rawFilters;
    try {
      filtersParam = URLDecoder.decode(rawUri, "UTF-8").split("&")[1];
      rawFilters = filtersParam.split("=")[1];
    } catch (UnsupportedEncodingException e) {
      LOGGER.error("Unsupported encoding.");
      throw new RuntimeException(e.getCause() + "\n" + e.getMessage());
    }
    return Optional.of(rawFilters.split(",")).orElse(DEFAULT_FILTERS);
  }
}
