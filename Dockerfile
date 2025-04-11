# Use an official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Java source code into the container
COPY /src/HelloWorldServer.java .

# Compile the Java program
RUN javac HelloWorldServer.java

# Expose port 8080
EXPOSE 8080

# Run the Java program
CMD ["java", "HelloWorldServer"]

