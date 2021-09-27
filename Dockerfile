FROM openjdk:8-jdk-alpine
VOLUME /tmp
# 注：spring-boot-docker-1.0.jar是需要根据自己项目mvn package生成的压缩包名来写的
ADD spring-boot-docker-1.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]