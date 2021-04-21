FROM openjdk:11
#ENTRYPOINT ["dumb-init", "--"]
#RUN gradle build
COPY build/libs/demo-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]