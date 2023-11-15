FROM gradle:8.4-jdk11
WORKDIR /recode-be
COPY . .
RUN gradle build
CMD ["java", "-jar", "./build/libs/*.jar"] 
