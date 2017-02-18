package com.aleksandrbogomolov;


import com.aleksandrbogomolov.stream.FilterStream;

public class Server {

  private Server() {}

  public static void main(String[] args) {
    FilterStream stream = new FilterStream(new String[]{"#java", "#scala", "#groovy"});
    stream.startStream();
  }
}
