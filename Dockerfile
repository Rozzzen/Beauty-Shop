FROM openjdk:8
ADD target/beauty-shop.jar beauty-shop.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "beauty-shop.jar"]