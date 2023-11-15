FROM gradle:8.4-jdk11-alpine
WORKDIR /recode-be
COPY . .
RUN gradle build --no-daemon
CMD ["java", "-jar", "recode-be.jar"] 
