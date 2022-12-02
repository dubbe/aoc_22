FROM gradle:alpine as BASE
WORKDIR /java/src
COPY ./java/ .
RUN gradle build

FROM openjdk:17-alpine
COPY --from=BASE /java/src/build/libs/aoc.jar /app.jar
COPY input.txt .
CMD java -jar /app.jar
