package com.aleksandrbogomolov;

import com.aleksandrbogomolov.configuration.SparkConfiguration;
import com.aleksandrbogomolov.helper.Helper;
import com.aleksandrbogomolov.stream.FilterStream;
import io.vertx.core.AbstractVerticle;

public class Server extends AbstractVerticle {

  private final Helper helper = new Helper();

  private FilterStream stream;

  @Override
  public void start() throws Exception {
    vertx.deployVerticle(helper);
    vertx.createHttpServer().requestHandler(event -> {
      if (event.path().contains("start")) {
        String[] filters = Helper.prepareFilters(event.absoluteURI());
        stream = new FilterStream(filters, helper, new SparkConfiguration());
        new Thread(stream).start();
      } else if (event.path().contains("stop")) {
        stream.stopStream();
      }
    }).listen(8888);
  }
}
