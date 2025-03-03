gcloud app deploy


--


mvn spring-boot:run
 -> http://localhost:8080/

mvn clean package
java -jar target/hello-jsp-app-0.0.1-SNAPSHOT.jar


mvn appengine:run
(not working)


--

tail logs from google cloud:
gcloud app logs tail -s default