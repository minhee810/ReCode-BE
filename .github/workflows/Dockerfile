# FROM gradle:8.4-jdk11
# WORKDIR /recode-be
# COPY . .
# RUN gradle build
# CMD ["java", "-jar", "./build/libs/*.jar"] 

FROM gradle:7.4-jdk11-alpine as builder
WORKDIR /build
COPY . /build
RUN gradle build

FROM openjdk:11.0-slim
WORKDIR /recode-be
COPY --from=builder /build/build/libs/*.jar .

EXPOSE 8080
CMD ["java", "-jar", "./build/libs/*.jar"] 
