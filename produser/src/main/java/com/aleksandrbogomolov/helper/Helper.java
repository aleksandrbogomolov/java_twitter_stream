package com.aleksandrbogomolov.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import org.apache.log4j.Logger;
import twitter4j.Status;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

public class Helper extends AbstractVerticle {

  private static final String[] DEFAULT_FILTERS = {"#java", "#scala", "#kotlin", "#groovy", "#haskell"};

  private final Logger logger = Logger.getLogger(Helper.class);

  private final ObjectMapper mapper = new ObjectMapper();

  public void publish(Status status) {
    try {
      String msg = mapper.writeValueAsString(status);
      vertx.eventBus().publish("stream", msg);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public static String[] prepareFilters(String rawUrl) {
    String filtersParam;
    String rawFilters = null;
    try {
      filtersParam = URLDecoder.decode(rawUrl, "UTF-8").split("&")[1];
      rawFilters = filtersParam.split("=")[1];
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return Optional.ofNullable(rawFilters.split(",")).orElse(DEFAULT_FILTERS);
  }
}
