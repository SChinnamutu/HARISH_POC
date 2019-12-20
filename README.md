# HARISH_POC


git clone https://github.com/SChinnamutu/HARISH_POC/new/api-queue-batch-service

cd goomo-user-service

mvn clean install -DskipTests

java -jar target/api-queue-batch-service-0.0.1-SNAPSHOT.jar

localhost:8081/run-batch-job  --> Trigger the Job.

