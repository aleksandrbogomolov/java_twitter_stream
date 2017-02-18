package com.aleksandrbogomolov.stream;

import com.aleksandrbogomolov.configuration.SparkConfiguration;
import com.aleksandrbogomolov.helper.Publisher;
import org.apache.spark.streaming.twitter.TwitterUtils;

public class FilterStream {

  private final String[] filters;

  private final Publisher publisher;

  private final SparkConfiguration configuration = new SparkConfiguration();

  public FilterStream(String[] filters, Publisher publisher) {
    this.filters = filters;
    this.publisher = publisher;
  }

  public void startStream() {
    TwitterUtils.createStream(configuration.streamingContext, configuration.auth, filters)
        .foreachRDD(rdd -> {
          rdd.collect().forEach(publisher::publish);
          return null;
        });
    configuration.streamingContext.start();
    configuration.streamingContext.awaitTermination();
  }

  public void stopStream() {
    configuration.streamingContext.stop();
  }
}
