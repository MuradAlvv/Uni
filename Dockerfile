FROM gradle:jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle .
COPY gradle gradle

RUN gradle --no-daemon build || return 0

COPY src src

RUN gradle --no-daemon build

FROM openjdk:17-slim AS production

WORKDIR /app

COPY --from=builder /app/build/libs/unitech-0.0.1-SNAPSHOT.jar unitech.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "unitech.jar"]
