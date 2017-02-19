package com.aleksandrbogomolov;

import com.aleksandrbogomolov.configuration.SparkConfiguration;
import com.aleksandrbogomolov.helper.Publisher;
import com.aleksandrbogomolov.stream.FilterStream;
import io.vertx.core.AbstractVerticle;

public class Server extends AbstractVerticle {

  private final String[] filters = {"#java", "#scala", "#groovy", "#kotlin"};

  private final SparkConfiguration configuration = new SparkConfiguration();

  private final Publisher publisher = new Publisher();

  private FilterStream stream;

  @Override
  public void start() throws Exception {
    vertx.deployVerticle(publisher);
    vertx.createHttpServer().requestHandler(event -> {
      if (event.path().contains("start")) {
        stream = new FilterStream(filters, publisher, configuration);
        new Thread(stream).start();
      } else if (event.path().contains("stop")) {
        stream.stopStream();
      }
    }).listen(8888);
  }
}
