FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Create uploads directory
RUN mkdir -p uploads/documents uploads/id uploads/address uploads/income

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/informant-whistleblower-1.0.0.jar"]

