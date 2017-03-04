package com.aleksandrbogomolov.stream;

import com.aleksandrbogomolov.configuration.SparkConfiguration;
import com.aleksandrbogomolov.helper.Helper;
import org.apache.spark.streaming.twitter.TwitterUtils;

public class FilterStream implements Runnable {

  private final String[] filters;

  private final Helper helper;

  private final SparkConfiguration configuration;

  public FilterStream(String[] filters, Helper helper, SparkConfiguration configuration) {
    this.filters = filters;
    this.helper = helper;
    this.configuration = configuration;
  }

  @Override
  public void run() {
    TwitterUtils.createStream(configuration.streamingContext, configuration.auth, filters)
        .foreachRDD(rdd -> {
          rdd.collect().forEach(helper::publish);
          return null;
        });
    configuration.streamingContext.start();
    configuration.streamingContext.awaitTermination();
  }

  public void stopStream() {
    configuration.streamingContext.stop(true);
  }
}
