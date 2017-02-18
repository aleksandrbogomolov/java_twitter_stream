package com.aleksandrbogomolov;

import com.aleksandrbogomolov.helper.Publisher;
import com.aleksandrbogomolov.stream.FilterStream;
import io.vertx.core.AbstractVerticle;

public class Server extends AbstractVerticle {

  private FilterStream stream;

  private final Publisher publisher = new Publisher();

  @Override
  public void start() throws Exception {
    vertx.deployVerticle(publisher);
    vertx.createHttpServer().requestHandler(event -> {
      if (event.path().contains("start")) {
        stream = new FilterStream(new String[]{"#java", "#scala", "#groovy", "#kotlin"}, publisher);
        stream.startStream();
      } else if (event.path().contains("stop")) {
        stream.stopStream();
      }
    }).listen(8888);
  }
}
