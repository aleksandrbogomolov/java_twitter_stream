package com.aleksandrbogomolov.stream;

import com.aleksandrbogomolov.configuration.SparkConfiguration;
import com.aleksandrbogomolov.helper.Publisher;
import org.apache.spark.streaming.twitter.TwitterUtils;

public class FilterStream implements Runnable {

  private final String[] filters;

  private final Publisher publisher;

  private final SparkConfiguration configuration;

  public FilterStream(String[] filters, Publisher publisher, SparkConfiguration configuration) {
    this.filters = filters;
    this.publisher = publisher;
    this.configuration = configuration;
  }

  @Override
  public void run() {
    TwitterUtils.createStream(configuration.streamingContext, configuration.auth, filters)
        .foreachRDD(rdd -> {
          rdd.collect().forEach(publisher::publish);
          return null;
        });
    configuration.streamingContext.start();
    configuration.streamingContext.awaitTermination();
  }

  public void stopStream() {
    configuration.streamingContext.stop(true);
  }
}
