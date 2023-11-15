FROM openjdk:11-jre-slim
WORKDIR /recode-be
RUN ./gradlew build
CMD ["java", "-jar", "recode-be.jar"] 
