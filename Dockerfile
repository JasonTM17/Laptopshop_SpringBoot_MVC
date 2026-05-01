FROM public.ecr.aws/docker/library/eclipse-temurin:17-jdk AS build

WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -B -DskipTests package

FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre

WORKDIR /app
RUN chown 10001:10001 /app

ENV SPRING_PROFILES_ACTIVE=prod
ENV PORT=8080
ENV JAVA_OPTS=""

COPY --from=build --chown=10001:10001 /workspace/target/*.war /app/laptopshop.war

USER 10001:10001

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/laptopshop.war"]
