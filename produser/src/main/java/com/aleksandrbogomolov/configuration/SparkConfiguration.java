package com.aleksandrbogomolov.configuration;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import twitter4j.auth.Authorization;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.conf.ConfigurationContext;

public class SparkConfiguration {

  public final Authorization auth = AuthorizationFactory.getInstance(ConfigurationContext
      .getInstance());

  private final JavaSparkContext context = new JavaSparkContext("local[*]", "twitter_stream");

  public final JavaStreamingContext streamingContext = new JavaStreamingContext(context, new Duration(10));

  public SparkConfiguration() {
    Logger logger = Logger.getRootLogger();
    logger.setLevel(Level.WARN);
  }
}
