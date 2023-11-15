FROM gradle:8.4-jdk11
WORKDIR /recode-be
COPY . .
RUN gradle build --no-daemon
CMD ["java", "-jar", "./build/libs/*.jar"] 
