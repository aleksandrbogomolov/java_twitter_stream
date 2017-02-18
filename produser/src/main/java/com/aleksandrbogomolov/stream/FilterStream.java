package com.aleksandrbogomolov.stream;

import com.aleksandrbogomolov.configuration.SparkConfiguration;
import com.aleksandrbogomolov.helper.Publisher;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.twitter.TwitterUtils;
import twitter4j.Status;

public class FilterStream {

  private final String[] filters;

  private SparkConfiguration configuration = new SparkConfiguration();

  public FilterStream(String[] filters) {
    this.filters = filters;
  }

  public void startStream() {
    JavaReceiverInputDStream<Status> stream = TwitterUtils
        .createStream(configuration.streamingContext, configuration.auth, filters);
    stream.foreachRDD(rdd -> {
      rdd.collect().forEach(Publisher::publish);
      return null;
    });
    configuration.streamingContext.start();
    configuration.streamingContext.awaitTermination();
  }
}
