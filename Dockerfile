FROM eclipse-temurin:21-jdk
RUN apt-get update
RUN apt-get install -y maven
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
CMD ["java", "-jar", "target/ReadMyPDF-0.0.1-SNAPSHOT.jar"]