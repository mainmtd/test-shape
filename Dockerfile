FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/test_shape_back-pl*.jar app.jar

EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]