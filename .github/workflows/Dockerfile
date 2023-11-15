FROM gradle:8.3-jdk11
WORKDIR /recode-be
COPY . .
RUN gradle build --no-daemon
CMD ["java", "-jar", "recode-be.jar"] 
