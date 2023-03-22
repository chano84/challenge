FROM gradle:jdk17

WORKDIR /app

COPY build.gradle .
COPY . /app

RUN gradle build

RUN mv ./build/libs/challenge-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","./app.jar"]