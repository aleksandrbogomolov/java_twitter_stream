package com.aleksandrbogomolov.stream;

import com.aleksandrbogomolov.configuration.SparkConfiguration;
import com.aleksandrbogomolov.helper.Helper;
import org.apache.spark.streaming.twitter.TwitterUtils;

/**
 * Class containing a list of keywords filters, {@link SparkConfiguration} and {@link Helper}
 * instances. Implements the {@link Runnable} interface. When launched, it creates and starts a
 * filtered stream of tweets. After the shutdown closes the thread and the corresponding context.
 */
public class FilterStream implements Runnable {

  /**
   * Array containing keywords filters.
   */
  private final String[] filters;

  /**
   * Instance of {@link Helper}.
   */
  private final Helper helper;

  /**
   * Instance of {@link SparkConfiguration}.
   */
  private final SparkConfiguration configuration;

  /**
   * Creates full initialized instance of {@link FilterStream}.
   *
   * @param filters       Array containing keywords filters.
   * @param helper        Instance of {@link Helper}.
   * @param configuration Instance of {@link SparkConfiguration}.
   */
  public FilterStream(String[] filters, Helper helper, SparkConfiguration configuration) {
    this.filters = filters;
    this.helper = helper;
    this.configuration = configuration;
  }

  /**
   * Entry point, creates and starts a filtered stream of tweets using class variables {@code
   * filters}, {@code helper} and {@code configuration}.
   */
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

  /**
   * Stops the executions of the streams, also stops the associated SparkContext.
   */
  public void stopStream() {
    configuration.streamingContext.stop(true);
  }
}
