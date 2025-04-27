#FROM eclipse-temurin:23
#LABEL authors="Acdevz"
#ARG JAR_FILE="build/libs/*.jar"
#COPY ${JAR_FILE} catalog-service.jar
#ENTRYPOINT ["java", "-jar", "/catalog-service.jar"]

FROM eclipse-temurin:23 AS builder
WORKDIR workspace
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} catalog-service.jar
RUN java -Djarmode=tools -jar catalog-service.jar extract --layers --launcher

FROM eclipse-temurin:23
WORKDIR workspace
RUN useradd spring
USER spring
COPY --from=builder workspace/catalog-service/dependencies/ ./
COPY --from=builder workspace/catalog-service/spring-boot-loader/ ./
COPY --from=builder workspace/catalog-service/snapshot-dependencies/ ./
COPY --from=builder workspace/catalog-service/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]