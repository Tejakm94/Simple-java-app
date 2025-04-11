FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy Java source and MySQL JDBC driver
COPY HelloWorldServer.java .
COPY mysql-connector-j-8.3.0.jar .

# Compile with JDBC driver in classpath
RUN javac -cp .:mysql-connector-j-8.3.0.jar HelloWorldServer.java

EXPOSE 8080

CMD ["java", "-cp", ".:mysql-connector-j-8.3.0.jar", "HelloWorldServer"]

