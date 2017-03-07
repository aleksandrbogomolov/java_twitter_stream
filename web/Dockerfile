FROM java:8-jre

ENV SPARK_FILE web.jar

# Set vertx option
ENV VERTX_OPTIONS "-cluster"

# Set the location of the verticles
ENV SPARK_HOME /

EXPOSE 8080

# Copy your fat jar to the container
COPY ./target/$SPARK_FILE $SPARK_HOME

# Launch the verticle
WORKDIR $SPARK_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar $SPARK_FILE $VERTX_OPTIONS"]
