Pull the ActiveMQ image and run it first with below command.

docker run --name activemq -p 8161:8161 rmohr/activemq

Access with below url.

http://localhost:8161/


## How to run application

- Clone the application
- Build the application with Maven
  ```
  mvn install
  ```
- Then just start the containers
  ```
  docker-compose up
  ```
