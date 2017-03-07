#Twitter streaming

This is a simple project using Java, Spark, Vertx, Docker and the
 architecture of microservices.

This application simply receives a filtered tweet from the twitter stream
 from Spark's TwitterUtils and outputs the received tweets to the HTML page.

Note you need installed Docker and Maven for run this app.

To run this application from terminal, you need to move it to the root "produser"
 folder and execute: 
 
 __mvn clean package -P con,run__
 
 Then in another terminal window repeate this command in the root of
  "web" folder.
  
 If you use Unix, you can also run the program using a __runapp.sh__ script.
 
 Note that this script stops all running Docker containers and can delete some
  images, check before using.
 
 When the application start, go to [http://localhost:8080](http://localhost:8080)
 enter some keywords or hashtags to the input form and press __Start__.
 
 For stop app press __Stop__ button and you back to the start page.