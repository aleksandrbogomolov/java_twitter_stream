package com.aleksandrbogomolov;


import com.aleksandrbogomolov.stream.FilterStream;
import io.vertx.core.AbstractVerticle;

public class Server extends AbstractVerticle {

  private Server() {}

  @Override
  public void start() throws Exception {
    super.start();
  }

  public static void main(String[] args) {
    FilterStream stream = new FilterStream(new String[]{"#java", "#scala", "#groovy"});
    stream.startStream();
  }
}
