package com.aleksandrbogomolov;

import com.aleksandrbogomolov.configuration.SparkConfiguration;
import com.aleksandrbogomolov.helper.Publisher;
import com.aleksandrbogomolov.stream.FilterStream;
import io.vertx.core.AbstractVerticle;

public class Server extends AbstractVerticle {

  private static final String[] FILTERS = {"#java", "#scala", "#groovy", "#kotlin"};

  private final SparkConfiguration configuration = new SparkConfiguration();

  private FilterStream stream;

  private final Publisher publisher = new Publisher();

  @Override
  public void start() throws Exception {
    vertx.deployVerticle(publisher);
    vertx.createHttpServer().requestHandler(event -> {
      if (event.path().contains("start")) {
        stream = new FilterStream(FILTERS, publisher, configuration);
        new Thread(stream).start();
      } else if (event.path().contains("stop")) {
        stream.stopStream();
      }
    }).listen(8888);
  }
}
